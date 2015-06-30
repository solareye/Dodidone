package mobile.solareye.dodidone;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.hudomju.swipe.OnItemClickListener;
import com.hudomju.swipe.SwipeableItemClickListener;
import com.hudomju.swipe.adapter.RecyclerViewAdapter;
import com.melnykov.fab.FloatingActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import mobile.solareye.dodidone.adapters.HeaderAdapter;
import mobile.solareye.dodidone.adapters.MainAdapter;
import mobile.solareye.dodidone.customviews.SwipeToDismissTouchListener;
import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.listeners.SetingCursorListener;


public class MainActivity extends AppCompatActivity {

    private static RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private static HeaderAdapter headerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OnHeaderClickListener, LoaderManager.LoaderCallbacks<Cursor> {

        @Bind(R.id.toolbar)
        Toolbar toolbar;

        FloatingActionButton btnCreate;

        private static final String TAG = "PlaceholderFragment";

        private static final String EVENT_ID_KEY = "EVENT_ID_KEY";

        private final static int EVENTS_LOADER_ID = 0;
        private final static int SELECTED_EVENT_LOADER_ID = 1;

        private FragmentActivity mActivity;

        public PlaceholderFragment() {
        }

        @Override
        public void onAttach(Activity activity) {
            Log.d(TAG, "onAttach");
            super.onAttach(activity);

            mActivity = (FragmentActivity) activity;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_main, container, false);

            ButterKnife.bind(this, view);

            return view;
        }

        @Override
        public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
            Log.d(TAG, "onViewCreated");
            super.onViewCreated(rootView, savedInstanceState);

            btnCreate = (FloatingActionButton) rootView.findViewById(R.id.fab);

            btnCreate.setOnClickListener(fab_click);

            final SlidingPaneLayout layout = (SlidingPaneLayout) rootView.findViewById(R.id.sliding_pane_layout);
            layout.setSliderFadeColor(Color.TRANSPARENT);


            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

            initToolbar();
        }

        void initToolbar() {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            Log.d(TAG, "onActivityCreated");
            super.onActivityCreated(savedInstanceState);

            mAdapter = new MainAdapter(mActivity, mActivity.getSupportFragmentManager());

            headerAdapter = new HeaderAdapter();

            StickyHeadersBuilder stickyHeadersBuilder = new StickyHeadersBuilder();
            stickyHeadersBuilder.setRecyclerView(mRecyclerView);
            stickyHeadersBuilder.setAdapter(mAdapter);

            stickyHeadersBuilder.setStickyHeadersAdapter(headerAdapter);
            stickyHeadersBuilder.setOnHeaderClickListener(this);
            stickyHeadersBuilder.build();

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(stickyHeadersBuilder.build());

            final SwipeToDismissTouchListener touchListener =
                    new SwipeToDismissTouchListener(
                            new RecyclerViewAdapter(mRecyclerView),
                            new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                                @Override
                                public boolean canDismiss(int position) {
                                    /*String freeTime = eventsDataProvider.getItems().get(position).getFreeTime();
                                    if(freeTime != null && !freeTime.isEmpty())
                                        return false;
                                    else*/
                                    return true;
                                }

                                @Override
                                public void onDelete(RecyclerViewAdapter view, int position) {
                                    //eventsDataProvider.getItems().remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }

                                @Override
                                public void onAchieve(RecyclerViewAdapter recyclerView, int position) {
                                    //eventsDataProvider.getItems().remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }
                            });

            mRecyclerView.setOnTouchListener(touchListener);
            mRecyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
            mRecyclerView.addOnItemTouchListener(new SwipeableItemClickListener(mActivity,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            int id = view.getId();
                            if (id == R.id.txt_do) {
                                touchListener.processPendingDismisses();
                            } else if (id == R.id.txt_undo) {
                                touchListener.undoPendingDismiss();
                            } else if (id != R.id.toggle_sound && id != R.id.btn_sound && id != R.id.free_time_tv) {
                                /*DetailDialogFragment ddf = new DetailDialogFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Event", .getItems().get(position));
                                ddf.setArguments(bundle);
                                ddf.show(mActivity.getSupportFragmentManager(), "Dialog");*/

                                Bundle bundle = new Bundle();
                                bundle.putInt("position", position);
                                getLoaderManager().restartLoader(SELECTED_EVENT_LOADER_ID, bundle, PlaceholderFragment.this);
                            }
                        }
                    }));

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(EVENTS_LOADER_ID, null, this);
            //loaderManager.initLoader(CAPITALS_LOADER_ID, null, this);

            mActivity.getContentResolver().registerContentObserver(
                    EventsContract.Events.CONTENT_URI,
                    true,
                    new ContentObserver(new Handler(Looper.getMainLooper())) {
                        @Override
                        public void onChange(final boolean selfChange) {
                            Toast.makeText(mActivity, "CONTENT CHANGED", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }

        @Override
        public void onHeaderClick(View header, long headerId) {

            TextView text = (TextView) header.findViewById(R.id.section_text);
            Toast.makeText(mActivity, "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
        }

        View.OnClickListener fab_click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] startingLocation = new int[2];
                btnCreate.getLocationOnScreen(startingLocation);
                startingLocation[0] += btnCreate.getWidth() / 2;
                CreateActivity.startFromLocation(startingLocation, mActivity);
                mActivity.overridePendingTransition(0, 0);
            }
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader: " + id);

            switch (id) {
                case EVENTS_LOADER_ID:
                    return new CursorLoader(
                            mActivity,
                            EventsContract.Events.CONTENT_URI,
                            null,
                            null,
                            null,
                            EventsContract.Events.EVENT_DATE_START + " ASC"
                    );

                case SELECTED_EVENT_LOADER_ID:

                    int position = args.getInt("position");

                    long event_id = mAdapter.getItemId(position);

                    return new CursorLoader(
                            mActivity,
                            ContentUris.withAppendedId(EventsContract.Events.CONTENT_URI, event_id),
                            null,
                            null,
                            null,
                            null
                    );

                default:
                    throw new IllegalArgumentException("Unknown loader id: " + id);
            }
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

            Log.d(TAG, "onLoadFinished: " + loader.getId());

            switch (loader.getId()) {
                case EVENTS_LOADER_ID:
                    ((SetingCursorListener) mAdapter).onSetCursor(cursor);
                    headerAdapter.onSetCursor(cursor);
                    break;

                case SELECTED_EVENT_LOADER_ID:
                    if (cursor != null) {

                        EventModel event = getEvent(cursor);
                        long id = cursor.getLong(cursor.getColumnIndex(EventsContract.Events._ID));

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Event", event);
                        bundle.putLong("Event_id", id);

                        Message message = new Message();
                        message.setData(bundle);
                        message.what = 2;


                        handler.sendMessage(message);

                    } else {
                        //resetCityEditingForm();
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Unknown loader id: " + loader.getId());
            }

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

            Log.d(TAG, "onLoaderReset: " + loader.getId());

            switch (loader.getId()) {
                case EVENTS_LOADER_ID:
                    ((SetingCursorListener) mAdapter).onSetCursor(null);
                    break;

                case SELECTED_EVENT_LOADER_ID:
                    //resetCityEditingForm();
                    break;

                default:
                    throw new IllegalArgumentException("Unknown loader id: " + loader.getId());
            }

        }

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 2) {
                    DetailDialogFragment ddf = new DetailDialogFragment();
                    Bundle bundle = msg.getData();
                    ddf.setArguments(bundle);
                    ddf.show(mActivity.getSupportFragmentManager(), "Dialog");
                }
            }
        };

        EventModel getEvent(Cursor cursor) {

            long id = cursor.getLong(cursor.getColumnIndex(EventsContract.Events._ID));
            String name = cursor.getString(cursor.getColumnIndex(EventsContract.Events.EVENT_NAME));
            long dateStart = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_DATE_START));
            long dateEnd = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_DATE_END));
            long duration = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_DURATION));
            long reminder = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER));
            String details = cursor.getString(cursor.getColumnIndex(EventsContract.Events.EVENT_DETAILS));

            return new EventModel((int) id, name, dateStart, dateEnd, duration, reminder, details);
        }

    }
}

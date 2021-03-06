package mobile.solareye.dodidone;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import android.widget.Button;
import android.widget.Toast;

import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobile.solareye.dodidone.adapters.HeaderAdapter;
import mobile.solareye.dodidone.adapters.MainAdapter;
import mobile.solareye.dodidone.behaviors.ScrollAwareAppBarLayoutBehavior;
import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.listeners.SetingCursorListener;
import mobile.solareye.dodidone.swipelib.OnItemClickListener;
import mobile.solareye.dodidone.swipelib.RecyclerViewAdapter;
import mobile.solareye.dodidone.swipelib.SwipeToDismissTouchListener;
import mobile.solareye.dodidone.swipelib.SwipeableItemClickListener;
import mobile.solareye.dodidone.util.CalendarDayDisableDecorator;
import mobile.solareye.dodidone.util.DateFormatHelper;


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


    public static class PlaceholderFragment extends Fragment implements OnHeaderClickListener, OnDateChangedListener,
            LoaderManager.LoaderCallbacks<Cursor>, OnMonthChangedListener {

        @Bind(R.id.toolbar)
        Toolbar toolbar;
        @Bind(R.id.calendarView)
        MaterialCalendarView calendarView;
        @Bind(R.id.appbar)
        AppBarLayout appbar;
        @Bind(R.id.main_content)
        CoordinatorLayout main_content;
        @Bind(R.id.monthBtn)
        Button monthBtn;

        private static final String TAG = "PlaceholderFragment";

        private static final String EVENT_ID_KEY = "EVENT_ID_KEY";

        private final static int EVENTS_LOADER_ID = 0;
        private final static int SELECTED_EVENT_LOADER_ID = 1;

        private FragmentActivity mActivity;

        HashMap<Long, Integer> days = new HashMap<>();

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

            View view = inflater.inflate(R.layout.fragment_main_new, container, false);

            ButterKnife.bind(this, view);

            return view;
        }

        @Override
        public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
            Log.d(TAG, "onViewCreated");
            super.onViewCreated(rootView, savedInstanceState);

            /*final SlidingPaneLayout layout = (SlidingPaneLayout) rootView.findViewById(R.id.sliding_pane_layout);
            layout.setSliderFadeColor(Color.TRANSPARENT);*/


            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager

            mRecyclerView.setLayoutManager(/*new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)*/
                                           /*new GridLayoutManager(getActivity(), 2)*/
                    new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

            initToolbar();

            loadBackdrop();

            calendarView.setTopbarVisible(false);
            calendarView.setOnMonthChangedListener(this);
        }

        private final SimpleDateFormat simpleMonthFormat = new SimpleDateFormat("MMMM");

        private void loadBackdrop() {

            /*final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
            Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);*/

            monthBtn.setText(simpleMonthFormat.format(calendarView.getCurrentDate().getDate()));
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

                                    long id = mAdapter.getItemId(position);

                                    mActivity.getContentResolver().delete(EventsContract.Events.CONTENT_URI, EventsContract.Events._ID + " = ?", new String[]{String.valueOf(id)});

                                    //mAdapter.notifyItemRemoved(position);

                                }

                                @Override
                                public void onArchive(RecyclerViewAdapter recyclerView, int position) {

                                    long id = mAdapter.getItemId(position);

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(EventsContract.Events.EVENT_SATISFIED, 1);

                                    mActivity.getContentResolver().update(EventsContract.Events.CONTENT_URI, contentValues, EventsContract.Events._ID + " = ?", new String[]{String.valueOf(id)});

                                    //eventsDataProvider.getItems().remove(position);
                                    //mAdapter.notifyItemRemoved(position);
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

            //TextView text = (TextView) header.findViewById(R.id.section_text);
            Toast.makeText(mActivity, "Click on headerId " + headerId, Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.fab)
        void createNewEvent(View createBtn) {
            int[] startingLocation = new int[2];
            createBtn.getLocationOnScreen(startingLocation);
            startingLocation[0] += createBtn.getWidth() / 2;
            CreateActivity.startFromLocation(startingLocation, mActivity, createBtn);
            mActivity.overridePendingTransition(0, 0);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader: " + id);

            switch (id) {
                case EVENTS_LOADER_ID:
                    return new CursorLoader(
                            mActivity,
                            EventsContract.Events.CONTENT_URI,
                            null,
                            EventsContract.Events.EVENT_DATE_START + " >= ? AND " + EventsContract.Events.EVENT_SATISFIED + " = ?",
                            new String[]{String.valueOf(DateFormatHelper.clearTimeOfDate(Calendar.getInstance().getTimeInMillis())), "0"},
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
                    List<EventModel> dataSet = compareDataList(cursor);
                    ((SetingCursorListener) mAdapter).onSetCursor(dataSet);
                    headerAdapter.onSetCursor(dataSet);

                    initCalendarView();

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

                        //NotificationHelper.createNotification(getActivity(), "content://mobile.solareye.dodidone.provider/events/" + id);

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
            long reminderFirst = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_FIRST));
            long reminderSecond = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_SECOND));
            String details = cursor.getString(cursor.getColumnIndex(EventsContract.Events.EVENT_DETAILS));

            boolean reminderNotify = cursor.getInt(cursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_NOTIFY)) > 0;
            boolean allDay = cursor.getInt(cursor.getColumnIndex(EventsContract.Events.EVENT_ALL_DAY)) > 0;
            int repeat = cursor.getInt(cursor.getColumnIndex(EventsContract.Events.EVENT_REPEAT));
            long repeatUntil = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_REPEAT_UNTIL));

            return new EventModel((int) id, name, dateStart, dateEnd, duration, reminderFirst, reminderSecond, reminderNotify, details, allDay, repeat, repeatUntil);
        }

        void initToolbar() {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        void initCalendarView() {

            calendarView.setOnDateChangedListener(this);

            // Add a decorator to disable prime numbered days
            calendarView.addDecorator(new CalendarDayDisableDecorator(getDays()));

        }

        @Override
        public void onDateChanged(MaterialCalendarView materialCalendarView, @Nullable CalendarDay calendarDay) {

            long time = calendarDay.getDate().getTime();

            int position = days.get(time);

            //((LinearLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
            mRecyclerView.smoothScrollToPosition(position);

        }

        private void setMinDate(long day) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(day));


            calendarView.setSelectedDate(cal.getTime());

            cal.set(Calendar.DAY_OF_MONTH, 1);

            calendarView.setMinimumDate(cal.getTime());
        }

        private void setMaxDate(long day) {


            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(day));
            cal.set(Calendar.DAY_OF_MONTH, 31);


            calendarView.setMaximumDate(cal.getTime());
        }

        public HashMap<Long, Integer> getDays() {
            return days;
        }

        public void setDays(HashMap<Long, Integer> days) {
            this.days = days;
        }

        @OnClick(R.id.monthBtn)
        public void month_onClick() {

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
            ScrollAwareAppBarLayoutBehavior behavior = (ScrollAwareAppBarLayoutBehavior) params.getBehavior();
            if (behavior != null) {

                boolean isAnimOut = behavior.getmIsAnimatingOut();
                float dy = isAnimOut ? 10000 : -10000;

                behavior.onNestedFling(main_content, appbar, null, 0, dy, isAnimOut);

                behavior.setIsScroll(!isAnimOut);

                behavior.setmIsAnimatingOut(!isAnimOut);

                behavior.setIsStop(false);
            }

        }

        @Override
        public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
            String month = simpleMonthFormat.format(calendarDay.getDate());
            monthBtn.setText(month);
        }

        private List<EventModel> compareDataList(Cursor mCursor) {

            List<EventModel> events = new ArrayList<>();

            if (mCursor != null) {

                EventModel event = null;

                HashMap<Long, Integer> days = new HashMap<>();

                while (mCursor.moveToNext()) {


                    long dateStart = mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_DATE_START));

                    if (event != null) {

                        long freeDateStart = dateStart;

                        long dateEnd = event.getDateEnd();

                        Calendar calStart = Calendar.getInstance();
                        Calendar calEnd = Calendar.getInstance();

                        calStart.setTimeInMillis(dateStart);
                        calEnd.setTimeInMillis(dateEnd);

                        int startDay, startMonth, startYear, endDay, endMonth, endYear;

                        startDay = calStart.get(Calendar.DAY_OF_MONTH);
                        startMonth = calStart.get(Calendar.MONTH);
                        startYear = calStart.get(Calendar.YEAR);

                        endDay = calEnd.get(Calendar.DAY_OF_MONTH);
                        endMonth = calEnd.get(Calendar.MONTH);
                        endYear = calEnd.get(Calendar.YEAR);

                        if (startDay != endDay || startMonth != endMonth || startYear != endYear) {
                            calEnd.add(Calendar.DAY_OF_YEAR, 1);
                            freeDateStart = DateFormatHelper.clearTimeOfDate(calEnd.getTimeInMillis());
                        }

                        long difference = freeDateStart - dateEnd;

                        if (difference > 0) {
                            long freeTimeDateStart = event.getDateEnd();

                            String freeTime = DateFormatHelper.correctingFreeTime(difference);

                            event = new EventModel(freeTime, freeTimeDateStart);
                            events.add(event);
                        }
                    }


                    event = new EventModel();

                    event.set_id(mCursor.getInt(mCursor.getColumnIndex(EventsContract.Events._ID)));
                    event.setName(mCursor.getString(mCursor.getColumnIndex(EventsContract.Events.EVENT_NAME)));
                    event.setDateStart(dateStart);
                    event.setDateEnd(mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_DATE_END)));
                    event.setDuration(mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_DURATION)));
                    event.setReminderFirst(mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_FIRST)));
                    event.setReminderSecond(mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_SECOND)));
                    event.setReminderNotify(mCursor.getInt(mCursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_NOTIFY)) > 0);
                    event.setDetails(mCursor.getString(mCursor.getColumnIndex(EventsContract.Events.EVENT_DETAILS)));

                    event.setAllDay(mCursor.getInt(mCursor.getColumnIndex(EventsContract.Events.EVENT_ALL_DAY)) > 0);
                    event.setRepeat(mCursor.getInt(mCursor.getColumnIndex(EventsContract.Events.EVENT_REPEAT)));
                    event.setRepeatUntil(mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_REPEAT_UNTIL)));

                    events.add(event);


                    // shape calendar days

                    dateStart = DateFormatHelper.clearTimeOfDate(dateStart);

                    int position = events.size() - 1;

                    if (!days.containsKey(dateStart) || (days.containsKey(dateStart) && days.get(dateStart) > position))
                        days.put(dateStart, position);

                    if (mCursor.isFirst())
                        setMinDate(dateStart);
                    if (mCursor.isLast())
                        setMaxDate(dateStart);

                }

                setDays(days);

            }
            return events;

        }

        /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @OnClick(R.id.startJobBtn) void startJobService() {

            sendBroadcast(true);

        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @OnClick(R.id.stopJobBtn) void stopAllJobServices() {

            sendBroadcast(false);

        }

        void sendBroadcast(boolean launch) {

            final String BROADCAST_ACTION = mActivity.getString(R.string.notification_launch_receiver);

            Intent intent = new Intent();
            intent.setAction(BROADCAST_ACTION);
            Bundle extras = new Bundle();
            extras.putBoolean("launch", launch);
            intent.putExtras(extras);
            mActivity.sendBroadcast(intent);

        }*/
    }
}

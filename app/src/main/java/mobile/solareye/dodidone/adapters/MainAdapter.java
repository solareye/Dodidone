package mobile.solareye.dodidone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.R;
import mobile.solareye.dodidone.listeners.SetingCursorListener;

/**
 * Created by Aleksander on 2/21/2015.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements SetingCursorListener/*implements View.OnClickListener*/ {

    private Context mContext;
    FragmentManager fragmentManager;
    private final int CARD_TYPE_FULL = 1;
    private final int CARD_TYPE_FREE_TIME = 2;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainAdapter(Context context, FragmentManager fragmentManager) {
        this.mContext = context;
        this.fragmentManager = fragmentManager;

        setHasStableIds(true);
    }

    private static Cursor mCursor;

    @Override
    public void onSetCursor(Cursor cursor) {
        this.mCursor = cursor;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public CardView cardView;
        public TextView freeTimeTV;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);

            freeTimeTV = (TextView) v.findViewById(R.id.free_time_tv);

            cardView = (CardView) v.findViewById(R.id.card_view);
        }

    }

    @Override
    public int getItemViewType(int position) {

        /*String freeTime = mDataset.get(position).getFreeTime();
        if (freeTime != null && !freeTime.isEmpty())
            return CARD_TYPE_FREE_TIME;
        else*/
            return CARD_TYPE_FULL;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = null;

        switch (viewType) {
            case CARD_TYPE_FULL:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_card_view, parent, false);
                break;
            case CARD_TYPE_FREE_TIME:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.free_time, parent, false);
                break;
        }
        return new ViewHolder(v);
    }

    @Override
    public long getItemId(int position) {
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            return mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events._ID));
        }
        return -1;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if(mCursor != null) {
            mCursor.moveToPosition(position);
            String eventName = mCursor.getString(mCursor.getColumnIndex(EventsContract.Events.EVENT_NAME));
            if (eventName != null && !eventName.isEmpty())
                holder.mTextView.setText(eventName);

            /*String freeTime = mDataset.get(position).getFreeTime();
            if (freeTime != null && !freeTime.isEmpty())
                holder.freeTimeTV.setText(freeTime);*/
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        } else {
            return mCursor.getCount();
        }
    }
}
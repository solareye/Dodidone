package mobile.solareye.dodidone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import mobile.solareye.dodidone.R;
import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.listeners.SetingCursorListener;
import mobile.solareye.dodidone.util.DateFormatHelper;

/**
 * Created by Aleksander on 2/23/2015.
 */
public class HeaderAdapter implements StickyHeadersAdapter<HeaderAdapter.ViewHolder>, SetingCursorListener {

    private static SimpleDateFormat dateFormatWeekDay = new SimpleDateFormat(
            "cccc");
    private static SimpleDateFormat dateFormatMonthDay = new SimpleDateFormat(
            "d");

    //private static Cursor mCursor;
    private List<EventModel> events;

    public static HashMap<Long, Integer> headerPosition = new HashMap<>();

    public HeaderAdapter() {
    }

    @Override
    public void onSetCursor(List<EventModel> dataSet) {
        //this.mCursor = cursor;
        events = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {

        //if (mCursor != null) {
           // mCursor.moveToPosition(position);

            //String[] dayConv = convertDate(mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_DATE_START)));
        String[] dayConv = convertDate(events.get(position).getDateStart());


            headerViewHolder.month_day.setText(dayConv[0]);
            headerViewHolder.week_day.setText(dayConv[1]);

        //}
    }

    @Override
    public long getHeaderId(int position) {

        return convertHeaderId(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.week_day)
        TextView week_day;
        @Bind(R.id.month_day)
        TextView month_day;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    private String[] convertDate(long date_start) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date_start);

        String dayOfMonth = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());


        return new String[] {dayOfMonth, day};
    }

    private long convertHeaderId(int position) {

        /*if (mCursor != null) {
            mCursor.moveToPosition(position);

            long headerId = mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_DATE_START));

            return DateFormatHelper.clearTimeOfDate(headerId);
        }*/
        if(events != null && events.size() > 0) {

            long headerId = events.get(position).getDateStart();

            headerId = DateFormatHelper.clearTimeOfDate(headerId);

            headerPosition.put(headerId, position);

            return headerId;
        }
        return 0;


    }
}
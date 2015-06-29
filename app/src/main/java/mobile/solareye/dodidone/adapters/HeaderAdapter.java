package mobile.solareye.dodidone.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter;

import java.text.SimpleDateFormat;

import mobile.solareye.dodidone.R;
import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.listeners.SetingCursorListener;

/**
 * Created by Aleksander on 2/23/2015.
 */
public class HeaderAdapter implements StickyHeadersAdapter<HeaderAdapter.ViewHolder>, SetingCursorListener {

    static SimpleDateFormat dateFormatWithoutYear = new SimpleDateFormat(
            "cccc d MMMM");
    private static Cursor mCursor;

    public HeaderAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder headerViewHolder, int position) {

        if(mCursor != null) {
            mCursor.moveToPosition(position);
            headerViewHolder.title.setText(convertDate(mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_DATE_START))));
        }
    }

    @Override
    public long getHeaderId(int position) {
        //return items.get(position).subSequence(0, 1).hashCode();
        if(mCursor != null) {
            mCursor.moveToPosition(position);
            return mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events.EVENT_DATE_START));
        }
        return 0;
    }

    @Override
    public void onSetCursor(Cursor cursor) {
        this.mCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.section_text);
        }
    }

    private String convertDate(long date_start){
        return dateFormatWithoutYear.format(date_start);
    }
}
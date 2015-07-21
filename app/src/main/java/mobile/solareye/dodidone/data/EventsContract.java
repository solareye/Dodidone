package mobile.solareye.dodidone.data;

import android.net.Uri;

/**
 * Created by Aleksander on 27.06.2015.
 */
public class EventsContract {

    public static final String AUTHORITY = "mobile.solareye.dodidone.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    private EventsContract() {}

    public static final class Events {

        private Events() {}

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/event";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/event";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "events");

        public static final String _ID = "_id";
        public static final String EVENT_NAME = "name";
        public static final String EVENT_DATE_START = "date_start";
        public static final String EVENT_DATE_END = "date_end";
        public static final String EVENT_ALL_DAY = "all_day";
        public static final String EVENT_DURATION = "duration";
        public static final String EVENT_REMINDER_FIRST = "reminder_first";
        public static final String EVENT_REMINDER_SECOND = "reminder_second";
        public static final String EVENT_REMINDER_NOTIFY = "reminder_notify";
        public static final String EVENT_DETAILS = "details";

        public static final String EVENT_REPEAT = "repeat";
        public static final String EVENT_REPEAT_UNTIL = "repeat_until";
    }

}

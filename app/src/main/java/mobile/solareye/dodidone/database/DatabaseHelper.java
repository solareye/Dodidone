package mobile.solareye.dodidone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.data.EventsDataProvider;

/**
 * Created by Aleksander on 2/21/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "Dodidone.db";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Table Name
    public static final String TABLE_EVENTS = "Event";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + " ("
                + EventsContract.Events._ID + " INTEGER PRIMARY KEY, "
                + EventsContract.Events.EVENT_NAME + " TEXT NOT NULL, "
                + EventsContract.Events.EVENT_DATE_START + " INTEGER, "
                + EventsContract.Events.EVENT_DATE_END + " INTEGER, "
                + EventsContract.Events.EVENT_DURATION + " INTEGER, "
                + EventsContract.Events.EVENT_REMINDER + " INTEGER, "
                + EventsContract.Events.EVENT_REMINDER_NOTIFY + " INTEGER, "
                + EventsContract.Events.EVENT_DETAILS + " TEXT);";

        db.execSQL(CREATE_EVENTS_TABLE);

        for (EventModel event : EventsDataProvider.events) {

            if (event.getName() == null)
                continue;

            ContentValues contentValues = new ContentValues();
            contentValues.put(EventsContract.Events.EVENT_NAME, event.getName());
            contentValues.put(EventsContract.Events.EVENT_DATE_START, event.getDateStart());
            contentValues.put(EventsContract.Events.EVENT_DATE_END, event.getDateEnd());
            contentValues.put(EventsContract.Events.EVENT_DURATION, event.getDuration());
            contentValues.put(EventsContract.Events.EVENT_REMINDER, event.getReminder());
            contentValues.put(EventsContract.Events.EVENT_DETAILS, event.getDetails());

            db.insert(TABLE_EVENTS, null, contentValues);
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

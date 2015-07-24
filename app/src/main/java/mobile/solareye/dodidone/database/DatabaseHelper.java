package mobile.solareye.dodidone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mobile.solareye.dodidone.data.EventsContract;

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
                + EventsContract.Events.EVENT_ALL_DAY + " INTEGER, "
                + EventsContract.Events.EVENT_DURATION + " INTEGER, "
                + EventsContract.Events.EVENT_REMINDER_FIRST + " INTEGER, "
                + EventsContract.Events.EVENT_REMINDER_SECOND + " INTEGER, "
                + EventsContract.Events.EVENT_REMINDER_NOTIFY + " INTEGER, "
                + EventsContract.Events.EVENT_REPEAT + " INTEGER, "
                + EventsContract.Events.EVENT_REPEAT_UNTIL + " INTEGER, "
                + EventsContract.Events.EVENT_DETAILS + " TEXT, "
                + EventsContract.Events.EVENT_SATISFIED + " INTEGER DEFAULT 0);";

        db.execSQL(CREATE_EVENTS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

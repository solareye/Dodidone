package mobile.solareye.dodidone.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.database.DatabaseHelper;

/**
 * Created by Aleksander on 27.06.2015.
 */
public class EventsContentProvider extends ContentProvider {

    private static final String TAG = "EventsContentProvider";

    private static final int EVENTS = 0;
    private static final int EVENT = 1;

    private DatabaseHelper mDatabaseHelper;
    private final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    @Override
    public boolean onCreate() {

        mDatabaseHelper = new DatabaseHelper(getContext());
        mUriMatcher.addURI(EventsContract.AUTHORITY, "events", EVENTS);
        mUriMatcher.addURI(EventsContract.AUTHORITY, "events/#", EVENT);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: " + uri);

        switch (mUriMatcher.match(uri)) {
            case EVENTS: {
                Log.d(TAG, "events URI match");
                final Cursor cursor = mDatabaseHelper.getReadableDatabase().query(
                        DatabaseHelper.TABLE_EVENTS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                cursor.setNotificationUri(getContext().getContentResolver(), EventsContract.Events.CONTENT_URI);

                return cursor;
            }

            case EVENT: {
                Log.d(TAG, "Event URI match");
                final Cursor cursor = mDatabaseHelper.getReadableDatabase().query(
                        DatabaseHelper.TABLE_EVENTS,
                        projection,
                        EventsContract.Events._ID + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null,
                        null,
                        null
                );
                cursor.moveToFirst();
                return cursor;
            }

            default:
                Log.d(TAG, "no URI match");
                return null;
        }
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: " + uri);

        switch (mUriMatcher.match(uri)) {

            case EVENT:
                return EventsContract.Events.CONTENT_ITEM_TYPE;

            default:
                return EventsContract.Events.CONTENT_TYPE;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d(TAG, "insert: " + uri);

        if (mUriMatcher.match(uri) != EVENTS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        long rowID = mDatabaseHelper.getWritableDatabase().insert(DatabaseHelper.TABLE_EVENTS, null, values);
        Uri resultUri = ContentUris.withAppendedId(EventsContract.Events.CONTENT_URI, rowID);

        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Log.d(TAG, "delete: " + uri);

        final int deletedRow = mDatabaseHelper.getWritableDatabase().delete(DatabaseHelper.TABLE_EVENTS, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return deletedRow;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        Log.d(TAG, "update: " + uri);

        final int affectedRows = mDatabaseHelper.getWritableDatabase().update(DatabaseHelper.TABLE_EVENTS, values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return affectedRows;
    }
}

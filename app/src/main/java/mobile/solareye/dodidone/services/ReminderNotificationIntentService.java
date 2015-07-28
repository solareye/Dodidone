package mobile.solareye.dodidone.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.util.NotificationHelper;

public class ReminderNotificationIntentService extends IntentService {

    private static final String ACTION_REMINDER_NOTIFY = "mobile.solareye.dodidone.services.action.REMINDER.NOTIFY";

    private static final String EXTRA_CONTENT_URI = "content_uri";

    public static void startActionReminderNotify(Context context, String contentUri) {

        EventModel event = getEvent(context, contentUri);

        long reminderFirst = event.getReminderFirst();
        long reminderSecond = event.getReminderSecond();
        int repeat = event.getRepeat();


        AlarmManager manager = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ReminderNotificationIntentService.getReminderNotifyIntent(context, contentUri), PendingIntent.FLAG_CANCEL_CURRENT);

        if(reminderFirst > 0)
            manager.setExact(AlarmManager.RTC_WAKEUP, reminderFirst, pendingIntent);
        if(reminderSecond > 0)
            manager.setExact(AlarmManager.RTC_WAKEUP, reminderSecond, pendingIntent);
        if(repeat > 0) {
            if(reminderFirst > 0)
                manager.setRepeating(AlarmManager.RTC_WAKEUP, reminderFirst, intervalMillis(repeat), pendingIntent);
            if(reminderSecond > 0)
                manager.setRepeating(AlarmManager.RTC_WAKEUP, reminderFirst, intervalMillis(repeat), pendingIntent);
        }
    }

    public static Intent getReminderNotifyIntent(Context context, String contentUri) {

        Intent intent = new Intent(context, ReminderNotificationIntentService.class);
        intent.setAction(ACTION_REMINDER_NOTIFY);
        intent.putExtra(EXTRA_CONTENT_URI, contentUri);

        return intent;

    }

    public ReminderNotificationIntentService() {
        super("ReminderNotificationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_REMINDER_NOTIFY.equals(action)) {
                final String contentUri = intent.getStringExtra("content_uri");
                handleActionReminderNotify(contentUri);
            }
        }
    }

    private void handleActionReminderNotify(String contentUri) {

        NotificationHelper.createNotification(this, contentUri);

    }

    private static long intervalMillis(int repeat) {

        switch (repeat) {
            case 1: return AlarmManager.INTERVAL_DAY;
            case 2: return AlarmManager.INTERVAL_DAY * 7;
            case 3: break;
            case 4: break;
            default: break;
        }

        return 0l;
    }

    private static EventModel getEvent(Context context, String contentUri) {

        Cursor cursor = context.getContentResolver().query(Uri.parse(contentUri), null, null, null, null);

        int eventId = cursor.getInt(cursor.getColumnIndex(EventsContract.Events._ID));
        String eventName = cursor.getString(cursor.getColumnIndex(EventsContract.Events.EVENT_NAME));
        String eventDetails = cursor.getString(cursor.getColumnIndex(EventsContract.Events.EVENT_DETAILS));
        long dateStart = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_DATE_START));
        int repeat = cursor.getInt(cursor.getColumnIndex(EventsContract.Events.EVENT_REPEAT));
        long repeatUntil = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_REPEAT_UNTIL));
        boolean reminderNotify = cursor.getInt(cursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_NOTIFY)) > 0;
        long reminderFirst = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_FIRST));
        long reminderSecond = cursor.getLong(cursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_SECOND));

        return new EventModel(eventId, eventName, dateStart, 0, 0, reminderFirst, reminderSecond, reminderNotify, eventDetails, false, repeat, repeatUntil);
    }

}

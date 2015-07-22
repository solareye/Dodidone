package mobile.solareye.dodidone.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import mobile.solareye.dodidone.MainActivity;
import mobile.solareye.dodidone.R;
import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.data.EventsContract;

/**
 * Created by amelikov on 07/07/15.
 */
public class NotificationHelper {

    public static void createNotification(Context context, String contentUri) {

        EventModel event = getEvent(context, contentUri);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(event.getName())
                .setContentText(context.getResources().getString(R.string.app_name)).setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new Notification.BigTextStyle().bigText(event.getDetails()))
                .setContentIntent(pIntent)
                .addAction(R.drawable.ic_action_delete, context.getResources().getString(R.string.delete), pIntent)
                .addAction(R.drawable.ic_action_done, context.getResources().getString(R.string.archive), pIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(event.get_id(), notification);

    }

    private static EventModel getEvent(Context context, String contentUri) {

        Cursor cursor = context.getContentResolver().query(Uri.parse(contentUri), null, null, null, null);

        int eventId = cursor.getInt(cursor.getColumnIndex(EventsContract.Events._ID));
        String eventName = cursor.getString(cursor.getColumnIndex(EventsContract.Events.EVENT_NAME));
        String eventDetails = cursor.getString(cursor.getColumnIndex(EventsContract.Events.EVENT_DETAILS));

        return new EventModel(eventId, eventName, eventDetails);
    }



}

package mobile.solareye.dodidone.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import mobile.solareye.dodidone.MainActivity;
import mobile.solareye.dodidone.R;

/**
 * Created by amelikov on 07/07/15.
 */
public class NotificationHelper {

    public static void createNotification(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new Notification.BigTextStyle().bigText(context.getResources().getString(R.string.big_text)))
                .setContentIntent(pIntent)
                .addAction(R.mipmap.ic_launcher, "Call", pIntent)
                .addAction(R.mipmap.ic_launcher, "More", pIntent)
                .addAction(R.mipmap.ic_launcher, "And more", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

    }

}

package mobile.solareye.dodidone.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import mobile.solareye.dodidone.services.ReminderNotificationIntentService;

public class NotificationLaunchReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean launch = intent.getBooleanExtra("launch", false);
        String contentUri = intent.getStringExtra("content_uri");

        /*if (launch)
            startJobService(context, contentUri);
        else stopAllJobServices(context);*/

        if (launch)
            startReminderService(context, contentUri);
    }

    void startReminderService(Context context, String contentUri) {

        ReminderNotificationIntentService.startActionReminderNotify(context, contentUri);

    }

    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startJobService(Context context, String contentUri) {

        PersistableBundle extras = new PersistableBundle();
        extras.putString("content_uri", contentUri);

        JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(context, ReminderNotificationJobService.class));

        //builder.setPeriodic(35 * 1000L);
        builder.setExtras(extras);

        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        //jobScheduler.schedule(builder.build());

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void stopAllJobServices(Context context) {
        JobScheduler tm = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
    }*/
}

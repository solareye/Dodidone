package mobile.solareye.dodidone.receivers;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import mobile.solareye.dodidone.services.ReminderNotificationJobService;

public class NotificationLaunchReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean launch = intent.getBooleanExtra("launch", false);

        if (launch)
            startJobService(context);
        else stopAllJobServices(context);


        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startJobService(Context context) {

        JobInfo.Builder builder = new JobInfo.Builder(0, new ComponentName(context, ReminderNotificationJobService.class));

        builder.setPeriodic(35 * 1000L);

        JobScheduler jobScheduler =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        jobScheduler.schedule(builder.build());

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void stopAllJobServices(Context context) {
        JobScheduler tm = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancelAll();
    }
}

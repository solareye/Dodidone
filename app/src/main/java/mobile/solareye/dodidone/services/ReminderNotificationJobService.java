package mobile.solareye.dodidone.services;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import mobile.solareye.dodidone.util.NotificationHelper;

/**
 * Created by amelikov on 06/07/15.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)

public class ReminderNotificationJobService extends JobService {

    String LOG = "Reminder JobService";


    @Override
    public boolean onStartJob(JobParameters params) {

        Log.i(LOG, "Starting Job");

        String contentUri = params.getExtras().getString("content_uri");

        NotificationHelper.createNotification(this, contentUri);

        Log.i(LOG, "Job Started");

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Log.i(LOG, "Job Stoped");

        return false;
    }
}

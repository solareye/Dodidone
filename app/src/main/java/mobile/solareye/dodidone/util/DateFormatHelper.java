package mobile.solareye.dodidone.util;

import java.util.Calendar;

/**
 * Created by amelikov on 02/07/15.
 */
public class DateFormatHelper {

    public static long clearTimeOfDate(long oldMillis) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(oldMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

}

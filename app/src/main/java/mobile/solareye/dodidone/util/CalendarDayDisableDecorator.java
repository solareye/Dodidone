package mobile.solareye.dodidone.util;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashMap;

/**
 * Created by amelikov on 02/07/15.
 */
public class CalendarDayDisableDecorator implements DayViewDecorator {

    private HashMap<Long, Integer> days = new HashMap<>();

    public CalendarDayDisableDecorator(HashMap<Long, Integer> days) {
        this.days = days;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        boolean is = days.containsKey(day.getDate().getTime());
        return !is;
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.setDaysDisabled(true);
    }
}

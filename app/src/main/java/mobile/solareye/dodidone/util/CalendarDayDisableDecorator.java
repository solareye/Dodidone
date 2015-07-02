package mobile.solareye.dodidone.util;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by amelikov on 02/07/15.
 */
public class CalendarDayDisableDecorator implements DayViewDecorator {

    private List<Long> days = new LinkedList<>();

    public CalendarDayDisableDecorator(List<Long> days) {
        this.days = days;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        boolean is = days.contains(day.getDate().getTime());
        return !is;
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.setDaysDisabled(true);
    }
}

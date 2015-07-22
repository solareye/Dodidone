package mobile.solareye.dodidone.data;

import java.io.Serializable;

/**
 * Created by Aleksander on 2/24/2015.
 */
public class EventModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int _id;
    private String name;
    private long dateStart;
    private long dateEnd;
    private long duration;
    private long reminderFirst;
    private long reminderSecond;
    private boolean reminderNotify;
    private String details;
    private String freeTime;
    private boolean allDay;
    private int repeat;
    private long repeatUntil;


    public EventModel() {
        this.reminderNotify = true;
    }

    /*public EventModel(EventModel eventModel) {
        this._id = eventModel.get_id();
        this.name = eventModel.getName();
        this.dateStart = eventModel.getDateStart();
        this.dateEnd = eventModel.getDateEnd();
        this.duration = eventModel.getDuration();
        this.reminderFirst = eventModel.getReminderFirst();
        this.details = eventModel.getDetails();
    }*/

    public EventModel(int _id, String name, long dateStart, long dateEnd, long duration, long reminderFirst,
                      long reminderSecond, boolean reminderNotify, String details, boolean allDay, int repeat, long repeatUntil) {
        this._id = _id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
        this.reminderFirst = reminderFirst;
        this.reminderSecond = reminderSecond;
        this.reminderNotify = reminderNotify;
        this.details = details;
        this.allDay = allDay;
        this.repeat = repeat;
        this.repeatUntil = repeatUntil;
    }

    public EventModel(int _id, String name, String details) {
        this._id = _id;
        this.name = name;
        this.details = details;
    }

    public EventModel(String freeTime, long dateStart) {
        this.freeTime = freeTime;
        this.dateStart = dateStart;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateStart() {
        return dateStart;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getReminderFirst() {
        return reminderFirst;
    }

    public void setReminderFirst(long reminder) {
        this.reminderFirst = reminder;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(String freeTime) {
        this.freeTime = freeTime;
    }

    public long getReminderSecond() {
        return reminderSecond;
    }

    public void setReminderSecond(long reminder_second) {
        this.reminderSecond = reminder_second;
    }

    public boolean isReminderNotify() {
        return reminderNotify;
    }

    public void setReminderNotify(boolean reminderNotify) {
        this.reminderNotify = reminderNotify;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public long getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(long repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public enum REPEAT {
        NONE,
        EVERY_DAY,
        EVERY_WEEK,
        EVERY_MONTH,
        EVERY_YEAR
    }

}

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
    private long reminder;
    private String details;
    private String freeTime;

    public EventModel() { }

    public EventModel(EventModel eventModel){
        this._id = eventModel.get_id();
        this.name = eventModel.getName();
        this.dateStart = eventModel.getDateStart();
        this.dateEnd = eventModel.getDateEnd();
        this.duration = eventModel.getDuration();
        this.reminder = eventModel.getReminder();
        this.details = eventModel.getDetails();
    }

    public EventModel(int _id, String name, long dateStart, long dateEnd, long duration, long reminder, String details){
        this._id = _id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.duration = duration;
        this.reminder = reminder;
        this.details = details;
    }

    public EventModel(String freeTime, long dateStart){
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

    public long getReminder() {
        return reminder;
    }

    public void setReminder(long reminder) {
        this.reminder = reminder;
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
}

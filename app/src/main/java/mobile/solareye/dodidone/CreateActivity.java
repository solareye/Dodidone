package mobile.solareye.dodidone;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import mobile.solareye.dodidone.customviews.RevealBackgroundView;
import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.util.DateFormatHelper;


public class CreateActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener, Spinner.OnItemSelectedListener {

    private String TAG = "CreateActivity";

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private SimpleDateFormat sdf = new SimpleDateFormat("E, MMM d, yyyy");
    private boolean pendingIntroAnimation;

    private EventModel eventModel;
    private Calendar calStart, calEnd, calRepeatUntil;

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;

    @Bind(R.id.event_time_start)
    TextView event_time_start;
    @Bind(R.id.event_time_end)
    TextView event_time_end;
    @Bind(R.id.event_date_start)
    TextView event_date_start;
    @Bind(R.id.event_date_end)
    TextView event_date_end;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content)
    TableLayout content;
    @Bind(R.id.ivLogo)
    ImageView ivLogo;
    @Bind(R.id.event_repeat)
    Spinner spinnerEventRepeat;
    @Bind(R.id.event_repeat_till)
    TextView eventRepeatTill;
    @Bind(R.id.event_name_et)
    EditText eventNameET;
    @Bind(R.id.event_comment_et)
    EditText eventCommentET;
    @Bind(R.id.event_reminder_first)
    TextView event_reminder_first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }

        initToolbar();

        setupRevealBackground(savedInstanceState);

        initData();

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initData() {

        eventModel = new EventModel();

        calStart = Calendar.getInstance();
        calEnd = Calendar.getInstance();
        calRepeatUntil = Calendar.getInstance();

        calStart.add(Calendar.HOUR_OF_DAY, 1);
        calEnd.add(Calendar.HOUR_OF_DAY, 2);

        event_date_start.setText(sdf.format(calStart.getTime()));
        event_date_end.setText(sdf.format(calEnd.getTime()));

        event_time_start.setText(DateFormatHelper.correctingTime(calStart.get(Calendar.HOUR_OF_DAY), calStart.get(Calendar.MINUTE)));
        event_time_end.setText(DateFormatHelper.correctingTime(calEnd.get(Calendar.HOUR_OF_DAY), calEnd.get(Calendar.MINUTE)));
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        //vRevealBackground.setFillPaintColor(0xFF16181A);
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);

        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            //startIntroAnimation();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save:

                save();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static void startFromLocation(int[] startingLocation, Activity startingActivity, View sharedView) {
        Intent intent = new Intent(startingActivity, CreateActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);

        String transitionName = startingActivity.getString(R.string.float_btn_transition);

        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(startingActivity, sharedView, transitionName);

        startingActivity.startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            content.setVisibility(View.VISIBLE);

            initSpinner();

            //eventNameET.requestFocus();

        } else {
            content.setVisibility(View.INVISIBLE);
        }
    }

    public void event_date_onClick(View v) {
        createDatePickerDialog((TextView) v);
    }

    public void event_time_onClick(View v) {
        createTimePickerDialog((TextView) v);
    }

    public void event_repeat_till_onClick(View v) {
        createDatePickerDialog((TextView) v);
    }

    public void event_reminder_onClick(View v) {
        createReminderDialog((TextView) v);
    }

    private void createTimePickerDialog(final TextView view) {

        final Calendar cal = view.getId() == R.id.event_time_start ? calStart : calEnd;

        //final int oldTimeGrowth = view.getId() == R.id.event_time_start ? 60 : 120;

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                int currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(Calendar.MINUTE);//+ oldTimeGrowth;
                int startTime = calStart.get(Calendar.HOUR_OF_DAY) * 60 + calStart.get(Calendar.MINUTE);
                int newTime = hourOfDay * 60 + minute;

                if (newTime < currentTime || (cal == calEnd && newTime < startTime)) {
                    Toast.makeText(CreateActivity.this, R.string.given_incorrect_time, Toast.LENGTH_SHORT).show();
                } else {

                    if(cal == calStart) {
                        calEnd.set(Calendar.HOUR_OF_DAY, hourOfDay + 1);
                        calEnd.set(Calendar.MINUTE, minute);

                        event_time_end.setText(DateFormatHelper.correctingTime(hourOfDay + 1, minute));
                    }

                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    cal.set(Calendar.MINUTE, minute);

                    view.setText(DateFormatHelper.correctingTime(hourOfDay, minute));
                }

            }
        }, hour, minute, true);

        tpd.show();
    }

    private void createDatePickerDialog(final TextView view) {

        final Calendar cal = view.getId() == R.id.event_date_start ? calStart : calEnd;
        int year = cal.get(Calendar.YEAR);
        int monthOfYear = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                cal.set(year, monthOfYear, dayOfMonth);

                String repeatUntilText = view.getId() == R.id.event_repeat_till ? getResources().getString(R.string.event_repeat_till) + "\r\n" : "";

                view.setText(repeatUntilText + sdf.format(new Date(cal.getTimeInMillis())));
            }
        }, year, monthOfYear, dayOfMonth);

        if (view.getId() == R.id.event_repeat_till) {

            calRepeatUntil.setTimeInMillis(calStart.getTimeInMillis());

            switch (eventModel.getRepeat()) {

                case 1:
                    calRepeatUntil.add(Calendar.DAY_OF_YEAR, 1);
                    break;
                case 2:
                    calRepeatUntil.add(Calendar.WEEK_OF_MONTH, 1);
                    break;
                case 3:
                    calRepeatUntil.add(Calendar.MONTH, 1);
                    break;
                case 4:
                    calRepeatUntil.add(Calendar.YEAR, 1);
                    break;
                default:
                    break;

            }

            long minDate = calRepeatUntil.getTimeInMillis();

            DatePicker datePicker = dpd.getDatePicker();

            datePicker.setMinDate(minDate);
        } else if (view.getId() == R.id.event_date_start) {

            long minDate = Calendar.getInstance().getTimeInMillis();

            DatePicker datePicker = dpd.getDatePicker();

            datePicker.setMinDate(minDate);
        } else if (view.getId() == R.id.event_date_end) {

            long minDate = calStart.getTimeInMillis();

            DatePicker datePicker = dpd.getDatePicker();

            datePicker.setMinDate(minDate);
        }

        dpd.show();
    }

    private void createReminderDialog(final TextView view) {


        int checkedItem;
        try {
            checkedItem = view.getTag() == null ? 1 : (int) view.getTag();
        } catch (Exception ex) {
            checkedItem = view.getTag() == null ? 1 : Integer.parseInt((String) view.getTag());
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.reminders, checkedItem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(CreateActivity.this, "which = " + which, Toast.LENGTH_SHORT).show();
                view.setTag(which);
                view.setText(getResources().getStringArray(R.array.reminders)[which]);


                if (view.getId() == R.id.event_reminder_first) {

                    long event_reminder_first_value = getResources().getIntArray(R.array.reminder_values)[which];
                    eventModel.setReminderFirst(event_reminder_first_value);

                    if (event_reminder_first_value > 0)
                        eventModel.setReminderNotify(true);
                    else
                        eventModel.setReminderNotify(false);
                } else
                    eventModel.setReminderSecond(getResources().getIntArray(R.array.reminder_values)[which]);

                dialog.cancel();

            }
        });
        builder.create().show();
    }

    private static final int ANIM_DURATION_TOOLBAR = 300;

    private void startIntroAnimation() {

        int actionbarSize = 0;

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionbarSize = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }


        toolbar.setTranslationY(-actionbarSize);
        ivLogo.setTranslationY(-actionbarSize);

        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        ivLogo.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400)
                .start();
    }

    private void initSpinner() {

        SimpleDateFormat dayNameFormat = new SimpleDateFormat("cccc");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");

        String dayName = dayNameFormat.format(new Date());
        String date = dateFormat.format(new Date());

        String[] dataFirst = new String[]{"нет повторения", "каждый день", "every " + dayName, "каждое " + date + " число", "каждый год"};

        ArrayAdapter<String> adapterFirst = new ArrayAdapter<>(toolbar.getContext(),
                R.layout.support_simple_spinner_dropdown_item, dataFirst);
        adapterFirst.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerEventRepeat.setAdapter(adapterFirst);

        spinnerEventRepeat.setOnItemSelectedListener(this);
    }

    private void save() {

        if(eventNameET.getText().length() > 0) {

            Uri mNewUri = getContentResolver().insert(
                    EventsContract.Events.CONTENT_URI,
                    getContentValues()
            );

            if (mNewUri != null) {

                final String BROADCAST_ACTION = getString(R.string.notification_launch_receiver);

                Intent intent = new Intent();
                intent.setAction(BROADCAST_ACTION);
                Bundle extras = new Bundle();
                extras.putBoolean("launch", true);
                extras.putString("content_uri", mNewUri.toString());
                intent.putExtras(extras);
                sendBroadcast(intent);

                finish();
            }
        } else
            Toast.makeText(this, R.string.empty_event_name_field_error, Toast.LENGTH_SHORT).show();

    }

    private ContentValues getContentValues() {

        ContentValues mNewValues = new ContentValues();

        mNewValues.put(EventsContract.Events.EVENT_NAME, getName());
        mNewValues.put(EventsContract.Events.EVENT_DATE_START, getDateStart());

        if (!eventModel.isAllDay()) {
            mNewValues.put(EventsContract.Events.EVENT_DATE_END, getDateStop());
            mNewValues.put(EventsContract.Events.EVENT_DURATION, getDuration());
        }

        mNewValues.put(EventsContract.Events.EVENT_REMINDER_FIRST, eventModel.getReminderFirst());
        mNewValues.put(EventsContract.Events.EVENT_REMINDER_SECOND, eventModel.getReminderSecond());
        mNewValues.put(EventsContract.Events.EVENT_REMINDER_NOTIFY, eventModel.isReminderNotify());
        mNewValues.put(EventsContract.Events.EVENT_DETAILS, getDetails());

        mNewValues.put(EventsContract.Events.EVENT_ALL_DAY, eventModel.isAllDay());
        mNewValues.put(EventsContract.Events.EVENT_REPEAT, eventModel.getRepeat());
        mNewValues.put(EventsContract.Events.EVENT_REPEAT_UNTIL, eventModel.getRepeatUntil());

        return mNewValues;

    }

    private String getName() {

        return eventNameET.getText().toString();
    }

    private SimpleDateFormat sdf2 = new SimpleDateFormat("E, MMM d, yyyy kk:mm");

    private long getDateStart() {

        Log.d(TAG, "Date Start = " + sdf2.format(new Date(calStart.getTimeInMillis())));

        if (eventModel.isAllDay())
            return DateFormatHelper.clearTimeOfDate(calStart.getTimeInMillis());
        else
            return calStart.getTimeInMillis();
    }

    private long getDateStop() {

        Log.d(TAG, "Date Stop = " + sdf2.format(new Date(calEnd.getTimeInMillis())));

        return calEnd.getTimeInMillis();
    }

    private long getDuration() {

        return getDateStop() - getDateStart();
    }

    private String getDetails() {

        return eventCommentET.getText().toString();
    }

    @OnClick(R.id.fab)
    public void close(View v) {

        this.supportFinishAfterTransition();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        eventModel.setRepeat(position);

        eventRepeatTill.setVisibility(position == 0 ? TextView.GONE : TextView.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnCheckedChanged(R.id.all_day)
    void allDaySwitch(CompoundButton buttonView, final boolean isChecked) {

        event_date_end.setEnabled(!isChecked);
        event_time_start.setEnabled(!isChecked);
        event_time_end.setEnabled(!isChecked);

        eventModel.setAllDay(isChecked);

    }

    /*@OnFocusChange(R.id.event_name_et) void onFoc(View v, boolean hasFocus) {

        if(!hasFocus) {

            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        }
    }*/
}

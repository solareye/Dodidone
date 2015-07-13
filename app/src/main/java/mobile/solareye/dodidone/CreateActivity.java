package mobile.solareye.dodidone;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
import butterknife.OnClick;
import mobile.solareye.dodidone.customviews.RevealBackgroundView;
import mobile.solareye.dodidone.data.EventModel;
import mobile.solareye.dodidone.data.EventsContract;


public class CreateActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener, Spinner.OnItemSelectedListener {

    private String TAG = "CreateActivity";

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private SimpleDateFormat sdf = new SimpleDateFormat("E, MMM d, yyyy");
    private boolean pendingIntroAnimation;

    private MenuItem inboxMenuItem;

    private EventModel eventModel;
    private Calendar calStart, calStop;

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    /*@Bind(R.id.textviewcreate)
    View textviewcreate;*/
    @Bind(R.id.event_time_start)
    TextView event_time_start;
    @Bind(R.id.event_time_stop)
    TextView event_time_stop;
    @Bind(R.id.event_date_start)
    TextView event_date_start;
    @Bind(R.id.event_date_stop)
    TextView event_date_stop;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind (R.id.content)
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
        calStop = Calendar.getInstance();
        calStop.add(Calendar.HOUR_OF_DAY, 1);

        event_date_start.setText(sdf.format(calStart.getTime()));
        event_date_stop.setText(sdf.format(calStop.getTime()));

        event_time_start.setText(calStart.get(Calendar.HOUR_OF_DAY) + ":" + calStart.get(Calendar.MINUTE));
        event_time_stop.setText(calStop.get(Calendar.HOUR_OF_DAY) + ":" + calStart.get(Calendar.MINUTE));
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
        inboxMenuItem = menu.findItem(R.id.action_save);
        //inboxMenuItem.setActionView(R.layout.actionview_menu);

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

            default: return super.onOptionsItemSelected(item);
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

            this.setTheme(R.style.AppTheme);

            initSpinner();

        } else {
            content.setVisibility(View.INVISIBLE);
        }
    }

    public void event_date_onClick(View v) {
        createDatePickerDialog((TextView)v);
    }

    public void event_time_onClick(View v) {
        createTimePickerDialog((TextView) v);
    }

    public void event_repeat_till_onClick(View v) {

    }

    public void event_reminder_onClick(View v) {
        createReminderDialog((TextView)v);
    }

    private void createTimePickerDialog(final TextView view) {

        final Calendar cal = view.getId() == R.id.event_time_start ? calStart : calStop;
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                view.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);
        tpd.show();
    }

    private void createDatePickerDialog(final TextView view) {

        final Calendar cal = view.getId() == R.id.event_date_start ? calStart : calStop;
        int year = cal.get(Calendar.YEAR);
        int monthOfYear = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                //Calendar cal = Calendar.getInstance();
                cal.set(year, monthOfYear, dayOfMonth);
                view.setText(sdf.format(new Date(cal.getTimeInMillis())));
            }
        }, year, monthOfYear, dayOfMonth);
        dpd.show();
    }

    private void createReminderDialog(final TextView view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(R.array.reminders, 1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(CreateActivity.this, "which = " + which, Toast.LENGTH_SHORT).show();
                view.setText("which = " + which);
            }
        });
        builder.create().show();
    }

    private static final int ANIM_DURATION_TOOLBAR = 300;

    private void startIntroAnimation() {

        int actionbarSize = 0;

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionbarSize = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }


        toolbar.setTranslationY(-actionbarSize);
        ivLogo.setTranslationY(-actionbarSize);
        View actionView = inboxMenuItem.getActionView();
        actionView.setTranslationY(-actionbarSize);

        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        ivLogo.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        inboxMenuItem.getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new android.animation.AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .start();
    }

    private void initSpinner() {

        SimpleDateFormat dayNameFormat = new SimpleDateFormat("cccc");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d");

        String dayName = dayNameFormat.format(new Date());
        String date = dateFormat.format(new Date());

        String[] dataFirst = new String[] {"нет повторения", "каждый день", "every " + dayName, "каждое " + date + " число", "каждый год"};

        ArrayAdapter<String> adapterFirst = new ArrayAdapter<>(toolbar.getContext(),
                R.layout.support_simple_spinner_dropdown_item, dataFirst);
        adapterFirst.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerEventRepeat.setAdapter(adapterFirst);

        spinnerEventRepeat.setOnItemSelectedListener(this);
    }

    private void save() {
        
        ContentValues mNewValues = new ContentValues();

        mNewValues.put(EventsContract.Events.EVENT_NAME, getName());
        mNewValues.put(EventsContract.Events.EVENT_DATE_START, getDateStart());
        mNewValues.put(EventsContract.Events.EVENT_DATE_END, getDateStop());
        mNewValues.put(EventsContract.Events.EVENT_DURATION, getDuration());
        /*mNewValues.put(EventsContract.Events.EVENT_REMINDER, null);*/
        mNewValues.put(EventsContract.Events.EVENT_DETAILS, getDetails());

        /*

        Uri mNewUri = getContentResolver().insert(
                EventsContract.Events.CONTENT_URI,   // the user dictionary content URI
                mNewValues                          // the values to insert
        );

        if (mNewUri != null)
            finish();

        */
    }

    private String getName() {

        return eventNameET.getText().toString();
    }

    private SimpleDateFormat sdf2 = new SimpleDateFormat("E, MMM d, yyyy kk:mm");

    private long getDateStart() {

        Log.d(TAG, "Date Start = " + sdf2.format(new Date(calStart.getTimeInMillis())));

        return calStart.getTimeInMillis();
    }

    private long getDateStop() {

        Log.d(TAG, "Date Stop = " + sdf2.format(new Date(calStop.getTimeInMillis())));

        return calStop.getTimeInMillis();
    }

    private long getDuration() {

        return getDateStop() - getDateStart();
    }

    private String getDetails() {

        return eventCommentET.getText().toString();
    }

    @OnClick(R.id.fab) public void close(View v) {

        this.supportFinishAfterTransition();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "SUCCESs", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

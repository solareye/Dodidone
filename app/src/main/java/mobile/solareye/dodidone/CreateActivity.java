package mobile.solareye.dodidone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import mobile.solareye.dodidone.customviews.RevealBackgroundView;


public class CreateActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private SimpleDateFormat sdf = new SimpleDateFormat("E, MMM d, yyyy");
    private boolean pendingIntroAnimation;

    private MenuItem inboxMenuItem;

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    /*@Bind(R.id.textviewcreate)
    View textviewcreate;*/
    @Bind(R.id.event_time_start)
    TextView event_time_start;
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
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void startFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, CreateActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            content.setVisibility(View.VISIBLE);

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


        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                view.setText(hourOfDay + ":" + minute);
            }
        }, hour, minute, true);
        tpd.show();
    }

    private void createDatePickerDialog(final TextView view) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int monthOfYear = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
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

    }
}

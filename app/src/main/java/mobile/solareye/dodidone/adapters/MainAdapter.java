package mobile.solareye.dodidone.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;
import mobile.solareye.dodidone.R;
import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.listeners.SetingCursorListener;

/**
 * Created by Aleksander on 2/21/2015.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements SetingCursorListener {

    private Context mContext;
    FragmentManager fragmentManager;
    private final int CARD_TYPE_FULL = 1;
    private final int CARD_TYPE_FREE_TIME = 2;

    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    private static float endFloat;

    public MainAdapter(Context context, FragmentManager fragmentManager) {
        this.mContext = context;
        this.fragmentManager = fragmentManager;

        setHasStableIds(true);
    }

    private static Cursor mCursor;

    @Override
    public void onSetCursor(Cursor cursor) {
        this.mCursor = cursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        private long id;

        @Bind(R.id.info_text)
        TextView mTextView;

        /*@Bind(R.id.card_view)
        CardView cardView;

        @Bind(R.id.free_time_tv)
        TextView freeTimeTV;

        @Bind(R.id.btn_sound)
        ImageButton soundBtn;*/

        @Bind(R.id.toggle_sound)
        ToggleButton toggleSound;

        public ViewHolder(View view, long id) {
            super(view);
            this.id = id;

            ButterKnife.bind(this, view);
        }

        public void setChecked(boolean checked) {

            toggleSound.setOnCheckedChangeListener(null);

            toggleSound.setChecked(checked);

            toggleSound.setOnCheckedChangeListener(this);

        }

       /* @OnCheckedChanged(R.id.toggle_sound)
        public void click(final boolean checked) {

            if (!isEditToggleState())
                return;

            AnimatorSet animatorSet = new AnimatorSet();


            if (checked) {
                endFloat = -360f;
            } else {
                endFloat = 360f;
            }

            ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(toggleSound, "rotation", 0f, endFloat);
            rotationAnim.setDuration(300);
            rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

            ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(toggleSound, "scaleX", 0.2f, 1f);
            bounceAnimX.setDuration(300);
            bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(toggleSound, "scaleY", 0.2f, 1f);
            bounceAnimY.setDuration(300);
            bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

            bounceAnimY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                    super.onAnimationStart(animation);
                }
            });

            animatorSet.play(rotationAnim);
            animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);



            animatorSet.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                    super.onAnimationEnd(animation);

                    updateEventReminderNotify(checked);
                }
            });

            animatorSet.start();

        }*/

        void updateEventReminderNotify(boolean isNotify) {

            ContentValues mNewValues = new ContentValues();

            mNewValues.put(EventsContract.Events.EVENT_REMINDER_NOTIFY, isNotify ? 1 : 0);


            int mNewUri = mContext.getContentResolver().update(
                    EventsContract.Events.CONTENT_URI,
                    mNewValues,
                    EventsContract.Events._ID + " = ?",
                    new String[] {String.valueOf(id)}
            );

        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {

            AnimatorSet animatorSet = new AnimatorSet();


            if (isChecked) {
                endFloat = -360f;
            } else {
                endFloat = 360f;
            }

            ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(toggleSound, "rotation", 0f, endFloat);
            rotationAnim.setDuration(300);
            rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

            ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(toggleSound, "scaleX", 0.2f, 1f);
            bounceAnimX.setDuration(300);
            bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(toggleSound, "scaleY", 0.2f, 1f);
            bounceAnimY.setDuration(300);
            bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);

            bounceAnimY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                    super.onAnimationStart(animation);
                }
            });

            animatorSet.play(rotationAnim);
            animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);



            animatorSet.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                    super.onAnimationEnd(animation);

                    updateEventReminderNotify(isChecked);
                }
            });

            animatorSet.start();

        }
    }

    @Override
    public int getItemViewType(int position) {

        /*String freeTime = mDataset.get(position).getFreeTime();
        if (freeTime != null && !freeTime.isEmpty())
            return CARD_TYPE_FREE_TIME;
        else*/
        return CARD_TYPE_FULL;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = null;

        switch (viewType) {
            case CARD_TYPE_FULL:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_card_view, parent, false);
                break;
            case CARD_TYPE_FREE_TIME:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.free_time, parent, false);
                break;
        }
        return new ViewHolder(v, -1);
    }

    @Override
    public long getItemId(int position) {
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            return mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events._ID));
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (mCursor != null) {
            mCursor.moveToPosition(position);

            holder.id = getItemId(position);

            String eventName = mCursor.getString(mCursor.getColumnIndex(EventsContract.Events.EVENT_NAME));

            boolean eventReminderNotify = mCursor.getInt(mCursor.getColumnIndex(EventsContract.Events.EVENT_REMINDER_NOTIFY)) == 0 ? false : true;

            if (eventName != null && !eventName.isEmpty())
                holder.mTextView.setText(eventName);
            if (eventReminderNotify)
                holder.setChecked(true);//holder.toggleSound.setChecked(true);
            else
                holder.setChecked(false);//holder.toggleSound.setChecked(false);

            /*String freeTime = mDataset.get(position).getFreeTime();
            if (freeTime != null && !freeTime.isEmpty())
                holder.freeTimeTV.setText(freeTime);*/
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        } else {
            return mCursor.getCount();
        }
    }
}
package mobile.solareye.dodidone.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import mobile.solareye.dodidone.R;
import mobile.solareye.dodidone.data.EventsContract;
import mobile.solareye.dodidone.listeners.SetingCursorListener;

/**
 * Created by Aleksander on 2/21/2015.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements SetingCursorListener/*implements View.OnClickListener*/ {

    private Context mContext;
    FragmentManager fragmentManager;
    private final int CARD_TYPE_FULL = 1;
    private final int CARD_TYPE_FREE_TIME = 2;

    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    private static float endFloat;

    // Provide a suitable constructor (depends on the kind of dataset)
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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

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

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        @OnCheckedChanged(R.id.toggle_sound) public void click(boolean checked) {

            AnimatorSet animatorSet = new AnimatorSet();


            if(checked) {
                endFloat = -360f;
            }
            else {
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

    // Create new views (invoked by the layout manager)
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
        return new ViewHolder(v);
    }

    @Override
    public long getItemId(int position) {
        if (mCursor != null) {
            mCursor.moveToPosition(position);
            return mCursor.getLong(mCursor.getColumnIndex(EventsContract.Events._ID));
        }
        return -1;
    }

    // boolean isActivated = false;

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if(mCursor != null) {
            mCursor.moveToPosition(position);
            String eventName = mCursor.getString(mCursor.getColumnIndex(EventsContract.Events.EVENT_NAME));
            if (eventName != null && !eventName.isEmpty()) {
                holder.mTextView.setText(eventName);


                
                /*holder.soundBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View soundBtn) {

                        AnimatorSet animatorSet = new AnimatorSet();

                        float endFloat;


                        if(isActivated) {
                            endFloat = -360f;
                            isActivated = false;
                        }
                        else {
                            endFloat = 360f;
                            isActivated = true;
                        }

                        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(soundBtn, "rotation", 0f, endFloat);
                        rotationAnim.setStartDelay(150);
                        rotationAnim.setDuration(300);
                        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

                        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(soundBtn, "scaleX", 0.2f, 1f);
                        bounceAnimX.setDuration(300);
                        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

                        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(soundBtn, "scaleY", 0.2f, 1f);
                        bounceAnimY.setDuration(300);
                        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                        bounceAnimY.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                if (isActivated) {
                                    ((ImageView)soundBtn).setImageResource(R.drawable.tuba_active);
                                    soundBtn.setAlpha(1f);
                                } else {
                                    ((ImageView)soundBtn).setImageResource(R.drawable.tuba_inactive);
                                    soundBtn.setAlpha(0.7f);
                                }
                            }
                        });

                        bounceAnimY.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {
                                super.onAnimationStart(animation);
                            }
                        });

                        animatorSet.play(rotationAnim);
                        //if(isActivated)
                        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

                        animatorSet.start();
                        
                    }
                });*/
            }

            /*String freeTime = mDataset.get(position).getFreeTime();
            if (freeTime != null && !freeTime.isEmpty())
                holder.freeTimeTV.setText(freeTime);*/
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        } else {
            return mCursor.getCount();
        }
    }
}
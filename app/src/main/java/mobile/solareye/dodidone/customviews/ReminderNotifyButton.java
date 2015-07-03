package mobile.solareye.dodidone.customviews;

/**
 * Created by amelikov on 03/07/15.
 */
public class ReminderNotifyButton /*extends CompoundButton*/ {
    /*private CharSequence mTextOn;
    private CharSequence mTextOff;

    private Drawable mIndicatorDrawable;

    private static final int NO_ALPHA = 0xFF;
    private float mDisabledAlpha;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReminderNotifyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, com.android.internal.R.styleable.ToggleButton, defStyleAttr, defStyleRes);
        mTextOn = a.getText(com.android.internal.R.styleable.ToggleButton_textOn);
        mTextOff = a.getText(com.android.internal.R.styleable.ToggleButton_textOff);
        mDisabledAlpha = a.getFloat(com.android.internal.R.styleable.ToggleButton_disabledAlpha, 0.5f);
        syncTextState();
        a.recycle();
    }

    public ReminderNotifyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ReminderNotifyButton(Context context, AttributeSet attrs) {
        this(context, attrs, com.android.internal.R.attr.buttonStyleToggle);
    }

    public ReminderNotifyButton(Context context) {
        this(context, null);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        syncTextState();
    }

    private void syncTextState() {
        boolean checked = isChecked();
        if (checked && mTextOn != null) {
            setText(mTextOn);
        } else if (!checked && mTextOff != null) {
            setText(mTextOff);
        }
    }


    public CharSequence getTextOn() {
        return mTextOn;
    }

    public void setTextOn(CharSequence textOn) {
        mTextOn = textOn;
    }

    public CharSequence getTextOff() {
        return mTextOff;
    }

    public void setTextOff(CharSequence textOff) {
        mTextOff = textOff;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        updateReferenceToIndicatorDrawable(getBackground());
    }

    @Override
    public void setBackgroundDrawable(Drawable d) {
        super.setBackgroundDrawable(d);

        updateReferenceToIndicatorDrawable(d);
    }

    private void updateReferenceToIndicatorDrawable(Drawable backgroundDrawable) {
        if (backgroundDrawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) backgroundDrawable;
            mIndicatorDrawable =
                    layerDrawable.findDrawableByLayerId(com.android.internal.R.id.toggle);
        } else {
            mIndicatorDrawable = null;
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (mIndicatorDrawable != null) {
            mIndicatorDrawable.setAlpha(isEnabled() ? NO_ALPHA : (int) (NO_ALPHA * mDisabledAlpha));
        }
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(ToggleButton.class.getName());
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ToggleButton.class.getName());
    }*/
}
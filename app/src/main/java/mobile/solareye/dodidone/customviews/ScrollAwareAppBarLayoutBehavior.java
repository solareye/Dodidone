package mobile.solareye.dodidone.customviews;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by amelikov on 09/07/15.
 */
public class ScrollAwareAppBarLayoutBehavior extends AppBarLayout.Behavior {

    private boolean mIsAnimatingOut;
    private boolean isStop;
    private boolean isScroll;

    public ScrollAwareAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super();

        mIsAnimatingOut = true;
        isStop = false;
        isScroll = true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  AppBarLayout appBar, View target, int dx, int dy, int[] consumed) {

        super.onNestedPreScroll(coordinatorLayout, appBar, target, dx, dy, consumed);

        if (dy > 0) {
            mIsAnimatingOut = true;
            isScroll = true;
            isStop = true;
        } else if (dy < 0) {
            isScroll = false;
        }

    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       AppBarLayout appBar, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, appBar, directTargetChild, target,
                        nestedScrollAxes)) && isScroll;

    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBar,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, appBar, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyUnconsumed > 0) {
            mIsAnimatingOut = true;
        } else if (dyUnconsumed < 0) {
            mIsAnimatingOut = false;
        }

        isStop = true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBar,
                                   View target) {

        super.onStopNestedScroll(coordinatorLayout, appBar, target);

        if (isStop) {

            float velocityY = -10000;

            setIsScroll(true);

            if (mIsAnimatingOut) {
                velocityY = 10000;
                setIsScroll(false);
            }

            onNestedFling(coordinatorLayout, appBar, null, 0, velocityY, mIsAnimatingOut);

            setIsStop(false);

            setmIsAnimatingOut(!mIsAnimatingOut);
        }

    }

    public void setmIsAnimatingOut(boolean mIsAnimatingOut) {
        this.mIsAnimatingOut = mIsAnimatingOut;
    }

    public Boolean getmIsAnimatingOut(){
        return mIsAnimatingOut;
    }

    public void setIsStop(boolean isStop) {
        this.isStop = isStop;
    }

    public Boolean getIsStop(){
        return isStop;
    }

    public void setIsScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public Boolean getIsScroll(){
        return isScroll;
    }

}

package mobile.solareye.dodidone.swipelib;

/**
 * Created by amelikov on 13/07/15.
 */

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

public interface ViewAdapter {

    Context getContext();
    int getWidth();
    int getChildCount();
    void getLocationOnScreen(int[] locations);
    View getChildAt(int index);
    int getChildPosition(View position);
    void requestDisallowInterceptTouchEvent(boolean disallowIntercept);
    void onTouchEvent(MotionEvent e);
    Object makeScrollListener(AbsListView.OnScrollListener listener);

}

package mobile.solareye.dodidone.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import mobile.solareye.dodidone.R;

/**
 * Created by amelikov on 14/07/15.
 */
public class DialView extends View {

    public DialView (Context context) {
        super(context);
        init();
    }
    public DialView (Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public DialView (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init() {
        paint = new Paint();
        paint.setColor(getContext().getResources().getColor(R.color.blue));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        bgpaint = new Paint();
        bgpaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
        bgpaint.setAntiAlias(true);
        bgpaint.setStyle(Paint.Style.FILL);
        rect = new RectF();
    }
    Paint paint;
    Paint bgpaint;
    RectF rect;
    float percentage = 10;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw background circle anyway
        int left = 0;
        int width = getWidth();
        int top = 0;
        rect.set(left, top, left+width, top + width);
        canvas.drawArc(rect, 180, 360, true, bgpaint);
        if(percentage!=0) {
            canvas.drawArc(rect, 180, (int) (3.6 * percentage), true, paint);
        }
    }
    public void setPercentage(float percentage) {
        this.percentage = percentage / 100;
        invalidate();
    }
}
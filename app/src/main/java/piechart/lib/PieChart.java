package piechart.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView.LayoutParams;

import java.util.List;

@SuppressLint("DrawAllocation")
public class PieChart extends View {

	public static final int WAIT = 0;
	public static final int IS_READY_TO_DRAW = 1;
	public static final int IS_DRAW = 2;
	private static final float START_INC = -90;

	private Paint bagPaints = new Paint();
	private Paint linePaints = new Paint();

	private int lineStrokeColor = 0x88FFFFFF;
	private int bagStrokeColor = 0x88FF0000;
	private float lineStrokeWidth = 3.0f;
	private float bagStrokeWidth = 0.0f;
	private boolean antiAlias = true;

	private int width;
	private int height;
	private int gapTop;
	private int gapBottm;
	private int backgroundColor;
	private int gapLeft;
	private int gapRight;
	private int state = WAIT;
	private float start;
	private float sweep;
	private int maxConnection;

	private List<PieDetailsItem> pieDetailsList;

	public PieChart(Context context) {
		super(context);
	}

	public PieChart(Context context, AttributeSet attr) {
		super(context, attr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (state != IS_READY_TO_DRAW) {
			return;
		}
		canvas.drawColor(backgroundColor);
		bagPaints.setAntiAlias(antiAlias);
		bagPaints.setStyle(Paint.Style.FILL);
		bagPaints.setColor(bagStrokeColor);
		bagPaints.setStrokeWidth(bagStrokeWidth);
		linePaints.setAntiAlias(antiAlias);
		linePaints.setColor(lineStrokeColor);
		linePaints.setStrokeWidth(lineStrokeWidth);
		linePaints.setStyle(Paint.Style.STROKE);
		RectF mOvals = new RectF(gapLeft, gapTop, width - gapRight, height
				- gapBottm);
		start = START_INC;
		PieDetailsItem item;
		for (int i = 0; i < pieDetailsList.size(); i++) {
			item = pieDetailsList.get(i);
			bagPaints.setColor(item.color);
			sweep = (float) 360 * ((float) item.count / (float) maxConnection);
			canvas.drawArc(mOvals, start, sweep, true, bagPaints);
			canvas.drawArc(mOvals, start, sweep, true, linePaints);
			start = start + sweep;
		}

		state = IS_DRAW;
	}

	public void setGeometry(int width, int height, int gapleft, int gapright,
			int gaptop, int gapbottom, int overlayid) {

		this.width = width;
		this.height = height;
		this.gapLeft = gapleft;
		this.gapRight = gapright;
		this.gapBottm = gapbottom;
		this.gapTop = gaptop;

	}

	public void setSkinparams(int bgcolor) {
		backgroundColor = bgcolor;
	}

	public void setData(List<PieDetailsItem> data, int maxconnection) {
		pieDetailsList = data;
		maxConnection = maxconnection;
		state = IS_READY_TO_DRAW;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getColorValues(int index) {
		if (pieDetailsList == null) {
			return 0;
		}

		else if (index < 0)
			return ((PieDetailsItem) pieDetailsList.get(0)).color;
		else if (index > pieDetailsList.size())
			return ((PieDetailsItem) pieDetailsList
					.get(pieDetailsList.size() - 1)).color;
		else
			return ((PieDetailsItem) pieDetailsList
					.get(pieDetailsList.size() - 1)).color;

	}

	@SuppressLint("InlinedApi")
	public void expand() {
		final View v = this;
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? LayoutParams.WRAP_CONTENT
						: (int) (targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		a.setDuration((int) (targtetHeight * 3 / v.getContext().getResources()
				.getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public void collapse() {
		final View v = this;
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight
							- (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		a.setDuration((int) (initialHeight * 3 / v.getContext().getResources()
				.getDisplayMetrics().density));
		v.startAnimation(a);
	}

	/**
	 * @return the antiAlias
	 */
	public boolean isAntiAlias() {
		return antiAlias;
	}

	/**
	 * @param antiAlias
	 *            the antiAlias to set
	 */
	public void setAntiAlias(boolean antiAlias) {
		this.antiAlias = antiAlias;
	}

	/**
	 * @return the lineStrokeColor
	 */
	public int getLineStrokeColor() {
		return lineStrokeColor;
	}

	/**
	 * @param lineStrokeColor
	 *            the lineStrokeColor to set
	 */
	public void setLineStrokeColor(int lineStrokeColor) {
		this.lineStrokeColor = lineStrokeColor;
	}

	/**
	 * @return the bagStrokeColor
	 */
	public int getBagStrokeColor() {
		return bagStrokeColor;
	}

	/**
	 * @param bagStrokeColor
	 *            the bagStrokeColor to set
	 */
	public void setBagStrokeColor(int bagStrokeColor) {
		this.bagStrokeColor = bagStrokeColor;
	}

	/**
	 * @return the lineStrokeWidth
	 */
	public float getLineStrokeWidth() {
		return lineStrokeWidth;
	}

	/**
	 * @param lineStrokeWidth
	 *            the lineStrokeWidth to set
	 */
	public void setLineStrokeWidth(float lineStrokeWidth) {
		this.lineStrokeWidth = lineStrokeWidth;
	}

	/**
	 * @return the bagStrokeWidth
	 */
	public float getBagStrokeWidth() {
		return bagStrokeWidth;
	}

	/**
	 * @param bagStrokeWidth
	 *            the bagStrokeWidth to set
	 */
	public void setBagStrokeWidth(float bagStrokeWidth) {
		this.bagStrokeWidth = bagStrokeWidth;
	}

	/**
	 * @return the backgroundColor
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}

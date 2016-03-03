package com.smxcwz.qiongyou.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.smxcwz.qiongyou.R;

public class ChengeColorIconWithText extends View {
	private int mColor = 0xff45c01a;
	private Bitmap mIconBitmap;
	private String mText = "微信";
	private int mTextSize = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Paint mPaint;

	private float mAlpha = 1.0f;

	private Rect mIconRect;
	private Rect mTextBound;
	private Paint mTextPaint;

	public ChengeColorIconWithText(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ChengeColorIconWithText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取自定义属性的值
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ChengeColorIconWithText(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ChengeColorIconWithText);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.ChengeColorIconWithText_icon:
				BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
				mIconBitmap = drawable.getBitmap();
				break;
			case R.styleable.ChengeColorIconWithText_color:
				mColor = a.getColor(attr, 0xff45c01a);

				break;
			case R.styleable.ChengeColorIconWithText_text:
				mText = a.getString(attr);

				break;
			case R.styleable.ChengeColorIconWithText_text_size:
				mTextSize = (int) a.getDimension(attr, TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
								getResources().getDisplayMetrics()));

				break;

			default:
				break;
			}

		}
		a.recycle();
		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff404040);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - mTextBound.height());
		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = (getMeasuredHeight() - mTextBound.height()) / 2 - iconWidth
				/ 2;
		mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth - 10);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
		int alpha = (int) Math.ceil(255 * mAlpha);
		// 内存区准备mBitmap，setAlpha，纯色，xfermode，图标
		setupTargetBitmap(alpha);
		// 绘制原文本 绘制变色文本
		drawSourceText(canvas, alpha);
		drawTargetText(canvas, alpha);
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}

	/**
	 * 绘制变色文本
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha) {
		// TODO Auto-generated method stub
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		int x = mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);

	}

	/**
	 * 绘制原文本
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceText(Canvas canvas, int alpha) {
		// TODO Auto-generated method stub
		mTextPaint.setColor(0Xff808080);
		mTextPaint.setAlpha(255 - alpha);
		int x = mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height();
		canvas.drawText(mText, x, y, mTextPaint);
	}

	/**
	 * 在内存中绘制可变色的Icon
	 * 
	 * @param alpha
	 */
	private void setupTargetBitmap(int alpha) {
		// TODO Auto-generated method stub
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(mIconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);

	}

	/**
	 * 设置透明度
	 * 
	 * @param alpha
	 */
	public  void setIconAlpha(float alpha) {
		this.mAlpha = alpha;
		invalidateView();
	}

	/**
	 * 重绘
	 */
	private void invalidateView() {
		// TODO Auto-generated method stub
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();

		}

	}

}

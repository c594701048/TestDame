package com.smxcwz.qiongyou.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.smxcwz.qiongyou.R;
import com.smxcwz.qiongyou.interfaces.MyOnClickLinsenner;

public class IconAndTextView extends View  {
	private int mColor = 0xff45c01a;
	private Bitmap mIconBitmap;
	private String mText = "微信";
	private int mTextSize = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Rect mIconRect;
	private Rect mTextBound;
	private Paint mTextPaint;

	public IconAndTextView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public IconAndTextView(Context context, AttributeSet attrs) {
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
	public IconAndTextView(Context context, AttributeSet attrs, int defStyle) {
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
		mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth-10);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
		// 内存区准备mBitmap，setAlpha，纯色，xfermode，图标

		setupTargetBitmap();
		// 绘制原文本
		drawSourceText(canvas);
	}

	/**
	 * 绘制原文本
	 * 
	 * @param canvas
	 */
	private void drawSourceText(Canvas canvas) {
		// TODO Auto-generated method stub
		mTextPaint.setColor(0Xff808080);
		int x = mIconRect.left + mIconRect.width() / 2 - mTextBound.width() / 2;
		int y = mIconRect.bottom + mTextBound.height()+8;
		canvas.drawText(mText, x, y, mTextPaint);
	}

	/**
	 * 在内存中绘制Icon
	 */
	private void setupTargetBitmap() {
		// TODO Auto-generated method stub
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);



	}

	public void setIcon(int id) {
		// TODO Auto-generated method stub
		BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(
				id);
		mIconBitmap = drawable.getBitmap();
		invalidateView();

	}

	private void invalidateView() {
		// TODO Auto-generated method stub
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();

		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			myOnClickLinsenner.onClick(IconAndTextView.this);			
			break;
		case MotionEvent.ACTION_UP:
			myOnClickLinsenner.onClick(IconAndTextView.this);
			break;

		default:
			break;
		}

		return true;
	}
	private MyOnClickLinsenner myOnClickLinsenner;
	public void setMyOnClickListener(MyOnClickLinsenner myOnClickLinsenner) {
		this.myOnClickLinsenner= myOnClickLinsenner;
	}

	

}

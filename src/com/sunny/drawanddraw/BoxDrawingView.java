package com.sunny.drawanddraw;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoxDrawingView extends View {
	
	public static final String TAG = "BoxDrawingView";
	
	private Box mCurrentBox;
	private ArrayList<Box> mBoxs = new ArrayList<Box>();
	
	private Paint mBoxPaint;
	private Paint mBackgroundPaint;

	/**
	 * Used when creating the view in code. 
	 * @param context
	 */
	public BoxDrawingView(Context context) {
		super(context, null);
	}
	
	/**
	 * Used when inflating the view from xml. 
	 * @param context
	 * @param attrs
	 */
	public BoxDrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// Paint the boxes a nice semitransparent red. 
		mBoxPaint = new Paint();
		mBoxPaint.setColor(0x22ff0000);
		
		// Paint the background off-white
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(0xfff8efe0);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		PointF curr = new PointF(event.getX(), event.getY());
		Log.i(TAG, "Received event at x=" + curr.x + 
				", y=" + curr.y + ":");
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, " ACTION_DOWN");
			// Reset drawing state
			mCurrentBox = new Box(curr);
			mBoxs.add(mCurrentBox);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, " ACTION_MOVE");
			if (mCurrentBox != null) {
				mCurrentBox.setCurrent(curr);
				// 该方法会强制BoxDrawingView重新绘制自己。这样，用户在屏幕上拖曳时就能实时看到矩形框。
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i(TAG, " ACTION_UP");
			mCurrentBox = null;
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.i(TAG, " ACTION_CANCEL");
			mCurrentBox = null;
			break;
		}
		
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Fill the background. 
		canvas.drawPaint(mBackgroundPaint);
		
		// Draw boxes. 
		for (Box box : mBoxs) {
			float left = Math.min(box.getOrigin().x, box.getCurrent().x);
			float right = Math.max(box.getOrigin().x, box.getCurrent().x);
			float top = Math.min(box.getOrigin().y, box.getCurrent().y);
			float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
			
			canvas.drawRect(left, top, right, bottom, mBoxPaint);
		}
	}
	
	/**
	 * setSaveEnabled (boolean enabled) controls whether the saving of this view's state 
	 * is enabled (that is, whether its onSaveInstanceState() method will be called). 
	 * Note that even if freezing is enabled, the view still must have an id 
	 * assigned to it (via setId(int)) for its state to be saved. 
	 * Set to false to disable state saving, or true (the default) to allow it.
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		// 设备旋转销毁View实例前，保存旋转前的状态。
		Log.i(TAG, "onSaveInstanceState");
		
		Bundle bundle = new Bundle();
		Parcelable state = super.onSaveInstanceState(); // 获取之前保存的状态
		
		bundle.putParcelable("State", state);
		bundle.putParcelableArrayList("Boxs", mBoxs);
		
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// 设备旋转后，恢复旋转前的状态。
		Log.i(TAG, "onRestoreInstanceState");
		
		Bundle bundle = (Bundle) state;
		super.onRestoreInstanceState(bundle.getParcelable("state")); // 首先恢复之前的状态
		
		mBoxs = bundle.getParcelableArrayList("Boxs");
	}

}

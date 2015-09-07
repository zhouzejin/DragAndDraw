package com.sunny.drawanddraw;

import android.graphics.PointF;

public class Box {

	private PointF mOrigin;
	private PointF mCurrent;

	public Box(PointF origin) {
		super();
		mOrigin = mCurrent = origin;
	}

	public PointF getCurrent() {
		return mCurrent;
	}

	public void setCurrent(PointF current) {
		mCurrent = current;
	}

	public PointF getOrigin() {
		return mOrigin;
	}

}

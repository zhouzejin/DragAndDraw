package com.sunny.draganddraw;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Android中实现序列化有两个选择：一是实现Serializable接口（是JavaSE本身就支持的），
 * 一是实现Parcelable接口（是Android特有功能，效率比实现Serializable接口高效，
 * 可用于Intent数据传递，也可以用于进程间通信（IPC））。
 */
public class Box implements Parcelable {

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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(mOrigin);
		dest.writeValue(mCurrent);
	}
	
	public static final Parcelable.Creator<Box> CREATOR = new Parcelable.Creator<Box>() {
		public Box createFromParcel(Parcel in) {
			return new Box(in);
		}

		public Box[] newArray(int size) {
			return new Box[size];
		}
	};

	private Box(Parcel in) {
		mOrigin = (PointF) in.readValue(PointF.class.getClassLoader());
		mCurrent = (PointF) in.readValue(PointF.class.getClassLoader());
	}

}

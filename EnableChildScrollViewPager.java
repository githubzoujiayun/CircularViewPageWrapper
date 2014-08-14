package android.intclub.net.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

public class EnableChildScrollViewPager extends ViewPager {

	public EnableChildScrollViewPager(Context context) {
		super(context);
	}

	public EnableChildScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean ret = super.dispatchTouchEvent(ev);
		if (ret) {
			((ViewGroup) this.getParent())
					.requestDisallowInterceptTouchEvent(true);
		}
		return ret;
	}
}

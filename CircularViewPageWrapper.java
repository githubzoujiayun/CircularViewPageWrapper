package android.intclub.net.widget;

import android.support.v4.view.ViewPager;

/**
 *  实现循环ViewPager适配器
 *  @author   :   Steve
 *	@date	  :   2013-11-21
 *	@version  :	  v1.0
 *	@describe :	
 */
public class CircularViewPageWrapper implements ViewPager.OnPageChangeListener{
	
	private PageState mStateBegin, mTmpState, mStateEnd;
	private ViewPager.OnPageChangeListener mListener;
	private boolean mScrolling = false;
	private boolean mFirstStateOnRetart = true;
	private int mStartIdx, mEndIdx;
	
	/**
	 * @author Steve
	 * @date 2013/11/21
	 * @param oldListener
	 * @param startIdx == 0
	 * @param endIdx == ViewPager.Container.getPageCount - 1
	 */
	public CircularViewPageWrapper(ViewPager.OnPageChangeListener oldListener,
			int startIdx, int endIdx) {
		mListener = oldListener;
		mStartIdx = startIdx;
		mEndIdx = endIdx;
	}
	
	/**
	 * 标记滑动过程中-滑动页的状态
	 * @author	:	Steve
	 * @date	:	2013-11-21
	 * @describe:
	 */
	private class PageState {
		/**
		 * 当前主显页索引
		 */
		int page;
		/**
		 * 滑动过程变化值: 用于判断是否滑动和滑动的方向<br>
		 * rang递增 : 向右; rang 递减: 向左. rang 滑动过程中恒为0, 表示在边界, 即最左或最右.<br> 
		 */
		float rang;
		/**
		 * 标识滑动状态: 闲置/滑动...
		 */
		int state;
		
		public PageState(int page, float rang, int state) {
			set(page, rang, state);
		}
		public void set(int page, float rang, int state) {
			this.page = page;
			this.rang = rang;
			this.state = state;
		}
		public boolean equals(Object o) {
			if(o instanceof PageState) {
				PageState obj = (PageState) o;
				return obj.page == this.page && obj.rang == this.rang
						&& obj.state == this.state;
			}
			return false;
		}
		public String toString() {
			return String.format("PageState: page=%d, rang=%f, state=%d\r\n",
					page, rang, state);
		};
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		switch (state) {
		case ViewPager.SCROLL_STATE_IDLE:
			// finish scrolling, reset flags
			mFirstStateOnRetart = true;
			mScrolling = false;
			if(mStateBegin != null && mStateEnd != null) {
				// on the left or the right: beginPage = endPage.
				if(mStateEnd.equals(mStateBegin)) {
					if(mStateEnd.page == mStartIdx) {
						// on the left
						onPageSelected(mEndIdx);
					} else if(mStateEnd.page == mEndIdx) {
						// on the right
						onPageSelected(mStartIdx);
					} else {
						// middle; do nothing...
					}
					// clear old data...do nothing...
				}
			}
			break;
		case ViewPager.SCROLL_STATE_DRAGGING:
			// just start to scroll
			mScrolling = true;
			break;
		case ViewPager.SCROLL_STATE_SETTLING:
			// already show current page
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrolled(int page, float rang, int state) {
		// TODO Auto-generated method stub
		if(mFirstStateOnRetart) {
			if(mStateBegin == null) {
				mStateBegin = new PageState(page, rang, state);
			} else {
				mStateBegin.set(page, rang, state);
			}
			mFirstStateOnRetart = false;
		} else {
			// check rolling
			// skip the first-state-on-restart
			String dir = "unknown";
			if(mTmpState == null) {
				mTmpState = new PageState(page, rang, state);
			} else {
				// check direction: rang > mTmpState.rang ? Right : Left.
				dir = rang > mTmpState.rang ? "Right" : "Left";
				mTmpState.set(page, rang, state);
			}
		}
		/* 可能的滑动完毕状态 */
		if(rang == 0.0f && state == 0) {
			if(mStateEnd == null) {
				mStateEnd = new PageState(page, rang, state);
			} else {
				mStateEnd.set(page, rang, state);
			}
		}
	}

	@Override
	public void onPageSelected(int page) {
		// TODO Auto-generated method stub
		if(mListener != null) {
			mListener.onPageSelected(page);
		}
	}
}

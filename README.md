CircularViewPageWrapper
=======================

ViewPager循环适配.

  use in Fragment:
  
  	private int mLastTabIndex = 0;
  
	private int mCurrentTabIndex = 0;
	
	// tabs
	ArrayList<View> mTabViewList = new ArrayList<View>();
	  mTabViewList.add((TextView)findViewById(R.id.tab0));
		mTabViewList.add((TextView)findViewById(R.id.tab1));
		mTabViewList.add((TextView)findViewById(R.id.tab2));
		mTabViewList.add((TextView)findViewById(R.id.tab3));
		mTabViewList.add((TextView)findViewById(R.id.tab4));
	int size = mTabViewList.size();
	for(int i = 0; i < size; i++) {
	  mTabViewList.get(i).setOnClickListener(new TabClickListener(i));
	}
	// tab title resource
	int[] mTabTitle = new int[] {
				R.string.title0,
				R.string.title1,
				R.string.title2,
				R.string.title3,
				R.string.title4 };
  
  	// the base ViewPagerAdapter/FragmentPagerAdapter
  
  	FragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity()
				.getSupportFragmentManager());
  	EnableChildScrollViewPager mViewPager = (EnableChildScrollViewPager)
  			findViewById(R.id.childscrollable_viewpager);
	mViewPager.setAdapter(adapter);
	
	ViewPager.OnPageChangeListener mPagerChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int page) {
			// TODO invoke this method when page has scrolled
			mCurrentTabIndex = page;
			showCurrentTab(mCurrentTabIndex);
			mLastTabIndex = mCurrentTabIndex;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO invoke this method when page is scrolling
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// 0:scrolling 1:scrolled 2:do nothing
		}
	};
	
	 /**
	 * 适配mPagerChangeListener以实现循环切页<br>
	 * 
	 * @author Steve
	 * @date 2013/11/21
	 */
	mViewPager.setOnPageChangeListener(new CircularViewPageWrapper(
			mPagerChangeListener, 0, mTabViewList.size() - 1));

  	// the method to indcate the current page for the current tab
  	
  	private void showCurrentTab(int index) {
		mTabViewList.get(mLastTabIndex).setBackgroundResource(
				R.drawable.bg_tag_last_focus);
		((TextView) (mTabViewList.get(mLastTabIndex)))
				.setTextColor(getResources().getColor(R.color.sub_tab_text_color));
		mTabViewList.get(index).setBackgroundResource(
				R.drawable.bg_tag_select);
		((TextView) (mTabViewList.get(index))).setTextColor(getResources()
				.getColor(R.color.sub_tab_text_color_selected));
		mViewPager.setCurrentItem(mCurrentTabIndex, true);
	}
	
	
	/* tab's OnClickListener */
	
	private class TabClickListener implements View.OnClickListener {
		private int index = 0;

		public TabClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mLastTabIndex = mCurrentTabIndex;
			mCurrentTabIndex = index;
			showCurrentTab(mCurrentTabIndex);
		};
	}
				
	/**
	 * base: FragmentPagerAdapter
	 */
	class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			Fragment temp = null;
			switch (index) {
			case 0:
				temp = new Fragment1();
				break;
			case 1:
				temp = new Fragment2();
				break;
			case 2:
				temp = new Fragment3();
				break;
			case 3:
				temp = new Fragment4();
				break;
			case 4:
				temp = new Fragment5();
				break;
			}
			return temp;
		}

		@Override
		public int getCount() {
		  // or return mTabTitle.lenth;
			return mTabViewList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getResources().getString(mTabTitle[position % mTabTitle.length]);
		}
	}
	

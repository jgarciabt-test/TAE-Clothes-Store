package com.tae.store.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.adapters.SlidePagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class HomeFragment extends SherlockFragment implements
		OnPageChangeListener {

	private Context context;
	private int currentSelectedFragmentPosition = 0;

	private PageIndicator mIndicator1;
	private PageIndicator mIndicator2;
	private ViewPager mPager1;
	private ViewPager mPager2;
	private SlidePagerAdapter mAdapter1;
	private SlidePagerAdapter mAdapter2;

	// TODO just for testing
	private PageIndicator mIndicator3;
	private PageIndicator mIndicator4;
	private ViewPager mPager3;
	private ViewPager mPager4;
	private SlidePagerAdapter mAdapter3;
	private SlidePagerAdapter mAdapter4;

	static public String[] name = { "Jeans", "shoes" };
	static public String[] pic = {
			"http://s3.amazonaws.com/springfield-shop/public/system/products/79878/small/P_033470746FM.jpg?1405422824",
			"http://s3.amazonaws.com/springfield-shop/public/system/products/79878/small/P_033470746FM.jpg?1405422824" };
	static public String[] price = { "22.55", "15.99" };
	
	
	// TODO only for testing
	private String[] first = {
			"http://s3.amazonaws.com/spf_images/banners/springfield-man-en1.jpg",
			"http://s3.amazonaws.com/spf_images/banners/springfield-man-en4.jpg",
			"http://s3.amazonaws.com/spf_images/banners/springfield-man-en3.jpg" };
	private String[] second = { "one", "two", "three", "for", "five" };
	private String[] otro = { "one", "two" };

	public HomeFragment(){
	}
	
	public HomeFragment(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_home, container, false);

		mAdapter1 = new SlidePagerAdapter(getChildFragmentManager(), first);

		mPager1 = (ViewPager) rootView.findViewById(R.id.vp1);
		mPager1.setAdapter(mAdapter1);

		mIndicator1 = (CirclePageIndicator) rootView
				.findViewById(R.id.indicator1);
		mIndicator1.setViewPager(mPager1);

		mAdapter2 = new SlidePagerAdapter(getChildFragmentManager(), second);
		mPager2 = (ViewPager) rootView.findViewById(R.id.vp2);
		mPager2.setAdapter(mAdapter2);

		mIndicator2 = (CirclePageIndicator) rootView
				.findViewById(R.id.indicator2);
		mIndicator2.setViewPager(mPager2);

		mAdapter3 = new SlidePagerAdapter(getChildFragmentManager(), first);
		mPager3 = (ViewPager) rootView.findViewById(R.id.vp3);
		mPager3.setAdapter(mAdapter3);

		mIndicator3 = (CirclePageIndicator) rootView
				.findViewById(R.id.indicator3);
		mIndicator3.setViewPager(mPager3);

		mAdapter4 = new SlidePagerAdapter(getChildFragmentManager(), otro);
		mPager4 = (ViewPager) rootView.findViewById(R.id.vp4);
		mPager4.setAdapter(mAdapter4);

		mIndicator4 = (CirclePageIndicator) rootView
				.findViewById(R.id.indicator4);
		mIndicator4.setViewPager(mPager4);

		final GestureDetector tapGestureDetector = new GestureDetector(
				getActivity(), new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						MainActivity.replaceFragment(new CategoryFragment("Men",name, pic, price),"CATEGORY_FRAGMENT",true);
						return false;
					}
				});

		mPager1.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				tapGestureDetector.onTouchEvent(event);
				return false;
			}
		});
		mIndicator1.setOnPageChangeListener(this);

		return rootView;
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		currentSelectedFragmentPosition = arg0;
	}

	public int getCurrentSelectedFragmentPosition() {
		return currentSelectedFragmentPosition;
	}

}

package com.tae.store.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.R;
import com.tae.store.adapters.SlidePagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class HomeFragment extends SherlockFragment {

	
	private PageIndicator mIndicator1;
	private PageIndicator mIndicator2;
    private ViewPager mPager1;
    private ViewPager mPager2;
    private SlidePagerAdapter mAdapter1;
    private SlidePagerAdapter mAdapter2;
    
    
    //TODO just for testing
	private PageIndicator mIndicator3;
	private PageIndicator mIndicator4;
    private ViewPager mPager3;
    private ViewPager mPager4;
    private SlidePagerAdapter mAdapter3;
    private SlidePagerAdapter mAdapter4;
	
    
    //TODO only for testing
    private String[] first = {"Lorem", "impsum", "dos", "tres", "cuatro"};
    private String[] second = {"one", "two", "three", "for", "five"};
    private String[] otro = {"one", "two"};
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);
        
        mAdapter1 = new SlidePagerAdapter(getChildFragmentManager(), first, "#FF0000");

        mPager1 = (ViewPager)rootView.findViewById(R.id.vp1);
        mPager1.setAdapter(mAdapter1);

        mIndicator1 = (CirclePageIndicator)rootView.findViewById(R.id.indicator1);
        mIndicator1.setViewPager(mPager1);
        
        mAdapter2 = new SlidePagerAdapter(getChildFragmentManager(), second, "#AA2200");
        mPager2 = (ViewPager) rootView.findViewById(R.id.vp2);
        mPager2.setAdapter(mAdapter2);
        
        mIndicator2 = (CirclePageIndicator)rootView.findViewById(R.id.indicator2);
        mIndicator2.setViewPager(mPager2);
        
        mAdapter3 = new SlidePagerAdapter(getChildFragmentManager(), first, "#FF5511");
        mPager3 = (ViewPager) rootView.findViewById(R.id.vp3);
        mPager3.setAdapter(mAdapter3);
        
        mIndicator3 = (CirclePageIndicator)rootView.findViewById(R.id.indicator3);
        mIndicator3.setViewPager(mPager3);
        
        mAdapter4 = new SlidePagerAdapter(getChildFragmentManager(), otro, "#FF00AA");
        mPager4 = (ViewPager) rootView.findViewById(R.id.vp4);
        mPager4.setAdapter(mAdapter4);
        
        mIndicator4 = (CirclePageIndicator)rootView.findViewById(R.id.indicator4);
        mIndicator4.setViewPager(mPager4);

        return rootView;
	}

}
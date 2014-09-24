package com.tae.store.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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
	
    
    //TODO only for testing
    private String[] first = {"Lorem", "impsum", "dos", "tres", "cuatro"};
    private String[] second = {"one", "two", "three", "for", "five"};
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, container, false);
        
        mAdapter1 = new SlidePagerAdapter(getChildFragmentManager(), first);

        mPager1 = (ViewPager)rootView.findViewById(R.id.vp1);
        mPager1.setAdapter(mAdapter1);

        mIndicator1 = (CirclePageIndicator)rootView.findViewById(R.id.indicator1);
        mIndicator1.setViewPager(mPager1);
        
        mAdapter2 = new SlidePagerAdapter(getChildFragmentManager(), second);
        mPager2 = (ViewPager) rootView.findViewById(R.id.vp2);
        mPager2.setAdapter(mAdapter2);
        
        mIndicator2 = (CirclePageIndicator)rootView.findViewById(R.id.indicator2);
        mIndicator2.setViewPager(mPager2);

        return rootView;
	}

}

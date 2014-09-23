package com.tae.store;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.tae.store.fragments.SlidePageFragment;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;


public class MainActivity extends SherlockFragmentActivity  {

	
	private static final int NUM_PAGES = 5;

	private PageIndicator mIndicator1;
	private PageIndicator mIndicator2;
    private ViewPager mPager1;
    private ViewPager mPager2;
    private SlidePagerAdapter mAdapter1;
    private SlidePagerAdapter mAdapter2;
    
    private String[] first = {"Lorem", "impsum", "dos", "tres", "cuatro"};
    private String[] second = {"one", "two", "three", "for", "five"};
    

    

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Instantiate a ViewPager and a PagerAdapter.
//        mPager = (ViewPager) findViewById(R.id.pager);
//        mPager2 = (ViewPager) findViewById(R.id.pager2);
//        mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), first);
//        mPagerAdapter2 = new SlidePagerAdapter(getSupportFragmentManager(),second);
//        mPager.setAdapter(mPagerAdapter);
//        mPager2.setAdapter(mPagerAdapter2);
//        
//        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
//        mIndicator.setViewPager(mPager);
        
        mAdapter1 = new SlidePagerAdapter(getSupportFragmentManager(), first);

        mPager1 = (ViewPager)findViewById(R.id.vp1);
        mPager1.setAdapter(mAdapter1);

        mIndicator1 = (CirclePageIndicator)findViewById(R.id.indicator1);
        mIndicator1.setViewPager(mPager1);
        
        mAdapter2 = new SlidePagerAdapter(getSupportFragmentManager(), second);
        mPager2 = (ViewPager) findViewById(R.id.vp2);
        mPager2.setAdapter(mAdapter2);
        
        mIndicator2 = (CirclePageIndicator)findViewById(R.id.indicator2);
        mIndicator2.setViewPager(mPager2);
        
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    public class SlidePagerAdapter extends FragmentStatePagerAdapter {
    	
    	private String [] data;
    	private ArrayList<SlidePageFragment> list;
    	
        public SlidePagerAdapter(FragmentManager fragmentManager, String[] array) {
            super(fragmentManager);
            
            list = new ArrayList<SlidePageFragment>();
            for(int i=0;i<NUM_PAGES;i++){
            	list.add(new SlidePageFragment());
            }
            
        	data = array;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
    
}

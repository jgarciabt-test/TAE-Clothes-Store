package com.tae.store.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tae.store.fragments.SlidePageFragment;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class SlidePagerAdapter extends FragmentStatePagerAdapter {
	
	private int num_pages;
	
	private String [] data;
	private ArrayList<SlidePageFragment> list;
	
    public SlidePagerAdapter(FragmentManager fragmentManager, String[] array) {
        super(fragmentManager);
        
        data = array;
        num_pages = data.length;
        list = new ArrayList<SlidePageFragment>();
        for(int i=0;i<num_pages;i++){
        	list.add(new SlidePageFragment(data[i]));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return num_pages;
    }
}
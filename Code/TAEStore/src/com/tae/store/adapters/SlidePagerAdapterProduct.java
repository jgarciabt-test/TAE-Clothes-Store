package com.tae.store.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tae.store.R;
import com.tae.store.fragments.SlidePageFragment;
import com.tae.store.model.Product;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class SlidePagerAdapterProduct extends FragmentStatePagerAdapter {

	private int num_pages;
	private ArrayList<SlidePageFragment> list;

	public SlidePagerAdapterProduct(FragmentManager fragmentManager, 
			ArrayList<Product> offers) {
		super(fragmentManager);
		


		list = new ArrayList<SlidePageFragment>();
		list.add(new SlidePageFragment(R.drawable.special_offers, true));
		if(offers!=null)
			num_pages = offers.size();
		for (int i = 0; i < num_pages; i++) {
			list.add(new SlidePageFragment(offers.get(i).getUrl_pic(),
					false));
		}
		num_pages++;
		
	}

	@Override
	public Fragment getItem(int position) {
		return list.get(position);
	}

	@Override
	public int getCount() {
		return num_pages;
	}
	
	@Override
	public int getItemPosition(Object object) {
	    return POSITION_NONE;
	}
}
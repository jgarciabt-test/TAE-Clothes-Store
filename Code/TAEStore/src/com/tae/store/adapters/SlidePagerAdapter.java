package com.tae.store.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tae.store.R;
import com.tae.store.fragments.SlidePageFragment;
import com.tae.store.model.Category;
import com.tae.store.utilities.MainCategories;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class SlidePagerAdapter extends FragmentStatePagerAdapter {

	private int num_pages;
	private ArrayList<SlidePageFragment> list;

	public SlidePagerAdapter(FragmentManager fragmentManager, int category,
			ArrayList<Category> categories) {
		super(fragmentManager);
		int resourceId = 0;

		switch (category) {
		case MainCategories.OFFERS:
			resourceId = R.drawable.men;
			break;
		case MainCategories.MEN:
			resourceId = R.drawable.men;
			break;
		case MainCategories.WOMEN:
			resourceId = R.drawable.women;
			break;
		}


		list = new ArrayList<SlidePageFragment>();
		list.add(new SlidePageFragment(resourceId, true));
		if(categories!=null)
			num_pages = categories.size();
		for (int i = 0; i < num_pages; i++) {
			list.add(new SlidePageFragment(categories.get(i).getUrl_pic(),
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
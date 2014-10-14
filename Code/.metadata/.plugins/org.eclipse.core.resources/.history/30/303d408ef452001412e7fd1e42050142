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
 * Pager adapter that represents N SlidePageFragment objects, in sequence.
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class SlidePagerAdapter extends FragmentStatePagerAdapter {

	/** Number of pages. */
	private int num_pages;
	/** ArrayList with all the SlidePageFragment objects. */
	private ArrayList<SlidePageFragment> list;

	/**
	 * Constructor. Depending the kind of category, the first element of the
	 * list will be different.
	 * 
	 * @param fragmentManager
	 *            Fragment manager.
	 * @param category
	 *            Determine the kind of the slider (men or women)
	 * @param categories
	 *            ArrayList with Category objects.
	 */
	public SlidePagerAdapter(FragmentManager fragmentManager, int category,
			ArrayList<Category> categories) {
		super(fragmentManager);
		int resourceId = 0;

		switch (category) {
		case MainCategories.MEN:
			resourceId = R.drawable.men;
			break;
		case MainCategories.WOMEN:
			resourceId = R.drawable.women;
			break;
		}

		// The first slide is always static for each category.
		list = new ArrayList<SlidePageFragment>();
		list.add(new SlidePageFragment(resourceId, true));
		if (categories != null)
			num_pages = categories.size();
		for (int i = 0; i < num_pages; i++) {
			list.add(new SlidePageFragment(categories.get(i).getUrl_pic(), false));
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
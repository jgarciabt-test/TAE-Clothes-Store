package com.tae.store.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.tae.store.model.Category;
import com.tae.store.utilities.MainCategories;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class HomeFragment extends SherlockFragment {

	private Context context;
	private int currentSelectedFragmentPosition = 0;

	// Offers
	private PageIndicator mIndicatorOffers;
	private ViewPager mPagerOffers;
	// TODO customize
	private SlidePagerAdapter mAdapterOffers;

	// Men
	private PageIndicator mIndicatorMen;
	private ViewPager mPagerMen;
	private SlidePagerAdapter mAdapterMen;
	private ArrayList<Category> menCategories;

	// Women
	private PageIndicator mIndicatorWomen;
	private ViewPager mPagerWomen;
	private SlidePagerAdapter mAdapterWomen;
	private ArrayList<Category> womenCategories;

	// Gesture detector to determine with screen has been tapped
	final GestureDetector tapGestureDetectorOffer = new GestureDetector(
			getActivity(), new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onSingleTapConfirmed(MotionEvent e) {
					MainActivity.replaceFragment(new CategoryFragment("Men",
							context), "CATEGORY_FRAGMENT", true);
					return false;
				}
			});

	// Gesture detector to determine with screen has been tapped
	final GestureDetector tapGestureDetectorMen = new GestureDetector(
			getActivity(), new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onSingleTapConfirmed(MotionEvent e) {
					int i = mPagerMen.getCurrentItem();
					switch (i) {
					case 0:
						MainActivity.replaceFragment(new CategoryFragment(
								"Men", context), "CATEGORY_FRAGMENT", true);
						break;
					default:
						MainActivity.replaceFragment(new ProductListFragment(
								menCategories.get(i - 1).getId(), menCategories
										.get(i - 1).getName(), context),
								"PRODUCT_LIST_FRAGMENT", true);
						break;
					}

					return false;
				}
			});

	final GestureDetector tapGestureDetectorWomen = new GestureDetector(
			getActivity(), new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onSingleTapConfirmed(MotionEvent e) {
					int i = mPagerWomen.getCurrentItem();
					switch (i) {
					case 0:
						MainActivity.replaceFragment(new CategoryFragment(
								"Women", context), "CATEGORY_FRAGMENT", true);
						break;
					default:
						MainActivity.replaceFragment(new ProductListFragment(
								menCategories.get(i - 1).getId(), menCategories
										.get(i - 1).getName(), context),
								"PRODUCT_LIST_FRAGMENT", true);
						break;
					}

					return false;
				}
			});

	public HomeFragment() {
	}

	public HomeFragment(Context context, ArrayList<Category> menCategories,
			ArrayList<Category> womenCategories) {
		this.context = context;
		this.menCategories = menCategories;
		this.womenCategories = womenCategories;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_home, container, false);
		
		if(context == null){
			context = getActivity();
		}
		
		if(savedInstanceState != null){
			menCategories = savedInstanceState.getParcelableArrayList("menCategories");
			womenCategories = savedInstanceState.getParcelableArrayList("womenCategories");
		}

		mAdapterOffers = new SlidePagerAdapter(getChildFragmentManager(),
				MainCategories.OFFERS, menCategories);

		mPagerOffers = (ViewPager) rootView.findViewById(R.id.vp1);
		mPagerOffers.setAdapter(mAdapterOffers);

		mIndicatorOffers = (CirclePageIndicator) rootView
				.findViewById(R.id.indicator1);
		mIndicatorOffers.setViewPager(mPagerOffers);

		mAdapterMen = new SlidePagerAdapter(getChildFragmentManager(),
				MainCategories.MEN, menCategories);
		mPagerMen = (ViewPager) rootView.findViewById(R.id.vp2);
		mPagerMen.setAdapter(mAdapterMen);

		mIndicatorMen = (CirclePageIndicator) rootView
				.findViewById(R.id.indicator2);
		mIndicatorMen.setViewPager(mPagerMen);

		mAdapterWomen = new SlidePagerAdapter(getChildFragmentManager(),
				MainCategories.WOMEN, womenCategories);
		mPagerWomen = (ViewPager) rootView.findViewById(R.id.vp3);
		mPagerWomen.setAdapter(mAdapterWomen);

		mIndicatorWomen = (CirclePageIndicator) rootView
				.findViewById(R.id.indicator3);
		mIndicatorWomen.setViewPager(mPagerWomen);

		mPagerOffers.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				tapGestureDetectorOffer.onTouchEvent(event);
				return false;
			}
		});

		mPagerMen.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				tapGestureDetectorMen.onTouchEvent(event);
				return false;
			}
		});
		
		mPagerWomen.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				tapGestureDetectorWomen.onTouchEvent(event);
				return false;
			}
		});

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList("menCategories", menCategories);
		outState.putParcelableArrayList("womenCategories", womenCategories);
	}


}

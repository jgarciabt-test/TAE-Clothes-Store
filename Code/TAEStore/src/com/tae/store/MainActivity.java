package com.tae.store;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tae.store.adapters.NavDrawerListAdapter;
import com.tae.store.fragments.CategoryFragment;
import com.tae.store.fragments.HomeFragment;
import com.tae.store.fragments.ProductListFragment;
import com.tae.store.model.NavDrawerItem;

@SuppressLint({ "InlinedApi", "NewApi" })
public class MainActivity extends SherlockFragmentActivity {

	private static final int NUM_PAGES = 5;

	// Fragments
	private FragmentManager fragmentManager;

	// Slide Pager

	// Slide Menu
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerListAdapter adapter;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;

	private Fragment fragment;

	
	//TODO Just for testing
	String[] name = { "Jeans", "shoes" };
	String[] pic = {
			"http://s3.amazonaws.com/springfield-shop/public/system/products/79878/small/P_033470746FM.jpg?1405422824",
			"http://s3.amazonaws.com/springfield-shop/public/system/products/79878/small/P_033470746FM.jpg?1405422824" };
	String[] price = { "22.55", "15.99" };
	
	// TODO Just for testing, to populate ImageViews
	static private ArrayList<String> generateData() {
		ArrayList<String> listData = new ArrayList<String>();
		listData.add("http://i62.tinypic.com/2iitkhx.jpg");
		listData.add("http://i61.tinypic.com/w0omeb.jpg");
		listData.add("http://i60.tinypic.com/w9iu1d.jpg");
		listData.add("http://i60.tinypic.com/iw6kh2.jpg");
		listData.add("http://i57.tinypic.com/ru08c8.jpg");
		listData.add("http://i60.tinypic.com/k12r10.jpg");
		listData.add("http://i58.tinypic.com/2e3daug.jpg");
		listData.add("http://i59.tinypic.com/2igznfr.jpg");

		return listData;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Slide menu
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.openDrawer(Gravity.START);
		mDrawerLayout.closeDrawer(Gravity.START);

		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		navDrawerItems = new ArrayList<NavDrawerItem>();
		for (int i = 0; i < navMenuTitles.length; i++) {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons
					.getResourceId(i, -1), false, ""));
		}
		navMenuIcons.recycle();

		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// Enabling action bar app icon and behaving it as toggle button
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_drawer);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#FF0000")));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.main_menu) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle("Cadena 1");
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Cadena 2");
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}

		};

		// if (savedInstanceState == null) {
		// selectItem(0);
		// }


		// Load fragment
		fragmentManager = getSupportFragmentManager();
		// Fragment fragment = new HomeFragment();
		// Fragment fragment = new CategoryFragment(name,pic,price);
		Fragment fragment = new ProductListFragment();
		addFragment(fragment);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.option1).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void addFragment(Fragment fragment) {
		if (fragment != null) {
			fragmentManager.beginTransaction()
					.add(R.id.frame_container, fragment).commit();
		}
	}

	private void replaceFragment(Fragment fragment) {
		if (fragment != null) {
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		}

	}

	private void displayView(int position) {
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new CategoryFragment(name,pic,price);
			break;
		default:
			break;
		}
	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}
}

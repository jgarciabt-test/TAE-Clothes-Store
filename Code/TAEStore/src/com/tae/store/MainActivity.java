package com.tae.store;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tae.store.adapters.NavDrawerListAdapter;
import com.tae.store.fragments.BagFragment;
import com.tae.store.fragments.CategoryFragment;
import com.tae.store.fragments.CustomMapFragment;
import com.tae.store.fragments.HomeFragment;
import com.tae.store.fragments.NoConnectionFragment;
import com.tae.store.model.Category;
import com.tae.store.model.NavDrawerItem;
import com.tae.store.utilities.NetworkCheckService;

@SuppressLint({ "InlinedApi", "NewApi" })
public class MainActivity extends SherlockFragmentActivity {

	private static final int NUM_PAGES = 5;
	// Fragments
	static public FragmentManager fragmentManager;
	static private String currentFragment;
	static public Fragment fragment;
	static public ArrayList<String> backStackFragment;

	// Slide Pager
	static public HashMap<String, Fragment> fragmentMap;

	private NetworkCheckReceiver netReceiver;

	// Slide Menu
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerListAdapter adapter;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent prev_screen = getIntent();

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
		getSupportActionBar().show();
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_drawer);
		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#b40909")));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.main_menu) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle("Cadena 1");
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			// TODO check
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Cadena 2");
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}

		};

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		fragmentManager = getSupportFragmentManager();
		if (savedInstanceState != null) {
			// Restore the fragment's instance
			currentFragment = savedInstanceState.getString("currentFragment");
			Log.v("ACTIVITY SAVED", "Current fragment: "+currentFragment);
			fragment = fragmentManager.getFragment(savedInstanceState,
					currentFragment);
			Log.v("ACTIVITY SAVED", "fragment: "+fragment.toString());
			replaceFragment(fragment, currentFragment, false);
			//backStackFragment = savedInstanceState
			//		.getStringArrayList("backStackFragment");
		} else {
			// Load first fragment
			backStackFragment = new ArrayList<String>();

			ArrayList<Category> men = prev_screen
					.getParcelableArrayListExtra("men");
			ArrayList<Category> women = prev_screen
					.getParcelableArrayListExtra("women");

			fragment = new HomeFragment(this, men, women);
			addFragment(fragment, "HOME_FRAGMENT");
		}

		netReceiver = new NetworkCheckReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("statusUpdate");
		this.registerReceiver(netReceiver, filter);

		Intent intent = new Intent(getApplicationContext(),
				NetworkCheckService.class);
		startService(intent);

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

	private void addFragment(Fragment fragment, String tag) {
		if (fragment != null) {
			fragmentManager.beginTransaction()
					.add(R.id.frame_container, fragment).commit();
			currentFragment = tag;
			backStackFragment.add(currentFragment);
		}
	}

	static public void replaceFragment(Fragment fragment, String tag,
			boolean backStack) {
		if (fragment != null) {
			currentFragment = tag;
			MainActivity.fragment = fragment;
			if (backStack) {
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment, tag)
						.addToBackStack(null).commit();

			} else {
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment, tag).commit();
			}
			if(!backStackFragment.get(backStackFragment.size()-1).matches(currentFragment)){
				backStackFragment.add(currentFragment);
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putStringArrayList("backstackFragment", backStackFragment);
		outState.putString("currentFragment", currentFragment);
		fragmentManager.putFragment(outState, currentFragment, fragment);

	}

	private void displayView(int position) {
		String fragmentName = "";
		switch (position) {
		case 0:
			ArrayList<Category> men = getIntent().getParcelableArrayListExtra(
					"men");
			ArrayList<Category> women = getIntent()
					.getParcelableArrayListExtra("women");

			fragment = new HomeFragment(this, men, women);
			fragmentName = "HOME_FRAGMENT";
			break;
		case 1:
			fragment = new CategoryFragment("Men", this);
			fragmentName = "CATEGORY_FRAGMENT";
			break;
		case 2:
			fragment = new CategoryFragment("Women", this);
			fragmentName = "CATEGORY_FRAGMENT";
			break;
		case 3:
			fragment = new CustomMapFragment();
			fragmentName = "MAP_FRAGMENT";
			break;
		case 4:
			fragment = new BagFragment();
			fragmentName = "BAG_FRAGMENT";
			break;
		default:
			break;
		}

		replaceFragment(fragment, fragmentName, true);
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		mDrawerLayout.closeDrawer(mDrawerList);
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

	@Override
	public void onBackPressed() {
//		if (backStackFragment.size()>1) {
//			backStackFragment.remove(backStackFragment.size() - 1);
//			Log.v("BACK", backStackFragment.get(backStackFragment.size()-1));
////			fragmentManager.beginTransaction()
////			.replace(R.id.frame_container, fragmentManager.findFragmentByTag(backStackFragment.get(backStackFragment.size()-1)), backStackFragment.get(backStackFragment.size()-1)).commit();
////		} else{
////			finish();
//		}
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(netReceiver);
		super.onDestroy();
	}

	class NetworkCheckReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			try {
				// limiting the double value to four decimal places for
				// consistent display
				String st = arg1.getStringExtra("networkstatus");
				if (st.equals("Not connected to Internet")
						&& (currentFragment != "NO_CONNECTION")) {
					replaceFragment(new NoConnectionFragment(currentFragment,
							fragment), "NO_CONNECTION", false);
				}
			}

			catch (Exception e) {
				Log.e("error", e.toString());
				Toast.makeText(getApplicationContext(),
						"Exception in reading status data", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	public void makeToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
	}
}

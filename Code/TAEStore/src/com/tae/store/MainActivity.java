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
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.tae.store.adapters.NavDrawerListAdapter;
import com.tae.store.fragments.HomeFragment;
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

	// nav drawer title
	// private CharSequence mDrawerTitle;

	// used to store app title
	// private CharSequence mTitle;

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
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));


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
		
        //if (savedInstanceState == null) {
        //    selectItem(0);
        //}

		// Load fragment
		fragmentManager = getSupportFragmentManager();
		Fragment fragment = new HomeFragment();
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


	// private void displayView(int position){
	//
	// }
	//
	// private class SlideMenuClickListener implements
	// ListView.OnItemClickListener {
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// // display view for selected nav drawer item
	// displayView(position);
	// }
	// }
}

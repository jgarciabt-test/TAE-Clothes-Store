package com.tae.store;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.tae.store.fragments.HomeFragment;
import com.tae.store.fragments.SlidePageFragment;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;


public class MainActivity extends SherlockFragmentActivity  {

	
	private static final int NUM_PAGES = 5;
	
	//Fragments
	private FragmentManager fragmentManager;

	//Slide Pager

    
    //Slide Menu
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	// nav drawer title
	//private CharSequence mDrawerTitle;

	// used to store app title
	//private CharSequence mTitle;
    

    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        fragmentManager = getSupportFragmentManager();
        
        Fragment fragment =  new HomeFragment(); 
        addFragment(fragment);

        
    }


    private void addFragment(Fragment fragment){	
    	if(fragment!=null){
            fragmentManager.beginTransaction().add(R.id.frame_container, fragment).commit();
    	}   	
    }
    
    private void replaceFragment(Fragment fragment){
    	if(fragment!=null){
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    	}
    	
    }

    
}

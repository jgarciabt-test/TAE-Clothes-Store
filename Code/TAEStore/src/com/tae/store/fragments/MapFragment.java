package com.tae.store.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.GoogleMap;
import com.tae.store.MainActivity;
import com.tae.store.R;

public class MapFragment extends SherlockFragment{

	private ViewGroup rootView;
	private GoogleMap map;
	private ImageButton toList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		if(rootView !=null){
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if(parent != null){
				parent.removeView(rootView);
			}
		}
		rootView = (ViewGroup)inflater.inflate(R.layout.fragment_location, container, false);
				
		toList = (ImageButton) rootView.findViewById(R.id.btn_to_list);
		
		toList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.replaceFragment(new StoreListFragment(), "STORE_LIST_FRAGMENT", true);
				
			}
		});
		
		return rootView;
	}

	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}

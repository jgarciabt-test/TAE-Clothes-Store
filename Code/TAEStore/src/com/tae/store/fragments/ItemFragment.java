package com.tae.store.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.R;

public class ItemFragment extends SherlockFragment {

	private Gallery gallery;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_item_details, container, false);
		
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}

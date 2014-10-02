package com.tae.store.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.tae.store.R;

public class BagFragment extends SherlockListFragment {

	private TextView qty;
	private TextView total;
	private TextView total_order;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup rootGroup = (ViewGroup) inflater.inflate(R.layout.fragment_bag, null, false);
		
		qty = (TextView)rootGroup.findViewById(R.id.txt_bag_qty);
		total_order = (TextView)rootGroup.findViewById(R.id.txt_bag_order);
		total = (TextView)rootGroup.findViewById(R.id.txt_bag_main_total);
		
		
		
		
		return rootGroup;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

}
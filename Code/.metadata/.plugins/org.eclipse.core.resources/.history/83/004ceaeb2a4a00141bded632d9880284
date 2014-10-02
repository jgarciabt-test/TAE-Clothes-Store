package com.tae.store.fragments;


import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.tae.store.R;
import com.tae.store.adapters.BagListAdapter;
import com.tae.store.helpers.DatabaseHandler;
import com.tae.store.model.Product;

public class BagFragment extends SherlockListFragment {

	private TextView qty;
	private TextView total;
	private TextView total_order;
	
	private DatabaseHandler BAG;
	private ArrayList<Product> myBag;
	
	public BagFragment(){
		
		myBag = new ArrayList<Product>();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup rootGroup = (ViewGroup) inflater.inflate(R.layout.fragment_bag, null, false);
		
		qty = (TextView)rootGroup.findViewById(R.id.txt_bag_qty);
		total_order = (TextView)rootGroup.findViewById(R.id.txt_bag_order);
		total = (TextView)rootGroup.findViewById(R.id.txt_bag_main_total);
		
		BAG = new DatabaseHandler(getActivity().getBaseContext());
		
		if(savedInstanceState == null){
			if(myBag.isEmpty()){
				myBag = BAG.getBag();
			}
		} else{
			myBag = savedInstanceState.getParcelableArrayList("myBag");
		}
		BagListAdapter adapter = new BagListAdapter(getActivity(), myBag);
		setListAdapter(adapter);
		
		return rootGroup;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList("myBag", myBag);
	}

}

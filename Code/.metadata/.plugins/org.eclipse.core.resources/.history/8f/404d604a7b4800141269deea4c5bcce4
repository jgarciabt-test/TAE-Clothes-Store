package com.tae.store.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.adapters.StoreListAdapter;
import com.tae.store.model.Store;

public class StoreListFragment extends SherlockListFragment {

	private ArrayList<Store> list;

	public StoreListFragment() {
		list = new ArrayList<Store>();
	}

	// TODO Just for testing
	private void populate() {

		for (int i = 0; i < 10; i++) {
			list.add(new Store("", "TAE Store", "25 Brondesbury Rd", "NW6 6TT",
					"London", "00011122233", "Mon - Sat 10:00 - 20:00",
					12.0000000, 12.0000000));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.store_list,
				null, false);

		if (savedInstanceState == null
				|| !savedInstanceState.containsKey("list")) {
			populate();
		} else {
			list = savedInstanceState.getParcelableArrayList("list");
		}

		StoreListAdapter adapter = new StoreListAdapter(getActivity(), list);
		setListAdapter(adapter);
		return rootView;
	}

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		MainActivity.replaceFragment(new StoreFragment(list.get(position)), "STORE_FRAGMENT", true);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelableArrayList("list", list);
	}
}

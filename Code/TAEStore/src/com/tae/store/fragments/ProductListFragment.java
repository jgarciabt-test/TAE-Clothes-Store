package com.tae.store.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.R;
import com.tae.store.adapters.ProductListAdapter;
import com.tae.store.model.Product;

public class ProductListFragment extends SherlockFragment {

	private ArrayList<Product> list;

	private String[] url = { "http://i62.tinypic.com/2iitkhx.jpg",
			"http://i61.tinypic.com/w0omeb.jpg",
			"http://i60.tinypic.com/w9iu1d.jpg",
			"http://i60.tinypic.com/k12r10.jpg",
			"http://i60.tinypic.com/iw6kh2.jpg",
			"http://i57.tinypic.com/ru08c8.jpg",
			"http://i60.tinypic.com/k12r10.jpg",
			"http://i58.tinypic.com/2e3daug.jpg",
			"http://i61.tinypic.com/w0omeb.jpg",
			"http://i59.tinypic.com/2igznfr.jpg" };

	// TODO Just for tesing, to populate the screen
	private void populate() {

		list = new ArrayList<Product>();
		for (int i = 0; i < 10; i++) {
			list.add(new Product("", "Product " + i, "", "", false, i, url[i]));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.product_list, null, false);

		if (savedInstanceState == null
				|| !savedInstanceState.containsKey("list")) {
			populate();
		} else {
			list = savedInstanceState.getParcelableArrayList("list");
		}

		
		ProductListAdapter adapter = new ProductListAdapter(getActivity(),android.R.layout.simple_list_item_1, list);
		GridView grid=(GridView) rootView.findViewById(R.id.grid);
        grid.setAdapter(adapter);
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList("list", list);
	}

}
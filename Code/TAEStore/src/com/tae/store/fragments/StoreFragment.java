package com.tae.store.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.R;
import com.tae.store.model.Store;

public class StoreFragment extends SherlockFragment {

	private Store store;

	public StoreFragment(Store store) {
		this.store = store;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_store_details, container, false);

		TextView txtName = (TextView) rootView
				.findViewById(R.id.txt_store_detail_name);
		TextView txtAddress = (TextView) rootView
				.findViewById(R.id.txt_store_detail_address);
		TextView txtPostCity = (TextView) rootView
				.findViewById(R.id.txt_store_detail_post_city);
		TextView txtOpening = (TextView) rootView
				.findViewById(R.id.txt_store_detail_opening);
		Button btnPhone = (Button) rootView
				.findViewById(R.id.btn_store_detail_phone);
		Button btnDirection = (Button) rootView
				.findViewById(R.id.btn_directions);

		txtName.setText(store.getName());
		txtAddress.setText(store.getAddress());
		txtPostCity.setText(store.getPostCode() + ","
				+ store.getCity());
		txtOpening.setText(store.getOpeningHours());
		btnPhone.setText(store.getPhone());

		return rootView;
	}

}

package com.tae.store.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.R;
import com.tae.store.model.Store;

/**
 * Fragment that show information about one store
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class StoreFragment extends SherlockFragment {

	/** Store object */
	private Store store;

	/**
	 * Empty constructor.
	 */
	public StoreFragment() {

	}

	/**
	 * Constructor
	 * 
	 * @param store
	 *            Store object that will be displayed.
	 */
	public StoreFragment(Store store) {
		this.store = store;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_store_details,
				container, false);

		if (savedInstanceState != null) {
			this.store = savedInstanceState.getParcelable("store");
		}

		TextView txtName = (TextView) rootView.findViewById(R.id.txt_store_detail_name);
		TextView txtAddress = (TextView) rootView.findViewById(R.id.txt_store_detail_address);
		TextView txtPostCity = (TextView) rootView.findViewById(R.id.txt_store_detail_post_city);
		TextView txtOpening = (TextView) rootView.findViewById(R.id.txt_store_detail_opening);
		Button btnPhone = (Button) rootView.findViewById(R.id.btn_store_detail_phone);
		Button btnDirection = (Button) rootView.findViewById(R.id.btn_directions);

		txtName.setText(store.getName());
		txtAddress.setText(store.getAddress());
		txtPostCity.setText(store.getPostCode() + ", " + store.getCity());
		txtOpening.setText(store.getOpeningHours());

		btnPhone.setText(store.getPhone());
		btnPhone.setOnClickListener(new OnClickListener() {
			// Action call
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + store.getPhone()));
				startActivity(intent);

			}
		});

		btnDirection.setOnClickListener(new OnClickListener() {
			// Action direcctions
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri
						.parse("http://maps.google.com/maps?daddr=" + store.getLatitude() + ","
								+ store.getLongitude()));
				startActivity(intent);

			}
		});

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelable("store", store);
	}

}

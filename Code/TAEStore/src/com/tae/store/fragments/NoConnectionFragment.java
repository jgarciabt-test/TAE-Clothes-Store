package com.tae.store.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tae.store.MainActivity;
import com.tae.store.R;

/**
 * Fragment that alert to the user that the Internet connection is lost. When
 * the connection comes back, the user can continue navigating from the same
 * screen where he/she was before.
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class NoConnectionFragment extends Fragment {

	/** String where the TAG of the current fragment is stored */
	private String currentFragment;
	/** Fragment where the current fragment is stored */
	private Fragment fragment;

	/**
	 * Empty constructor.
	 */
	public NoConnectionFragment() {

	}

	/**
	 * Constructor
	 * 
	 * @param currentFragment
	 *            String with the current fragment's tag
	 * @param fragment
	 *            Current fragment
	 */
	public NoConnectionFragment(String currentFragment, Fragment fragment) {
		this.currentFragment = currentFragment;
		this.fragment = fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.no_connection, container, false);

		Button btnRetry = (Button) rootView.findViewById(R.id.btn_retry);
		btnRetry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.replaceFragment(fragment, currentFragment, true);
			}
		});

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}

}

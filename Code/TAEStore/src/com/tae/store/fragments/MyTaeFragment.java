package com.tae.store.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tae.store.R;
import com.tae.store.utilities.SPTags;

/**
 * Fragment where the user can login and create an account or change his/her
 * preferences, depending if the user is logged or no.
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class MyTaeFragment extends Fragment {

	public static FragmentManager fragmentManagerLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_tae, container,
				false);

		fragmentManagerLogin = getChildFragmentManager();

		SharedPreferences sharedPreferences = getActivity().getPreferences(
				getActivity().MODE_PRIVATE);
		// Get the preferences, if the user is logged...
		if (sharedPreferences.getBoolean(SPTags.LOGGED, false)) {
			// Start preferences fragment
			fragmentManagerLogin.beginTransaction().add(R.id.my_tae, new PreferencesFragment())
					.commit();
		} else {
			// Start login fragment
			fragmentManagerLogin.beginTransaction().add(R.id.my_tae, new LogInFragment()).commit();
		}

		return rootView;
	}

	static public void replaceFragment(Fragment fragment, String tag) {
		if (fragment != null) {
			fragmentManagerLogin.beginTransaction().replace(R.id.my_tae, fragment, tag).commit();
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}

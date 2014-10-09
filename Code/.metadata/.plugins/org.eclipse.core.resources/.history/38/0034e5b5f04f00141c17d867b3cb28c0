package com.tae.store.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.utilities.SPTags;

public class MyTaeFragment extends SherlockFragment {

	public static FragmentManager fragmentManagerLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_tae, container,
				false);

		fragmentManagerLogin = getChildFragmentManager();

		SharedPreferences sharedPreferences = getActivity().getPreferences(
				getActivity().MODE_PRIVATE);
		if (sharedPreferences.getBoolean(SPTags.LOGGED, false)) {
			fragmentManagerLogin.beginTransaction().add(R.id.my_tae, new PreferencesFragment())
					.commit();

		} else {
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
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}

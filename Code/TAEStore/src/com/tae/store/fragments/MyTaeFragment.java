package com.tae.store.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.R;

public class MyTaeFragment extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_my_tae, container, false);

		FragmentManager fragmentManagerLogin = getChildFragmentManager();
		
		fragmentManagerLogin.beginTransaction().add(R.id.my_tae, new LogInFragment()).commit();
		
		
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

}

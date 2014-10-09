package com.tae.store.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.MainActivity;
import com.tae.store.R;

public class NoConnectionFragment extends SherlockFragment{

	private String currentFragment;
	private Fragment fragment;
	
	public NoConnectionFragment(){
		
	}
	
	public NoConnectionFragment(String currentFragment, Fragment fragment){
		this.currentFragment = currentFragment;
		this.fragment = fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.no_connection, container,false);
		
		Button btnRetry = (Button) rootView.findViewById(R.id.btn_retry);
		btnRetry.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				MainActivity.replaceFragment(fragment, currentFragment,true);
			}
		});
		
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		super.onSaveInstanceState(outState);
	}
	

}

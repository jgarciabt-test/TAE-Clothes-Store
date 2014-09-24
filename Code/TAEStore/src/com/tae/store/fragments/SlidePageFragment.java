package com.tae.store.fragments;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.squareup.picasso.Picasso;
import com.tae.store.R;

@SuppressLint("NewApi")
public class SlidePageFragment extends SherlockFragment {

	private String color;
	private String url;
	private ImageView picture;
	
	public SlidePageFragment(){
		super();
	}
	
	public SlidePageFragment(String url){
		this. url = url;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	if(savedInstanceState != null){
    		url = savedInstanceState.getString("url");
    	}
    	
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_page, null, false);
        
        picture = (ImageView) rootView.findViewById(R.id.iv_slide_pager);
        
        Picasso.with(getActivity().getApplicationContext()).load(url).placeholder(R.drawable.ic_launcher).into(picture);

        return rootView;
    }
    
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString("url", url);
	}
    
    
}
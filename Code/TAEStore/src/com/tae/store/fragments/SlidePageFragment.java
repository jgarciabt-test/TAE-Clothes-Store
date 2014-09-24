package com.tae.store.fragments;



import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.tae.store.R;

@SuppressLint("NewApi")
public class SlidePageFragment extends SherlockFragment {

	private String text;
	private String color;
	
	public SlidePageFragment(){
		super();
	}
	
	public SlidePageFragment(String text, String color){
		this. text = text;
		this.color = color;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	if(savedInstanceState != null){
    		text = savedInstanceState.getString("text");
    		color = savedInstanceState.getString("color");
    	}
    	
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_page, null, false);
        
        TextView tv = (TextView) rootView.findViewById(R.id.textView1);
        tv.setText(text);
        
        RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.rel1);
        rl.setBackground(new ColorDrawable(Color.parseColor(color)));

        return rootView;
    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putString("text", text);
		outState.putString("color", color);
	}

//	 @Override
//     public void onConfigurationChanged(Configuration newConfig) {
//         super.onConfigurationChanged(newConfig);
// 
//         if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//
//         } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//
//         }
//     }
    
    
    
}
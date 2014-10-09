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
import com.tae.store.utilities.ServerUrl;

/**
 * Fragment placed on the ViewPager. 
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
@SuppressLint("NewApi")
public class SlidePageFragment extends SherlockFragment {

	/** Boolean that indicate if the fragment is the main page of the ViewPAger.*/
	private boolean mainPic;
	/** Int with the ID of the image that will be displayed */
	private int resourceId;
	/** String with the URL of the image that will be displayed */
	private String url;
	/** ImageView to displaye the image */
	private ImageView picture;
	
	public SlidePageFragment(){
		super();
	}
	
	/**
	 * Constructor.
	 *
	 * @param resourceId Id of the picture that will be displayed on the ImageView.
	 * @param mainPic true if is the main picture of the ViewPager
	 */
	public SlidePageFragment(int resourceId, boolean mainPic){
		this.resourceId = resourceId;;
		this.mainPic = mainPic;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param url String with the URL of the picture that will be displayed on the ImageView.
	 * @param mainPic
	 */
	public SlidePageFragment(String url, boolean mainPic){
		this.url = url;
		this.mainPic = mainPic;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	if(savedInstanceState != null){
    		mainPic = savedInstanceState.getBoolean("mainPic");
    		if(mainPic){
    			resourceId = savedInstanceState.getInt("resourceId");
    		}else{
    			url = savedInstanceState.getString("url");
    		}
    	}
    	
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_page, null, false);
        
        picture = (ImageView) rootView.findViewById(R.id.iv_slide_pager);
        if(mainPic){ //Main picture obtained from the resources
        	picture.setImageDrawable(getResources().getDrawable(resourceId));
        } else{ //Picture obtained from internet
        	Picasso.with(getActivity().getApplicationContext()).load(ServerUrl.BASE_URL+ServerUrl.IMG+url).placeholder(getActivity().getResources().getDrawable(R.drawable.back)).into(picture);
        }

        return rootView;
    }
    
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putBoolean("mainPic", mainPic);
		outState.putInt("resourceId", resourceId);
		outState.putString("url", url);
	}
    
    
}
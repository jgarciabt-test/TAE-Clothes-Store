package com.tae.store.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.tae.store.MainActivity;
import com.tae.store.R;
import com.tae.store.adapters.CategoryListAdapter;
import com.tae.store.model.Category;

public class CategoryFragment extends SherlockListFragment {

	private String rootCategory;
	
	private String[] cat_name;
	private String[] pic_url;
	private String[] lower_price;
	private ArrayList<Category> list;
	
	public CategoryFragment(){
		super();
	}
	
	public CategoryFragment(String rootCategory,String []cat_name, String[]pic_url, String[] lower_price){
		this.rootCategory = rootCategory;
		this.cat_name = cat_name;
		this.pic_url = pic_url;
		this.lower_price = lower_price;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.category_list, null, false);
		
		if(savedInstanceState == null || !savedInstanceState.containsKey("list")){
			list = new ArrayList<Category>();
			for(int i=0;i<cat_name.length;i++){
				list.add(new Category(cat_name[i], pic_url[i], lower_price[i]));
			}	
		}else{
			list = savedInstanceState.getParcelableArrayList("list");
		}
		
		TextView rootCat = (TextView) rootView.findViewById(R.id.txt_category_list_title);
		rootCat.setText(rootCategory);
		CategoryListAdapter adapter = new CategoryListAdapter(getActivity(), list);
		setListAdapter(adapter);
		
		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putParcelableArrayList("list", list);
		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String title = list.get(position).getName();
		MainActivity.replaceFragment(new ProductListFragment(title),"PRODUCT_LIST_FRAGMENT",true);
	}
	

}

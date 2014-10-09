package com.tae.store.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.store.R;
import com.tae.store.model.Category;
import com.tae.store.utilities.ServerUrl;


/**
 *  Adapter for Category list.
 * 
 * @author Jose Garcia 
 * @version 1.0
 * @since 2014-10-08
 */
public class CategoryListAdapter extends BaseAdapter {

	/** Application context. */
	Context context;
	
	/** ArrayList with all the Category objects. */
	ArrayList<Category> categoryList;
	
	/** LayoutInflater. */
	private LayoutInflater mInflater;

	/** Constructor.
	 * 
	 * @param context Application context
	 * @param list ArrayList of Category objects.
	 */
	public CategoryListAdapter(Context context, ArrayList<Category> list) {
		this.context = context;
		this.categoryList = list;
		mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return categoryList.size();
	}

	@Override
	public Object getItem(int position) {
		return categoryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return categoryList.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.category_item, null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.iv_category);
		TextView txtName = (TextView) convertView.findViewById(R.id.txt_catName);
		TextView txtPrice = (TextView) convertView.findViewById(R.id.txt_catPrice);

		Category cat = categoryList.get(position);
		txtName.setText(cat.getName());
		
		//Just to format the output properly
		float aux = Float.parseFloat(cat.getLower_price());
		
		txtPrice.setText(context.getResources().getString(R.string.from) + new DecimalFormat("##.##").format(aux));
		Picasso.with(context).load(ServerUrl.BASE_URL + ServerUrl.IMG + cat.getUrl_pic())
				.placeholder(context.getResources().getDrawable(R.drawable.back)).into(imgIcon);

		return convertView;
	}

	/** 
	 * Clean the ArrayList and call <i>notofyDataSetChanged</i>.
	 */
	public void cleanAdapter() {
		categoryList.clear();
		notifyDataSetChanged();
	}
}

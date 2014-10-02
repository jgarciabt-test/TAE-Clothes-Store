package com.tae.store.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.store.R;
import com.tae.store.model.Product;
import com.tae.store.utilities.ServerUrl;

public class BagListAdapter extends BaseAdapter {

	Context context;
	private ArrayList<Product> productList;
	private LayoutInflater mInflater;

	public BagListAdapter(Context context, ArrayList<Product> productList) {
		this.context = context;
		this.productList = productList;
		mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return productList.size();
	}

	@Override
	public Object getItem(int position) {
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return productList.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.bag_item, null);
		}

		ImageView imgIcon = (ImageView) convertView
				.findViewById(R.id.iv_bag_pic);
		TextView txtName = (TextView) convertView
				.findViewById(R.id.txt_bag_name);
		TextView txtPrice = (TextView) convertView
				.findViewById(R.id.txt_bag_price);
		TextView txtSize = (TextView) convertView
				.findViewById(R.id.txt_bag_size);

		Product prod = productList.get(position);
		txtName.setText(prod.getName());
		txtPrice.setText(context.getResources()
				.getString(R.string.pound_symbol)
				+ new DecimalFormat("##.##").format(prod.getPrice()));
		txtSize.setText(prod.getSize());
		Picasso.with(context)
				.load(ServerUrl.BASE_URL + ServerUrl.IMG + prod.getUrl_pic())
				.into(imgIcon);
		
		Log.v("BAG_ADAPTER_PIC", ServerUrl.BASE_URL + ServerUrl.IMG + prod.getUrl_pic());

		return convertView;

	}
	// TODO clean
	// public void clearAdapter()
	// {
	// categoryList.clear();
	// notifyDataSetChanged();
	// }
}

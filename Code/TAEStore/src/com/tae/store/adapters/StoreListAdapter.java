package com.tae.store.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tae.store.R;
import com.tae.store.model.Store;

public class StoreListAdapter extends BaseAdapter {

	//TODO pendiente de comprobar si metemos la distancia en la clase Store
	private Context context;
	private ArrayList<Store> storeList;
	private LayoutInflater mInflater;
	
	public StoreListAdapter(Context context, ArrayList<Store> storeList){
		mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		this.storeList = storeList;
	}
	
	@Override
	public int getCount() {
		return storeList.size();
	}

	@Override
	public Object getItem(int position) {
		return storeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return storeList.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.store_item, null);
        }

        
        TextView txtName = (TextView) convertView.findViewById(R.id.txt_store_name);
        TextView txtAddress = (TextView) convertView.findViewById(R.id.txt_store_address);
        TextView txtOpening = (TextView) convertView.findViewById(R.id.txt_store_open);
        TextView txtDistance = (TextView) convertView.findViewById(R.id.txt_store_distance);

        Store store = storeList.get(position);      
        txtName.setText(store.getName());
        txtAddress.setText(store.getAddress()+","+store.getPostCode());
        txtOpening.setText(store.getOpeningHours());
        txtDistance.setText(new DecimalFormat("##.##").format(store.getDistance())+" Mi");

        return convertView;

	}

}

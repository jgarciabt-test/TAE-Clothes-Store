package com.tae.store.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tae.store.R;
import com.tae.store.model.NavDrawerItem;

/**
 * Adapter for navigation drawer
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class NavDrawerListAdapter extends BaseAdapter {

	/** Application context. */
	private Context context;
	/** ArrayList with all NavDrawerItem objects. */
	private ArrayList<NavDrawerItem> navDrawerItems;
	/** LayoutInflater. */
	private LayoutInflater mInflater;

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            Application context.
	 * @param navDrawerItems
	 *            ArrayList with NavDraweItem objects.
	 */
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		txtTitle.setText(navDrawerItems.get(position).getTitle());

		// This can be useful for futures versions
		// displaying count check whether it set visible or not
		if (navDrawerItems.get(position).getCounterVisibility()) {
			txtCount.setText(navDrawerItems.get(position).getCount());
		} else {
			// hide the counter view
			txtCount.setVisibility(View.GONE);
		}

		return convertView;
	}

}

package com.tae.store.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tae.store.R;
import com.tae.store.model.Product;
import com.tae.store.utilities.ServerUrl;

/**
 * Adapter for products list.
 * 
 * @author Jose Garcia
 * @version 1.0
 * @since 2014-10-08
 */
public class ProductListAdapter extends ArrayAdapter<Product> {

	/** Application context. */
	private Context context;
	/** ArrayList with all the Product objects */
	private ArrayList<Product> productList;
	/** LayoutInflater */
	private LayoutInflater mInflater;

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            Application context.
	 * @param textViewResourceId
	 *            ListView Id.
	 * @param productList
	 *            ArrayList with Product objects.
	 */
	public ProductListAdapter(Context context, int textViewResourceId,
			ArrayList<Product> productList) {
		super(context, textViewResourceId, productList);
		this.context = context;
		this.productList = productList;
		mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return productList.size();
	}

	@Override
	public Product getItem(int position) {
		return productList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return productList.indexOf(getItem(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.product_item, null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.iv_product);
		TextView txtName = (TextView) convertView.findViewById(R.id.txt_prodName);
		TextView txtPrice = (TextView) convertView.findViewById(R.id.txt_prodPrice);

		Product prod = productList.get(position);
		txtName.setText(prod.getName());

		txtPrice.setText(context.getResources().getString(R.string.pound_symbol)
				+ String.valueOf(new DecimalFormat("##.##").format(prod.getPrice())));
		Picasso.with(context).load(ServerUrl.BASE_URL + ServerUrl.IMG + prod.getUrl_pic())
				.placeholder(context.getResources().getDrawable(R.drawable.back)).into(imgIcon);

		return convertView;
	}

}

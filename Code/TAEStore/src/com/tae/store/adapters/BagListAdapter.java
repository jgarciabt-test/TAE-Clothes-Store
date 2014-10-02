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

import com.squareup.picasso.Picasso;
import com.tae.store.R;
import com.tae.store.model.Category;
import com.tae.store.utilities.ServerUrl;

public class BagListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Category> categoryList;
    private LayoutInflater mInflater;

    public BagListAdapter(Context context, ArrayList<Category> rowItem) {
        this.context = context;
        this.categoryList = rowItem;
        mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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
        txtPrice.setText("From "+cat.getLower_price());
        Picasso.with(context).load(ServerUrl.BASE_URL+ServerUrl.IMG+cat.getUrl_pic()).placeholder(R.drawable.ic_launcher).into(imgIcon);

        return convertView;

    }
   
    public void clearAdapter()
    {
        categoryList.clear();
        notifyDataSetChanged();
    }
}


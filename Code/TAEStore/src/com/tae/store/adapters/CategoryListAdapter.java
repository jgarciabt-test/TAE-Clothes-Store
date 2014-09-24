package com.tae.store.adapters;

import java.util.ArrayList;
import java.util.List;

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

public class CategoryListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Category> categoryList;

    public CategoryListAdapter(Context context, ArrayList<Category> rowItem) {
        this.context = context;
        this.categoryList = rowItem;
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
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.category_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.iv_category);
        TextView txtName = (TextView) convertView.findViewById(R.id.txt_catName);
        TextView txtPrice = (TextView) convertView.findViewById(R.id.txt_catPrice);

        Category cat = categoryList.get(position);      
        txtName.setText(cat.getName());
        txtPrice.setText(cat.getLower_price());
        Picasso.with(context).load(cat.getUrl_pic()).placeholder(R.drawable.ic_launcher).into(imgIcon);

        return convertView;

    }

}


package com.tae.store.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {

	String name;
	String url_pic;
	String lower_price;
	
	public Category(){
		
	}
	
	public Category(String name, String url_price, String lower_price){
		this.name = name;
		this.url_pic = url_price;
		this.lower_price = lower_price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl_pic() {
		return url_pic;
	}

	public void setUrl_pic(String url_pic) {
		this.url_pic = url_pic;
	}

	public String getLower_price() {
		return lower_price;
	}

	public void setLower_price(String lower_price) {
		this.lower_price = lower_price;
	}
	
	
	private Category(Parcel in){
		name = in.readString();
        url_pic = in.readString();
        lower_price = in.readString();
	}
	
	public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(url_pic);
        out.writeString(lower_price);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

	@Override
	public int describeContents() {
		return 0;
	}
	
}

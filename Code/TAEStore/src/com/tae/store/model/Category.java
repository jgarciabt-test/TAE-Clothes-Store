package com.tae.store.model;

import android.os.Parcel;
import android.os.Parcelable;



/**
* @author      Jose Garcia 
* @version     1.0                 
* @since       2014-10-08         
*/
public class Category implements Parcelable {

	String id;
	String name;
	String url_pic;
	String lower_price;
	String main_cat;
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
        out.writeString(name);
        out.writeString(url_pic);
        out.writeString(lower_price);
        out.writeString(main_cat);
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
	
	public Category(){
		
	}
	
	public Category(String id, String name, String url_price, String lower_price, String main_cat){
		this.id = id;
		this.name = name;
		this.url_pic = url_price;
		this.lower_price = lower_price;
		this.main_cat = main_cat;
	}
	
	public String getMain_cat() {
		return main_cat;
	}

	public void setMain_cat(String main_cat) {
		this.main_cat = main_cat;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
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
		id = in.readString();
		name = in.readString();
        url_pic = in.readString();
        lower_price = in.readString();
        main_cat = in.readString();
	}	
}

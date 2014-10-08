package com.tae.store.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
* @author      Jose Garcia 
* @version     1.0                 
* @since       2014-10-08         
*/
public class Product implements Parcelable {

	private String id;
	private String name;
	private String description;
	private String details;
	private Boolean promoted;
	private String size;
	private float price;
	private String url_pic;
	private String urlPicOffer;

	public Product() {
		size = "";
		urlPicOffer="";
	}

	public Product(String id, String name, String description, String details,
			boolean promoted, int price, String url_pic) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.details = details;
		this.promoted = promoted;
		this.price = price;
		this.url_pic = url_pic;
		urlPicOffer="";
		size = "";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Boolean getPromoted() {
		return promoted;
	}

	public void setPromoted(Boolean promoted) {
		this.promoted = promoted;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getUrl_pic() {
		return url_pic;
	}

	public void setUrl_pic(String pic_url) {
		this.url_pic = pic_url;
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	public String getUrlPicOffer() {
		return urlPicOffer;
	}

	public void setUrlPicOffer(String urlPicOffer) {
		this.urlPicOffer = urlPicOffer;
	}

	private Product(Parcel in) {
		id = in.readString();
		name = in.readString();
		description = in.readString();
		details = in.readString();
		promoted = in.readByte() != 0;
		size = in.readString();
		price = in.readFloat();
		url_pic = in.readString();
		urlPicOffer = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(name);
		out.writeString(description);
		out.writeString(details);
		out.writeByte((byte) (promoted ? 1 : 0));
		out.writeString(size);
		out.writeFloat(price);
		out.writeString(url_pic);
		out.writeString(urlPicOffer);

	}

	   public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
	        public Product createFromParcel(Parcel in) {
	            return new Product(in);
	        }

	        public Product[] newArray(int size) {
	            return new Product[size];
	        }
	    };
}

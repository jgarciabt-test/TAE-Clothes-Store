package com.tae.store.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

	private String id;
	private String name;
	private String description;
	private String details;
	private Boolean promoted;
	private int price;
	private String url_pic;

	public Product() {
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getUrl_pic() {
		return url_pic;
	}

	public void setUrl_pic(String pic_url) {
		this.url_pic = pic_url;
	}

	private Product(Parcel in) {
		id = in.readString();
		name = in.readString();
		description = in.readString();
		details = in.readString();
		promoted = in.readByte() != 0;
		price = in.readInt();
		url_pic = in.readString();
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
		out.writeInt(price);
		out.writeString(url_pic);

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

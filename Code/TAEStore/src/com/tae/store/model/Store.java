package com.tae.store.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
* @author      Jose Garcia 
* @version     1.0                 
* @since       2014-10-08         
*/
public class Store implements Parcelable{
	
	private String id;
	private String name;
	private String address;
	private String postCode;
	private String city;
	private String phone;
	private String openingHours;
	private double latitude;
	private double longitude;
	private double distance = -1;
	
	public Store(){
	}
	
	public Store(String id, String name, String address, String postCode, String city, String phone, String openingHours, double lat, double lng){
		this.id = id;
		this.name = name;
		this.address = address;
		this.postCode = postCode;
		this.city = city;
		this.phone = phone;
		this.openingHours = openingHours;
		this.latitude = lat;
		this.longitude = lng;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	private Store(Parcel in){
		id = in.readString();
		name = in.readString();
		address = in.readString();
		postCode = in.readString();
		city = in.readString();
		phone = in.readString();
		openingHours = in.readString();
		latitude =in.readDouble();
		longitude = in.readDouble();
		distance = in.readDouble();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(id);
		out.writeString(name);
		out.writeString(address);
		out.writeString(postCode);
		out.writeString(city);
		out.writeString(phone);
		out.writeString(openingHours);
		out.writeDouble(latitude);
		out.writeDouble(longitude);
		out.writeDouble(distance);
	}
	public static final Parcelable.Creator<Store> CREATOR = new Parcelable.Creator<Store>() {
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        public Store[] newArray(int size) {
            return new Store[size];
        }
    };
	
}

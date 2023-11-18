package com.company.cla.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private Integer pincode;
	private String city;
	private String state;
	private String country;

	/* ===================default constructor====================== */

	public Address() {
		super();
	}

	/* ===================parameterized constructor====================== */

	public Address(Integer pincode, String city, String state, String country) {
		super();
		this.pincode = pincode;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	/* ===================getters methods====================== */
	public Integer getPincode() {
		return pincode;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getCountry() {
		return country;
	}

	/* ===================setter methods====================== */
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}

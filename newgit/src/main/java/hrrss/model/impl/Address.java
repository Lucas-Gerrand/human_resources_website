package hrrss.model.impl;

import java.io.Serializable;

import hrrss.model.IAddress;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class Address implements IAddress, Serializable {

	
	@Column (name = "STREET")
	private String street;

	@Column (name = "CITY")
	private String city;

	@Column (name = "ZIP_CODE")
	private String zipCode;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}

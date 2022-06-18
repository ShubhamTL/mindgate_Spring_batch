package com.sky.batchprocessing.model;

public class InProduct {

	private String name;
	private double price;
	
	public InProduct() {
		// TODO Auto-generated constructor stub
	}

	public InProduct(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "InProduct [name=" + name + ", price=" + price + "]";
	}
	
	
}

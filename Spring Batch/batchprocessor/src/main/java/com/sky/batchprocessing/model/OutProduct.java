package com.sky.batchprocessing.model;

public class OutProduct {
	private String name;
	private double price;
	private double disPrice;
	
	public OutProduct() {
		// TODO Auto-generated constructor stub
	}

	public OutProduct(String name, double price, double disPrice) {
		super();
		this.name = name;
		this.price = price;
		this.disPrice = disPrice;
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

	public double getDisPrice() {
		return disPrice;
	}

	public void setDisPrice(double disPrice) {
		this.disPrice = disPrice;
	}

	@Override
	public String toString() {
		return "OutProduct [name=" + name + ", price=" + price + ", disPrice=" + disPrice + "]";
	}
	
	
}

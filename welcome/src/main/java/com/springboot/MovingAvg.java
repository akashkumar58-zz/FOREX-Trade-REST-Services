package com.springboot;

public class MovingAvg {
	private String currency_pair;
	private double price;
	
	public MovingAvg() {}
	
	public String getCurrency_pair() {
		return currency_pair;
	}
	public void setCurrency_pair(String currency) {
		this.currency_pair = currency;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
package com.springboot;

public class LimitOrder {
	private String currency_pair;
	private String order_side;
	private int lot_size ;
	private double price;
	private String username;
	
	public String getCurrency_pair() {
		return currency_pair;
	}
	public void setCurrency_pair(String currency_pair) {
		this.currency_pair = currency_pair;
	}
	public String getOrder_side() {
		return order_side;
	}
	public void setOrder_side(String order_side) {
		this.order_side = order_side;
	}
	public int getLot_size() {
		return lot_size;
	}
	public void setLot_size(int lot_size) {
		this.lot_size = lot_size;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}

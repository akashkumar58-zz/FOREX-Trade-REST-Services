package com.springboot;

public class MarketOrder {
	private String currency_pair;
	private String order_side;
	private int lot_size ;
	private String username;
	
	public MarketOrder(){}
	
	public MarketOrder(String currency_pair, String order_side, int lot_size, String uname) {
		super();
		this.currency_pair = currency_pair;
		this.order_side = order_side;
		this.lot_size = lot_size;
		this.username = uname;
	}
	
	public int getLot_size() {
		return lot_size;
	}
	public void setLot_size(int lot_size) {
		this.lot_size = lot_size;
	}
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
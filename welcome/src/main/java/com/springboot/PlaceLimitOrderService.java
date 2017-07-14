package com.springboot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PlaceLimitOrderService {
	boolean globalStatus = false;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public String placeLimitOrder(final LimitOrder order) {
		final String sql = "insert into orders(order_type, username, currency_pair, order_side, "
				+ "price, lot_size, amount, order_status, insert_time) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP())";
		String returnString = "";
		try{
			KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					boolean status = false;
					double latestPrice = findMarketPrice(order.getCurrency_pair());
					double price = order.getPrice();
					if(order.getOrder_side().equalsIgnoreCase("BUY")) {
						status = latestPrice <= price;
					}
					else {
						status = latestPrice >= price;
					}
					globalStatus = status;
					PreparedStatement ps= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, "LIMIT");
					ps.setString(2, order.getUsername());
					ps.setString(3, order.getCurrency_pair());
					ps.setString(4, order.getOrder_side());
					ps.setDouble(5, price);
					ps.setInt(6, order.getLot_size());
					ps.setDouble(7, order.getLot_size() * price);
					ps.setBoolean(8, status);		
					return ps;
				}
			}, holder);
			int newOrderID = holder.getKey().intValue();
			returnString =  "New Order registered with ID = " + newOrderID + "\nOrder Status = ";
			if (globalStatus) {
				String query = "insert into hist_reuters values(?,?,?, CURRENT_TIMESTAMP() )";
				jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement ps= connection.prepareStatement(query);
						ps.setString(1, order.getCurrency_pair());
						ps.setDouble(2, order.getPrice());
						ps.setInt(3, order.getLot_size());		
						return ps;
					}
				});
				returnString += "Executed successfully";
			}
			else {
				returnString += " Pending";
			}
		}
		catch(DataAccessException ex) {
			return "User can not be Authorized!!!";
		}
		return returnString;
	}
	@Transactional(readOnly=true)
	public List<LimitOrder> findAllOrders() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("select * from orders", new LimitRowMapper());

	}
	@Transactional(readOnly=true)
	public double findMarketPrice(String currencyPair) {
		// TODO Auto-generated method stub
		String value = jdbcTemplate.queryForObject("select price from hist_reuters where insert_time="
				+ "(select max(insert_time) from hist_reuters where currency_pair=?)"
				+ "and currency_pair=?", new Object[]{currencyPair, currencyPair}, String.class);
		return Double.parseDouble(value);
	}
	
	@Transactional
	public String cancelTradeRequest(String username, int orderId) {
		int result = jdbcTemplate.update("delete from orders where username = ? and order_id = ?"
				+ " and order_status is false", new Object[]{username, orderId});
		if(result == 1) {
			return "Cancelled Successfully";
		}
		else {
			return "Trade has been already executed";
		}
	}
	
	@Transactional(readOnly=true)
	public List<LimitOrder> searchAllOpenTrades() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("select * from orders where "
				+ "order_status = false", new Object[]{}, new LimitRowMapper());
	}
	
	@Transactional(readOnly=true)
	public int countOpen() {
		String res = jdbcTemplate.queryForObject("select count(1) from orders where order_status is false", 
				new Object[]{}, String.class);
		return Integer.parseInt(res);
	}
}

class LimitRowMapper implements RowMapper<com.springboot.LimitOrder> {
	@Override
	public LimitOrder mapRow(ResultSet rs,int rowNum) throws SQLException {
		LimitOrder order = new LimitOrder();
		order.setCurrency_pair(rs.getString("currency_pair"));
		order.setOrder_side(rs.getString("order_side"));
		order.setLot_size(rs.getInt("lot_size"));
		order.setPrice(rs.getDouble("price"));
		order.setUsername(rs.getString("username"));
		return order;
	}
}
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
public class PlaceMarketOrderService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String placeMarketOrder(final MarketOrder marketOrder) {
		String returnString = "";
		final String sql = "insert into orders(order_type, username, currency_pair, order_side, "
				+ "price, lot_size, amount, order_status, insert_time) "
				+ "values(?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP())";
		try {
			KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					double price = findMarketPrice(marketOrder.getCurrency_pair());
					PreparedStatement ps= connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, "MARKET");
					ps.setString(2, marketOrder.getUsername());
					ps.setString(3, marketOrder.getCurrency_pair());
					ps.setString(4, marketOrder.getOrder_side());
					ps.setDouble(5, price);
					ps.setInt(6, marketOrder.getLot_size());
					ps.setDouble(7, marketOrder.getLot_size() * price);
					ps.setBoolean(8, true);
					return ps;
				}
			}, holder);
			int marketOrderID = holder.getKey().intValue();
			returnString = "New Order registered with ID = " + marketOrderID + 
									"\nOrder Status = Executed";
			String query = "insert into hist_reuters values(?,?,?, CURRENT_TIMESTAMP() )";
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					double price = findMarketPrice(marketOrder.getCurrency_pair());
					PreparedStatement ps= connection.prepareStatement(query);
					ps.setString(1, marketOrder.getCurrency_pair());
					ps.setDouble(2, price);
					ps.setInt(3, marketOrder.getLot_size());		
					return ps;
				}
			});
		}
		catch(DataAccessException ex) {
			return "User can not be Authorized!!!";
		}
		return returnString;
	}
	
	@Transactional(readOnly=true)
	public List<MarketOrder> findAllOrders() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("select * from orders", new OrderRowMapper());

	}
	
	@Transactional(readOnly=true)
	public double findMarketPrice(String currencyPair) {
		// TODO Auto-generated method stub
		String value = jdbcTemplate.queryForObject("select price from hist_reuters where insert_time="
				+ "(select max(insert_time) from hist_reuters where currency_pair=?)"
				+ "and currency_pair=?", new Object[]{currencyPair, currencyPair}, String.class);
		return Double.parseDouble(value);
	}
	@Transactional(readOnly=true)
	public List<MarketOrder> searchAllClosedTrades() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("select * from orders where "
				+ "order_status = true", new Object[]{}, new OrderRowMapper());
	}
}

class OrderRowMapper implements RowMapper<com.springboot.MarketOrder> {
	@Override
	public MarketOrder mapRow(ResultSet rs,int rowNum) throws SQLException {
		MarketOrder order=new MarketOrder();
		order.setCurrency_pair(rs.getString("currency_pair"));
		order.setOrder_side(rs.getString("order_side"));
		order.setLot_size(rs.getInt("lot_size"));
		order.setUsername(rs.getString("username"));
		return order;
	}
}
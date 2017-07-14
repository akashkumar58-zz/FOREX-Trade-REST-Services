package com.springboot;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MovingAvgService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly=true)
	public List<MovingAvg> movingAverage() {
		// TODO Auto-generated method stub
		return jdbcTemplate.query("select * from mov_avg", new Object[]{}, new MovingRowMapper());
	}
	
	@Transactional(readOnly=true)
	public double movingAverage(String pair) {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("select MOVING_AVG from mov_avg where "
				+ "CURRENCY_PAIR = ?", new Object[]{pair}, Double.class);
	}
}
class MovingRowMapper implements RowMapper<com.springboot.MovingAvg> {
	@Override
	public MovingAvg mapRow(ResultSet rs,int rowNum) throws SQLException {
		MovingAvg order=new MovingAvg();
		order.setCurrency_pair(rs.getString("currency_pair"));
		order.setPrice(rs.getDouble("MOVING_AVG"));
		return order;
	}
}
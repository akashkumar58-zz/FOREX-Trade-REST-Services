package com.welcome.welcome;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.springboot.MarketOrder;
import com.springboot.PlaceMarketOrderService;
import com.springboot.OrderApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { OrderApplication.class})
public class PlaceMarketOrderTest {
	@Autowired
	PlaceMarketOrderService pm;
	@Before
	public void setUpDatabase() {
		MarketOrder marketOrder = new MarketOrder();
		marketOrder.setCurrency_pair("JPY/USD");
		marketOrder.setOrder_side("BUY");
		marketOrder.setUsername("suhit");
		marketOrder.setLot_size(300);
		pm.placeMarketOrder(marketOrder);
	}
	
	@Test
	public void insertSingleMarketOrderCompletesSuccessfully() {
		List<MarketOrder> marketOrders = pm.findAllOrders();
		System.out.println(marketOrders.size());
		MarketOrder marketOrder = marketOrders.get(0);
		assertThat("","JPY/USD", equalTo(marketOrder.getCurrency_pair()));
		assertThat("", "BUY", equalTo(marketOrder.getOrder_side()));
		assertThat("", 300, equalTo(marketOrder.getLot_size()));
		assertThat("", "suhit", equalTo(marketOrder.getUsername()));
	}
}
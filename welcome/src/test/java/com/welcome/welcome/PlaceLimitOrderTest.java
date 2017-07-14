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
import com.springboot.LimitOrder;
import com.springboot.OrderApplication;
import com.springboot.PlaceLimitOrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { OrderApplication.class})
public class PlaceLimitOrderTest {
	@Autowired
	PlaceLimitOrderService lm;
	@Before
	public void setUpDatabase() {
		LimitOrder order = new LimitOrder();
		order.setCurrency_pair("USD/EUR");
		order.setOrder_side("BUY");
		order.setUsername("arpan");
		order.setLot_size(200);
		order.setPrice(1.06);
		lm.placeLimitOrder(order);
	}
	@Test
	public void insertSingleLimitOrderCompletesSuccessfully() {
		List<LimitOrder> orders = lm.findAllOrders();
		LimitOrder marketOrder = orders.get(0);
		assertThat("","USD/EUR", equalTo(marketOrder.getCurrency_pair()));
		assertThat("", "BUY", equalTo(marketOrder.getOrder_side()));
		assertThat("", 200, equalTo(marketOrder.getLot_size()));
		assertThat("", 1.06, equalTo(marketOrder.getPrice()));
		assertThat("", "arpan", equalTo(marketOrder.getUsername()));
	}
}
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
@SpringBootTest(classes = {OrderApplication.class})
public class OpenOrderTest {
	@Autowired
	PlaceLimitOrderService lm;
	@Before
	public void setUpDatabase() {
		LimitOrder order = new LimitOrder();
		order.setCurrency_pair("USD/EUR");
		order.setOrder_side("SELL");
		order.setUsername("arpan");
		order.setLot_size(200);
		order.setPrice(2.06);
		lm.placeLimitOrder(order);
		
		order.setCurrency_pair("USD/EUR");
		order.setOrder_side("SELL");
		order.setUsername("akash");
		order.setLot_size(100);
		order.setPrice(0.61);
		lm.placeLimitOrder(order);
	}
	@Test
	public void insertSingleLimitOrderCompletesSuccessfully() {
		List<LimitOrder> orders = lm.findAllOrders();
		int total = orders.size();
		int open = lm.countOpen();
		assertThat("", 1, equalTo(total - open));
		assertThat("", 1, equalTo(open));
	}
}
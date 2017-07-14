package com.welcome.welcome;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
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
public class CancelTradeOrderTest {
	LimitOrder order = new LimitOrder();
	@Autowired
	PlaceLimitOrderService lm;
	
	@Before
	public void setIdOfCancelTrade() {
		order.setCurrency_pair("USD/EUR");
		order.setOrder_side("BUY");
		order.setLot_size(300);
		order.setPrice(1.01);
		order.setUsername("akash");
		lm.placeLimitOrder(order);
		
		order.setOrder_side("BUY");
		order.setLot_size(300);
		order.setPrice(1.06);
		lm.placeLimitOrder(order);
	}
	
	@Test
	public void deleteTradeSuccessfully() {
		String cancelStatus = lm.cancelTradeRequest("akash", 1);
		assertThat(" ", "Cancelled Successfully", equalTo(cancelStatus));
		
		cancelStatus = lm.cancelTradeRequest("akash", 2);
		assertThat(" ", "Trade has been already executed", equalTo(cancelStatus));
	}
}
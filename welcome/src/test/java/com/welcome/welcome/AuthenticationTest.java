package com.welcome.welcome;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.springboot.MarketOrder;
import com.springboot.OrderApplication;
import com.springboot.PlaceMarketOrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { OrderApplication.class})
public class AuthenticationTest {
	@Autowired
	PlaceMarketOrderService pm;	
	@Test
	public void testUser() {
		MarketOrder order1 = new MarketOrder();
		order1.setCurrency_pair("JPY/USD");
		order1.setUsername("abc");
		order1.setLot_size(200);
		order1.setOrder_side("BUY");
		String result = pm.placeMarketOrder(order1);
		assertThat("", "User can not be Authorized!!!", equalTo(result));
	}
}

package com.welcome.welcome;

import static org.junit.Assert.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.springboot.MovingAvgService;
import com.springboot.OrderApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OrderApplication.class} )
public class MovingAverageTest {
	//MovingAvg object = new MovingAvg();
	@Autowired
	MovingAvgService object;
	@Test
	public void matchAvg() {
		double price = object.movingAverage("JPY/USD");
		assertThat("", 0.08, equalTo(price));
		price = object.movingAverage("GPB/USD");
		assertThat("", 1.29, equalTo(price));
		price = object.movingAverage("USD/GPB");
		assertThat("", 0.78, equalTo(price));
		price = object.movingAverage("USD/EUR");
		assertThat("", 1.08, equalTo(price));
		price = object.movingAverage("USD/JPY");
		assertThat("", 113.47, equalTo(price));
		price = object.movingAverage("EUR/USD");
		assertThat("", 0.87, equalTo(price));
	}
}
package com.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	@Autowired
	PlaceMarketOrderService pm;
	@RequestMapping(value="/order/market", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
	public String placeMarketOrder(@RequestBody MarketOrder marketOrder) {
		 return pm.placeMarketOrder(marketOrder);
	}
	
	@Autowired
	PlaceLimitOrderService lm;
	@RequestMapping(value="/order/limit", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
	public String placeLimitOrder(@RequestBody LimitOrder order) {
		 return lm.placeLimitOrder(order);
	}
	@RequestMapping("/cancel/{username}/{orderId}")
	public String getTradeById(@PathVariable String username, @PathVariable int orderId) {
		String cancelStatus = lm.cancelTradeRequest(username, orderId);
		return cancelStatus;
	}
	
	@RequestMapping("/viewOpenTrades")
	public List<LimitOrder> viewOpenTrades() {
		List<LimitOrder> openTrades= (List<LimitOrder>) lm.searchAllOpenTrades();
		return openTrades;
	}
	
	@RequestMapping("/viewClosedTrades")
	public List<MarketOrder> viewClosedTrades() {
		List<MarketOrder> closedTrades= (List<MarketOrder>) pm.searchAllClosedTrades();
		return closedTrades;
	}
	@Autowired
	MovingAvgService ma;
	
	@RequestMapping("/movingAverage")
	public List<MovingAvg> movingAvg() {
		List<MovingAvg> avg = (List<MovingAvg>) ma.movingAverage();
		return avg;
	}
}
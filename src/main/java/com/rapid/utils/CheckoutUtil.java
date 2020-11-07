package com.rapid.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import com.rapid.model.OrderItem;

public class CheckoutUtil {
	
	public static int generateRandomNumber() {
		Random rand = new Random();
	      int upperbound = 1000000;
	        //generate random values from 0-1000000
	      int int_random = rand.nextInt(upperbound); 
	      
	      return int_random;
	}
	
	public static BigDecimal getOrderTotal(List<OrderItem> cartItems) {
		
		BigDecimal total = BigDecimal.ZERO;
		
		for(OrderItem item: cartItems) {
			BigDecimal lineTotal = item.getLineTotal();
			total = total.add(lineTotal);
		}
		
		return total;
		
	}

}

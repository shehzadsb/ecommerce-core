package com.rapid.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.rapid.controller.ProductDataController;
import com.rapid.model.Order;
import com.rapid.model.OrderItem;
import com.rapid.utils.CheckoutUtil;

public class OrderService {
	
	private static final int MAX_QUANTITY_LIMIT = 1000;
	
	public OrderService() {
		
	}
	
	public Order createOrder(List<OrderItem> cartItems) {
		
		Order order = new Order();
		
		
		if(cartItems.size() == 0) {
			order.setErrorMessage("The order was not placed because the cart must have at least 1 item");
			
			return order;
		} else if(isQuantityExceedsMaxLimit(cartItems)) {
			order.setErrorMessage("The quantity of one of the items exceeds the limit");
			
			return order;
		} else {
			for(OrderItem item: cartItems) {
				if(ProductDataController.getProductByName(item.getProduct().getName()) == null) {
					order.setErrorMessage("The item " + item.getProduct().getName() + " doesn't exist in the catalog");
					return order;
				}
			}
		}
		
		
		BigDecimal orderTotal = CheckoutUtil.getOrderTotal(cartItems);
		order.setAmount(orderTotal);		
		order.setOrderNumber(CheckoutUtil.generateRandomNumber());
		order.setOrderDate(new Date());
		order.setSuccessMessage("Your order has been created. Your order number is " + order.getOrderNumber() + " and order total is " + order.getAmount());
		
		return order;
		
		
	}
	
	private boolean isQuantityExceedsMaxLimit(List<OrderItem> cartItems) {
		boolean result = false;
		
		for(OrderItem item: cartItems) {
			if(item.getQuantity() > MAX_QUANTITY_LIMIT) {
				return true;
			}
		}
		return result;
	}
	

}

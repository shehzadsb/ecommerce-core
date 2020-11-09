package com.rapid.order.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import com.rapid.controller.ProductDataController;
import com.rapid.controller.ShoppingCartController;
import com.rapid.model.Customer;
import com.rapid.model.Order;
import com.rapid.model.OrderItem;
import com.rapid.utils.CheckoutUtil;

public class OrderService extends Observable {
	
	private static final int MAX_QUANTITY_LIMIT = 1000;
	
	private Order order = new Order();
	
	public OrderService() {
		
	}
	
	public Order createOrder(List<OrderItem> cartItems) {
		
		
		//Order order = new Order();
		
		if(cartItems.size() == 0) {
			order.setErrorMessage("The order was not placed because the cart must have at least 1 item");
			order.setStatusCode("F");  //failed
			return order;
		} else if(isQuantityExceedsMaxLimit(cartItems)) {
			order.setErrorMessage("The quantity of one of the items exceeds the limit");
			order.setStatusCode("F");  //failed
			return order;
		} else {
			for(OrderItem item: cartItems) {
				if(ProductDataController.getProductByName(item.getProduct().getName()) == null) {
					order.setErrorMessage("The item " + item.getProduct().getName() + " doesn't exist in the catalog");
					order.setStatusCode("F");  //failed
					return order;
				}
			}
		}
		
		//checking limited stock here
		if(isOutOfStock(cartItems)) {
			BigDecimal orderTotal = ShoppingCartController.getOrderTotal();
			Customer customer = new Customer();
			customer.setEmail(ShoppingCartController.getCustomerEmail());
			order.setCustomer(customer);
			order.setAmount(orderTotal);		
			order.setOrderDate(new Date());
			order.setCartLineItems(cartItems);
			order.setStatusCode("F");  //failed
			
			order.setErrorMessage("Your failed because we are running out of stock." 
					+ "\nHere are the order details "
					+ getLineItemsFormatted(cartItems)
					
							
					);
			
			return order;
		}
		
		
		
		
		BigDecimal orderTotal = ShoppingCartController.getOrderTotal();
		Customer customer = new Customer();
		customer.setEmail(ShoppingCartController.getCustomerEmail());
		order.setCustomer(customer);
		order.setAmount(orderTotal);		
		order.setOrderNumber(CheckoutUtil.generateRandomNumber());
		order.setOrderDate(new Date());
		order.setCartLineItems(cartItems);
		order.setStatusCode("S");  //success
		order.setSuccessMessage("Your order has been created. Your order number is " 
		+ order.getOrderNumber() + " and order total is " + order.getAmount()
		+ "\nHere are the order details "
		+ getLineItemsFormatted(cartItems)
		
				
		);
		
		
		
		return order;
		
		
	}
	
	public boolean isOutOfStock(List<OrderItem> cartItems) {
		boolean result = false;
		for(OrderItem item: cartItems) {
			int lineItemQty = item.getQuantity();
			int qtyInStock = item.getProduct().getQuantityInStock();
			
			if(lineItemQty > qtyInStock) {
				return true;
			}
		}
		
		return result;
	}
	
	
	

	public void setOrder(Order order) {
		this.order = order;
		
		setChanged();
        notifyObservers(order);
        
	}

	
	 public Order getOrder() {
			return order;
		}
	 
	 
	
	
	private static String getLineItemsFormatted(List<OrderItem> cartItems) {
		StringBuilder line = new StringBuilder("");
		int count = 1;
		for(OrderItem item: cartItems) {
			
			line.append("\n" + count + ")  Name: " + item.getProduct().getName() + ", Qty: " + item.getQuantity() + ", Line Total: " + item.getLineTotal() );
			count++;
		}
		
		return line.toString();
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

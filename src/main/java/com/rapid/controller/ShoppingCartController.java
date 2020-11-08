package com.rapid.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.rapid.model.OrderItem;
import com.rapid.model.Product;

public class ShoppingCartController {
	
	private static List<OrderItem> cartLineItems = new ArrayList<>();
	
	private static String customerEmail;
	
	public static void addItem(OrderItem orderItem) {
		
		if(cartLineItems.contains(orderItem)) {
			
			String name = orderItem.getProduct().getName();
			OrderItem existingItem = null;
			for(OrderItem item: cartLineItems) {
				if(item.getProduct().getName().equals(name)) {
					existingItem = item;
					break;
				}
				
			}
			existingItem.setQuantity(existingItem.getQuantity() + 1);
			
			
		} else {
			orderItem.setQuantity(1);
			cartLineItems.add(orderItem);
		}
		
		
		for(OrderItem item: cartLineItems) {
			BigDecimal lineTotal = item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())).setScale(2, RoundingMode.CEILING);
			item.setLineTotal(lineTotal);
		}
		
		
		
		
		
	}
	
	public static void setCustomerEmail(String email) {
		customerEmail = email;
	}
	
	public static String getCustomerEmail() {
		return customerEmail;
	}
	
	public static void addItemsToCart(List<String> itemsList) {
		
		for(String item: itemsList) {
		
			Product productToAdd = ProductDataController.getProductByName(item);
			
			if(productToAdd != null) {
				OrderItem orderItem = new OrderItem();
				orderItem.setQuantity(1);
				orderItem.setProduct(new Product(item, productToAdd.getPrice()));
				addItem(orderItem);
				
			}
		}
		
		applyOffer1();
		
		applyOffer2();
		
	}
	
	public static BigDecimal getOrderTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		for(OrderItem item: cartLineItems) {
			BigDecimal lineTotal = item.getLineTotal();
			if(lineTotal != null)
				total = total.add(lineTotal);
		}
		
		return total;
	}
	public static List<OrderItem> getCartItems() {
		return cartLineItems;
	}

	public static void applyOffer1() {
		//Offer 1: buy one get one free on Apples
		//For each Apple line item, double the quantity but don't change the line item total
		
		for(OrderItem item: cartLineItems) {
			if(item.getProduct().getName().equals("Apple")) {
				int newQty = item.getQuantity() * 2;
				item.setQuantity(newQty);
			}
		}
				
		
	}
	
	public static void applyOffer2() {
		//Offer 2: 3 for the price of 2 on Oranges
		//For each Orange line item, for every 2 oranges add 1 to the quantity
		
		for(OrderItem item: cartLineItems) {
			if(item.getProduct().getName().equals("Orange")) {
				int currentQty = item.getQuantity();
				
				if(currentQty == 1) {
					item.setQuantity(currentQty);  // no change
				} else if(currentQty > 1 && currentQty % 2 == 0) {  //if currentQty is an even number
					int multiplier = currentQty / 2;
					int newQty = (multiplier * 3);
					item.setQuantity(newQty);
				} else if(currentQty > 1 && currentQty % 2 != 0) {  //if currentQty is an odd number
					int _qty = currentQty - 1;
					int multiplier = _qty / 2;
					int newQty = (multiplier * 3) + 1;
					item.setQuantity(newQty);
				}
			}
		}
		
	}

	public static void removeItem(String itemName) {
		
		for(OrderItem item: cartLineItems) {
			if(item.getProduct().getName().equals(itemName)) {
				cartLineItems.remove(item);
				break;
			}
			
		}
		
	}

	public static void clearCart() {
		cartLineItems = new ArrayList<>();
	}
	
	public static int getCartSize() {
		return cartLineItems.size();
	}
	
}

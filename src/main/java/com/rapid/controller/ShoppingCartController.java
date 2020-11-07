package com.rapid.controller;

import java.util.ArrayList;
import java.util.List;

import com.rapid.model.OrderItem;

public class ShoppingCartController {
	
	private static List<OrderItem> cartLineItems = new ArrayList<>();
	
	
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
		
	}
	
	public static List<OrderItem> getCartItems() {
		return cartLineItems;
	}

	public static void applyOffer() {
		
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

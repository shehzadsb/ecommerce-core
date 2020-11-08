package com.rapid.notification;

import java.util.Observable;
import java.util.Observer;

import com.rapid.model.Order;

public class MailService implements Observer {
	private Order order;

	
	
	@Override
	public void update(Observable o, Object obj) {
		
		this.order = (Order) obj;
		System.out.println("Order received by the observer. Order number: " + order.getOrderNumber()
		+ ", CustomerEmail: " + order.getCustomer().getEmail());
		
		System.out.println("The event will be published to kafka in the next step");
		
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
	
	

}

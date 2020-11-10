package com.rapid.notification;

import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;

import com.google.gson.Gson;
import com.rapid.kafka.producer.OrderProducer;
import com.rapid.model.Order;

public class MailService implements Observer {
	private Order order;

	
	
	
	@Override
	public void update(Observable o, Object obj) {
		
		this.order = (Order) obj;
		
		String statusCode = order.getStatusCode();
		
		String orderJson = toJsonString(order);
		
		if(statusCode.equals("S")) {  //success
			System.out.println("Order received by the observer. Order number: " + order.getOrderNumber()
			+ ", CustomerEmail: " + order.getCustomer().getEmail());
			
			System.out.println("The event will be published to kafka in the next step");
			
		
			OrderProducer orderProducer = new OrderProducer();
			orderProducer.produceOrder(orderJson);
			
		} else if(statusCode.equals("F")) {  //failed
			System.out.println("Order received by the observer but failed for the following reason:");
			System.out.println(order.getErrorMessage());
			
			System.out.println("Sending an email to customer notifying them the order failed due to stock running out");
		
			OrderProducer orderProducer = new OrderProducer();
			orderProducer.produceOrder(orderJson);
		}
		
	}

	private String toJsonString(Order order) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(order);
		return jsonString;
		
	}
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
	
	

}

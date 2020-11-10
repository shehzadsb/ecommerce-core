package com.rapid.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;

public class OrderProducer {
	

	
	String BOOTSTRAP_SERVER = "127.0.0.1:9092";
	String TOPIC = "order_submitted";
	
	public OrderProducer() {
		// TODO Auto-generated constructor stub
	}
	
	public void produceOrder(String orderJson) {
		//create producer properties
		Properties properties = new Properties();
		
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class.getName());
		
		
		//create the producer
		org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
		
		//create a producer record
		org.apache.kafka.clients.producer.ProducerRecord<String, String> record = new org.apache.kafka.clients.producer.ProducerRecord<String, String>(TOPIC, orderJson);
		//send data - asynchronous
		
		System.out.println("orderJson: " + orderJson);
		System.out.println("Writing order json to the kafka topic");
		
		producer.send(record);	
		
		producer.flush();
		producer.close();
	}

}

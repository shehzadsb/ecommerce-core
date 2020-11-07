package com.rapid.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rapid.controller.ShoppingCartController;
import com.rapid.model.Order;
import com.rapid.model.OrderItem;
import com.rapid.model.Product;
import com.rapid.order.service.OrderService;



public class OrderServiceTest {
	
	@Test
	  void invalidOrder() {
	    final OrderService orderService = new OrderService();
	    
	    //Sending an empty cart to the order
	    List<OrderItem> cartItems = new ArrayList<>();
	    
	    Order order = orderService.createOrder(cartItems);
	    
	    assertThat(order.getErrorMessage()).isEqualTo("The order was not placed because the cart must have at least 1 item");
	    

	    //assertThat(scheme.n()).isEqualTo(5);
	    //assertThat(scheme.k()).isEqualTo(3);
	  }

	@Test
	  void validOrder() {
	    final OrderService orderService = new OrderService();
	    
	    //Sending an empty cart to the order
	    List<OrderItem> cartItems = new ArrayList<>();
	    
	    Product p1 = new Product("Apple", new BigDecimal(0.60));
	    Product p2 = new Product("Orange", new BigDecimal(0.25));
	    
	    
	    cartItems.add(new OrderItem(p1, 1));
	    cartItems.add(new OrderItem(p2, 1));
	    
	    
	    
	    Order order = orderService.createOrder(cartItems);
	    
	    assertThat(order.getSuccessMessage()).contains("Your order has been created. Your order number is");
	    

	   
	  }

	@Test
	  void validateOrderTotal() {
	    final OrderService orderService = new OrderService();
	    
	    //Populating cart items
	    List<OrderItem> cartItems = new ArrayList<>();
	    
	    Product p1 = new Product("Apple", new BigDecimal(0.60));
	    Product p2 = new Product("Apple", new BigDecimal(0.60));
	    Product p3 = new Product("Apple", new BigDecimal(0.60));
	    Product p4 = new Product("Orange", new BigDecimal(0.25));
	    
	    
	    cartItems.add(new OrderItem(p1, 1));
	    cartItems.add(new OrderItem(p2, 1));
	    cartItems.add(new OrderItem(p3, 1));
	    cartItems.add(new OrderItem(p4, 1));
	    
	    
	    
	    Order order = orderService.createOrder(cartItems);
	    
	    BigDecimal orderTotal = order.getAmount();
	    
	    assertThat(orderTotal).isEqualTo(new BigDecimal(2.05).setScale(2, RoundingMode.CEILING));
	    

	   
	  }
	
	  
	  @Test
	  void quantityExceedsLimit() {
		  final OrderService orderService = new OrderService();
		  
		  //Populating cart items
		    List<OrderItem> cartItems = new ArrayList<>();
		    
		    Product p1 = new Product("Apple", new BigDecimal(0.60));
		    Product p2 = new Product("Apple", new BigDecimal(0.60));
		    Product p3 = new Product("Apple", new BigDecimal(0.60));
		    Product p4 = new Product("Orange", new BigDecimal(0.25));
		    
		    
		    cartItems.add(new OrderItem(p1, 1));
		    cartItems.add(new OrderItem(p2, 1));
		    cartItems.add(new OrderItem(p3, 1));
		    cartItems.add(new OrderItem(p4, 1001));
		    
		    
		    
		    Order order = orderService.createOrder(cartItems);
		    
		    
		    
		    assertThat(order.getErrorMessage()).contains("The quantity of one of the items exceeds the limit");
		    
		    
	  }

}

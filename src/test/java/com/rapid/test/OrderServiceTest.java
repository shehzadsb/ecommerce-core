package com.rapid.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rapid.controller.ProductDataController;
import com.rapid.controller.ShoppingCartController;
import com.rapid.model.Order;
import com.rapid.model.OrderItem;
import com.rapid.model.Product;
import com.rapid.notification.MailService;
import com.rapid.order.service.OrderService;



public class OrderServiceTest {
	
	@Test
	  void invalidOrder() {
	    final OrderService orderService = new OrderService();
	    //building product data during runtime
		ProductDataController.buildProductDataList();
	    
	    //Sending an empty cart to the order
	    List<OrderItem> cartItems = new ArrayList<>();
	    Order order = orderService.createOrder(cartItems);
	    
	    assertThat(order.getErrorMessage()).isEqualTo("The order was not placed because the cart must have at least 1 item");
	    
	  }

	@Test
	  void validOrder() {
	    final OrderService orderService = new OrderService();
	    //building product data during runtime
		ProductDataController.buildProductDataList();
	    
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
	  void invalidOrder_ItemDoesntExist() {
	    final OrderService orderService = new OrderService();
	    //building product data during runtime
		ProductDataController.buildProductDataList();
	    
	    //Sending an empty cart to the order
	    List<OrderItem> cartItems = new ArrayList<>();
	    
	    Product p1 = new Product("Banana", new BigDecimal(0.40));
	    Product p2 = new Product("Peach", new BigDecimal(0.35));
	    
	    
	    cartItems.add(new OrderItem(p1, 1));
	    cartItems.add(new OrderItem(p2, 1));
	    
	    
	    
	    Order order = orderService.createOrder(cartItems);
	    
	    assertThat(order.getErrorMessage()).contains("doesn't exist in the catalog");

	   
	  }
	
	@Test
	  void validateOrderTotal() {
	    final OrderService orderService = new OrderService();
	    
	    //building product data during runtime
		ProductDataController.buildProductDataList();
	    
	    
	    
	    Product p1 = new Product("Apple", new BigDecimal(0.60));
	    Product p2 = new Product("Apple", new BigDecimal(0.60));
	    Product p3 = new Product("Apple", new BigDecimal(0.60));
	    Product p4 = new Product("Orange", new BigDecimal(0.25));
	    
	    
	    ShoppingCartController.addItem(new OrderItem(p1, 1));
	    ShoppingCartController.addItem(new OrderItem(p2, 1));
	    ShoppingCartController.addItem(new OrderItem(p3, 1));
	    ShoppingCartController.addItem(new OrderItem(p4, 1));
	    
	    ShoppingCartController.applyOffer1();
	    ShoppingCartController.applyOffer2();
	    
	    
	    List<OrderItem> cartItems = ShoppingCartController.getCartItems();
	    Order order = orderService.createOrder(cartItems);
	    BigDecimal orderTotal = order.getAmount();
	    
	    assertThat(new BigDecimal(2.05).setScale(2, RoundingMode.CEILING)).isEqualTo(orderTotal);

	  }
	
	
	@Test
	  void validateOrderLineItemQtyAndTotals() {
	    final OrderService orderService = new OrderService();
	    
	    
	    ShoppingCartController.clearCart();
	    
	    List<String> itemsArray = new ArrayList<>();
	    itemsArray.add("Apple");
	    itemsArray.add("Apple");
	    itemsArray.add("Apple");
	    itemsArray.add("Orange");
	    itemsArray.add("Orange");
	    
	    ShoppingCartController.addItemsToCart(itemsArray);  
	    List<OrderItem> cartItems = ShoppingCartController.getCartItems();
	    
	    
	    Order order = orderService.createOrder(cartItems);
	    List<OrderItem> orderCartItems = order.getCartLineItems();
	    
	    Integer appleQty = null;
	    BigDecimal appleLineTotal = null;
	    for(OrderItem item: orderCartItems) {
			if(item.getProduct().getName().equals("Apple")) {
				appleQty = item.getQuantity();
				appleLineTotal = item.getLineTotal();
			}
		}
	    
	    assertThat(new Integer(6)).isEqualTo(appleQty);
	    assertThat(new BigDecimal(1.8).setScale(1, RoundingMode.FLOOR)).isEqualTo(appleLineTotal.setScale(1, RoundingMode.CEILING));
		
	    Integer orangeQty = null;
	    BigDecimal orangeLineTotal = null;
	    for(OrderItem item: orderCartItems) {
			if(item.getProduct().getName().equals("Orange")) {
				orangeQty = item.getQuantity();
				orangeLineTotal = item.getLineTotal();
			}
		}
	    
	    assertThat(new Integer(3)).isEqualTo(orangeQty);
	    assertThat(new BigDecimal(0.50).setScale(1, RoundingMode.FLOOR)).isEqualTo(orangeLineTotal.setScale(1, RoundingMode.CEILING));
			
	    
	  }
	
	  
	  @Test
	  void quantityExceedsLimit() {
		  final OrderService orderService = new OrderService();
		   //building product data during runtime
		  ProductDataController.buildProductDataList();
		  
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
	  
	  @Test
	  void testOrderReeivedFromObservableToObserver() {
		    OrderService orderService = new OrderService();  //Observable
			MailService mailService = new MailService();     //Observer
			
			
		    //building product data during runtime
			ProductDataController.buildProductDataList();
		    
		    //Sending an empty cart to the order
		    List<OrderItem> cartItems = new ArrayList<>();
		    
		    Product p1 = new Product("Apple", new BigDecimal(0.60));
		    Product p2 = new Product("Orange", new BigDecimal(0.25));
		    
		    
		    cartItems.add(new OrderItem(p1, 1));
		    cartItems.add(new OrderItem(p2, 1));
			
			Order order = orderService.createOrder(cartItems);
			
			orderService.addObserver(mailService);
			orderService.setOrder(order);
			
			    
			assertThat(mailService.getOrder().getSuccessMessage()).contains("Your order has been created. Your order number is");
			    

			  

	  }

}

package com.rapid.console;

import java.util.ArrayList;
import java.util.List;

import com.rapid.controller.ProductDataController;
import com.rapid.controller.ShoppingCartController;
import com.rapid.model.Order;
import com.rapid.model.OrderItem;
import com.rapid.model.Product;
import com.rapid.order.service.OrderService;

public class Console {

	/**
	 * The console interface to create simple orders of shopping goods
	 *
	 * @author Shahzad Anjum
	 */
	
	public static void main(String[] args) {
		try {
			
			int a = 2;
			int b = a % 3;
			
			
			//building product data during runtime
			ProductDataController.buildProductDataList();

            if (args.length < 1) {
                System.err.println("need at least one command");
                help();
                System.exit(-1);
            }
            if (args[0].equals("add-items-to-cart-and-place-order")) {
            	if (args.length > 1 && args[1] != null) {
            		
            		
            		List<String> itemsList = new ArrayList<>();
            		
            		for(int i=1; i<args.length; i++) {
            			itemsList.add(args[i]);
            		}
            		
            			
            		ShoppingCartController.addItemsToCart(itemsList);      
            		//System.out.println(itemsList.toString() + " items added to the cart.");
            		
            		placeOrder();
            		
            	}
            	else 
            	{
                    System.err.println("must have at least one command");
            	}
            	
            }
            else if (args[0].equals("clear-cart")) {
            	
            }
            else if (args[0].equals("place-order")) {
            	List<OrderItem> cartItems = ShoppingCartController.getCartItems();
            	if (cartItems.size() > 1) {
            		
            		
            		placeOrder();
            	}
            	else 
            	{
                    System.err.println("can't place the order because cart is empty");
            	}
                
            } 
            else if (args[0].equals("help")) {
                help();
            } else {
                System.err.println("unknown action");
                help();
            }

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            System.exit(-1);
        }

	}
	
	
	
	
	 private static void placeOrder() throws Exception {
	        
		OrderService orderService = new OrderService();
		
		List<OrderItem> cartItems = ShoppingCartController.getCartItems();
		
		Order order = orderService.createOrder(cartItems);
		
		if(order != null && order.getErrorMessage() != null) {
			 System.err.println(order.getErrorMessage());
		} else if(order != null && order.getSuccessMessage() != null) {
			System.out.println(order.getSuccessMessage());
		} else {
			System.err.println("Unknown error while placing an order");
		}
		
		
	}
	 
	
	/**
     * prints help to STDOUT
     */
    private static void help() {
        System.out.println("java -jar ecommerce-backend.jar add-items-to-cart-and-place-order <ItemName1> <ItemName2> ...");
        System.out.println("java -jar ecommerce-backend.jar clear-cart");
        System.out.println("java -jar ecommerce-backend.jar place-order");
        
        System.out.println("--");
        System.out.println("NOTE: ItemName should have no spaces");
    }

}

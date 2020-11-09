package com.rapid.console;

import java.util.ArrayList;
import java.util.List;

import com.rapid.controller.ProductDataController;
import com.rapid.controller.ShoppingCartController;
import com.rapid.model.Order;
import com.rapid.model.OrderItem;
import com.rapid.model.Product;
import com.rapid.notification.MailService;
import com.rapid.order.service.OrderService;
import com.rapid.utils.ProfileValidator;

public class Console {

	/**
	 * The console interface to create simple orders of shopping goods
	 *
	 * @author Shahzad Anjum
	 */
	
	public static void main(String[] args) {
		try {
			
			
			//building product data during runtime
			ProductDataController.buildProductDataList();

            if (args.length < 1) {
                System.err.println("need at least one command");
                help();
                System.exit(-1);
            }
            if (args[0].equals("add-items-to-cart-and-place-order")) {
            	if (args.length > 1 && args[1] != null) {
            		
            		String customerEmail = args[args.length - 1];
            		
            		if(!ProfileValidator.validateEmailAddress(customerEmail)) {
            			System.err.println("the last argument should be a valid email address");
            			help();
                        System.exit(-1);
            		}
            		
            		List<String> itemsList = new ArrayList<>();
            		
            		for(int i=1; i<args.length - 1; i++) {
            			itemsList.add(args[i]);
            		}
            			
            		ShoppingCartController.addItemsToCart(itemsList);      
            		//System.out.println(itemsList.toString() + " items added to the cart.");
            		
            		ShoppingCartController.setCustomerEmail(customerEmail);
            		
            		placeOrder();
            		
            	}
            	else 
            	{
                    System.err.println("must have at least one command");
            	}
            	
            }
            else if (args[0].equals("clear-cart")) {
            	ShoppingCartController.clearCart();
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
	        
		OrderService orderService = new OrderService();  //Observable
		MailService mailService = new MailService();     //Observer
		
		List<OrderItem> cartItems = ShoppingCartController.getCartItems();
		
		Order order = orderService.createOrder(cartItems);
		
		
		
		if(order != null && order.getErrorMessage() != null) {
			 System.err.println(order.getErrorMessage());
		} else if(order != null && order.getSuccessMessage() != null) {
			System.out.println(order.getSuccessMessage());
		} else {
			System.err.println("Unknown error while placing an order");
		}
		
		//MailService (observer) is subscribing to the OrderService (observable), will receive order object
		orderService.addObserver(mailService);
		orderService.setOrder(order);
		
	}
	 
	
	/**
     * prints help to STDOUT
     */
    private static void help() {
        System.out.println("java -jar ecommerce-backend.jar add-items-to-cart-and-place-order <ItemName1> <ItemName2> ...<CustomerEmail>");
        System.out.println("java -jar ecommerce-backend.jar clear-cart");
        System.out.println("--");
        System.out.println("NOTE: ItemName should have no spaces");
    }

}

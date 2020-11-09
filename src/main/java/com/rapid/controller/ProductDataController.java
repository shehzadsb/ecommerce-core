package com.rapid.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.rapid.model.Product;

public class ProductDataController {

	
	private static List<Product> productDataList = new ArrayList<>();
	
	public static List<Product> buildProductDataList() {
		productDataList.add(new Product("Apple", new BigDecimal(0.60), 5));
		productDataList.add(new Product("Orange", new BigDecimal(0.25), 3));
		
		return productDataList;
	}
	

	public static List<Product> getProductDataList() {
		
		return productDataList;
	}
	
	public static Product getProductById(Integer productId) {
		
		Product product = productDataList.stream()
				  .filter(p -> productId.equals(p.getProductId()))
				  .findAny()
				  .orElse(null);
		
		
		return product;
	}
	
	public static Product getProductByName(String name) {
		
		Product product = productDataList.stream()
				  .filter(p -> name.equals(p.getName()))
				  .findAny()
				  .orElse(null);
		
		
		return product;
	}
	
	
}

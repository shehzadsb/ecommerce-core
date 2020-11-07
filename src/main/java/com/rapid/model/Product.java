package com.rapid.model;

import java.math.BigDecimal;

public class Product {
	private Integer productId;
	private String name;
	private BigDecimal price;
	
	
	public Product(Integer productId, String name, BigDecimal price) {
		this.productId = productId;
		this.name = name;
		this.price = price;
	}

	public Product(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
	
}

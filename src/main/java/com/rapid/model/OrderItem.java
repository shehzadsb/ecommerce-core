package com.rapid.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderItem {
	
	private Integer itemId;
	private Product product;
	private Integer quantity;
	private String description;
	private BigDecimal lineTotal;
	
	public OrderItem(Product product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public OrderItem() {
		
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getLineTotal() {
		/*BigDecimal total = this.product.getPrice().multiply(new BigDecimal(this.quantity));
		this.lineTotal = total;
		this.lineTotal = lineTotal.setScale(2, RoundingMode.CEILING);*/
		return this.lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}
	
	
	@Override
	public boolean equals(Object obj)
	{
		 if( obj == null)   
			 return false;
	     if ( this == obj ) 
	    	 return true;
	     if ( !(obj instanceof OrderItem) ) 
	    	 return false;
	        	 
	     OrderItem orderItem = (OrderItem) obj;
	     return	product.getName().equals(orderItem.getProduct().getName());

	}

	
	
	
	

}

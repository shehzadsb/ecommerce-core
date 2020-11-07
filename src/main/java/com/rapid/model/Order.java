package com.rapid.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
	
	private Integer orderNumber;
	private Date orderDate;
	private String transactionId;
	private String statusCode;  
	private BigDecimal amount;
	private List<OrderItem> cartLineItems;
	
	private String errorMessage;
	private String successMessage;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public List<OrderItem> getCartLineItems() {
		return cartLineItems;
	}
	public void setCartLineItems(List<OrderItem> cartLineItems) {
		this.cartLineItems = cartLineItems;
	}
	
	
	

}

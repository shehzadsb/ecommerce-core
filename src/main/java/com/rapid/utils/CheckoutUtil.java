package com.rapid.utils;


import java.util.Random;



public class CheckoutUtil {
	
	public static int generateRandomNumber() {
		Random rand = new Random();
	      int upperbound = 1000000;
	        //generate random values from 0-1000000
	      int int_random = rand.nextInt(upperbound); 
	      
	      return int_random;
	}
	
	

}

package com.devon.myretailtarget.model;

public class Price {

	private Double value;
	
	private String currency_code;
	  
    public Price() {
    	
    }
    
    public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
    
   public String getCurrencyCode() {
		return currency_code;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currency_code = currencyCode;
	}
   
    public Price(Double value, String currency_code) {
        this.value = value;
        this.currency_code = currency_code;
    }  
 
}

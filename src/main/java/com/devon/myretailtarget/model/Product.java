package com.devon.myretailtarget.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

@Document(collection = "products")
public class Product {
	
    private int id;
    
    @JsonInclude()
    @Transient
    private String name;
    private Price current_price;
	
	public Product() {
		
	}
	
    public Product(int id, String name, Price current_price) {
        this.id = id;
        this.name = name;
        this.current_price = current_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getCurrentPrice() {
        return current_price;
    }

    public void setCurrentPrice(Price current_price) {
        this.current_price = current_price;
    }
    
}

package com.devon.myretailtarget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devon.myretailtarget.exceptions.ProductNotFoundException;
import com.devon.myretailtarget.exceptions.UpdateValidationException;
import com.devon.myretailtarget.model.Product;
import com.devon.myretailtarget.repository.ProductRepository;
import com.devon.myretailtarget.repository.RedskyRepository;

@RestController
public class ProductRESTController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private RedskyRepository redSkyRepository;
    @GetMapping(value = "/")
    public String home(){
        return "Hello Target World!";
    }
    
	// Responds to an HTTP GET request at /products/{id} and delivers product data as 
	// JSON (where {id} will be a number. 
	@GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getProduct(@PathVariable Integer id) {
		Product product = new Product(id, null, null);
		
		try {
			String productTitle = redSkyRepository.getProductTitleByProductId(id);

			// Retrieve Title from RedSky
	        if(productTitle == null) {
				throw new ProductNotFoundException("product title not found for product id: " + id);
	        } else {
	        	product.setName(productTitle);
	        }

	        // Retrieve rest of data from Mongo
	        Product dataFromDatabase = productRepository.findById(id).orElse(null);
	        if (dataFromDatabase == null) {
				throw new ProductNotFoundException("product pricing not found for product id: " + id);
	        } else {
	        	product.setCurrentPrice(dataFromDatabase.getCurrentPrice());
	        }
        
		} catch (ProductNotFoundException ex) {
			ex.printStackTrace();
		}
		
        return product;
		
	}
	
	// BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON 
	// request body similar to the GET response, and updates the product’s price in the data store.
	@PutMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product updateProductPricing(@PathVariable Integer id, @RequestBody Product product) {
		Product returnedProduct = new Product();
		
		try {
			if (id != product.getId()) {
	            throw new UpdateValidationException("hold up... IDs (" + id + " <> " + product.getId() + ") do NOT match");
	        }
	        if (productRepository.existsById(id) == false) {
	            throw new UpdateValidationException("entity with the given ID (" + id + ") does NOT exist");
	        }
	        
	        returnedProduct = productRepository.save(product);
	        
		} catch (UpdateValidationException ex) {
			ex.printStackTrace();
		}
        
        return returnedProduct;
	}
}

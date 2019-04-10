package com.devon.myretailtarget.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.devon.myretailtarget.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductRESTControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void testHelloTargetWorld() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort + "/";
        URI uri = new URI(baseUrl);
     
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
         
        //Verify request succeeded
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().contains("Hello Target World!"));
    }
    
    @Test
    public void testProductsNoID() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:" + randomServerPort + "/products";
        URI uri = new URI(baseUrl);
     
        try {
        	 restTemplate.getForEntity(uri, String.class);
        } catch(HttpClientErrorException ex) {
            //Verify request failed '$NotFound: 404 null'
            assertEquals(404, ex.getRawStatusCode());
        }       
    }
    
    @Test
    public void testProductsWithKnownID() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        
        final String knownID = "13860428";
        final String baseUrl = "http://localhost:" + randomServerPort + "/products/" + knownID;
        final String expected = "{\"id\":" + knownID + ",\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":13.49,\"currency_code\":\"USD\"}}";

        URI uri = new URI(baseUrl);
     
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        
        //Verify request succeeded
        assertEquals(200, result.getStatusCodeValue());
        try {
			JSONAssert.assertEquals(expected, result.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testProductsWithUnKnownID() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        
        // Console/Log Exception: "unable to locate product using product id: 12345678"
        final String unKnownID = "12345678";
        final String baseUrl = "http://localhost:" + randomServerPort + "/products/" + unKnownID;
        final String expected = "{\"id\":" + unKnownID + ",\"name\":null,\"current_price\":null}";

    	URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        
        //Verify request succeeded
        assertEquals(200, result.getStatusCodeValue());
        try {
			JSONAssert.assertEquals(expected, result.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}

    }
    
    @Test
    public void testProductsWithoutPrice() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        
        // Console/Log Exception:  com.devon.myretailtarget.exceptions.ProductNotFoundException: 
        // unable to locate product using product id: 15117729
        final String id = "15117729";
        final String baseUrl = "http://localhost:" + randomServerPort + "/products/" + id;
        final String expected = "{\"id\":" + id + ",\"name\":null,\"current_price\":null}";

    	URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        
        //Verify request succeeded
        assertEquals(200, result.getStatusCodeValue());
        try {
			JSONAssert.assertEquals(expected, result.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testPutWithDifferentIDs() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        
        final String id = "13860428";
        final String baseUrl = "http://localhost:" + randomServerPort + "/products/" + id;
        
        // Console/Log Exception:  com.devon.myretailtarget.exceptions.UpdateValidationException: 
        // hold up... IDs (13860428 <> 13860429) do NOT match
        final String updString = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":77.77,\"currency_code\":\"USD\"}}";

        URI uri = new URI(baseUrl);
     
        Product product = restTemplate.getForObject(uri, Product.class);
        assertNotNull(product);
        assertEquals(Integer.parseInt(id), product.getId());
  
        HttpHeaders headers = new HttpHeaders();
        List <MediaType> mediaTypeList = new ArrayList<MediaType>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypeList);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> requestEntity = new HttpEntity<String>(updString, headers);
        
        // Create the HTTP PUT request
        ResponseEntity<String> updatedProd = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);
        
        // Check...
        assertEquals(200, updatedProd.getStatusCodeValue());
        assertNotNull(updatedProd);
        System.out.println(updatedProd.getBody());
        //assertNotEquals(Integer.parseInt(id), updatedProd.getBody().getId());
    }

    @Test
    public void testPutWithSameID() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        
        final String id = "13860428";
        final String baseUrl = "http://localhost:" + randomServerPort + "/products/" + id;
        final String origBody = "{\"id\":" + id + ",\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":13.49,\"currency_code\":\"USD\"}}";
        final String updBody = "{\"id\":13860428,\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\":77.77,\"currency_code\":\"GBP\"}}";

        URI uri = new URI(baseUrl);
     
        Product product = restTemplate.getForObject(uri, Product.class);
        assertNotNull(product);
        assertEquals(Integer.parseInt(id), product.getId());
  
        HttpHeaders headers = new HttpHeaders();
        List <MediaType> mediaTypeList = new ArrayList<MediaType>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypeList);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> requestEntity = new HttpEntity<String>(updBody, headers);
        
        // Create the HTTP PUT request
        ResponseEntity<String> updatedProd = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);
        
        // Check...
        assertEquals(200, updatedProd.getStatusCodeValue());
        assertNotNull(updatedProd);
        try {
			JSONAssert.assertEquals(updBody, updatedProd.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        // Reset...
        requestEntity = new HttpEntity<String>(origBody, headers);
        
        // Create the HTTP PUT request
        ResponseEntity<String> resetProd = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);

        assertEquals(200, resetProd.getStatusCodeValue());
        assertNotNull(resetProd);
        try {
			JSONAssert.assertEquals(origBody, resetProd.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
}

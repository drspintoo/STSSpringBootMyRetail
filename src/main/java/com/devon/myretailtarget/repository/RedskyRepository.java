package com.devon.myretailtarget.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.devon.myretailtarget.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class RedskyRepository {
    private static final String REDSKY_URL = "https://redsky.target.com/v2/pdp/tcin/";
    // Excludes
    private static final String REDSKY_EXCLUDES = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    public String getProductTitleByProductId(Integer productId) throws ProductNotFoundException {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", productId.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        
        // Full Example : http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics
        String productUrl = REDSKY_URL + productId + REDSKY_EXCLUDES;

        try {
            ResponseEntity<String> responseEntity     = restTemplate.getForEntity(productUrl, String.class, uriVariables);
            Map<String, Map> responseBodyMap          = objectMapper.readValue(responseEntity.getBody(), Map.class);

            Map<String, Map> productMap               = responseBodyMap.get("product");
            Map<String, Map> itemMap                  = productMap.get("item");
            Map<String, String> productDescriptionMap = itemMap.get("product_description");
            String productTitle                       = productDescriptionMap.get("title");

            return productTitle;
        } catch (Exception e) {
            throw new ProductNotFoundException("unable to locate product using product id: " + productId);
        }

    }
}

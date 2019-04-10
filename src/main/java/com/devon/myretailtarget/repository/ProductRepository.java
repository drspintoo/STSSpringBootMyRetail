package com.devon.myretailtarget.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.devon.myretailtarget.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer>{

}

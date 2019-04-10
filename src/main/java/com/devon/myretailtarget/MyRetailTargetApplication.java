package com.devon.myretailtarget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.devon.myretailtarget.repository")
public class MyRetailTargetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyRetailTargetApplication.class, args);
	}

}

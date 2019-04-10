# STSSpringBootMyRetail
Technical Assessment Case Study -- myRetail RESTful service

## Technology Stack
MongoDB, MongoDB Compass, Spring Tool Suite (STS), Spring, Spring Boot, Maven, Java 1.8, Mac OS X

## Narrative...
>myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. 
>
>The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 
>
>Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.
>
>Build an application that performs the following actions: 
>- Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. 
>- Example product IDs: 15117729, 16483589, 16696652, 16752456, 15643793) 
>- Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
>- Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)
>- Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics
>- Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response.
>- BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store. 


## Technology Used
Spring Tool Suite (STS)  -- Download/Install from https://spring.io/tools
  - This is the IDE used to write/build the code, and comes with support for Spring/Spring Boot/Maven.
  
Java 1.8 & Maven -- Download/Install; set environment variables.

MongoDB --  Download from https://www.mongodb.com/download-center/community
  - Install MongoDB on Mac OS X -- http://www.codebind.com/mongodb/install-mongodb-mac-os-x/
  - Start the MongoDB Server

MongoDB Compass GUI for MongoDB --  Download from https://www.mongodb.com/download-center/compass
  - Connect to MongoDB  -- This will be used to manage our no-sql database server.


## Instructions for Building and Running 

Create Database via MongoDB Compass 
 - Create the "productsDatabase" database 
 - Create "products" collection in that database 
 - Insert a single document, as follows, that is representative of the EXAMPLE Document -- https://docs.mongodb.com/manual/tutorial/insert-documents/

	```
	1 _id:13860428                                      Int32
	2 current_price:Object                              Object
	3	value:"13.49"                                   String
	4	currency_ode:"USD"                              String
	5 _class:"com.devon.myretailtarget.model.Product"   String
	```


Steps for Setting up and Starting Project/Application
  - Download the code repository to your location
  - Open as a Maven project in your (STS) IDE
  - Perform a 'mvn clean -e -X install' from the Mac OS terminal/console
  - From your (STS) Boot Dashboard select the project and choose Start
  - 
 
## Evidence/Results
 - Unit test results will be generated when the above Maven command is executed.
 - Postman screen shots are included in project folder 'postmanresults'.



 
  
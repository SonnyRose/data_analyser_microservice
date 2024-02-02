# This is one part of a massive project called "data-microservice".
## This part of "data_analyser_microservice" performs the function of analysing and saving data to database using Apahce Kakfa and Liquibase.

After receiving data from "data_generator_microservicе", "data-analyser-microservice" processes it, structures and stores it in a database using PostgreSQL and integrates it into Liqubase.

After all movements "data_analyser_microservice" send already structured and saved data sends to "data_store_microservice" where incoming data finally structures and statistics of incoming data are created.
After all movements "data_analyser_microservice" send already structured and saved data sends to "data_store_microservice" where incoming data finally structures and statistics of incoming data are created.

Full list of technologies used for the microservice "data_ana_microservice" :

Java 17
 - Spring 3.2.2
 - Jedis 5.1.0
 - MapStruct 1.5.5Final
 - MapStruct Processor 1.5.5Final
 - Spring Kafka 3.1.1
 - JUnit5 5.0.0 ALPHA
 - Mockito 5.9.0
 - Jcabi 0.29.0
 - Gson 2.10.1
 - PostgreSQL 42.7.1
 - Liquibase 4.25.1

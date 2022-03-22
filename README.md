# Authentication and Authorization using SpringSecurity and JWT

This is a small project built using Spring Security that authenticates a user and authorizes that user based on their role.  It uses a JSON Web Token to authenticate users and role-based authorization to protect specific URLs. All URL's are tested using the postman API. 

This project was built using information provided in the tutorial found here: https://www.getarrays.io/p/spring-security-with-json-web-token-refresh-token

Before running this project for the first time, please make sure to create a Schema in your PostgreSQL based relational database or edit the application.properties file in the project to change the SQL dialect. 

All test data is already included and will be automatically entered into the database whenever the application is run through the use of CommandLineRunner.  You may add additional data as you deem necessary. Please make sure, if you manually add data to your relational database, to change the hbm2ddlauto command in the application.properties file from "create" to "update" and delete/comment out the CommandLineRunner in the Main Class.  

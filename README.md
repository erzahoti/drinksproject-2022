# Drinks

## Project description
The task is to implement the backend logic to order different drinks.
The drinks available are:
 1. Italian Coffee
 2. American Coffee
 3. Chocolate

There are different APIs that allow multiple functionality such as:
 * Get all drinks
 * Add drinks to the bucket
 * Update bucket order (e.g modify quantity of drinks)
 * Apply discount code
 * Pay

You can think of buckets as orders or shopping carts (if it were an online store).

To run this project you should have already installed Java 11 (Set JAVA_HOME Path), PostgreSQL 11, Apache Maven

## Before 1st time running
1. Create empty database `drinks`, user: erza, password: erza.

`mvn clean install` (Unit tests will be running as well)

## Development server
Run `mvn spring-boot:run`.

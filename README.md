[![Codacy Badge](https://app.codacy.com/project/badge/Grade/369fc654c03840348a1a54f6485f980f)](https://www.codacy.com/gh/DLukin911/restaurant_voting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DLukin911/restaurant_voting&amp;utm_campaign=Badge_Grade)
[![Build Status](https://app.travis-ci.com/DLukin911/restaurant_voting.svg?branch=master)](https://app.travis-ci.com/DLukin911/restaurant_voting)

# Restaurant voting system
## Task

Design and implement a REST API using Hibernate/Spring/Spring-Boot without frontend.

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    * If it is before 11:00 we assume that he changed his mind.
    * If it is after 11:00 then it is too late, vote can't be changed.

Each restaurant provides a new menu each day.

## Overview

Start your server as a simple Spring-Boot application
```
mvn spring-boot:run
```
Or package it then run it as a Java application
```
mvn package
java -jar target/restaurant_voting-{VERSION}.jar
```
You can view the API documentation in swagger-ui by pointing to
http://localhost:8080/swagger-ui.html

## Credentials:
```
LOGIN                        PASSWORD

admin@restaurant.com         admin
user1@gmail.com              password
user2@gmail.com              password
user3@gmail.com              password
userfortest@gmail.com        password
userfortestapi@gmail.com     password
```
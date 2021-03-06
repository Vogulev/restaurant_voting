[![Build Status](https://app.travis-ci.com/Vogulev/restaurant_voting.svg?branch=master)](https://app.travis-ci.com/Vogulev/restaurant_voting)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/fefa4c24b8ff472b8024fd5523859198)](https://www.codacy.com/gh/Vogulev/restaurant_voting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Vogulev/restaurant_voting&amp;utm_campaign=Badge_Grade)  
[JavaOps.ru](https://javaops.ru/view/topjava) Graduation project  
====================================================================================  
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

### **The task is:**

    Build a voting system for deciding where to have lunch.

    2 types of users: admin and regular users
    Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
    Menu changes each day (admins do the updates)
    Users can vote on which restaurant they want to have lunch at
    Only one vote counted per user
    If user votes again the same day:
    If it is before 11:00 we assume that he changed his mind.
    If it is after 11:00 then it is too late, vote can't be changed
    Each restaurant provides a new menu each day.

    As a result, provide a link to github repository. It should contain the code, README.md with API documentation 
    and couple curl commands to test it (better - link to Swagger).

    P.S.: Make sure everything works with latest version that is on github :)
    P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

[REST API documentation](http://localhost:8080/swagger-ui.html)

Креденшелы:

    User:  user@yandex.ru / password
    Admin: admin@gmail.com / admin
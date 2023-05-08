# A Student Attention Tracking System

A Student Attention Tracking System is designed for measuring the attentiveness of students during online classes. It is aplatform for students and professors, where professors can understand the average attention paid during their class and where students can track their classes by clusters and attend online classes.

It consists of a backend API, front end and a machine learning model.
                         
- endpoint for backend: http://localhost:8080/                
- endpoint for frontend: ?    
- endpoint for Flask: http://localhost:5001/ 
                         
# Prerequisites                         
JDK 17                        
Spring Boot                         
Gradle 7.6                       
PostgreSql    
Python 3.10.10
Flask
cv2


                         
# Data                         
Data is stored in PostgreSql database                                    
Tables:
  Schema: face_recognition
    attention                         
    cluster                         
    course                         
    link_course_cluster                         
    link_course_user
    
*last three are enum tables*                         
                         
![diagram](https://user-images.githubusercontent.com/67556986/99569478-1d901500-29ea-11eb-98fc-cbc6adf0dff1.png)
                         

# Libraries used                         
Hibernate Validation API                         
Swagger API: http://localhost:8080/swagger-ui.html#/                         
Lombok                         
JSON Library                         
                         
                         
# External tools                         
Postman                         

# Features                         
- User and To-do List CRUD operations                         
- Logger                         
- Custom exceptions                         
- Spring security                         
- Unit tests                         
                         
# Package structure                         
                         
ws: sub packages : controller , dto , exception, converter)                          
core: sub packages : model, service, serviceImpl                         
infrastructure: subpackages : entity, repositories                         
utils: enumeration                         
visibility:                         
ws -> core -> infrastructure                         
object  conversation: model mapper                            
                         
![Uml](https://user-images.githubusercontent.com/67556986/100229933-67bf4c00-2f3e-11eb-9bd7-41ed34123fb2.png)
                         
                         
# Instructions: getting started                         
Content-Type: application/json;charset=UTF-8 Accept: application/json;charset=UTF-8                         
Headers:                         
To get user: GET: http://localhost:8080/user/{id}                         
To get all users: GET: http://localhost:8080/user                         
To create user: POST: http://localhost:8080/user                         
e.g. {                         
         "name": "name",                         
         "surname": "surname",                         
         "salary": 0,                         
         "email": "email",                         
         "age": 0,                         
         "genderId": 1,                         
         "professionId": 2,                         
         "passportNo": 0                         
     }                         
                              
To update user: PUT: http://localhost:8080/user/{id}                         
To delete user: DELETE: http://localhost:8080/user/{id}                         
                         
                         
To get toDoList: GET: http://localhost:8080/toDoList/{id}                         
To get all toDoLists: GET: http://localhost:8080/toDoLists (request params available: boolean active, boolean ordered, integer status)                         
e.g. http://localhost:8080/toDoList?ordered=true&active=true                          
                         
To create toDoList: POST: http://localhost:8080/toDoList                         
e.g.  {"userId": 1,                         
         "statusId": 1,                         
         "description": "do something"}                         
                                  
To update toDoList: PUT: http://localhost:8080/toDoList/{id}                         
To delete toDoList: DELETE: http://localhost:8080/toDoList/{id}                         
To delete all toDoLists: DELETE: http://localhost:8080/toDoList                         
                         
                                                  

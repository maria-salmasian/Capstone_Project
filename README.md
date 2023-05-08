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
                         
                         
![diagram](![face_recognition](https://user-images.githubusercontent.com/104355435/236812250-6198b425-9113-4fe2-9cf6-21a7ee35e8e7.png)

                                              
                         
                         
# External tools                         
Postman                         

# Features                         
- User and Course CRUD operations                         
- Logger                         
- Custom exceptions                         
- Spring security                         
- Unit tests                         
                         
# Package structure for the API                        
                         
ws: sub packages : controller , dto , exception, converter                        
core: sub packages : model, service, serviceImpl                         
infrastructure: subpackages : entity, repositories                         
utils: enumeration                         
visibility:                         
ws -> core -> infrastructure                         
object  conversation: model mapper                            
                         

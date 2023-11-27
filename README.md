# Employee Microservice
## Diagram
![diagram](https://github.com/alpersener/employee-microservice/blob/master/diagram.png)
## Features
- Lombok
- Openfeign
- JWT
- Redis cache
- Eureka Server/Client
- PostgreSQL
- Testing with Swagger/Postman
- Zuul
- Hibernate
# Employee-Service
- Running on port:9788
- Exception handling with customize responses
- ResourceBundle about FriendlyMessage and Language option(EN/TR)
 ```properties
# EN messages
FriendlyMessageCodeImpl__EMPLOYEE_NOT_CREATED_EXCEPTION=Employee not created.

# TR messages
FriendlyMessageCodeImpl__EMPLOYEE_NOT_CREATED_EXCEPTION=Çalışan oluşturulamadı.
```
  
## Endpoints (/api/v1/employee)
- These endpoints are not best practices!
- To receive a response from the employee service, we need to send requests to the register and auth endpoints first. Additionally, in the request bodies, the email and pw must be the same otherwise, we cannot authorize
 ### POST (Register And Authenticate)
Example request endpoint
```
/api/v1/auth/register
/api/v1/auth/authenticate
```
Request body(Register):
```
{
    "firstname":"string",
    "lastname":"string",
    "role":"role",
    "email":"string",
    "password":"string"

}

```
Response:
```
token:eyJhb....

```

Request body(Authenticate):
```
{
    "email":"string",
    "password":"string"
}

```
Response:
```
token:eyJhb....
```

* When making requests to employee-service endpoints you should use a Bearer Token to receive a response
### GET
Example request endpoint
```
/{language}/get/{employeeId}
/{language}/employees
```
Returns:
```
{
  "friendlyMessage": null,
  "payload": {
    "employeeId": 0,
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "salary": 0,
    "department": "string",
    "position": "string",
    "startDate": 0,
    "endDate": 0
  },
  "hasError": false,
  "errorMessages": null,
  "httpStatus": "OK"
}
```
### POST
Example request endpoint
```
/{language}/create
```
Request body:
```
{
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "salary": 0,
  "department": "string",
  "position": "string"
}
```
Response body:
```
{
  "friendlyMessage": {
    "title": "Success.",
    "description": "Employee successfully created.",
    "buttonPositive": null
  },
  "payload": {
    "employeeId": $,
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "salary": 0,
    "department": "string",
    "position": "string",
    "startDate": 1700153111326,
    "endDate": 1700153111326
  },
  "hasError": false,
  "errorMessages": null,
  "httpStatus": "CREATED"
}
```
### PUT
Example request endpoint
```
/{language}/update/{employeeId}
```
Request Body:
```
{
  "employeeId": 0,
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "salary": 0,
  "department": "string",
  "position": "string"
}
```
Response Body:
```
{
  "friendlyMessage": {
    "title": "Success.",
    "description": "Employee successfully updated.",
    "buttonPositive": null
  },
  "payload": {
    "employeeId": $,
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "salary": 0,
    "department": "string",
    "position": "string",
    "startDate": 1700153111326,
    "endDate": 1700153331322
  },
  "hasError": false,
  "errorMessages": null,
  "httpStatus": "OK"
}
```
### DELETE

Example request endpoint
```
/{language}/delete/{employeeId}
```
Response Body:
```

{
  "friendlyMessage": {
    "title": "Success.",
    "description": "Employee successfully deleted..",
    "buttonPositive": null
  },
  "payload": {
    "employeeId": $,
    "firstName": "string",
    "lastName": "string",
    "email": "string",
    "salary": 0,
    "department": "string",
    "position": "string",
    "startDate": 1700153111326,
    "endDate": 1700153525140
  },
  "hasError": false,
  "errorMessages": null,
  "httpStatus": "OK"
}
```
# Employee-Cache-Service(/api/v1/employee-cache)
- Only caching get endpoint
- Running on port:9791
  
Example request endpoint
```
/{language}/get/{employeeId}
```
# Zuul Edge Server(Api gateway)
- Running on port:8762
- We can access all endpoints of employee-service and employee-cache-service through the Zuul edge port (8762)

# Eureka Server(Service discovery)
- Running on port:8761





  
  

## Challenge: User service
A rest-service which is able to:
- Create new user with contact data
- Return user by id
- Return user by name
- Add additional mail/phone data
- Update existing mail/phone data
- Delete user

## Prerequisites:
* Java 11
* Springboot
* Maven
* Lombok
* Junit Jupiter
* Mockito
* Openapi Swagger Documentation

## How to Build the Project
1. package application (use ./mvnw or mvn).
   ```
   mvn clean package -DskipTests
   ```
2. Run the application with its dependency (postgres) on docker
   ```
   docker-compose up 
   ```
   * Or run on detached mode
   ```
   docker-compose up -d
   ```
   * To rebuild images 
   ```
   * docker-compose up --build
   ```

## Api Documentation
Check Swagger Documentation
   ```
   http://localhost:5000/swagger-ui/index.html
   ```
- payload example
   * ### Post: Create user
     * http://localhost:5000/api/user/create
     * {
     "lastName": "steve",
     "firstName": "john",
     "email": "steve23@gmail.com",
     "phoneNumber": "08100897654"
     }
     
   * ### Get: User by id
      * http://localhost:5000/api/user/1
  
   * ### Get: Search User by name
      * http://localhost:5000/api/user/search?name=steve
  
   * ### POST: Add user Contact
      * http://localhost:5000/api/user/contact/add
        * {
            "userId": 1,
            "contactType": "email",
            "phoneNumber": "",
            "email": "steve23@gmail.com"
          }
        * {
          "userId": 1,
          "contactType": "phone number",
          "phoneNumber": "08176352283",
          "email": ""
        }
     
   * ### Put: Update User Contact
       * http://localhost:5000/api/user/contact/update
       * {
             "userId": 1,
             "contactType": "email",
             "oldPhoneNumber": "",
             "newPhoneNumber": "",
             "oldEmailContact": "steve23@gmail.com",
             "newEmailContact": "steve20@gmail.com"
         }
       * {
             "userId": 1,
             "contactType": "phone number",
             "oldPhoneNumber": "09123456781",
             "newPhoneNumber": "07198276354",
             "oldEmailContact": "",
             "newEmailContact": ""
         }
       
   * ### DELETE: Delete User
     http://localhost:5000/api/user/delete/4
     
## To Run Tests
```
mvn spring-boot:run 
mvn clean verify
```

# 📚📙📖 Online Bookstore API 📖📙📚
This is an application for online book store . 
An Online Bookstore API is a back-end part of virtual store  where customers can browse the catalog and select books of interest. User can select many books and those books stored in cart. At checkout time, the items in the shopping cart will be presented as an order.
Admins can manage this online bookstore.  This application serves as the core infrastructure , delivering crucial functionalities for book management, user authentication, and order processing.

 ### 🛠️ Technologies and Tools Used
- Spring Boot: For building the application framework.
- Spring Security: To handle authentication and authorization.
- Spring Data JPA: For database interactions using JPA.
- Spring Web: For building web-based applications with Spring MVC.
- Swagger: For API documentation and testing.
- Hibernate: As the ORM tool.
- MySQL: For the relational database.
- Liquibase: For database schema versioning and management.
- Docker: For containerizing the application.
- Maven: For project management and dependency management.
### 🚀 Current Functionalities
- ##### Authentication 
  - Register a new user: POST /api/auth/register
    - Example of request body:
      ```
      {
      "email": "john.doe@example.com",
      "password": "securePassword123",
      "repeatPassword": "securePassword123",
      "firstName": "John",
      "lastName": "Doe",
      "shippingAddress": "123 Main St, City, Country"
      }
      ```
    - Example of response body:
      ```
      {
      "id": 1,
      "email": "john.doe@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "shippingAddress": "123 Main St, City, Country"
      }
      ```
  - Authenticate a user: POST /api/auth/login   
  
    The Online Bookstore API uses JWT (JSON Web Token) for securing API endpoints. JWTs are issued to users upon successful authentication and must be included in the Authorization header of subsequent requests to protected endpoints.After a user successfully logs in, the server issues a JWT. This token must be included in the Authorization header of subsequent requests to protected endpoints.
    - Example of request body:
      ```
      {
      "email": "john.doe@example.com",
      "password": "securePassword123",
      }
      ```
    - Example of response body:
      ```
      {
      "token": "eyJhbGciOiJJ9.eyJzdWIiOiIxMjM0yfQ.SflKssw5c"
      }
      ```
- ##### Book
  - Get books from catalog: GET /api/books
  - Get book for id: GET /api/books/{id}
  - Create a new book: POST /api/books
  - Update a specific book: PUT /api/books/{id}
  - Delete a specific book: DELETE /api/books/{id}
- ##### Category
  - Get all books' categories: GET /api/categories
  - Get a category by id: GET /api/categories/{id}
  - Get all books by current category: GET /api/categories/{id}/books
  - Add a new category (only for role "ADMIN"): POST /api/categories
  - Update a current category (only for role "ADMIN"):PUT /api/categories/{id}
  - Delete a current category (only for role "ADMIN"): DELETE /api/categories/{id}
- ##### Shopping Cart
  - Get a user's shopping cart: GET /api/cart
  - Add book to the shopping cart: POST /api/cart
  - Update quantity of a book in the shopping cart: PUT /api/cart/cart-items/{cartItemId}
  - Remove a book from the shopping cart: DELETE /api/cart/cart-items/{cartItemId}
- ##### Orders
  - Create an order: POST /api/orders
  - Get history of orders: GET /api/orders
  - Get all OrderItems for a specific order: GET /api/orders/{orderId}/items
  - Get a specific OrderItem within an order: GET /api/orders/{orderId}/items/{itemId}
  - Update order status: PATCH /api/orders/{id}
- #####  Swagger Documentation
  - the API documentation at http://localhost:8080/swagger-ui/index.html.
  - You can explore all available endpoints, their descriptions, request parameters, and response schemas.

### Postman Collection 
- **[Click to use collection](https://www.postman.com/rent-masters/workspace/online-bookstore-api/collection/34492567-9cf5612a-c2d4-40b0-a8e4-8f2e06f2f996?action=share&creator=34492567)**

### Live Preview 💻
Check out the live demo of the Book Store application:

- [Live Demo](https://www.loom.com/share/c6710f4f1ba14b339a09f87667c4046c?sid=fd664c29-e2e8-4f5b-92be-cddac9c62bfa)

### 🌟 Getting Started
##### Prerequisites
- Java 17+
- Maven 4+
- MySQL 8+
- Docker
##### Installation
To run the application locally, follow these steps:  

1. Clone the Repository
```
git clone https://github.com/AnyaRatusnyak/project-book-store.git  
cd project-book-store
```
2. Set up MySQL:
Create a new MySQL database and note the database URL, username, and password.  
3. Configure environment variables:
Create a .env file in the project root directory and populate it with the following environment variables:
```env
- MYSQLDB_USER=your_db_user_name  
- MYSQLDB_ROOT_PASSWORD=your_db_password  
- JWT_SECRET=qwertyuioplkjhgfdsazxcvbnm1234567890 
- JWT_EXPIRATION=3600000

- MYSQLDB_DATABASE=your_db_name
- MYSQLDB_LOCAL_PORT=3306
- MYSQLDB_DOCKER_PORT=3306

- SPRING_LOCAL_PORT=8080
- SPRING_DOCKER_PORT=8080
- DEBUG_PORT=5005
- jwt.expiration=token_expiration_time  
- jwt.secret=your_secret_key
```
4. Install dependencies and build the project:
```
mvn clean install
```
5. Run the application:
```
mvn spring-boot:run
```
The server will start on http://localhost:8080.

##### Using Docker
1. Build the Docker image:
```
docker build -t books-store .
```
2. Build and Run the Docker Containers:
```
docker-compose up
```
3. Stop and Remove Containers:
```
docker-compose down
```

##### 📄 Challenges and Solutions
Challenge 1: Securing the API
Solution: Implemented Spring Security to manage authentication and authorization, ensuring that sensitive endpoints are protected.

Challenge 2: Database Management
Solution: Utilized Spring Data JPA and Hibernate for efficient database interactions and ORM capabilities.

Challenge 3: Comprehensive API Documentation
Solution: Integrated Swagger to provide detailed API documentation and facilitate easy testing of endpoints.

##### 👷Author
- LinkedIn: **[Hanna Ratushniak](https://www.linkedin.com/in/hanna-ratushnyak/)** 
- Github: **[AnyaRatusnyak](https://github.com/AnyaRatusnyak)**
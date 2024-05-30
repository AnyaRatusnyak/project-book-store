
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
  - Authenticate a user: POST /api/auth/login
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
  - the API documentation at http://localhost:8080/swagger-ui.html.
  - You can explore all available endpoints, their descriptions, request parameters, and response schemas.

### Live Preview 💻
Check out the live demo of the Book Store application:

- [Live Demo](https://www.loom.com/share/9b5f2e7bd2f845c2ad2a25361530810f)

### 🌟 Getting Started
##### Prerequisites
- Java 17+
- Maven 4+
- MySQL 8+
- Docker
##### Installation
To run the application locally, follow these steps:  

1. Clone the Repository
git clone https://github.com/AnyaRatusnyak/project-book-store.git  
cd project-book-store
2. Set up MySQL:
Create a new MySQL database and note the database URL, username, and password.  
3. Configure environment variables:
Create a .env file in the project root directory and populate it with the following environment variables:

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

jwt.expiration=token_expiration_time
jwt.secret=your_secret_key
4. Install dependencies and build the project:
mvn clean install
5. Run the application:
mvn spring-boot:run
The server will start on http://localhost:8080.

##### Using Docker
1. Build the Docker image:
docker build -t books-store .
2. Run the Docker container:
docker run -d -p 8080:8080 --name books-store books-store

##### 📄 Challenges and Solutions
Challenge 1: Securing the API
Solution: Implemented Spring Security to manage authentication and authorization, ensuring that sensitive endpoints are protected.

Challenge 2: Database Management
Solution: Utilized Spring Data JPA and Hibernate for efficient database interactions and ORM capabilities.

Challenge 3: Comprehensive API Documentation
Solution: Integrated Swagger to provide detailed API documentation and facilitate easy testing of endpoints.
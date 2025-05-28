![bank-01-Converted-01-1024x428](https://github.com/user-attachments/assets/8235a88a-2d3b-4cb3-8fee-676c8521c1a0)
[![GitHub stars](https://img.shields.io/github/stars/iampawan/FlutterExampleApps.svg?style=social&label=Star)](https://github.com/amirziyacode)
[![GitHub forks](https://img.shields.io/github/forks/iampawan/FlutterExampleApps.svg?style=social&label=Fork)](https://github.com/amirziyacode?tab=repositories)


# 📚 Book Store Website – Spring Boot

An advanced online bookstore built with **Java Spring Boot**, where users can browse and order books, interact with an **AI chatbot**, and admins can manage the entire platform through a dedicated panel.

---

## 🌐 Live Demo

Check out the deployed frontend here: [https://ai-book-store-jyd2.vercel.app/](https://ai-book-store-jyd2.vercel.app/)
> ⚠️ Note: This is the frontend-only version and is not connected to the backend API yet. Some features like login, ordering, and chatbot may not function properly.

---

## ✨ Features

### 👤 User Panel:

* Register and log in securely
* Browse and search books
* Place and view orders
* Contact us and send  Messages
* AI-powered chatbot for:

  * Book information
  * Recommendations
  * talk about everything

### 🔧 Admin Panel:

* Manage all books (Add / Edit / Delete)
* View and manage user accounts
* Monitor and manage orders

### 🤖 AI Chatbot (gemma3):

* Answers questions about books and categories
* Recommends books based on user interests

---

## 🛠️ Tech Stack

* **Backend**: Java, Spring Boot, Spring Security, Spring Data JPA
* **Database**: MySQL or PostgreSQL
* **Frontend**: NextJs
* **AI Integration**: gemma3/googleAi
* **Authentication**: JWT / Spring Security
* **Deployment**: Docker / Heroku / VPS

---


## 🚀 Getting Started

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/bookstore.git
   cd bookstore
   ```

2. **Configure your environment:**

   * Set database connection and credentials in `application.properties`
   * Configure AI chatbot API (if needed)

3. **Run the project:**

   ```bash
   ./mvnw spring-boot:run
   ```

---

## 📊 Future Improvements

* Add payment gateway integration
* Implement wishlists and reviews
* Improve chatbot with machine learning
* Add internationalization (i18n) support

---

## ✅ Project Testing Documentation

This document describes the testing structure and coverage for the project, including unit tests, integration tests, and testing tools.

---

### 🧪 Test Strategy

We follow a layered testing approach:

- **Unit Tests**: Focus on individual classes or methods (e.g., services, controllers).
- **Integration Tests**: Validate how different layers interact (e.g., controller + service + repository).
- **End-to-End Tests (Optional)**: Simulate real user scenarios through the whole application.

---

### 🛠 Testing Tools

- **JUnit 5** – For writing and executing unit tests.
- **Mockito** – For mocking dependencies in unit tests.
- **Spring Boot Test** – For integration tests with a real Spring context.
- **Testcontainers** (optional) – For testing with real databases like PostgreSQL or MySQL.

---

### 📁 Test Structure

```
src/
├── main/
│   └── java/
│       └── com/example/project/...
├── test/
│   └── java/
│       └── com/example/project/
│           ├── controller/   # Controller layer tests
│           ├── service/      # Service layer unit tests
│           ├── repository/   # Integration tests for repositories
│           └── utils/        # Utility/helper tests
```

---

### 🧪 Sample Test Cases

- `BookServiceTest`: Test book creation, update, deletion, and fetching.
- `OrderControllerTest`: Validate admin order status update API.
- `AccountControllerTest`: Test account retrieval and update for a user.
- `AIChatControllerTest`: Simulate AI responses and test streaming behavior.
- `ContactServiceTest`: Test contact message storage and retrieval.

---

### 🚀 How to Run Tests

With Maven:

```bash
./mvnw test
```

With Gradle:

```bash
./gradlew test
```

# API Documention

I hope you have good experience with this project but for better experience i wrote this documentations

## 🤖 Ollame Run:
should downlod it on your desktop [Download](https://ollama.com)

to run your llm model localy on your desktop

```bash
ollama run gemma3
```

## 🔧 Admin API Endpoints

This section includes administrative endpoints for managing books, orders, and users.

---

### 📚 Book Administration (`BookAdminController.java`)

**Base Path:** `/api/admin/book`

| Method | Endpoint                  | Description |
|--------|---------------------------|-------------|
| GET    | `/getAllBooks`            | Retrieves a list of all books |
| POST   | `/addBook`                | Adds a new book – Requires `BookRequest` in the request body |
| PUT    | `/updateBook/{bookId}`    | Updates an existing book identified by `bookId` – Requires `BookRequest` in the request body |
| DELETE | `/deleteBook/{bookId}`    | Deletes a specific book identified by `bookId` |
| DELETE | `/deleteAll`              | Deletes all books |

---

### 🛒 Order Administration (`OrderAdminController.java`)

**Base Path:** `/api/admin/order`

| Method | Endpoint                     | Description |
|--------|------------------------------|-------------|
| GET    | `/getAllOrders`              | Retrieves a list of all orders |
| PUT    | `/updateStatus/{orderId}`    | Updates the status of an order identified by `orderId` – Requires `OrderStatusResponse` (containing `orderStatus`) in the request body |

---

### 👥 User Administration (`UserAdminController.java`)

**Base Path:** `/api/admin/user`

| Method | Endpoint                     | Description |
|--------|------------------------------|-------------|
| GET    | `/getAllUsers`               | Retrieves a list of all users |
| PUT    | `/updateStatus/{userId}`     | Updates the active status of a user – Requires `status` query parameter (true or false) |
| PUT    | `/updateRole/{userId}`       | Updates the role of a user – Requires `UserRoleResponse` (containing `role`) in the request body |



## 📖 Public Book API Endpoints

This section outlines the public API endpoints for accessing book information.

---

### 📚 Book Operations (`BookController.java`)

**Base Path:** `/api/book`

| Method | Endpoint             | Description |
|--------|----------------------|-------------|
| GET    | `/Books`             | Retrieves a paginated list of all books |

**Query Parameters for `/Books`:**
- `pageNumber` (int, default: 0): The page number to retrieve
- `perPage` (int, default: 12): The number of books per page

---

| Method | Endpoint             | Description |
|--------|----------------------|-------------|
| GET    | `/getByCategory`     | Retrieves a list of books belonging to a specified category |

**Query Parameter:**
- `category` (String): The name of the category

---

| Method | Endpoint             | Description |
|--------|----------------------|-------------|
| GET    | `/getById`           | Retrieves a specific book by its ID |

**Query Parameter:**
- `id` (int): The ID of the book

---

| Method | Endpoint             | Description |
|--------|----------------------|-------------|
| GET    | `/findBestSeller`    | Retrieves a list of best-selling books, limited by the specified count |

**Query Parameter:**
- `books` (int): The maximum number of best-selling books to return




## 👤 Account API Endpoints

This section outlines the API endpoints for managing user account details.

---

### 🔐 Account Operations (`AccountController.java`)

**Base Path:** `/api/account`

| Method | Endpoint        | Description |
|--------|------------------|-------------|
| GET    | `/getAccount`    | Retrieves the account details for a user specified by email |

**Query Parameter:**
- `email` (String): The email address of the user

**Response:** `UserDTO` object containing account details

---

| Method | Endpoint        | Description |
|--------|------------------|-------------|
| PUT    | `/setAccount`    | Updates the account details for a user specified by email |

**Query Parameter:**
- `email` (String): The email address of the user whose account is to be updated

**Request Body:** `UserDTO` object containing the new account details  
**Response:** A string message indicating the outcome of the update


## 🤖 AI Chat API Endpoint

This section outlines the API endpoint for interacting with the AI chatbot.

---

### 💬 Chat Operations (`ChatController.java`)

**Base Path:** `/api/ai`

| Method | Endpoint     | Description |
|--------|--------------|-------------|
| GET    | `/ask-bot`   | Sends a message to the AI chatbot and receives a streamed response |

**Query Parameter:**
- `message` (String): The message/question to send to the chatbot

**Produces:** `MediaType.TEXT_EVENT_STREAM_VALUE` (Server-Sent Events)  
**Response:** A `Flux<String>` representing the stream of responses from the chatbot


# Contact API Summary

This section outlines the API endpoints for managing contact messages.

## Contact Operations (ContactController.java)

**Base Path:** `/api/contact`

### POST /addContact
Adds a new contact message.

- **Request Body:** `ContactDTO` object containing the message details.
- **Response:** `ContactResponse` object.

### GET /getMyMassages
Retrieves a list of contact messages for a user specified by email.

- **Query Parameter:**
  - `email` (String): The email address of the user.
- **Response:** A list of `ContactDTO` objects.

# DTO (Data Transfer Object) Documentation

This document outlines the structure of various Data Transfer Objects (DTOs) used in the application.

## ContactDTO.java
**Description:** Represents the data transfer object for contact information.

**Properties:**
- `id` (String): The unique identifier of the contact.
- `fullName` (String): The full name of the contact.
- `email` (String): The email address of the contact.
- `subject` (String): The subject of the contact message.
- `message` (String): The content of the contact message.
- `status` (ContactStatus): Enum representing the status of the contact.
- `createdAt` (LocalDateTime): The date and time when the contact was created.

## OrderDTO.java
**Description:** Represents the data transfer object for creating or detailing an order.

**Properties:**
- `items` (List<OrderItem>): A list of items included in the order.
- `email` (String): The email address associated with the order.
- `subtotal` (double): The subtotal amount for the order.
- `tax` (double): The tax amount for the order.
- `total` (double): The total amount for the order.

## OrderResponse.java
**Description:** Represents the data transfer object for responding with order details.

**Properties:**
- `id` (Integer): The unique identifier of the order.
- `items` (List<OrderItem>): A list of items included in the order.
- `email` (String): The email address associated with the order.
- `subtotal` (double): The subtotal amount for the order.
- `tax` (double): The tax amount for the order.
- `total` (double): The total amount for the order.
- `status` (OrderStatus): Enum representing the current status of the order.
- `date` (LocalDate): The date the order was placed or last updated.

## UserDTO.java
**Description:** Represents the data transfer object for user information.

**Properties:**
- `name` (String): The name of the user.
- `phone` (Long): The phone number of the user.
- `email` (String): The email address of the user.
- `address` (String): The street address of the user.
- `city` (String): The city of the user.
- `state` (String): The state or region of the user.
- `zipCode` (Integer): The postal or zip code of the user.
- `country` (String): The country of the user.

# JWT Handling Documentation

This document outlines the structure and purpose of classes involved in JSON Web Token (JWT) generation, validation, and management.

## JwtService.java
**Description:** A service class responsible for handling JSON Web Token (JWT) operations such as generation, validation, and extraction of claims.

**Key Public Methods:**
- `String extractUsername(String token)`: Extracts the username (subject) from a given JWT.
- `String generateToken(UserDetails userDetails)`: Generates a new JWT for the provided user details.
- `boolean isTokenValid(String token, UserDetails userDetails)`: Validates a given JWT against the user details, checking if the username matches and the token is not expired.

**Configuration:**
- `secretKey`: The secret key used for signing and verifying tokens, loaded from application properties (`${application.security.jwt.secret-key}`).
- `jwtExpiration`: The expiration time for JWTs in milliseconds, loaded from application properties (`${application.security.jwt.expiration}`).

## Token.java
**Description:** An entity class representing a JWT stored in the database, typically used for managing token lifecycle, such as revocation.

**Properties:**
- `id` (Integer): The unique identifier for the token record.
- `token` (String): The actual JWT string.
- `tokenType` (TokenType): The type of the token (e.g., BEARER).
- `revoked` (Boolean): A flag indicating whether the token has been revoked.
- `user` (User): The user associated with this token, creating a many-to-one relationship with the User entity.

## TokenRepo.java
**Description:** A JPA repository interface for `Token` entities, providing database operations.

**Key Methods:**
- `List<Token> findAllValidTokensByUser(Integer userId)`: Finds all tokens for a given user ID that have not been revoked. This is a custom query.
- `Optional<Token> findByToken(String token)`: Finds a token entity by its token string.

## TokenType.java
**Description:** An enumeration representing the types of tokens.

**Values:**
- `BEARER`: Indicates a bearer token type.

# Authentication API and Services Documentation

This document outlines the API endpoints, Data Transfer Objects (DTOs), and services involved in user authentication and session management.

## AuthenticationController.java
**Description:** This controller handles user registration and login requests.

**Base Path:** `/api/auth`

**Endpoints:**
- **POST /register**: Registers a new user.
  - **Request Body:** `RegisterRequest`
  - **Response:** `AuthenticationResponse` containing an access token.
- **POST /login**: Authenticates an existing user.
  - **Request Body:** `AuthenticationRequest`
  - **Response:** `AuthenticationResponse` containing an access token and an admin status flag.

**Cross-Origin Resource Sharing (CORS):** Configured to allow requests from `http://localhost:3000`.

## AuthenticationRequest.java
**Description:** A Data Transfer Object (DTO) representing the request payload for user login.

**Properties:**
- `email` (String): The email address of the user.
- `password` (String): The password of the user.

## AuthenticationResponse.java
**Description:** A DTO representing the response payload after successful authentication (login or register).

**Properties:**
- `token` (String): The JWT access token, labeled as `"access_token"` in the JSON response.
- `isAdmin` (boolean): A flag indicating if the authenticated user has admin privileges.

## AuthenticationService.java
**Description:** This service class contains the business logic for user registration and login.

**Key Public Methods:**
- `AuthenticationResponse register(RegisterRequest registerRequest)`: Handles new user registration. Checks if the email is already in use, creates a new user with the `USER` role, encodes the password, saves the user, generates a JWT token, revokes any existing tokens for the user, and saves the new token. Throws an `IllegalArgumentException` if the email already exists.
- `AuthenticationResponse login(AuthenticationRequest authenticationRequest)`: Authenticates a user using Spring Security's `AuthenticationManager`. If authentication is successful, checks if the user is an `ADMIN`, generates a new JWT token, revokes all previous valid tokens for that user, and saves the new token to the repository. Returns an `AuthenticationResponse` with the token and an `isAdmin` flag if applicable. Throws `UsernameNotFoundException` if the user is not found.

**Key Private Methods:**
- `void revokeAllUserTokens(User user)`: Revokes all existing valid tokens for a given user by setting their `revoked` status to `true`.
- `void saveUserToken(String token, User user)`: Saves a new JWT token for a user with `TokenType.BEARER` and `revoked` status as `false`.

## LogoutService.java
**Description:** Implements Spring Security's `LogoutHandler` to manage user logout by revoking the JWT token.

**Key Public Methods:**
- `void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)`: Handles the logout process. Extracts the JWT from the `"Authorization"` header of the request. If a valid `Bearer` token is found, retrieves the token from `TokenRepo`, sets its `revoked` status to `true`, and saves the updated token.

## RegisterRequest.java
**Description:** A DTO representing the request payload for user registration.

**Properties:**
- `fullName` (String): The full name of the user.
- `email` (String): The email address of the user.
- `password` (String): The password of the user.


# Mapper Documentation

This document outlines the purpose and methods of various mapper components responsible for converting between Data Transfer Objects (DTOs) and entity objects.

## Contact Mapper
**Interface:** `ContactMapper.java`  
**Implementation:** `ContactMapperImpl.java`  
**Purpose:** Handles conversions between `ContactDTO` objects and `Contact` entity objects.

**Methods:**
- `Contact contactDtoToContact(ContactDTO contactDto, User user)`: Converts a `ContactDTO` and a `User` object into a `Contact` entity.  
  - Implementation sets `status` to `PENDING` and `createdAt` to the current time by default.
- `List<ContactDTO> contactToContactDTOList(List<Contact> contactList)`: Converts a list of `Contact` entities into a list of `ContactDTO` objects.
- `ContactDTO contactToContactDTO(Contact contact)`: (Private utility method in implementation) Converts a single `Contact` entity to a `ContactDTO`.

## Order Mapper
**Interface:** `OrderMapper.java`  
**Implementation:** `OrderMapperImpl.java` (Marked as `@Component`)  
**Purpose:** Facilitates conversions between order-related DTOs (`OrderDTO`, `OrderResponse`) and entities (`Order`, `OrderItem`), as well as to detail view objects (`OrderDetails`, `OrderItemDetails`).

**Methods:**
- `Order OrderDTOToOrder(OrderDTO orderDTO)`: Converts an `OrderDTO` to an `Order` entity.  
  - Implementation sets `status` to `PROCESSING`, `data` to the current `LocalDateTime`, `createdAt` to the current `LocalDate`, and generates a partial `orderNumber` using UUID.
- `List<OrderResponse> OrderToOrderResponse(List<Order> orders)`: Converts a list of `Order` entities to a list of `OrderResponse` DTOs.
- `OrderResponse OrderToOrderResponse(Order order)`: Converts a single `Order` entity to an `OrderResponse` DTO.  
  - Implementation sets the `date` in `OrderResponse` to the current `LocalDate`.
- `OrderDetails OrderToOrderDetails(Order order)`: Converts an `Order` entity to an `OrderDetails` object.
- `List<OrderDetails> OrderToOrderDetailsList(List<Order> orders)`: Converts a list of `Order` entities to a list of `OrderDetails` objects.
- `List<OrderItemDetails> OrderItemToOrderItemDetails(List<OrderItem> items)`: Converts a list of `OrderItem` entities to a list of `OrderItemDetails` objects.
- `OrderItemDetails OrderItemToOrderItemDetails(OrderItem orderItem)`: Converts a single `OrderItem` entity to an `OrderItemDetails` object.

## User Mapper
**Interface:** `UserMapper.java`  
**Implementation:** `UserMapperImpl.java` (Marked as `@Component`)  
**Purpose:** Responsible for conversions between `UserDTO`, `UserResponse` DTOs, and the `User` entity.

**Methods:**
- `UserDTO UserToUserDTO(User user)`: Converts a `User` entity to a `UserDTO`.  
  - Throws a `RuntimeException` if the `user` object is null.
- `User UserDtoToUser(UserDTO userDTO)`: Converts a `UserDTO` to a `User` entity.
- `UserResponse UserToUserResponse(User user)`: Converts a `User` entity to a `UserResponse` DTO.
- `List<UserResponse> UserToUserResponseList(List<User> users)`: Converts a list of `User` entities to a list of `UserResponse` DTOs.


# Notification System Documentation

This document outlines the components of the notification system, including the entity, controller, repository, service, and status enum.

## Notification.java
**Description:** An entity class representing a notification in the system.  
**Entity Name:** `_notifications`

**Properties:**
- `id` (Integer): The unique identifier for the notification.
- `title` (String): The title of the notification.
- `message` (String): The content of the notification message.
- `type` (NotificationStatus): The type of notification, represented by an enum.
- `date` (LocalDateTime): The date and time when the notification was created.
- `isRead` (boolean): A flag indicating whether the notification has been read.
- `user` (User): The user associated with this notification. Creates a many-to-one relationship with the `User` entity and is ignored during JSON serialization (`@JsonIgnore`).

## NotificationController.java
**Description:** This controller handles HTTP requests related to notifications.  
**Base Path:** `/api/notification`

**Endpoints:**
- **GET /getAll/{email}**: Retrieves all notifications for a user specified by their email address.  
  - **Path Variable:** `email` (String) - The email of the user.  
  - **Response:** A list of `Notification` objects with an HTTP 200 OK status.
- **DELETE /deleteById/{id}**: Deletes a specific notification by its ID for a given user's email.  
  - **Path Variable:** `id` (int) - The ID of the notification to delete.  
  - **Query Parameter:** `email` (String) - The email of the user.  
  - **Response:** The updated list of notifications for the user with an HTTP 202 Accepted status.
- **GET /marksAllRead/{email}**: Marks all notifications for a user (specified by email) as read.  
  - **Path Variable:** `email` (String) - The email of the user.  
  - **Response:** The updated list of notifications for the user with an HTTP 200 OK status.

## NotificationRepo.java
**Description:** A JPA repository interface for `Notification` entities, providing database operations.

**Key Methods:**
- `List<Notification> findByEmail(@Param("email") String email)`: Finds all notifications associated with a user's email address (case-insensitive search on email) using a custom JPQL query.

## NotificationService.java
**Description:** This service class contains the business logic for managing notifications.

**Key Public Methods:**
- `List<Notification> getAllNotificationsByEmail(String email)`: Retrieves all notifications for a given email by calling `notificationRepo.findByEmail(email)`.
- `void createNotification(Notification notification)`: Saves a new notification to the database using `notificationRepo.save(notification)`.
- `List<Notification> deleteById(String email, int id)`: Deletes a notification by its ID for a specific email. Checks if the user has any notifications; if not, throws a `RuntimeException`. Filters the user's notifications by the provided `id` and deletes the matching one(s). Returns the updated list of notifications for the email.
- `List<Notification> marksAllRead(String email)`: Marks all notifications for a given email as read by setting their `isRead` property to `true` and saving the changes. Returns the updated list of notifications for the email.

## NotificationStatus.java
**Description:** An enumeration representing the different types or categories of notifications.

**Values:**
- `ORDER`
- `DELIVERED`
- `PROMOTION`
- `GIFT`
- `OTHER`


# Order Management System Documentation

This document outlines the components of the order management system, including entities, Data Transfer Objects (DTOs), controllers, repositories, services, and enums.

## Entities

### Order.java
**Description:** Represents an order placed in the system.  
**Table Name:** `_orders`

**Properties:**
- `id` (Integer): The unique identifier for the order.
- `data` (LocalDateTime): The date and time when the order data was recorded.
- `status` (OrderStatus): The current status of the order (e.g., PROCESSING, SHIPPED).
- `items` (List<OrderItem>): A list of items included in the order. Configured with a one-to-many relationship with `OrderItem`, using cascade all and orphan removal.
- `email` (String): The email of the user who placed the order, stored in the `user_email` column.
- `subTotal` (double): The subtotal amount for the order.
- `tax` (double): The tax amount for the order.
- `total` (double): The total amount for the order.
- `createdAt` (LocalDate): The date when the order was created, automatically timestamped.
- `orderNumber` (String): A unique number identifying the order.

### OrderItem.java
**Description:** Represents an individual item within an order.  
**Table Name:** `_order_items`

**Properties:**
- `author` (String): The author of the book in the order item.
- `coverImage` (String): The URL or path to the cover image of the book.
- `id` (Integer): The unique identifier for the order item, ignored during JSON serialization.
- `price` (double): The price of this item.
- `quantity` (int): The quantity of this item.
- `title` (String): The title of the book in the order item.

## Data Transfer Objects (DTOs) / View Models

### OrderDetails.java
**Description:** A DTO used to provide detailed information about an order, often for display or administrative purposes.

**Properties:**
- `id` (Integer): The order ID.
- `orderNumber` (String): The unique order number.
- `customerEmail` (String): The email of the customer.
- `totalAmount` (double): The total amount of the order.
- `status` (String): The current status of the order.
- `createdAt` (String): The creation date of the order.
- `items` (List<OrderItemDetails>): A list of detailed items in the order.

### OrderItemDetails.java
**Description:** A DTO providing specific details for an item within an order, often used within `OrderDetails`.

**Properties:**
- `bookId` (Integer): The ID of the book (presumably, `OrderItem.id` is used here, but named `bookId` in this DTO).
- `quantity` (int): The quantity of the item.
- `price` (double): The price of the item.

### OrderMassage.java
**Description:** A DTO used to return a message string, typically after an order operation.

**Properties:**
- `massage` (String): The message content, labeled as `"Massage_Order"` in JSON.

### OrderStatusResponse.java
**Description:** A DTO for conveying order status information, likely used when updating or querying an order's status.

**Properties:**
- `orderStatus` (String): The status of the order.

## Controller

### OrderController.java
**Description:** Handles HTTP requests related to orders.  
**Base Path:** `/api/order`

**Endpoints:**
- **POST /addOrder/{email}**: Adds a new order for the specified email.  
  - **Path Variable:** `email` (String) - The email of the user placing the order.  
  - **Request Body:** `OrderDTO` - Contains the details of the order to be added.  
  - **Response:** `OrderMassage` with an HTTP 201 Created status.
- **GET /getAllOrders/{email}**: Retrieves all orders for the specified email.  
  - **Path Variable:** `email` (String) - The email of the user.  
  - **Response:** A list of `OrderResponse` objects with an HTTP 200 OK status.

## Repository

### OrderRepo.java
**Description:** A JPA repository interface for `Order` entities, providing database operations.

**Key Methods:**
- `List<Order> findByEmail(@Param("email") String email)`: Finds all orders associated with a user's email address (case-insensitive search on email) using a custom JPQL query.

## Service

### OrderService.java
**Description:** Contains the business logic for managing orders.

**Key Public Methods:**
- `OrderMassage addOrder(String email, OrderDTO order)`: Adds a new order for the given email and `OrderDTO`. Maps the `OrderDTO` to an `Order` entity, sets the user's email, saves the order, creates a notification for the user, and returns an `OrderMassage`. Throws a `RuntimeException` if the user is not found.
- `List<OrderResponse> getAllOrders(String email)`: Retrieves all orders for a given email by fetching them from the `orderRepo` and mapping them to a list of `OrderResponse` DTOs using `OrderMapper`.

## Enum

### OrderStatus.java
**Description:** An enumeration representing the possible statuses of an order.

**Values:**
- `DELIVERED`
- `PROCESSING`
- `PENDING`
- `SHIPPED`
- `CANCELLED`


## 📚 License

This project is licensed under the [MIT License](LICENSE).

---

Feel free to fork and contribute to this project!

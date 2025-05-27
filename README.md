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


## 📚 License

This project is licensed under the [MIT License](LICENSE).

---

Feel free to fork and contribute to this project!

# Products REST API

A secure, versioned RESTful Product Management API built with **Java
17** and **Spring Boot**.

This project was developed as part of the **Java Backend Developer
Hiring Assignment by Zest India IT Pvt Ltd**, with a focus on clean API
design, CRUD operations, validation, pagination, standardized errors,
and JWT-based authentication.

------------------------------------------------------------------------

## 🚀 Project Highlights

-   Java 17
-   Spring Boot 4.1.1
-   Spring Data JPA + Hibernate
-   MySQL 8
-   RESTful API with `/api/v1/` versioning
-   Complete Product CRUD
-   Paginated product listing
-   Product → Item relationship
-   Jakarta Bean Validation
-   Standardized JSON error responses
-   JWT access tokens
-   JWT refresh tokens with rotation
-   Role-based authorization
-   BCrypt password encoding
-   Stateless Spring Security
-   Maven Wrapper
-   Spring Boot integration test setup

------------------------------------------------------------------------

## 🏗️ Architecture

The application follows a simple layered structure:

``` text
com.api.products
│
├── controller
│   └── ProductController
│
├── service
│   └── ProductService
│
├── repository
│   ├── ProductRepository
│   └── ItemRepository
│
├── entity
│   ├── Product
│   └── Item
│
├── dto
│   ├── ProductRequestDTO
│   └── ProductResponseDTO
│
├── exception
│   ├── GlobalExceptionHandler
│   ├── ErrorResponseDTO
│   └── ProductNotFoundException
│
└── security
    ├── AuthController
    ├── SecurityConfig
    ├── SecurityBeansConfig
    ├── JwtAuthenticationFilter
    ├── JwtService
    ├── CustomUserDetailsService
    └── dto
        ├── LoginRequest
        └── TokenResponse
```

### Request flow

``` text
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
MySQL
```

For secured requests:

``` text
Client
  ↓
JWT Authentication Filter
  ↓
Security / Role Authorization
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
MySQL
```

------------------------------------------------------------------------

## 🔐 Authentication & Authorization

The API uses **JWT-based stateless authentication**.

Two token types are generated:

  Token           Purpose                     Default Lifetime
  --------------- ------------------------- ------------------
  Access Token    API authentication                15 minutes
  Refresh Token   Generate new token pair               7 days

Refresh token rotation is implemented. When a valid refresh token is
submitted, a new access token and a new refresh token are generated.

### Demo users

  Username   Password     Role
  ---------- ------------ --------------
  `admin`    `admin123`   `ROLE_ADMIN`
  `user`     `user123`    `ROLE_USER`

> These credentials are demonstration credentials for the assignment and
> should not be used in a production deployment.

### Authorization rules

  Operation         USER   ADMIN
  ---------------- ------ -------
  GET products       ✅     ✅
  POST product       ✅     ✅
  PUT product        ✅     ✅
  DELETE product     ❌     ✅

------------------------------------------------------------------------

## 📡 API Endpoints

Base URL:

``` text
http://localhost:8081/api/v1
```

### Authentication

#### Login

``` http
POST /auth/login
```

Request:

``` json
{
  "username": "admin",
  "password": "admin123"
}
```

Response contains:

``` json
{
  "accessToken": "...",
  "refreshToken": "...",
  "tokenType": "Bearer"
}
```

#### Refresh Token

``` http
POST /auth/refresh
```

Send the refresh token returned by login.

------------------------------------------------------------------------

### Products

#### Create Product

``` http
POST /products
```

Example request:

``` json
{
  "productName": "Laptop",
  "createdBy": "admin"
}
```

Returns `201 Created`.

#### Get All Products

``` http
GET /products
```

Pagination example:

``` text
GET /products?page=0&size=10&sort=productName,asc
```

#### Get Product by ID

``` http
GET /products/{id}
```

#### Update Product

``` http
PUT /products/{id}
```

Example:

``` json
{
  "productName": "Updated Laptop",
  "createdBy": "admin"
}
```

#### Delete Product

``` http
DELETE /products/{id}
```

Returns `204 No Content` when successful.

#### Get Product Items

``` http
GET /products/{id}/items
```

------------------------------------------------------------------------

## 🗄️ Database Model

The project uses two entities:

### Product

``` text
Product
---------
id
productName
createdBy
createdOn
modifiedBy
modifiedOn
```

### Item

``` text
Item
---------
id
product
quantity
```

Relationship:

``` text
Product 1 ─────────── * Item
```

The `Item` entity uses a `ManyToOne` relationship with `Product`.

------------------------------------------------------------------------

## ✅ Validation & Error Handling

Product requests use Jakarta Validation.

For example, `productName` and `createdBy` cannot be blank.

Validation failures return a standardized response structure:

``` json
{
  "timestamp": "2026-09-01T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/products",
  "details": [
    "productName: Product name is required"
  ]
}
```

A missing product returns `404 Not Found`.

Authentication failures return `401 Unauthorized`.

Insufficient permissions return `403 Forbidden`.

------------------------------------------------------------------------

## ⚙️ Tech Stack

  Technology           Usage
  -------------------- --------------------------------
  Java 17              Backend language
  Spring Boot 4.1.1    Application framework
  Spring Web MVC       REST APIs
  Spring Data JPA      Persistence
  Hibernate            ORM
  MySQL 8              Database
  Spring Security      Authentication & authorization
  JJWT 0.12.6          JWT generation/validation
  Jakarta Validation   Request validation
  Lombok               Boilerplate reduction
  Maven                Build & dependency management
  JUnit 5              Testing

------------------------------------------------------------------------

## 🛠️ Prerequisites

Install:

-   Java 17 or later
-   MySQL 8.x
-   Git

Verify Java:

``` powershell
java -version
```

Verify Maven wrapper:

``` powershell
.\mvnw.cmd -version
```

------------------------------------------------------------------------

## 🗃️ Database Setup

Create the database in MySQL:

``` sql
CREATE DATABASE products;
```

The application is configured for:

``` text
Host: localhost
Port: 3306
Database: products
Username: root
Password: password
```

Update `application.properties` if your MySQL credentials are different.

Hibernate is configured with:

``` properties
spring.jpa.hibernate.ddl-auto=update
```

so the required tables can be created/updated from the JPA entities.

------------------------------------------------------------------------

## ▶️ Run the Application

Clone the repository:

``` bash
git clone https://github.com/debugwithsushant/zest-java-backend-assignment.git
cd zest-java-backend-assignment
```

On Windows PowerShell:

``` powershell
.\mvnw.cmd clean test
```

Run the application:

``` powershell
.\mvnw.cmd spring-boot:run
```

The API starts on:

``` text
http://localhost:8081
```

------------------------------------------------------------------------

## 🧪 Testing

The project contains a Spring Boot application context test.

Run:

``` powershell
.\mvnw.cmd clean test
```

The build should finish with:

``` text
BUILD SUCCESS
```

------------------------------------------------------------------------

## 📋 Assignment Requirement Tracking

The following table reflects the current project implementation.

  Requirement                                         Status
  ----------------------------- ---------------------------------------------------
  Java 17+                                              ✅
  Spring Boot                                           ✅
  Spring Data JPA / Hibernate                           ✅
  MySQL / PostgreSQL                                 ✅ MySQL
  Product CRUD                                          ✅
  `/api/v1/` versioning                                 ✅
  JSON request/response                                 ✅
  Standardized error handling                           ✅
  Pagination                                            ✅
  JWT authentication                                    ✅
  Refresh token                                         ✅
  Refresh token rotation                                ✅
  Role-based authorization                              ✅
  Jakarta Validation                                    ✅
  Spring Boot test                                      ✅
  JUnit 5                                               ✅
  Mockito unit tests                         ⚠️ Not currently included
  H2 test database                          ⚠️ Not currently configured
  Swagger/OpenAPI                            ⚠️ Not currently included
  Database indexing strategy                ⚠️ Not currently configured
  Async processing               ⚠️ Not currently required by the implemented flow
  CORS configuration                        ⚠️ Not currently configured
  HTTPS enforcement                         ⚠️ Not currently configured
  Dockerfile                                 ⚠️ Not currently included
  docker-compose.yml                         ⚠️ Not currently included

> The repository documentation intentionally describes the
> implementation as it exists rather than claiming features that are not
> present.

------------------------------------------------------------------------

## 🔎 Example API Workflow

A typical secured workflow is:

``` text
1. POST /api/v1/auth/login
          ↓
2. Receive accessToken + refreshToken
          ↓
3. Send accessToken in Authorization header
          ↓
   Authorization: Bearer <accessToken>
          ↓
4. Call Product APIs
          ↓
5. When access token expires,
   POST /api/v1/auth/refresh
          ↓
6. Receive a new access + refresh token pair
```

------------------------------------------------------------------------

## 📁 Repository Structure

``` text
zest-java-backend-assignment/
│
├── src/
│   ├── main/
│   │   ├── java/com/api/products/
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/com/api/products/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
└── README.md
```

------------------------------------------------------------------------

## 🎯 Engineering Focus

This project demonstrates practical backend development concepts:

-   RESTful resource design
-   Layered application architecture
-   DTO-based API contracts
-   JPA entity mapping
-   Repository abstraction
-   Service-layer business logic
-   Pagination using Spring Data
-   Validation at API boundaries
-   Centralized exception handling
-   Stateless JWT security
-   Access/refresh token separation
-   Refresh token rotation
-   Role-based access control
-   HTTP status code handling

------------------------------------------------------------------------

## 👨‍💻 Author

**Sushant Pawar**

Java Backend Developer \| Java 17 \| Spring Boot \| Spring Data JPA \|
REST APIs \| MySQL \| JWT \| SQL

GitHub:

https://github.com/debugwithsushant

------------------------------------------------------------------------

## 📌 Assignment

Developed for the **Zest India IT Pvt Ltd Java Backend Developer Hiring
Assignment**.

The repository contains the source code and Maven project required to
build and run the backend application.

# 🌸 Spring Boot RBAC Permission System

A simple **RBAC (Role-Based Access Control)** backend system built with
**Spring Boot** and **Spring Data JPA**.

This project demonstrates a typical **enterprise permission management
architecture** including **User, Role and Permission modules**.

------------------------------------------------------------------------

## 🌍 Language

English \| 日本語(README.ja.md)

------------------------------------------------------------------------

## 🛠 Tech Stack

✨ Java 21\
✨ Spring Boot 3\
✨ Spring Data JPA\
✨ MySQL\
✨ Lombok\
✨ Maven

------------------------------------------------------------------------

## 📦 Project Structure

    com.rbac
    ├── controller        # REST API controllers
    ├── service           # Service interfaces
    ├── service.impl      # Service implementations
    ├── repository        # JPA repositories
    ├── entity            # JPA entities
    ├── dto               # Request DTOs
    ├── exception         # Exception handling
    └── common            # Common response wrapper

------------------------------------------------------------------------

## 🔐 RBAC Model

This project follows the standard **RBAC (Role-Based Access Control)**
design.

    User
    Role
    Permission

    UserRole
    RolePermission

Relationships:

👤 **User ↔ Role** (Many-to-Many)\
🛡 **Role ↔ Permission** (Many-to-Many)

------------------------------------------------------------------------

## ✨ Main Features

### 👤 User Management

-   Create user
-   Update user
-   Delete user (logical delete)
-   Query user list
-   Assign roles to user

### 🛡 Role Management

-   Create role
-   Update role
-   Delete role
-   Query role list
-   Assign permissions to role

### 🔑 Permission Management

-   Create permission
-   Update permission
-   Delete permission
-   Query permission list

------------------------------------------------------------------------

## 🚀 API Examples

### Create User

POST /user/create

``` json
{
  "username": "admin",
  "password": "123456",
  "enabled": 1
}
```

### Assign Role To User

POST /user/{id}/roles

``` json
{
  "roleIds": [1,2]
}
```

### Assign Permission To Role

POST /role/{id}/permissions

``` json
{
  "permissionIds": [1,2,3]
}
```

------------------------------------------------------------------------

## 🗄 Database Design

Main tables:

    sys_user
    sys_role
    sys_permission
    sys_user_role
    sys_role_permission

The project uses **logical delete** with a `deleted` field.

------------------------------------------------------------------------

## ⚙ Exception Handling

Global exception handling is implemented with:

    BusinessException
    ErrorCode
    GlobalExceptionHandler

All APIs return a **unified response format**.

------------------------------------------------------------------------

## 📬 Response Format

Example response:

``` json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

------------------------------------------------------------------------

## 🌱 Future Improvements

-   🔐 Password encryption (BCrypt)
-   🔑 Login authentication
-   🪪 JWT authorization
-   ✅ DTO validation
-   📦 VO layer for API response

------------------------------------------------------------------------

## 👨‍💻 Author

GitHub:\
https://github.com/luosheep2020

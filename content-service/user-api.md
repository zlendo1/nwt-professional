# User API Documentation

This document contains the details of the **User API**, which allows users to manage user data. Below, you will find information about the available endpoints, request parameters, responses, and examples for each action.

## Base URL
The base URL for all API requests is:
http://localhost:8080/users

### 1. GET /users

**Method**: `GET`  
**Description**: Returns a list of all users.

**Request**:
- **Method**: `GET`
- **URL**: `/users`
- **Body**: No body required for this request.

**Response**:
- **Status code**: `200 OK`
- **Body**:
```json
[
    {
        "userId": 1,
        "username": "johndoe",
        "email": "johndoe@example.com"
    },
    {
        "userId": 2,
        "username": "janedoe",
        "email": "janedoe@example.com"
    }
]
```

### 2. GET /users/{id}

**Method**: `GET`  
**Description**: Retrieves a user by ID.

**Request**:
- **Method**: `GET`
- **URL**: `/users/{id}`  
- **Parameters**: 
  - `id` (Path variable): The ID of the user to retrieve.
  
**Response**:
- **Status code**: `200 OK`
- **Body**:
```json
{
    "userId": 1,
    "username": "johndoe",
    "email": "johndoe@example.com"
}
```
```json
{
"error": "User not found with id: 1"
}
```
### 3. POST /users

**Method**: `POST`  
**Description**: Creates a new user.

**Request**:
- **Method**: `POST`
- **URL**: `/users`
- **Body**:
```json
{
    "username": "johndoe",
    "email": "johndoe@example.com",
    "password": "password123"
}
```
```json
{
    "userId": 1,
    "username": "johndoe",
    "email": "johndoe@example.com"
}
```
### 4. PUT /users/{id}

**Method**: `PUT`  
**Description**: Updates the user details by ID.

**Request**:
- **Method**: `PUT`
- **URL**: `/users/{id}`
- **Body**:
```json
{
    "username": "johnnydoe",
    "email": "johnnydoe@example.com"
}
```
```json
{
    "userId": 1,
    "username": "johnnydoe",
    "email": "johnnydoe@example.com"
}
```
### 5. DELETE /users/{id}

**Method**: `DELETE`  
**Description**: Deletes the user by ID.

**Request**:
- **Method**: `DELETE`
- **URL**: `/users/{id}`
- **Body**: No body required for this request.

**Response**:
- **Status code**: `204 No Content`
- **Body**: No content.

### 1. GET /users

**Description**:
This endpoint returns a list of all users.

#### Request
- **Method**: GET
- **URL**: `http://localhost:8080/users`
- **Headers**:
  - `Content-Type: application/json`
  - `Authorization: Bearer <token>`
- **Body**: None

#### Response
- **Status code**: `200 OK`
- **Body**:
```json
[
    {
        "userId": 1,
        "username": "johndoe",
        "email": "johndoe@example.com"
    },
    {
        "userId": 2,
        "username": "janedoe",
        "email": "janedoe@example.com"
    }
]

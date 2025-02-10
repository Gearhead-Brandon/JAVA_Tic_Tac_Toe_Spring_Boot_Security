# Project Backend 04 — Java Bootcamp

## Summary
In this project, you will learn how to integrate databases into a Java web application using Spring and implement user authentication and authorization.

## Contents
- [Chapter I - Instructions](#chapter-i---instructions)
- [Chapter II - General Information](#chapter-ii---general-information)
  - [Authorization](#authorization)
  - [Identification, Authentication, Authorization](#identification-authentication-authorization)
  - [Login and Password Authorization](#login-and-password-authorization)
- [Chapter III - Tasks](#chapter-iii---tasks)
  - [Task 1 - Adding a Database](#task-1---adding-a-database)
  - [Task 2 - Implementing Authorization](#task-2---implementing-authorization)
  - [Task 3 - Implementing Multiplayer Game Logic](#task-3---implementing-multiplayer-game-logic)

## Chapter I - General Information

### Authorization
Authorization mechanisms control user access to system resources, ensuring each user has the correct permissions.

### Identification, Authentication, Authorization
- **Identification**: Assigning a unique identifier to a user.
- **Authentication**: Verifying the user's identity (e.g., checking passwords).
- **Authorization**: Granting permissions to perform specific actions.

### Login and Password Authorization
- The user provides a login and password for authentication.
- If successful, the server assigns access rights for further requests.
- Unauthorized requests receive a `401 Unauthorized` response.

### Topics to Study
- Web Applications
- Basic Authentication (login/password)
- PostgreSQL
- Spring Security

## Chapter III - Tasks

### Task 1 - Adding a Database
- Configure PostgreSQL in `application.properties`.
- Replace in-memory storage with a database.
- Use appropriate annotations to persist classes in the database.
- Extend repository interfaces from `CrudRepository`.

### Task 2 - Implementing Authorization
- Add users with `UUID`, login, and password.
- Implement user support across all application layers.
- Create a `SignUpRequest` model with login and password.
- Develop an authentication service that:
  - Registers users.
  - Authorizes users by verifying credentials via `base64(login:password)`.
- Implement an authentication controller with endpoints for registration and login.
- Create an `AuthFilter` extending `GenericFilterBean` to validate credentials.
- Define a `SecurityFilterChain` bean:
  - Allow public access to authentication endpoints.
  - Require authentication for all other endpoints.
  - Integrate `AuthFilter` for security.

### Task 3 - Implementing Multiplayer Game Logic
- Introduce game states:
  - Waiting for players
  - Player’s turn (UUID)
  - Draw
  - Player victory (UUID)
- Assign unique symbols for each player.
- Enhance game completion logic based on states.
- Add endpoints for:
  - Creating a new game (vs. user or AI).
  - Listing available games.
  - Joining an existing game.
  - Updating game state based on user moves.
  - Retrieving game details.
  - Fetching user information by UUID.

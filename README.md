# Project 2

## Overview of the API

Our team has developed a RESTful API using **Spring Boot** to handle various functionalities, including authentication, user management, and book operations. This API serves as the backend for both the web and mobile platforms, ensuring smooth interaction between the front end and the database.

### API Features

1. **Authentication and Authorization**
   - **JWT Authentication:** Uses JSON Web Tokens (JWT) to manage secure user sessions.
   - **Login Endpoint:** Authenticates users and issues JWT tokens upon successful login.
   - **Role-based Access Control:** Provides access restrictions based on user roles (Admin, User).

2. **User Management**
   - **Admin Privileges:** Admins can create, update, and delete user records.
   - **User Registration:** Allows users to register with their email and password.
   - **Password Encryption:** Uses **BCrypt** for secure password hashing.

3. **Book Management**
   - **CRUD Operations:** Provides endpoints for creating, reading, updating, and deleting books.
   - **Search and Filtering:** Supports searching books by title and filtering by genre.

4. **Security**
   - **JWT-based Authentication:** Secures API endpoints to prevent unauthorized access.
   - **Spring Security Integration:** Configured for role-based access and authentication filtering.

5. **API Documentation**
   - **Postman:** Testing of API endpoints through Postman.

---

## Technologies Used

- **Spring Boot**: Backend framework
- **MongoDB**: Database for persisting data
- **JWT**: For secure authentication
- **BCrypt**: For password encryption
- **JUnit & Mockito**: For unit and integration testing
- **Docker**: For containerizing the application
- **Heroku**: For cloud deployment

---

## Entity-Relationship Diagrams (ERDs) for the Application

Below are the ERDs designed for various aspects of the application. Each diagram serves a specific purpose and models different relationships and user stories to ensure comprehensive coverage of the system.

### 1. **User Activity and Roles ERD**
This ERD models the different roles within the application such as Admin, User, and Visitor. It captures how each role interacts with the system:
- **User**: The primary role in the system that can browse and save favorite books.
- **Admin**: A special type of user with additional privileges like managing users.
- **Visitor**: An unregistered role that can browse books but has limited access.
- This diagram ensures that role-based access control is implemented in the system.

- ![image](https://github.com/user-attachments/assets/a8f87a86-fc67-4e1b-aa01-061bf8914e1e)


### 2. **User Interaction with Reviews and Ratings ERD**
This ERD focuses on modeling user interactions with the reviews and ratings functionality:
- **User**: The entity that writes reviews and rates books.
- **Book**: Books that can be reviewed and rated.
- **Review**: Contains attributes like rating, text, and date of review.
- This diagram highlights how users contribute content to the platform, providing feedback on the books they read.

- ![image](https://github.com/user-attachments/assets/6cec7c52-8d73-47c2-a247-f0fe76b2442b)


### 3. **Search and Filtering ERD**
This ERD illustrates how the search and filtering functionality works:
- **User**: Initiates searches and applies filters.
- **Search**: Contains attributes for search queries and filters.
- **Book**: Represents the books returned by search queries.
- **Genre**: Used as a filter to narrow down book searches.
- This ERD ensures efficient querying and provides a clear model of search interactions within the application.

- ![image](https://github.com/user-attachments/assets/06dab5ff-a881-4f51-8d19-3ad213931604)


### 4. **User Preferences and Notifications ERD**
This ERD models user settings, preferences, and notifications:
- **User**: The entity that sets preferences.
- **Preferences**: Stores user-specific settings like language, theme, and notification options.
- **Genre** and **Book**: Can be linked to preferences to customize user experience.
- This ERD ensures that the system can handle personalized user experiences based on individual settings.

- ![image](https://github.com/user-attachments/assets/8200f2d2-2c82-4321-a6e3-c0581a7a95a8)


### 5. **Order and Purchase History ERD**
This ERD is focused on modeling the purchasing process:
- **User**: Places orders for books.
- **Order**: Stores order information like total price and date.
- **Book**: Represents books included in an order.
- **Payment** and **Shipping**: Tracks payment and shipping details for each order.
- This diagram ensures a comprehensive view of the order management system.

- ![image](https://github.com/user-attachments/assets/7036d86d-17ca-4dbc-8c37-ad7723cdd9d3)


### 6. **Audit Logging and History ERD**
This ERD models the logging and history of actions taken within the system:
- **Log**: Captures the details of actions, including timestamp, user, and affected entities.
- **User**, **Book**, and **Author**: Each action is related to one or more of these entities.
- This ERD provides a model for tracking changes and actions, useful for auditing and debugging.

- ![image](https://github.com/user-attachments/assets/df8eee12-4ceb-4fb3-b5d0-a8ca4ec74536)
  
### 7. **Database ERD**
This ERD captures the relationships between core entities in the database:
- **User**: Stores user-specific information including favorite books and genres.
- **Book**: Contains details about the books such as title, description, ranking, and price.
- **Author**: Information about the author including name, bio, and nationality.
- **Genre**: Represents different genres a book can belong to.
- Relationships include:
  - A **User** can have many favorite **Books** and **Genres**.
  - A **Book** can be written by multiple **Authors** and can belong to multiple **Genres**.
 
  - ![image](https://github.com/user-attachments/assets/a70973e9-9dc2-49b5-be74-65218f4c85f2)
 
### 8. **Full Application ERD**
This ERD models the entire system, including external interactions:
- **User**: Represents the user entity with attributes like name and password.
- **Book, Author, Genre**: Core entities that store book-related information.
- **API**: Represents the external New York Times Best-seller API that fetches data for books, authors, and genres.
- **Frontend UI**: Captures the user interface interactions, displaying book and author data.
- The diagram shows how:
  - **Users** interact with the frontend for browsing, searching, and saving books.
  - **API** interactions fetch the necessary data to populate the application.
 
  - ![image](https://github.com/user-attachments/assets/dcb594b4-c87c-487e-880b-5e2fb8a8df0f

---

## API Documentation

Our API is documented and tested using **Postman**. Postman collections help developers explore the available endpoints and interact with the backend.

- **Postman Collection URL**: [Postman Collection](https://your-api-postman-url)
- Use the collection to view endpoints, parameters, and test requests in real-time.

---

## Testing Strategy

Our testing strategy ensures code quality and proper functionality through the following:

1. **Unit Testing**:
   - Each service class is thoroughly tested with **JUnit** to ensure expected behavior.
   - **Mockito** is used to mock dependencies, isolating the components being tested.

2. **Integration Testing**:
   - Integration tests ensure that the system components work well together.
   - Database interactions are validated by simulating real-world scenarios.

3. **End-to-End Testing**:
   - Postman tests validate the entire workflow, from API calls to database updates.
   - These tests ensure that the API functions as expected when deployed.

---

## Deployment

We have containerized the API using **Docker** to ensure consistency across different environments. The application is deployed to **Heroku**, allowing scalable and reliable cloud hosting.

- **Docker**: Containerizes the application for portability and reproducibility.
- **Heroku**: Hosts the API, ensuring high availability and scalability.

---

## Contribution Guidelines

We follow standard practices to maintain code quality and collaboration within the team.

1. **Branching Strategy**:
   - Use the `main` branch for production-ready code.
   - Feature branches should follow the naming convention `feature/feature-name`.

2. **Code Reviews**:
   - All pull requests must be reviewed and approved by at least two other team member.

3. **Commit Messages**:
   - Follow the convention: `feat:` and `fix:`, to indicate the purpose of each commit.

---




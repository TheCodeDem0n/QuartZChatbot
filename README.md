# QuartZChatbot

QuartZChatbot is a Spring Boot–based chatbot application that provides secure user authentication, personalized chat sessions, and persistent chat history. It integrates the Gemini API to generate AI-based responses while maintaining user-specific interactions.

---

## Features

* User authentication with encrypted passwords (BCrypt)
* Individual chat history for each user
* Persistent storage using MySQL
* AI-based response generation using Gemini API
* Layered architecture (Controller, Service, Repository)
* Simple and responsive web interface

---

## Technology Stack

* Backend: Java, Spring Boot
* Frontend: HTML, CSS, JavaScript
* Database: MySQL
* Security: Spring Security (BCrypt)
* AI Integration: Gemini API

---

## Project Structure

```
src/
 ├── main/
 │   ├── java/com/miniproject/
 │   │   ├── controllers/
 │   │   ├── services/
 │   │   ├── repositories/
 │   │   ├── entities/
 │   │   └── securityconfig/
 │   └── resources/
 │       ├── static/
 │       ├── templates/
 │       ├── application.properties
 │       └── application-local.properties
```

---

## Setup Instructions

### 1. Clone the repository

```
git clone https://github.com/TheCodeDem0n/QuartZChatbot.git
```

---

### 2. Configure local properties

Create the file:

```
src/main/resources/application-local.properties
```

Add the following:

```
spring.datasource.password=your_database_password
gemini.api.key=your_api_key
```

---

### 3. Database Setup

* Install and run MySQL
* Create a database named:

```
chatbot_db
```

---

### 4. Run the application

In STS / Eclipse:

* Open Run Configurations
* Set profile:

```
local
```

Or run using:

```
-Dspring.profiles.active=local
```

---

## Contributors

* Gorang Giri
* Bhoomika Gupta

---

## Notes

* Sensitive information such as API keys and database passwords are not included in the repository
* The project uses environment-specific configuration for secure development
* Designed as a mini-project demonstrating backend, frontend, and AI integration

---

## Future Enhancements

* Deployment on cloud platforms
* Real-time messaging support
* Improved UI/UX
* Additional AI model integrations

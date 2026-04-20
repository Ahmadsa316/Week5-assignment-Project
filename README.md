# Week 4 Assignment – Shopping Cart (JavaFX + MySQL + Localization)

## Overview

This project is a **JavaFX-based Shopping Cart application** integrated with a **MySQL database** and **multi-language support**.

The application allows users to:

* Enter item prices and quantities
* Calculate the total cost
* Switch between multiple languages dynamically
* Store cart calculation results in a database
* Load localization text from the database

---

## Features

* JavaFX GUI
* MySQL database integration
* Multi-language support:

  * English (en)
  * Finnish (fi)
  * Swedish (sv)
  * Japanese (ja)
  * Arabic (ar)
* Dynamic UI translation using database values
* Cart record storage in database
* Item record storage in database
* Maven build system
* JUnit testing
* Docker support
* Jenkins pipeline configuration

---

## Project Structure

```text id="b6n4x3"
Week3_Assignment
│── src
│   ├── main
│   │   ├── java/org/example/week3_assignment
│   │   │   ├── Main.java
│   │   │   ├── ShoppingCartController.java
│   │   │   ├── db/
│   │   │   │   ├── CartService.java
│   │   │   │   ├── DatabaseConnection.java
│   │   │   │   └── LocalizationService.java
│   │   │   └── model/
│   │   │       ├── CartItem.java
│   │   │       └── CartRecord.java
│   │   └── resources/org/example/week3_assignment
│   │       └── shopping_cart.fxml
│   └── test
│
│── database/
│   └── schema.sql
│
│── Screenshots/
│── pom.xml
│── Dockerfile
│── Jenkinsfile
```

---

## Database Setup

### 1. Open MySQL Workbench

Connect to your local MySQL server.

### 2. Run the schema file

Execute the SQL in:

```text id="u7icux"
database/schema.sql
```

This creates:

* `shopping_cart_localization` database
* `cart_records` table
* `cart_items` table
* `localization_strings` table

### 3. Insert localization data

After creating the tables, insert the language data into `localization_strings`.

### 4. Update database connection

In:

```text id="7kgr8m"
src/main/java/org/example/week3_assignment/db/DatabaseConnection.java
```

set your correct MySQL details:

```java id="h3jqh3"
private static final String URL = "jdbc:mysql://127.0.0.1:3307/shopping_cart_localization";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

---

## How to Run

### Compile the project

```bash id="v3r11m"
mvn clean compile
```

### Run the application

```bash id="ozf7yx"
mvn javafx:run
```

---

## Running Tests

```bash id="beucjz"
mvn test
```

---

## Docker

### Build Docker image

```bash id="pp5v3s"
docker build -t week3-assignment .
```

### Run container

```bash id="mn0lov"
docker run week3-assignment
```

---

## Jenkins Pipeline

The project includes a Jenkins pipeline for:

* Building the project
* Running tests
* Packaging the application
* Building Docker image

---

## Screenshots

Screenshots of the application, database tables, and outputs are included in the `Screenshots` folder.

---

## Technologies Used

* Java 21
* JavaFX
* MySQL
* Maven
* JUnit 5
* Docker
* Jenkins

---

## Notes

* UTF-8 encoding is required for multilingual text support
* Arabic and Japanese require correct font rendering
* Localization strings are loaded from the database instead of property files
* Cart calculations are saved into database tables

---

## Author

Ahmad Sarfaraz

## Project Overview

This project implements core functionalities of a Digital ID Platform using Java and Maven. This project implements the core functionalities of a Digital ID Platform using Java (Maven-based project).

The system is designed to manage personal information and identity documents while enforcing strict validation rules. It demonstrates object-oriented programming principles, input validation, file handling, automated testing, and continuous integration using GitHub Actions.

### The system allows administrators to:

- Add a new person with validation rules

- Update personal details with specific constraints

- Add different types of identity documents

- Automatically validate all input data

- Store valid records in a TXT file

- Execute automated unit tests using GitHub Actions

This project follows structured validation logic and software engineering best practices.


### Technologies Used

- Java 21

- Maven

- JUnit 5

- Git & GitHub

- GitHub Actions (CI/CD)

- TXT File Handling (Java IO)


# 🚀 How to Run and Use This Project

## 📥 1️. Clone the Repository

Open your terminal and run:

git clone https://github.com/Varadraj-Tarapure/DigitalId_Platform.git

Then navigate into the project folder:

cd DigitalId_Platform


---

## ⚙️ 2. Prerequisites

Ensure the following software is installed on your system:

- Java 21 (or compatible version)
- Maven
- Git

You can verify installations using:

java -version
mvn -version


---

## 🧪 3️. Run Unit Tests

To run all unit tests, execute the following command in the project directory:

mvn test

This will:
- Compile the project
- Run all JUnit test cases
- Display test results in the terminal

If all tests pass successfully, you will see:

BUILD SUCCESS


---

## 📦 4️. Build the Project

To build and package the project:

mvn clean package

This will:
- Compile the project
- Run tests
- Generate compiled files in the `target/` directory


---

## 📄 5️. View Stored Data

After successful execution:

- Valid person records are stored in `persons.txt`
- Identity document data is stored in respective TXT files

You can open these files to verify saved records.


---

## 🔄 Continuous Integration (GitHub Actions)

This project is integrated with GitHub Actions.

Whenever code is pushed to the repository:
- The project builds automatically
- All unit tests run automatically
- The build status is displayed on GitHub

No manual execution is required for CI testing.


---

## ✅ Expected Test Output

After running:

mvn clean test

You should see output similar to:

Tests run: 15, Failures: 0, Errors: 0
BUILD SUCCESS

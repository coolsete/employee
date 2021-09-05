# CRUD API for Employee table

Simple application for demonstrate how to implement simple CRUD operation with postgre in Docker.
"Employee" is Spring Boot application with REST API and postgre in Docker.

Build
 >gradlew clean build
 
Build and Run Docker containers
 >docker-compose up --build

Application port
8088

Example API to get subordinated employees

curl --location --request GET 'http://localhost:8088/api/employeeManagement/employee/subordinated'
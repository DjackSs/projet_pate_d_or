# Projet file rouge

## Description

This is a Java console program that performs CRUD operations on a database.

This project is the first step in developing an application to manage a fictional restaurant chain called "Pate D'Or".

Information about the context can be found in the ./assets folder.

## Technologies

* Java

### Features

* Use Java DataBase Connectivity (JDBC)
* Implement a Data Access Object (DAO) pattern
* Implement a csv parser to read and insert a set of data into database

## Setup

### installation:

This project was developed using the Eclipse IDE.

- `git clone https://github.com/DjackSs/projet_fil_rouge.git`
- Import project from folder or archive into Eclipse

### JDBC

This project uses a Relational DataBase Management System (RDBMS).

#### SSMS

By default, the project is run using SQL Server.
The ./BDD/ssms folder contains what is needed to connect to the database.

- The create_bdd.sql file
- The JDBC dependency jar 

Run the create_bdd script and add the jar file to the project libraries (using the build path option in Eclipse).

#### mysql

An alternative configuration is provided to make the project work with MySQL.
The ./BDD/mysql folder contains what is needed to connect to the database:

- The create_bdd_mysql.sql file
- The mysql-connector jar
- The ConnectionProvider.java file

Run the create_bdd script and add the jar file to the project libraries (using the build path option in Eclipse). In addition, it is necessary to replace the ConnectionProvider file in the ./src/dal folder.

### ENV variable

By default, this project is configured to use the USER_SQLSERVER and PASSWORD_SQLSERVER env variables from the operating system.

### Run the application

Run ./src/controller/Menu as Java Application.

#### Use the CSV reader

This project contains a csv parser that performs automatic insertion into a database.
The ./assets folder contains 2 templates with the correct structure for the csv file and examples of data.

- CSV path :

```
============================================
    AJOUTER UN RESTAURANT A PARTIR D'UN CSV
============================================
 Indiquer le chemin d'accès de votre fichier CSV :
 ```

The program will ask for the path to the CSV file.
The project root folder is the starting point for pathing to the file.
Thus, if the CSV file is in the root folder, enter the name of the file.
Do not specify the mime type, only CSV is supported.

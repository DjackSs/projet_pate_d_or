# Projet file rouge

## Description

This is a java console programe that perform CRUD operations on a database.

This project is the first step to developpe an application that manage a fictive restaurant chain called "Pate D'Or".

## Technologies

* Java

### Features

* Use Java DataBase Connectivity (JDBC)
* Implement a Data Acces Object (DAO) pattern

## Setup

### installation:

This project was develope on Eclipse IDE.

- `git clone https://github.com/DjackSs/projet_fil_rouge.git`
- Importe the project from folder or archive in Eclipse

### JDBC

This project use a Relational DataBase Management System (RDBMS).

#### SSMS

By default, the project  run with SQl Server Manager Studio (SSMS).
The BDD > ssms folder, contain what it is needed to set up the conection with the database :

- The create_bdd.sql file
- The JDBC dependency jar 

Run the create_bdd script and add the jar file to the project libraries (build path option in Eclipse).

#### mysql

An alternative configuration is provided to make the project work with MySQL.
The BDD > mysql folder, inside the BDD folder, contain what it is needed to set up the conection with the database :

- The create_bdd_mysql.sql file
- The mysql-connector jar
- The ConnectionProvider.java file

Run the create_bdd script and add the jar file to the project libraries (build path option in Eclipse). Moreover it is required to replace the ConnectionProvider file in src > dal folder.

### ENV variable

By default, this project is configure to use USER_SQLSERVER and PASSWORD_SQLSERVER env variable from the operating system.

### Run the application

Run src > controller > Menu as Java Application.

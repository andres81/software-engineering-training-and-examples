# Database (relational) tutorial

## Using several databases (Postgres, MySql, H2) to introduce a student to
database .

# Sources

* [Postgres](https://www.postgresql.org/)
* [MySql](https://www.mysql.com/)
* [H2](https://h2database.com/html/main.html)

# Introduction

Databases are the powerhouses of persistence for software applications. Especially
in modern times, with the high demand for data on the fly, turning data in information
by delivering data at the right time, to the right place to the right entity,
to whom the data then, there becomes valuable, by the definition of information.

It isn't a surprise therefore, that there are plenty of databases, brands, projects,
implementations available, but also as opensource and for free commercially.

For this tutorial, the important thing, is to learn how to use databases.

Assumed is that the reader and user of this tutorial understands
[relation databases](https://en.wikipedia.org/wiki/Relational_database),
what [SQL](https://en.wikipedia.org/wiki/SQL-92) stands for and what it
involves, but most importantly knows how to use the DDL and DML with SQL of
databases.

# Lessons

## 1. Local Setup

### [Setting up your local environment with Docker to start experimenting](lesson1-local-setup/README.md)

## 2. Transactions: The A in ACID

### [Using transactions to execute multiple statements as one atomic operation](lesson2-transactions/README.md)

## 3. Isolation level of Transactions: The I in ACID

### [What one transaction does, is not necessarily visible to another transaction, while they are both in progress.](lesson3-isolation/README.md)

## 4. Optimistic/pessimistic locking

### [How to prevent overwriting data by other transactions](lesson4-locking/README)


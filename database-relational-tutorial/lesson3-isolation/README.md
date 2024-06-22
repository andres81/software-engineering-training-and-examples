# Lesson 3 - Isolation level of Transactions: The I in ACID

### Sources

* [MySQL isolation levels](https://dev.mysql.com/doc/refman/8.4/en/innodb-transaction-isolation-levels.html)
* [Postgres isolation levels](https://www.postgresql.org/docs/current/transaction-iso.html)

The isolation level of a transaction, determines that what can be seen from a
transaction:

* What was in the database at the beginning of the transaction. (Repeatable read)
* What is in the database right now. (Read committed)
* What is currently the situation after changes within transactions currently
  going on. (Read uncommitted)


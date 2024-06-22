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


    NOTE: Postgres doesn't implement Read Uncommitted.

## Examples and scenarios

A [strong example](https://en.wikipedia.org/wiki/ACID#isolation%20failure:~:text=in%20other%20tables.-,Isolation%20failure,-%5Bedit%5D)
is present over at Wikipedia.

Another, concrete example, is for example updating the balance of a bank
account.
Imagine the first transaction trying to subtract from the balance, and another
one, to add money.
Let's  use T1,T2...Tn as the descriptors of moments in time. Then:

T1: Transaction 1 starts

T2: Transaction 1 retrieves the balance of the bank account: 30.00

T3: Transaction 2 starts

T4: Transaction 1 withdraws the money by subtracting the amount, resulting in a
    negative balance: 30.00 - 120.00 = -90.00

T5: Transaction 2 reads the balance, which is negative, and adds the intended
    amount to it. -90.00 + 100 = 10.00

t6: Transaction 1 ends, commits, but fails: A constraint in the database
    prevents a negative balance. Balance in DB: 30.00

t7: Transaction 2 ends, commits, success. But the balance is not correct: 10.00

The above can occur, if the isolation level of transaction 2, is set to Read
Uncommitted. A level that Postgres doesn't support. If set, it works the same as
Read Committed.

If Transaction 2 would have been set to Read Committed however, it would have
read the balance as it was in the database at the time of starting transaction 1.

Of course, this does not prevent overwriting updates. For example, if in the
above example, Transaction 1 would not have been cancelled, with minimum 
isolation level Read Committed, both Transactions woudl have read the balance of
30.00. But the new balance of -90.00 created in Transaction 1 would then have
been overwritten by Transaction 2 with the new balance of 130.00.

The solution to this is: (Pessimistic/Optimistic) Locking

# Lesson 4 - Optimistic/pessimistic locking

We covered transactions and isolation levels when working with updates to the
database, in a concurrent fashion.

But, as could be seen in lesson 3, isolation levels are not enough: Different
transactions can still overwrite each others changes to the database.

The last technique/methodology we need is locking: A row that has to be updated
can be locked in two ways, to prevent two or more transactions from overwriting
the changes the other made, leading to inconsistent and undesirable results.

The two ways that are possible are optimistic and pessimistic. Most used
technique is optimistic locking: One is optimistic regarding the frequency a
particular row will be updated, so if it fails, simply retry. The failing is
however properly implemented with optimistic locking.

With pessimistic locking, a lock is applied to a row that is read, before being
updated, so that transactions subsequently have to wait for each other. This
comes with a huge performance penalty, though, and therefore optimistic locking
is preferred over pessimistic

.
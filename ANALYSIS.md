# Analysis

## High Level implementation

The implementation follows a classical skip list implementation: one head node, one sentinel (nill) node and each node being in up to the maximum level of lanes inclusively (from one up to that specific level). Each node in a lane points to the next node in the same lane. The current node always has a value less than the next node in the same lane.

Suppose the maximal value in the skip list does not surpass LONG.max_value - 1, then we can assign  LONG.max_value to the nill node. Using this assumption, we can implement the insert and search by only searching for an element greater than or equal to the inserted element, going lane by lane from the top-most lane. If we find an element equal to ours, we return from the function with the appropriate information. Otherwise, we go to the lane in the level below, until we exhaust lanes. For the element search (contains) this means the element does not exist.

For the element insert (add), this outcome means we can insert the newly added element into the SkipList. Then, we create a novel node with the inserted value. Further, we probabilistically determine the number of levels this value will have and rewire the previous links in the lanes to point to the newly current node.

## Assumptions
So, initially, the assumption that made the code more readable is that LONG.max_value does not appear. Next, we assume the skip list has a predetermined maximal level of lanes passed to the constructor. Next, for the concurrent implementation, we assume a low number of concurrent reads and writes.

## Advantages and disadvantages of the current implementation.
The current implementation is more readable.
However, under a heavy number of inserts, the complexity of the skip list can start to degrade from the expected logarithmic complexity since the maximal level is limited.
For the concurrent implementation, the methods are mutually exclusive, which limits the throughput of the concurrent version.

## Scaling
I would choose the maximal level of the skip list to be between ten and twenty.
I would also provide a dynamically adaptable maximal level implementation depending on the number of values in the skip list.
I would use several preallocated memory pool allocators since each node can have a size from 1 to max level, so there is less memory fragmentation, and the implementation is more cache friendly.
This would also result in nodes that belong to top lanes being adjacent one to another.

I would also care about the cache alignment of nodes in order to prevent false sharing; in particular, we would pad the nodes so their size is aligned with the cache size.
This padding is quite important for the concurrent implementation of the skip list.

I would make a finer-grained lock implementation of the concurrent skip list.
In particular, I would have two implementations.

1. One would be using a read and write lock.
When we insert the value, we acquire the write locks of the nodes we iterate over.
When we move to the next node, we acquire the write lock for the next node and release the write lock for the previous node.
When we switch to the next lane, we acquire the write lock for the node in the next lane without releasing the write lock on the last node in the previous lane.
This approach limits the congestion to only small parts of the skip list.
Then, we release the write locks by going from the most recently acquired one to the least recently acquired one as we link the newly added node in the corresponding lanes.
When we search for the value, we acquire the read locks instead of write locks, and release the read locks in the reverse order of acquisition.
This implementation allows a large throughput for reads, and the concurrency isolation for writes.

2. The second implementation would be lock-free, where instead of a read and write lock for each node, we would use the spinlock around the node as a sort of exclusive lock.
This approach reduces memory usage and does not require going through the kernel for the lock acquisition. However, in the case of a large number of inserts, the spinlocks can exhaust threads, so either timed spinlocks with sleep or retrying the whole search insert function should be taken.

## Production readiness
I would provide an error handling, in particular: memory exceptions, incorrect values.
I would templatize the SkipList data structure to support variable types.
I would provide more unit and performance tests.

## Feedback

I quite liked the exercise.
I think it provides an opportunity to show the skills the candidate posses.
In particular, the deep feeling of performance analysis and system design alongside the engineering and research experience.

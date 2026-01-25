package com.neo4j.index_team_hiring_exercise;

/**
 * An implementation of the concurrent skip list with set-like characteristics for {@code long} values.
 */
public class SkipListConcurrentImpl extends SkipListImpl {
    SkipListConcurrentImpl(int maxLevel)
    {
        super(maxLevel);
    }
    /**
     * Adds a value to this list if it is not already present.
     *
     * @return {@code true} if the list did not contain the element, otherwise {@code false}.
     */
    public boolean add(long value)
    {
        synchronized (this)
        {
            return super.add(value);
        }
    }

    /**
     * Tests if an element is contained in this list.
     *
     * @return {@code true} of the list contains the value, {@code false} if not.
     */
    public boolean contains(long value)
    {
        synchronized (this)
        {
            return super.contains(value);
        }
    }

    /**
     * Returns the number of values in this list.
     *
     * @return the number of values in this list.
     */
    public int size()
    {
        synchronized (this)
        {
            return super.size();
        }
    }

    /**
     * Tests if this list is empty.
     *
     * @return {@code true} if the list does not contain any values, {@code false} it it does.
     */
    public boolean isEmpty()
    {
         synchronized (this)
        {
            return super.isEmpty();
        }
    }
}

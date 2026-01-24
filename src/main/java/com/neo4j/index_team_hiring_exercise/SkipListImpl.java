package com.neo4j.index_team_hiring_exercise;
import java.util.Random;

public class SkipListImpl implements SkipList {
    private class Node
    {
        long value;
        Node[] aNext;
        Node(long value, int numLevels)
        {
            this.value = value;
            aNext = new Node[numLevels];
        }
    };
    private Node headNode;
    private Node nillNode;
    private int maxLevel;
    private int cntEntries;
    private Random randomGenerator;

    public SkipListImpl(int maxLevel)
    {
        this.maxLevel = maxLevel;
        this.headNode = new Node(Long.MIN_VALUE, maxLevel);
        this.nillNode = new Node(Long.MAX_VALUE, maxLevel);
        for (int idx = 0; idx < maxLevel; idx++)
        {
            headNode.aNext[idx] = nillNode;
        }
        this.cntEntries = 0;
        this.randomGenerator = new Random();
    }

    private boolean flipHead()
    {
        return randomGenerator.nextDouble() < 0.5;
    }

    private int randomLevel()
    {
        int cnt;
        for (cnt = 1; cnt < this.maxLevel; cnt++)
        {
            if (!flipHead())
            {
                break;
            }
        }
        return cnt;
    }

     /**
     * Adds a value to this list if it is not already present.
     *
     * @return {@code true} if the list did not contain the element, otherwise {@code false}.
     */
    @Override
    public boolean add(long value)
    {
        // System.out.println("Adding:" + value);
        int randomLevelNode = randomLevel();
        Node[] prevNodes = new Node[randomLevelNode];
        Node curNode = headNode;
        for (int level = maxLevel - 1; level >= 0; level--)
        {
            while ((curNode.aNext[level] != nillNode) &&
                (value > curNode.aNext[level].value))
            {
                curNode = curNode.aNext[level];
            }
            if (level < randomLevelNode)
            {
                prevNodes[level] = curNode;
            }
            if ((curNode.aNext[level] != nillNode) &&
                (value == curNode.aNext[level].value))
            {
                return false;
            }
        }

        Node newNode = new Node(value, randomLevelNode);
        for (int level = 0; level < randomLevelNode; level++)
        {
            Node prevNode = prevNodes[level];
            newNode.aNext[level] = prevNode.aNext[level];
            prevNode.aNext[level] = newNode;
        }

        cntEntries++;
        return true;
    }

    /**
     * Tests if an element is contained in this list.
     *
     * @return {@code true} of the list contains the value, {@code false} if not.
     */
     @Override
    public boolean contains(long value)
    {
        Node curNode = headNode;
        for (int level = maxLevel - 1; level >= 0; level--)
        {
            while ((curNode.aNext[level] != nillNode) && (curNode.aNext[level].value < value))
            {
                curNode = curNode.aNext[level];
            }
            if ((curNode.aNext[level] != nillNode) &&  (curNode.aNext[level].value == value))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of values in this list.
     *
     * @return the number of values in this list.
     */
     @Override
    public int size()
    {
        return cntEntries;
    }

    /**
     * Tests if this list is empty.
     *
     * @return {@code true} if the list does not contain any values, {@code false} it it does.
     */
     @Override
    public boolean isEmpty()
    {
        return cntEntries == 0;
    }
}

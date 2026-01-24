package com.neo4j.index_team_hiring_exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {

    private SkipList skipList;

    private void init(int maxLevel)
    {
        skipList = new SkipListImpl(maxLevel);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void newSkipListShouldBeEmpty(int maxLevel) {
        init(maxLevel);
        assertTrue(skipList.isEmpty());
        assertEquals(0, skipList.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void addSingleElement(int maxLevel) {
        init(maxLevel);
        assertTrue(skipList.add(42));
        assertFalse(skipList.isEmpty());
        assertEquals(1, skipList.size());
        assertTrue(skipList.contains(42));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void addMultipleElements(int maxLevel) {
        init(maxLevel);
        assertTrue(skipList.add(42));
        assertFalse(skipList.add(42));
        assertFalse(skipList.add(42));
        assertTrue(skipList.add(1));
        assertTrue(skipList.add(2));
        assertTrue(skipList.add(43));
        assertFalse(skipList.isEmpty());
        assertEquals(4, skipList.size());
        assertTrue(skipList.contains(42));
        assertTrue(skipList.contains(1));
        assertTrue(skipList.contains(2));
        assertTrue(skipList.contains(43));
        assertFalse(skipList.contains(0));
        assertFalse(skipList.contains(55));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void largeNumberElements(int maxLevel)
    {
        init(maxLevel);
        for (int idx = 0; idx < 10000; idx++)
        {
            assertTrue(skipList.add(idx));
            assertFalse(skipList.add(idx));
        }
        for (int idx = 0; idx < 10000; idx++)
        {
            assertTrue(skipList.contains(idx));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10})
    void largeNumberElements2(int maxLevel)
    {
        init(maxLevel);
        for (int idx = 0; idx < 100000; idx++)
        {
            assertTrue(skipList.add(idx));
            assertFalse(skipList.add(idx));
        }
        for (int idx = 0; idx < 100000; idx++)
        {
            assertTrue(skipList.contains(idx));
        }
    }

}

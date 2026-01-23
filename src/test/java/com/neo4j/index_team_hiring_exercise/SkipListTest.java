package com.neo4j.index_team_hiring_exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {

    private SkipListImpl skipList;

    @BeforeEach
    void setUp() {
        skipList = new SkipListImpl();
        // throw new UnsupportedOperationException("Provide your implementation here");
    }

    @Test
    void newSkipListShouldBeEmpty() {
        assertTrue(skipList.isEmpty());
        assertEquals(0, skipList.size());
    }

    @Test
    void addSingleElement() {
        assertTrue(skipList.add(42));
        assertFalse(skipList.isEmpty());
        assertEquals(1, skipList.size());
        assertTrue(skipList.contains(42));
    }

    // You can add more tests below
}

/*
 * Copyright 2025 Kalen Cha, Evan Lei, Raiden H
 * 
 * All rights reserved. This software made available under the terms of
 * the GNU General Public License v3 or later
 * 
 * The JUnit 5 library is distributed under the terms of the Eclipse Public License v2.0
 * More information can be found in /lib/LICENSE.md
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.model.DatabaseManager;
import src.model.Direction;
import src.model.Door;
import src.model.Question;
import src.model.QuestionFactory;
import src.model.Room;

/**
 * Class for testing the Room object.
 * @author Raiden H
 * @version May 5, 2025
 */
public class TestRoom {
    /**
     * Objects to test on.
     */
    private Question testQuestion;
    private Door testDoor;
    private Room testRoom;
    
    /**
     * Setup fresh objects before each test.
     */
    @BeforeEach
    void setup() {
        DatabaseManager.connect();
        testQuestion = QuestionFactory.buildQuestion(1);
        testDoor = new Door(testQuestion);
        testRoom = new Room(0, 0);
    }

    /**
     * Test isVisited() and setVisited() methods
     */
    @Test
    void testVisited() {
        // flip em back and forth a few times to be sure.
        assertFalse(testRoom.isVisited());

        testRoom.setVisited(false);

        assertFalse(testRoom.isVisited());

        testRoom.setVisited(true);

        assertTrue(testRoom.isVisited());

        testRoom.setVisited(false);

        assertFalse(testRoom.isVisited());
    }

    /**
     * Test coordinate getters.
     */
    @Test
    void testPosition() {
        assertEquals(0, testRoom.getX());
        assertEquals(0, testRoom.getY());

        // reassigning to try different coordinates
        testRoom = new Room(3, 5);

        assertEquals(3, testRoom.getX());
        assertEquals(5, testRoom.getY());
    }

    /**
     * Test addDoor(), getDoor(), and hasDoor() methods.
     */
    @Test
    void testDoors() {
        for (Direction dir : Direction.values()) {
            assertFalse(testRoom.hasDoor(dir));
        }

        assertEquals(testDoor, testDoor);
    }
}

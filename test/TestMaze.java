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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.model.DatabaseManager;
import src.model.Direction;
import src.model.Maze;
import src.model.Position;

/**
 * Class for testing the Maze object.
 * @author Raiden H
 * @version May 2, 2025
 */
public class TestMaze {
    /**
     * Objects for testing on.
     */
    private Maze testMaze;

    /**
     * Setup clean Maze object and database connection before each test.
     */
    @BeforeEach
    void setup() {
        DatabaseManager.connect();
        testMaze = new Maze(5, 5);
    }

    /**
     * Test the Maze contructor, making sure appropriate exceptions are thrown.
     */
    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> testMaze = new Maze(3, 0));
        assertThrows(IllegalArgumentException.class, () -> testMaze = new Maze(0, 3));
        assertThrows(IllegalArgumentException.class, () -> testMaze = new Maze(0, 0));
    }

    /**
     * Test dimension getters.
     */
    @Test
    void testDimensions() {
        assertEquals(5, testMaze.getWidth());
        assertEquals(5, testMaze.getHeight());

        testMaze = new Maze(3, 7);

        assertEquals(3, testMaze.getWidth());
        assertEquals(7, testMaze.getHeight());
    }

    /**
     * Test the isPathAvailable() method.
     */
    @Test
    void testIsPathAvailable() {
        testMaze = new Maze(3, 3);
        assertTrue(testMaze.isPathAvailable(new Position(0, 0)));

        testMaze.getRoom(new Position(0, 0)).getDoor(Direction.EAST).lock();
        assertTrue(testMaze.isPathAvailable(new Position(0, 0)));

        testMaze.getRoom(new Position(0, 0)).getDoor(Direction.SOUTH).lock();
        assertFalse(testMaze.isPathAvailable(new Position(0, 0)));
    }
    
    /**
     * Test the getRoom() method.
     */
    @Test
    void testGetRoom() {
        // Consider setting up a mock Maze object for testing.

        // Testing bounds allowed
        assertThrows(IndexOutOfBoundsException.class, () ->  testMaze.getRoom(new Position(-1, 0)));
        assertThrows(IndexOutOfBoundsException.class, () ->  testMaze.getRoom(new Position(0, -1)));
        assertThrows(IndexOutOfBoundsException.class, () ->  testMaze.getRoom(new Position(6, 0)));
        assertThrows(IndexOutOfBoundsException.class, () ->  testMaze.getRoom(new Position(0, 6)));
    }

    /**
     * Testing the entrance and exit
     */
    @Test
    void testWaypoints() {
        assertEquals(0, testMaze.getEntrance().getX());
        assertEquals(0, testMaze.getEntrance().getY());

        assertEquals(testMaze.getWidth() - 1, testMaze.getExit().getX());
        assertEquals(testMaze.getHeight() - 1, testMaze.getExit().getY());
    }
}
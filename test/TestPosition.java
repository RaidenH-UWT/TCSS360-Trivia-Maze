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

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import src.model.Position;

/**
 * Class for testing the Position class.
 * @author Evan Lei
 * @version May 13, 2025
 */
public class TestPosition {
    /** Test position with coordinates (3,5) */
    private final Position posA = new Position(3, 5);
    /** Duplicate of posA */
    private final Position posB = new Position(3, 5);
    /** Different X coordinate */
    private final Position posC = new Position(4, 5);
    /** Different Y coordinate */
    private final Position posD = new Position(3, 6);
    /** Null position for equality test */
    private final Position posNull = null;

    /**
     * Test constructor and getter methods.
     */
    @Test
    void testConstructorAndGetters() {
        assertEquals(3, posA.getX());
        assertEquals(5, posA.getY());
    }

    /**
     * Test equals() method with various cases.
     */
    @Test
    void testEquals() {
        // Equality cases
        assertTrue(posA.equals(posA));
        assertTrue(posA.equals(posB));
        
        // Inequality cases
        assertNotEquals(posA, posC);
        assertNotEquals(posA, posD);
        assertNotEquals(posA, posNull);
    }
}

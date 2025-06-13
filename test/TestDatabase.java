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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import src.model.DatabaseManager;
import src.model.QuestionFactory;

/**
 * Class for testing the SQLite database
 * @author Raiden H
 * @version May 1, 2025
 */
public class TestDatabase {
    /**
     * Setup the database connection before each test.
     */
    @BeforeEach
    void setup() {
        DatabaseManager.connect();
    }

    /**
     * Test getById to make sure we get the same question every time.
     */
    @Test
    void testGetByID() {
        assertEquals(QuestionFactory.buildQuestion(0), QuestionFactory.buildQuestion(0));
        assertNotEquals(QuestionFactory.buildQuestion(0), QuestionFactory.buildQuestion(1));
    }
}

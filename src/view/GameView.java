/*
 * Copyright 2025 Kalen Cha, Evan Lei, Raiden H
 * 
 * All rights reserved. This software made available under the terms of
 * the GNU General Public License v3 or later
 */
package src.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Interface describing behaviour of GameView classes.
 * @author Raiden H
 * @version May 1, 2025
 */
public interface GameView extends PropertyChangeListener {
    /**
     * List of states for a door to be in
     * 0: Wall
     * 1: Not visited (never been)
     * 2: Visited (been, but hasn't answered yet)
     * 3: Failed
     * 4: Succeeded
     */
    public static final int DOOR_WALL = 0;
    public static final int DOOR_NOT_VISITED = 1;
    public static final int DOOR_VISITED = 2;
    public static final int DOOR_FAILED = 3;
    public static final int DOOR_SUCCEEDED = 4;

    public abstract void propertyChange(final PropertyChangeEvent theEvent);
}
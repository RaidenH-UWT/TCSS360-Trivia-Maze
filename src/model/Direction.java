/*
 * Copyright 2025 Kalen Cha, Evan Lei, Raiden H
 * 
 * All rights reserved. This software made available under the terms of
 * the GNU General Public License v3 or later
 */
package src.model;

/**
 * Enumeration of the four cardinal directions.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version May 1, 2025
 */
public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public Direction getOpposite() {
        return switch (this) {
            case NORTH ->
                SOUTH;
            case SOUTH ->
                NORTH;
            case EAST ->
                WEST;
            case WEST ->
                EAST;
        };
    }
}

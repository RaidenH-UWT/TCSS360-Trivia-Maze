package src.model;

/**
 * Enumeration of the four cardinal directions.
 *
 * @author Raiden H
 * @author Kalen Cha
 * @version May 1, 2025
 */
public enum Direction implements java.io.Serializable {
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

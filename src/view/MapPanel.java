package src.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import src.model.Position;
/**
 * Map panel for display in the view
 * @author Raiden H
 * @version Spring 2025
 */
public class MapPanel extends JPanel {
    private RoomPanel[] myRooms;

    MapPanel(final int theWidth, final int theHeight, final Color theBackgroundColor, final Position theExit) {
        super();

        myRooms = new RoomPanel[theWidth * theHeight];

        setLayout(new GridLayout(theHeight, theHeight, 5, 5));
        setBackground(theBackgroundColor);

        for (int row = 0; row < theHeight; row++) {
            for (int col = 0; col < theHeight; col++) {
                // Start every door off at DOOR_NOT_VISITED
                int[] doorStates = {GameView.DOOR_NOT_VISITED, GameView.DOOR_NOT_VISITED,
                    GameView.DOOR_NOT_VISITED, GameView.DOOR_NOT_VISITED}; // N, S, E, W

                // If this room is on an edge, set the corresponding door to DOOR_WALL
                if (col == 0) {
                    doorStates[3] = GameView.DOOR_WALL;
                }
                if (col == theHeight - 1) {
                    doorStates[2] = GameView.DOOR_WALL;
                }
                if (row == 0) {
                    doorStates[0] = GameView.DOOR_WALL;
                }
                if (row == theHeight - 1) {
                    doorStates[1] = GameView.DOOR_WALL;
                }

                final RoomPanel roomPane = new RoomPanel(new Position(col, row), doorStates, theBackgroundColor);
                if (theExit.equals(new Position(col, row))) {
                    roomPane.setIsExit(true);
                }
                myRooms[row * theHeight + col] = roomPane;
                add(roomPane);
            }
        }
    }

    /**
     * Set the sprite of all rooms to the given sprite.
     * @param theSprite ImageIcon sprite to set
     */
    void updateSprite(final ImageIcon theSprite) {
        for(RoomPanel room : myRooms) {
            room.setSelectedSprite(theSprite);
        }
    }

    /**
     * @return RoomPanel[] in this map.
     */
    RoomPanel[] getRoomPanels() {
        return myRooms;
    }
}

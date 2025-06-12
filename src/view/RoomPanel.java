package src.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

import src.model.Direction;
import src.model.Position;

/**
 * RoomPanel class for the View representation of a Room.
 * @author Raiden H
 * @version Spring 2025
 */
public class RoomPanel extends JPanel {
        /**
         * Position of the RoomPanel relative to the Maze.
         */
        private final Position myPos;

        /**
         * Stores sprites of this room in each direction
         */
        private final Map<Direction, ImageIcon[]> DOORSPRITES = new HashMap<Direction, ImageIcon[]>(4);

        /**
         * Array of states for a door to be in 
         * 0: Wall 1: Not visited 2: Visited
         * 3: Failed 4: Succeeded
         * north, south, east, west (Direction ordinal order)
         */
        private int[] myDoorState;

        /**
         * Background color of the RoomPanel
         */
        private final Color myBackgroundColor;

        /**
         * Whether or not the player is in this room
         */
        private boolean isPlayerPosition = false;

        /**
         * Whether or not this RoomPanel is the exit.
         */
        private boolean myIsExit = false;

        /**
         * Stores selected sprite.
         */
        private ImageIcon mySelectedSprite;

        /**
         * Construct a new RoomPanel object.
         * @param thePos Position of this RoomPanel relative to the Maze
         * @param theRoomState int[] state of the doors in this room (N,S,E,W)
         * @param theBackgroundColor Color for the background of this panel
         */
        RoomPanel(final Position thePos, final int[] theRoomState, final Color theBackgroundColor) {
            super();

            myPos = thePos;
            myDoorState = theRoomState;
            myBackgroundColor = theBackgroundColor;
            setBackground(myBackgroundColor);
        }

        /**
         * Set the state of a door in this RoomPanel.
         * @param theDir Direction of the Door to set
         * @param theState int state to set (reference GameView interface for values)
         */
        void setDoorState(final Direction theDir, final int theState) {
            if (theState >= 0 && theState <= 4) {
                // Assign to index based on the ordinal
                // {NORTH, SOUTH, EAST, WEST}
                myDoorState[theDir.ordinal()] = theState;
            }
            repaint();
        }

        /**
         * Set the state of all doors in this RoomPanel.
         * @param theStates int[] of states in N,S,E,W order
         */
        void setDoorStates(final int[] theStates) {
            for (int state : theStates) {
                if (state < 0 || state > 4) {
                    throw new IllegalArgumentException("Door state not defined");
                }
            }
            myDoorState = theStates;
            repaint();
        }

        /**
         * Set the sprite to display.
         * @param theSprite ImageIcon sprite to set
         */
        void setSelectedSprite(final ImageIcon theSprite) {
            mySelectedSprite = theSprite;
        }

        /**
         * Get the state of the doors in this RoomPanel.
         * @return int[] of the states in N,S,E,W order
         */
        int[] getDoorState() {
            return myDoorState;
        }

        /**
         * Set if the player is in this room.
         * @param theIsPosition boolean true if the player is in this room, false if not
         */
        void setIsPlayerPosition(final boolean theIsPosition) {
            isPlayerPosition = theIsPosition;
        }

        /**
         * Set whether this RoomPanel is the exit or not.
         * @param theIsExit boolean true if is the exit, false otherwise
         */
        void setIsExit(final boolean theIsExit) {
            myIsExit = theIsExit;
        }

        @Override
        public void paintComponent(final Graphics theGraphics) {
            super.paintComponent(theGraphics);

            Graphics2D g2d = (Graphics2D) theGraphics;

            if (myIsExit) {
                g2d.drawImage((new ImageIcon("src/sprites/exit.png")).getImage(), 0, 0, getWidth(), getHeight(), this);
            } else {
                updateDoorTriangles();

                for (Direction dir : Direction.values()) {
                    g2d.drawImage(DOORSPRITES.get(dir)[myDoorState[dir.ordinal()]].getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
            

            if (isPlayerPosition && mySelectedSprite != null) {
                Image spriteImg = mySelectedSprite.getImage();
                int panelW = getWidth();
                int panelH = getHeight();
                int spriteW = Math.min(panelW, panelH) / 2;
                int spriteH = Math.min(panelW, panelH) / 2;
                int x = (panelW - spriteW) / 2;
                int y = (panelH - spriteH) / 2;
                g2d.drawImage(spriteImg, x, y, spriteW, spriteH, this);
            }
        }

        /**
         * Make sure the DOORSPRITES map has its values.
         */
        private void updateDoorTriangles() {
            if (DOORSPRITES.isEmpty()) {
                ImageIcon[] northSprites = {
                    new ImageIcon("src/sprites/wall-NORTH.png"),
                    new ImageIcon("src/sprites/unexplored-NORTH.png"),
                    new ImageIcon("src/sprites/visited-NORTH.png"),
                    new ImageIcon("src/sprites/locked-NORTH.png"),
                    new ImageIcon("src/sprites/unlocked-NORTH.png")
                };

                ImageIcon[] southSprites = {
                    new ImageIcon("src/sprites/wall-SOUTH.png"),
                    new ImageIcon("src/sprites/unexplored-SOUTH.png"),
                    new ImageIcon("src/sprites/visited-SOUTH.png"),
                    new ImageIcon("src/sprites/locked-SOUTH.png"),
                    new ImageIcon("src/sprites/unlocked-SOUTH.png")
                };

                ImageIcon[] eastSprites = {
                    new ImageIcon("src/sprites/wall-EAST.png"),
                    new ImageIcon("src/sprites/unexplored-EAST.png"),
                    new ImageIcon("src/sprites/visited-EAST.png"),
                    new ImageIcon("src/sprites/locked-EAST.png"),
                    new ImageIcon("src/sprites/unlocked-EAST.png")
                };

                ImageIcon[] westSprites = {
                    new ImageIcon("src/sprites/wall-WEST.png"),
                    new ImageIcon("src/sprites/unexplored-WEST.png"),
                    new ImageIcon("src/sprites/visited-WEST.png"),
                    new ImageIcon("src/sprites/locked-WEST.png"),
                    new ImageIcon("src/sprites/unlocked-WEST.png")
                };

                DOORSPRITES.put(Direction.NORTH, northSprites);
                DOORSPRITES.put(Direction.SOUTH, southSprites);
                DOORSPRITES.put(Direction.EAST, eastSprites);
                DOORSPRITES.put(Direction.WEST, westSprites);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        @Override
        public String toString() {
            return myPos.getX() + ", " + myPos.getY() + " with " + Arrays.toString(myDoorState);
        }
}

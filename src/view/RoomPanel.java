package src.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;




import src.model.Direction;
import src.model.Position;

public class RoomPanel extends JPanel {
        /**
         * Array of Colors for door state, indexed according to state constants.
         * Wall, Unexplored, Visited, Locked, Unlocked
         */
        private static final Color[] DOOR_COLORS = {Color.DARK_GRAY, Color.GRAY, Color.BLUE, Color.RED, Color.GREEN};

        private final Position myPos;

        private final Map<Direction, Polygon> DOORTRIANGLES = new HashMap<Direction, Polygon>(4);

        private final Map<Direction, ImageIcon[]> DOORSPRITES = new HashMap<Direction, ImageIcon[]>(4);

        /**
         * Array of states for a door to be in 0: Wall 1: Not visited 2: Visited
         * 3: Failed 4: Succeeded north, south, east, west
         */
        private int[] myDoorState;

        private Color myBackgroundColor;

        private boolean isPlayerPosition = false;

        /**
         * Stores selected sprite.
         */
        private ImageIcon mySelectedSprite;

        RoomPanel(final Position thePos, final int[] theRoomState, final Color theBackgroundColor) {
            super();

            myPos = thePos;
            myDoorState = theRoomState;
            myBackgroundColor = theBackgroundColor;
            setBackground(myBackgroundColor);
        }

        public void setDoorState(final Direction theDir, final int theState) {
            if (theState >= 0 && theState <= 4) {
                // Assign to index based on the ordinal
                // {NORTH, SOUTH, EAST, WEST}
                myDoorState[theDir.ordinal()] = theState;
            }
            repaint();
        }

        public void setDoorStates(final int[] theStates) {
            for (int state : theStates) {
                if (state < 0 || state > 4) {
                    throw new IllegalArgumentException("Door state not defined");
                }
            }
            myDoorState = theStates;
            repaint();
        }

        public void setSelectedSprite(ImageIcon theSprite) {
            mySelectedSprite = theSprite;
        }

        public int[] getDoorState() {
            return myDoorState;
        }

        public void setIsPlayerPosition(boolean theIsPosition) {
            isPlayerPosition = theIsPosition;
        }

        @Override
        public void paintComponent(final Graphics theGraphics) {
            super.paintComponent(theGraphics);

            Graphics2D g2d = (Graphics2D) theGraphics;

            updateDoorTriangles();

            for (Direction dir : Direction.values()) {
                // g2d.setColor(DOOR_COLORS[myDoorState[dir.ordinal()]]);
                // g2d.fillPolygon(DOORTRIANGLES.get(dir));

                // g2d.setColor(myBackgroundColor);
                // g2d.setStroke(new BasicStroke(5));
                // g2d.drawPolygon(DOORTRIANGLES.get(dir));
                g2d.drawImage(DOORSPRITES.get(dir)[myDoorState[dir.ordinal()]].getImage(), 0, 0, getWidth(), getHeight(), this);
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
            int width = getWidth();
            int height = getHeight();

            if (width > 0 && height > 0) {
                Polygon northPoly = new Polygon(
                        new int[]{0, width / 2, width},
                        new int[]{0, height / 2, 0},
                        3
                );

                Polygon southPoly = new Polygon(
                        new int[]{0, width / 2, width},
                        new int[]{height, height / 2, height},
                        3
                );

                Polygon eastPoly = new Polygon(
                        new int[]{width, width / 2, width},
                        new int[]{0, height / 2, height},
                        3
                );

                Polygon westPoly = new Polygon(
                        new int[]{0, width / 2, 0},
                        new int[]{0, height / 2, height},
                        3
                );

                DOORTRIANGLES.put(Direction.NORTH, northPoly);
                DOORTRIANGLES.put(Direction.SOUTH, southPoly);
                DOORTRIANGLES.put(Direction.EAST, eastPoly);
                DOORTRIANGLES.put(Direction.WEST, westPoly);
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

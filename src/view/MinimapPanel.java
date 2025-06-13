/*
 * Copyright 2025 Kalen Cha, Evan Lei, Raiden H
 * 
 * All rights reserved. This software made available under the terms of
 * the GNU General Public License v3 or later
 */
package src.view;

import java.awt.Color;

import src.model.Position;

public class MinimapPanel extends RoomPanel {
    MinimapPanel(final Position thePlayerPosition, final int[] theDoorState, final Color theBackgroundColor) {
        super(thePlayerPosition, theDoorState, theBackgroundColor);
    }
}

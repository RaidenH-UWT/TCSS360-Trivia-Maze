package src.view;

import java.awt.Color;

import src.model.Position;

public class MinimapPanel extends RoomPanel {
    MinimapPanel(final Position thePlayerPosition, final int[] theDoorState, final Color theBackgroundColor) {
        super(thePlayerPosition, theDoorState, theBackgroundColor);
    }
}

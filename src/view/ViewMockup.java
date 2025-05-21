package src.view;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.BorderLayout;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import src.model.Direction;
import src.model.GameState;
import src.model.Maze;
import src.model.Position;
import src.model.PropertyChangeEnabledGameState;
import src.model.Question;
import src.model.Room;
import src.model.GameSaver;

/*
 * @author Kalen Cha
 */
public class ViewMockup implements GameView {

    /**
     * The Dimension of the screen.
     */
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static final Dimension WINDOW_SIZE = new Dimension(1024, 1024);

    /**
     * Reference to the JFrame for the window
     */
    private final JFrame myFrame;

    /**
     * Array of rooms in the maze panel.
     */
    private Component[] myRooms;

    private Position testPos = new Position(0, 0);

    /**
     * Dimensions of the maze (in rooms)
     */
    private final Dimension myMazeSize;
    /**
    * Stores selected sprite.
    */
    private ImageIcon selectedSprite;
    public ViewMockup(GameState theState) {
        super();

        theState.addPropertyChangeListener(this);

        myMazeSize = new Dimension(theState.getMaze().getWidth(), theState.getMaze().getWidth());

        // Initializing the frame
        myFrame = new JFrame("Trivia Maze");

        // Setting behaviour
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(true);
        myFrame.setPreferredSize(WINDOW_SIZE);
    }

    public void initialize() {
        // Adding GUI components
        myFrame.setJMenuBar(createMenuBar());
        myFrame.setContentPane(createContentPane());

        // Displaying the window
        myFrame.pack();
        myFrame.setLocation(SCREEN_SIZE.width / 2 - myFrame.getWidth() / 2,
                SCREEN_SIZE.height / 2 - myFrame.getHeight() / 2);
        myFrame.setVisible(true);
    }

    @Override
    public void displayMaze(Maze theMaze, Position theCurrentPosition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayMaze'");
    }

    @Override
    public void displayRoom(Room theRoom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayRoom'");
    }

    @Override
    public void displayQuestion(Question theQuestion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayQuestion'");
    }

    @Override
    public void displayGameOver(boolean isWon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayGameOver'");
    }

    /**
     * Update the position of the player.
     */
    private void updatePosition(Position oldPos, Position newPos) {
        // called when the player position changes
        ((RoomPanel) myRooms[oldPos.getY() * myMazeSize.width + oldPos.getX()]).resetBackground();

        myRooms[newPos.getY() * myMazeSize.width + newPos.getX()].setBackground(Color.YELLOW);
        myRooms[newPos.getY() * myMazeSize.width + newPos.getX()].repaint();
    }

    /**
     * Update question stats for the player.
     */
    private void updateStats(int answered, int correct) {
        // called when questionsAnswered or questionsCorrect increments, maybe replace?
    }

    /**
     * Update displayed rooms.
     */
    private void updateRooms(LinkedList<Position> visitedRooms) {
        // called when a new room is visited.
    }

    @SuppressWarnings("unchecked") // it's checked by the interface, that event gives that type
    @Override
    public void propertyChange(PropertyChangeEvent theEvent) {
        switch (theEvent.getPropertyName()) {
            case PropertyChangeEnabledGameState.PROPERTY_POSITION:
                updatePosition((Position) theEvent.getOldValue(), (Position) theEvent.getNewValue());
                break;
            case PropertyChangeEnabledGameState.PROPERTY_QUESTION_ANSWERED:
                updateStats(1, 0);
                break;
            case PropertyChangeEnabledGameState.PROPERTY_QUESTION_CORRECT:
                updateStats(0, 1);
                break;
            case PropertyChangeEnabledGameState.PROPERTY_ROOM_VISITED:
                updateRooms((LinkedList<Position>) theEvent.getNewValue());
                break;
            default:
                throw new UnsupportedOperationException("Property change not supported");
        }
    }


    /* 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * GUI EVENT METHODS BELOW
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    // File menu events
    private void newGameEvent(final ActionEvent theEvent) {
        JOptionPane.showMessageDialog(myFrame, "Starting a new game! (reset state, choose game parameters, etc.)");
    }

    private void settingsEvent(final ActionEvent theEvent) {
        JOptionPane.showMessageDialog(myFrame, "Opening settings!");
    }

    private void exitEvent(final ActionEvent theEvent) {
        int selection = JOptionPane.showConfirmDialog(myFrame, "Exit without saving?", "Exit?", JOptionPane.YES_NO_OPTION);
        // Exit only if the user confirms.
        if (selection == 0) {
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    // Save menu events
    private void saveGameEvent(final ActionEvent theEvent) {

        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Save Game");

        int userSelection = fileChooser.showSaveDialog(myFrame);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            try {
                GameState currentState = null;
                GameSaver gameSaver = new GameSaver();
                gameSaver.saveGame(currentState, fileToSave.getAbsolutePath());
                JOptionPane.showMessageDialog(myFrame, "Game saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(myFrame, "Failed to save the game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadGameEvent(final ActionEvent theEvent) {
        // Create a file chooser to select the save file
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Load Game");

        // Show the open dialog
        int userSelection = fileChooser.showOpenDialog(myFrame);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToLoad = fileChooser.getSelectedFile();

            try {
                GameSaver gameSaver = new GameSaver();
                GameState loadedState = gameSaver.getSave(fileToLoad.getAbsolutePath());

                // Assuming we have a method to update the current game state
                updateGameState(loadedState);
                JOptionPane.showMessageDialog(myFrame, "Game loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(myFrame, "Failed to load the game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Updates the current game state with the loaded state.
     *
     * @param loadedState The loaded GameState object.
     */
    private void updateGameState(GameState loadedState) {
        // TODO: Implement logic to update the current game state and refresh the UI
        // maybe just reassign the GameState variable and run all the update methods?
    }

    private void manageSavesEvent(final ActionEvent theEvent) {
        JOptionPane.showMessageDialog(myFrame, "Managing Saves! (opened a file explorer!)");
    }

    // Help menu events
    private void aboutEvent(final ActionEvent theEvent) {
        String aboutMsg = """
        Trivia Maze Game
        Made for TCSS360 Spring 2025 at UWT

        Made by:
        - Evan Lei
        - Kalen Cha
        - Raiden Hiland
        """;
        JOptionPane.showMessageDialog(myFrame, aboutMsg);
    }

    private void howToEvent(final ActionEvent theEvent) {
        String howtoMsg = """
        Answer trivia questions
        """;
        JOptionPane.showMessageDialog(myFrame, howtoMsg);
    }


    /* 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * GUI COMPONENTS BELOW
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    /**
     * Create a new menu bar for the window.
     *
     * @return JMenuBar with necessary elements
     */
    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = buildFileMenu();

        JMenu saveMenu = buildSaveMenu();

        JMenu helpMenu = buildHelpMenu();

        bar.add(fileMenu);
        bar.add(saveMenu);
        bar.add(helpMenu);

        return bar;
    }

    private JMenu buildFileMenu() {
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);

        final JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(this::newGameEvent);

        final JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.addActionListener(this::settingsEvent);

        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this::exitEvent);

        menu.add(newGameItem);
        menu.add(settingsItem);
        menu.addSeparator();
        menu.add(exitItem);

        return menu;
    }

    private JMenu buildSaveMenu() {
        final JMenu menu = new JMenu("Save");
        menu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem saveItem = new JMenuItem("Save Game");
        saveItem.addActionListener(this::saveGameEvent);

        final JMenuItem loadItem = new JMenuItem("Load Game");
        loadItem.addActionListener(this::loadGameEvent);

        final JMenuItem manageItem = new JMenuItem("Manage Saves");
        manageItem.addActionListener(this::manageSavesEvent);

        menu.add(saveItem);
        menu.add(loadItem);
        menu.add(manageItem);

        return menu;
    }

    private JMenu buildHelpMenu() {
        JMenu menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);

        final JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this::aboutEvent);

        final JMenuItem howToItem = new JMenuItem("How to play");
        howToItem.addActionListener(this::howToEvent);

        menu.add(aboutItem);
        menu.add(howToItem);

        return menu;
    }

    private JPanel createContentPane() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Ok this is kind of stupid and excessively complicated. but.
        // int gridx: x coordinate from top left    int gridy: y coordinate from top left
        // int gridwidth: x width    int gridheight: y height
        // double weightx: percent of parent width taken up   double weighty: percent of parent height taken up
        // GridBagConstraints. anchor constant for where this shit goes
        // GridBagConstraints. fill constant if it takes up full height/width?
        // Insets(int top, int left, int bottom, int right) external padding
        // int ipadx: internal x padding   int ipady: internal y padding
        final JPanel mapPanel = createMapPanel();
        GridBagConstraints mapConstraint = new GridBagConstraints(0, 0, 3, 3, 0.7, 0.7,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel minimapPanel = createDoorPanel();
        GridBagConstraints minimapConstraint = new GridBagConstraints(3, 0, 1, 1, 0.3, 0.3,
                GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel statsPanel = createStatsPanel(3, 5);
        GridBagConstraints statsConstraint = new GridBagConstraints(3, 1, 1, 1, 0.3, 0.3,
                GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel questionPanel = createQuestionPanel();
        GridBagConstraints questionConstraint = new GridBagConstraints(3, 2, 1, 1, 0.3, 0.3,
                GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel controlPanel = createControlPanel();
        GridBagConstraints controlConstraint = new GridBagConstraints(3, 3, 1, 1, 0.3, 0.3,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        final JPanel infoPanel = createInfoPanel();
        GridBagConstraints infoConstraint = new GridBagConstraints(0, 3, 3, 1, 0.3, 0.3,
                GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        mainPanel.add(mapPanel, mapConstraint);
        mainPanel.add(minimapPanel, minimapConstraint);
        mainPanel.add(statsPanel, statsConstraint);
        mainPanel.add(questionPanel, questionConstraint);
        mainPanel.add(controlPanel, controlConstraint);
        mainPanel.add(infoPanel, infoConstraint);

        return mainPanel;
    }

    /*
    private JPanel createMapPanel() {
        // If we're going to do this, it probably shouldn't be an inline class
        // either break it off into a seperate class
        // or an internal class
        final JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();
                // TODO: Rework implementation to work with non-square mazes
                int rows = myMazeSize.height;
                int cols = myMazeSize.width;

                Color nodeColor = new Color(192, 192, 192);
                Color arrowColor = new Color(255, 255, 255);
                Color bgColor = new Color(0, 0, 0);

                setBackground(bgColor);

                int spacingX = width / (cols + 1);
                int spacingY = height / (rows + 1);
                int nodeSize = Math.min(spacingX, spacingY) / 2;

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        int x = spacingX * (col + 1) - nodeSize / 2;
                        int y = spacingY * (row + 1) - nodeSize / 2;

                        if (row == rows - 1 && col == cols - 1) {
                            g.setColor(new Color(255, 0, 0));
                            g.fillRect(x, y, nodeSize, nodeSize);

                            g.setColor(Color.BLACK);
                            g.drawRect(x, y, nodeSize, nodeSize);

                            g.setColor(Color.WHITE);
                            g.setFont(new Font("Monospaced", Font.BOLD, 18));
                            FontMetrics fm = g.getFontMetrics();
                            String endText = "END";
                            int textWidth = fm.stringWidth(endText);
                            int textHeight = fm.getAscent();

                            g.drawString(endText,
                                    x + (nodeSize - textWidth) / 2,
                                    y + (nodeSize + textHeight) / 2 - 2);
                        } else {
                            g.setColor(new Color(192, 192, 192));
                            g.fillRect(x, y, nodeSize, nodeSize);

                            g.setColor(Color.BLACK);
                            g.drawRect(x, y, nodeSize, nodeSize);
                        }

                        if (col < cols - 1) {
                            int midX = x + nodeSize;
                            int midY = y + nodeSize / 2;
                            g.setColor(Color.WHITE);
                            g.fillRect(midX, midY - 2, spacingX - nodeSize, 4);
                        }

                        if (row < rows - 1) {
                            int midX = x + nodeSize / 2;
                            int midY = y + nodeSize;
                            g.setColor(Color.WHITE);
                            g.fillRect(midX - 2, midY, 4, spacingY - nodeSize);
                        }
                    }
                }

            }
        };

        panel.setBackground(Color.BLACK);
        return panel;
    }
*/

    // Reworked createMapPanel() to work with any dimensions and use seperate RoomPanels 
    // instead of coloured boxes.
    private JPanel createMapPanel() {
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(myMazeSize.width, myMazeSize.height, 5, 5));
        panel.setBackground(Color.BLACK);

        for (int row = 0; row < myMazeSize.width; row++) {
            for (int col = 0; col < myMazeSize.height; col++) {
                final JPanel roomPane = new RoomPanel(new Position(row, col));
                panel.add(roomPane, row,  col);
            }
        }

        myRooms = panel.getComponents();

        updatePosition(new Position(0,0), new Position(0, 0));

        return panel;
    }

    private JPanel createDoorPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));
        panel.setLayout(new GridBagLayout());

        JButton openDoorButton = new JButton("OPEN DOOR");
        openDoorButton.addActionListener(this::positionHelper); // TODO: TEMPORARY
        openDoorButton.setOpaque(true);
        openDoorButton.setContentAreaFilled(true);
        openDoorButton.setBorderPainted(true);
        openDoorButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        openDoorButton.setBackground(new Color(255, 204, 0));
        openDoorButton.setForeground(Color.BLACK);
        openDoorButton.setFocusPainted(false);
        openDoorButton.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK, 2));
        openDoorButton.setPreferredSize(new Dimension(450, 100));

        panel.add(openDoorButton);
        return panel;
    }

    // TODO: TEMPORARY
    private void positionHelper(final ActionEvent theEvent) {
        Position temp = testPos;
        testPos = new Position((testPos.getX() + 1) % myMazeSize.width, (testPos.getY() + 1) % myMazeSize.height);
        updatePosition(temp, testPos);
    }

    private JPanel createStatsPanel(int answered, int failed) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 18));
        panel.setLayout(new GridBagLayout());

        Font labelFont = new Font("Monospaced", Font.BOLD, 14);

        JLabel answeredLabel = new JLabel("QUESTIONS ANSWERED: " + answered);
        answeredLabel.setFont(labelFont);
        answeredLabel.setForeground(Color.GREEN);

        JLabel failedLabel = new JLabel("QUESTIONS FAILED:   " + failed);
        failedLabel.setFont(labelFont);
        failedLabel.setForeground(new Color(255, 85, 85));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(answeredLabel, gbc);

        gbc.gridy = 1;
        panel.add(failedLabel, gbc);

        return panel;
    }

    private JPanel createQuestionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel questionLabel = new JLabel("QUESTION: ");
        questionLabel.setForeground(new Color(0, 191, 255));
        questionLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(questionLabel, gbc);

        JLabel currentQuestion = new JLabel("2 + 2 = 5 (T OR F)");
        currentQuestion.setForeground(Color.WHITE);
        currentQuestion.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(currentQuestion, gbc);

        JLabel answerLabel = new JLabel("ANSWER >");
        answerLabel.setForeground(new Color(106, 90, 205));
        answerLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(answerLabel, gbc);

        JTextField answerField = new JTextField(20);
        answerField.setBackground(Color.BLACK);
        answerField.setForeground(Color.GREEN);
        answerField.setCaretColor(Color.GREEN);
        answerField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gbc.gridx = 1;

        return panel;
    }

    private JPanel createControlPanel() {
        final JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int width = getWidth();
                int height = getHeight();
                int centerX = width / 2;
                int centerY = height / 2;

                int crossSize = Math.min(width, height) / 2;
                int armWidth = crossSize / 3;
                int circleRadius = armWidth / 2;
                int arrowSize = armWidth / 2;

                circleRadius = (int) (circleRadius * 0.6);

                java.awt.GradientPaint gradientPaint = new java.awt.GradientPaint(
                        0, 0, new Color(60, 60, 60),
                        width, height, new Color(40, 40, 40));
                ((java.awt.Graphics2D) g).setPaint(gradientPaint);

                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                int vX = centerX - armWidth / 2;
                int vY = centerY - crossSize / 2;
                int hX = centerX - crossSize / 2;
                int hY = centerY - armWidth / 2;

                RoundRectangle2D verticalArm = new RoundRectangle2D.Float(vX, vY, armWidth, crossSize, 10, 10);
                RoundRectangle2D horizontalArm = new RoundRectangle2D.Float(hX, hY, crossSize, armWidth, 10, 10);

                g2d.fill(verticalArm);
                g2d.fill(horizontalArm);

                g2d.setColor(Color.BLACK);
                g2d.draw(verticalArm);
                g2d.draw(horizontalArm);

                g.setColor(new Color(26, 26, 26));
                g.fillOval(centerX - circleRadius, centerY - circleRadius,
                        circleRadius * 2, circleRadius * 2);

                g.setColor(new Color(26, 26, 26));

                drawDirectionalArrow(g2d, centerX, centerY - crossSize / 3, Direction.NORTH, arrowSize);
                drawDirectionalArrow(g2d, centerX, centerY + crossSize / 3, Direction.SOUTH, arrowSize);
                drawDirectionalArrow(g2d, centerX - crossSize / 3, centerY, Direction.WEST, arrowSize);
                drawDirectionalArrow(g2d, centerX + crossSize / 3, centerY, Direction.EAST, arrowSize);
            }

            private void drawDirectionalArrow(java.awt.Graphics2D g, int x, int y, Direction dir, int size) {
                int[] xPoints = new int[3];
                int[] yPoints = new int[3];

                switch (dir) {
                    case NORTH -> {
                        xPoints = new int[]{x, x - size / 2, x + size / 2};
                        yPoints = new int[]{y - size / 2, y + size / 2, y + size / 2};
                    }
                    case SOUTH -> {
                        xPoints = new int[]{x, x - size / 2, x + size / 2};
                        yPoints = new int[]{y + size / 2, y - size / 2, y - size / 2};
                    }
                    case EAST -> {
                        xPoints = new int[]{x + size / 2, x - size / 2, x - size / 2};
                        yPoints = new int[]{y, y - size / 2, y + size / 2};
                    }
                    case WEST -> {
                        xPoints = new int[]{x - size / 2, x + size / 2, x + size / 2};
                        yPoints = new int[]{y, y - size / 2, y + size / 2};
                    }
                }

                g.fillPolygon(xPoints, yPoints, 3);
            }
        };

        panel.setPreferredSize(new Dimension(200, 200));
        panel.setBackground(new Color(240, 240, 240));

        return panel;
    }

    private JPanel createInfoPanel() {
       final JPanel panel = new JPanel();
            panel.setBackground(new Color(0, 0, 0));
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
            int[] widths = {56, 80, 60, 90};
            int[] heights = {56, 100, 60, 60};
            String[] imagePaths = {
                "src/Sprites/kirby1.png",
                "src/Sprites/kirby2.png",
                "src/Sprites/kirby3.png",
                "src/Sprites/kirby4.png"
            };
            
            JLabel[] kirbyLabels = new JLabel[imagePaths.length];
            
           
            final Border selectedBorder = BorderFactory.createLineBorder(Color.YELLOW, 3);
            final Border defaultBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
            
            final JLabel[] selectedLabel = {null};
            
            for (int i = 0; i < imagePaths.length; i++) {
                ImageIcon originalIcon = new ImageIcon(imagePaths[i]);
             
                Image scaledImage = originalIcon.getImage().getScaledInstance(widths[i], heights[i], Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
            
                JLabel label = new JLabel(scaledIcon);
                label.setBorder(defaultBorder);
                kirbyLabels[i] = label;
                final int index = i;
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                     public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int choice = JOptionPane.showConfirmDialog(
                        panel,
                        "Confirm selection of character" + (index + 1) + "?",
                        "Confirm Selection",
                        JOptionPane.YES_NO_OPTION
                    );
            
                    if (choice == JOptionPane.YES_OPTION) {
                        if (selectedLabel[0] != null) {
                            selectedLabel[0].setBorder(defaultBorder);
                        }
                        label.setBorder(selectedBorder);
                        selectedLabel[0] = label;
            
                       
                        selectedSprite = ((ImageIcon) label.getIcon());
                } else {
                    selectedSprite = null;
                }
            }
            });
    
            panel.add(label);
        }
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.BLACK);
    
       
            mainPanel.add(panel, BorderLayout.CENTER);
    
            JLabel chooseLabel = new JLabel("CHOOSE YOUR CHARACTER");
            chooseLabel.setForeground(Color.YELLOW);
            chooseLabel.setHorizontalAlignment(JLabel.CENTER);
            chooseLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
            mainPanel.add(chooseLabel, BorderLayout.NORTH);

        return mainPanel;
        
    }
    private ImageIcon getSelectedSprite() {
        return selectedSprite;
    }
    

    private class RoomPanel extends JPanel {
        // Add display for rooms
        // i'm thinking 4 triangles, 1 for each cardinal direction
        // and colour coded based on lock state
        // and mystery colour if they haven't been visited yet.
        private final Position myPos;

        private Color myColor;

        public RoomPanel(Position thePos) {
            super();

            myPos = thePos;
            myColor = new Color((myPos.getX() * 32) % 255, 0, (myPos.getY() * 32) % 255);
            setBackground(myColor);
        }

        public void resetBackground() {
            myColor = new Color((myPos.getX() * 32) % 255, 0, (myPos.getY() * 32) % 255);
            setBackground(myColor);
            repaint();
        }

        @Override
        public String toString() {
            return myPos.getX() + ", " + myPos.getY() + " with " + this.getBackground().toString();
        }
    }
}

package src.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import src.model.Direction;
/**
 * Control panel for display in the view
 * @author Raiden H
 * @author Kalen Cha
 * @version Spring 2025
 */
@Deprecated // Broken implementation, unused
public class ControlPanel extends JPanel {
    ControlPanel(final Consumer<Direction> theInputHandler) {
        super();

        final int panelSize = 200;
        JPanel dPadPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon dpadImage = new ImageIcon("src/sprites/dpadTrimmed.png");
                Image img = dpadImage.getImage();
                g.drawImage(img, 83, 10, panelSize, panelSize, this);

            }
        };

        ImageIcon upImage = new ImageIcon("src/sprites/upArrow.png");
        Image upImg = upImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon rightImage = new ImageIcon("src/sprites/rightArrow.png");
        Image rightImg = rightImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon leftImage = new ImageIcon("src/sprites/leftArrow.png");
        Image leftImg = leftImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon downImage = new ImageIcon("src/sprites/downArrow.png");
        Image downImg = downImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        ImageIcon upIcon = new ImageIcon(upImg);
        ImageIcon rightIcon = new ImageIcon(rightImg);
        ImageIcon leftIcon = new ImageIcon(leftImg);
        ImageIcon downIcon = new ImageIcon(downImg);

        JButton upButton = new JButton(upIcon);
        JButton rightButton = new JButton(rightIcon);
        JButton leftButton = new JButton(leftIcon);
        JButton downButton = new JButton(downIcon);

        dPadPanel.setPreferredSize(new Dimension(panelSize, panelSize));
        dPadPanel.setLayout(null);

        upButton.setBounds(151, 23, 66, 66);
        upButton.setOpaque(false);
        upButton.setContentAreaFilled(false);
        upButton.setBorderPainted(false);

        rightButton.setBounds(200, 75, 66, 66);
        rightButton.setOpaque(false);
        rightButton.setContentAreaFilled(false);
        rightButton.setBorderPainted(false);

        leftButton.setBounds(100, 75, 66, 66);
        leftButton.setOpaque(false);
        leftButton.setContentAreaFilled(false);
        leftButton.setBorderPainted(false);

        downButton.setBounds(151, 128, 66, 66);
        downButton.setOpaque(false);
        downButton.setContentAreaFilled(false);
        downButton.setBorderPainted(false);

        upButton.addActionListener(e -> theInputHandler.accept(Direction.NORTH));
        rightButton.addActionListener(e -> theInputHandler.accept(Direction.EAST));
        leftButton.addActionListener(e -> theInputHandler.accept(Direction.WEST));
        downButton.addActionListener(e -> theInputHandler.accept(Direction.SOUTH));

        dPadPanel.add(upButton);
        dPadPanel.add(rightButton);
        dPadPanel.add(leftButton);
        dPadPanel.add(downButton);
        dPadPanel.setBackground(new Color(215, 215, 215));
    }
}

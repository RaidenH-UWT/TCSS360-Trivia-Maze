package src.view;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import javax.swing.JDialog;

/**
 * Settings for the Trivia Maze game.
 * Allows users to toggle game settings such as music.
 * @author Kalen Cha
 * @version Spring 2025
 */
public class SettingsPage extends JDialog {
     public SettingsPage(JFrame parentFrame, MusicPlayer musicPlayer) {
        super(parentFrame, "Settings", true);
        
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(0, 1, 0, 10));

        JCheckBox musicToggle = new JCheckBox("Turn off music");
        musicToggle.setSelected(!musicPlayer.isPlaying());
        musicToggle.setFocusPainted(false);

        musicToggle.addItemListener(e -> {
            if (musicToggle.isSelected()) {
                musicPlayer.stopMusic();
            } else {
                musicPlayer.playMusic("src/music/SundayPicnic.wav");
            }
        });

        panel.add(musicToggle);
        
        // Add more settings options here as needed
        
        add(panel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        javax.swing.JButton closeButton = new javax.swing.JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack(); 
        setSize(300, 150); 
        setLocationRelativeTo(parentFrame);
    }
}


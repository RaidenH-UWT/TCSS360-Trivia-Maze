package src.view;

import java.awt.*;
import java.awt.event.ItemEvent;
import javax.swing.*;

/**
 * A settings window for the Trivia Maze game. Lets the user control background
 * music and sound effects. Includes toggles and sliders for volume.
 *
 * @author Kalen
 * @version Spring 2025
 */
public class SettingsPage extends JDialog {

    /**
     * Creates the settings page with music and sound settings.
     *
     * @param parentFrame the main game window
     * @param musicPlayer the music player controlling sounds
     */
    SettingsPage(JFrame parentFrame, MusicPlayer musicPlayer) {
        super(parentFrame, "Settings", true);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

        JLabel musicVolumeLabel = new JLabel("Music Volume:");
        JSlider musicSlider = new JSlider(0, 100, (int) (musicPlayer.getMusicVolume() * 100));
        musicSlider.setMajorTickSpacing(25);
        musicSlider.setPaintTicks(true);
        musicSlider.setPaintLabels(true);
        musicSlider.addChangeListener(e -> {
            float newMusicVolume = musicSlider.getValue() / 100f;
            musicPlayer.setMusicVolume(newMusicVolume);
        });
        panel.add(musicVolumeLabel);
        panel.add(musicSlider);

        JCheckBox sfxToggle = new JCheckBox("Enable sound effects");
        sfxToggle.setSelected(musicPlayer.isSoundEffectsEnabled());
        sfxToggle.setFocusPainted(false);
        sfxToggle.addItemListener(e -> {
            boolean enabled = e.getStateChange() == ItemEvent.SELECTED;
            musicPlayer.setSoundEffectsEnabled(enabled);
        });
        panel.add(sfxToggle);

        JLabel sfxVolumeLabel = new JLabel("Sound Effect Volume:");
        JSlider volumeSlider = new JSlider(0, 100, (int) (musicPlayer.getSoundEffectsVolume() * 100));
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(e -> {
            float newVolume = volumeSlider.getValue() / 100f;
            musicPlayer.setSoundEffectsVolume(newVolume);
        });
        panel.add(sfxVolumeLabel);
        panel.add(volumeSlider);

        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setSize(350, 350);
        setLocationRelativeTo(parentFrame);
    }
}

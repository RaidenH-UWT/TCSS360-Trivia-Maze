/*
 * Copyright 2025 Kalen Cha, Evan Lei, Raiden H
 * 
 * All rights reserved. This software made available under the terms of
 * the GNU General Public License v3 or later
 */
package src.view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * Plays music and sound effects. Now supports global mute and volume control
 * for sound effects.
 *
 * @author Kalen Cha
 * @version Spring 2025
 */
public class MusicPlayer {

    /**
     * Clip for background music
     */
    private Clip backgroundClip;

    /**
     * Whether sound effects are enabled
     */
    private boolean soundEffectsEnabled = true;

    /**
     * Volume for sound effects (0.0 to 1.0)
     */
    private float soundEffectsVolume = 1.0f;

    /**
     * Volume for music (0.0 to 1.0)
     */
    private float musicVolume = 1.0f;

    /**
     * Plays looping background music from a .wav file.
     *
     * @param musicFilePath Path to the .wav file
     */
    public void playMusic(String musicFilePath) {
        try {
            File musicFile = new File(musicFilePath);
            if (!musicFile.exists()) {
                System.err.println("Music file not found: " + musicFilePath);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioInput);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the background music if it's playing.
     */
    public void stopMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    /**
     * Checks if background music is currently playing.
     */
    public boolean isPlaying() {
        return backgroundClip != null && backgroundClip.isRunning();
    }

    /**
     * Plays a one-shot sound effect.
     *
     * @param soundFilePath Path to the .wav file
     */
    public void playSoundEffect(String soundFilePath) {
        if (!soundEffectsEnabled) {
            return;
        }

        try {
            File soundFile = new File(soundFilePath);
            if (!soundFile.exists()) {
                System.err.println("Sound effect not found: " + soundFilePath);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (20f * Math.log10(Math.max(soundEffectsVolume, 0.0001)));
                gainControl.setValue(dB);
            } else {
                System.err.println("Volume control not supported for: " + soundFilePath);
            }

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Enable or disable all sound effects.
     *
     * @param enabled true to allow sound effects
     */
    public void setSoundEffectsEnabled(boolean enabled) {
        this.soundEffectsEnabled = enabled;
    }

    /**
     * Check if sound effects are enabled.
     *
     * @return true if sound effects are on
     */
    public boolean isSoundEffectsEnabled() {
        return soundEffectsEnabled;
    }

    /**
     * Sets the volume level for sound effects.
     *
     * @param volume value between 0.0 (mute) and 1.0 (full volume)
     */
    public void setSoundEffectsVolume(float volume) {
        soundEffectsVolume = Math.max(0f, Math.min(volume, 1f));
    }

    /**
     * Gets the current sound effects volume.
     *
     * @return volume from 0.0 to 1.0
     */
    public float getSoundEffectsVolume() {
        return soundEffectsVolume;
    }

    /**
     * Gets the current background music volume.
     *
     * @return volume from 0.0 to 1.0
     */
    public float getMusicVolume() {
        return musicVolume;
    }

    /**
     * Sets the volume for background music.
     *
     * @param volume value between 0.0 and 1.0
     */
    public void setMusicVolume(float volume) {
        musicVolume = Math.max(0f, Math.min(volume, 1f));
        if (backgroundClip != null && backgroundClip.isOpen()) {
            setClipVolume(backgroundClip, musicVolume);
        }
    }

    /**
     * Sets the volume for background music.
     *
     * @param volume value between 0.0 and 1.0
     */
    private void setClipVolume(Clip clip, float volume) {
        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = gainControl.getMinimum() + (range * volume);
            gainControl.setValue(gain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package src.view;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Plays music.
 *
 * @author Kalen Cha
 * @version Spring 2025
 */
public class MusicPlayer {

    private Clip backgroundClip;

    /**
     * Plays a looping background music clip from a .wav file.
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
     * Stops and closes the music if it is playing.
     */
    public void stopMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    /**
     * Returns whether music is currently playing.
     */
    public boolean isPlaying() {
        return backgroundClip != null && backgroundClip.isRunning();
    }
}

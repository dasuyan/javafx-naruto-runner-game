package Utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music {
    private static String path;
    private Clip clip;
    public Music(String path) {
        Music.path = path;
    }
    public  synchronized void play() {
        new Thread(() -> {
            try {
                File file = new File(path);
                if (file.exists()) {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                    clip = AudioSystem.getClip();
                    clip.open(inputStream);
                    clip.start();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    //if (!play) Thread.currentThread().interrupt();
    }
}

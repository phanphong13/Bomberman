package uet.oop.bomberman.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    Clip clip;
    boolean running = false;
    public static boolean isMute = false;
    private String fileName;

    public static String soundGame = "bomberman";
    public static String soundMenu = "loop";

    public Sound(String fileName) {
        this.fileName = fileName;
        String path = (System.getProperty("user.dir") + "\\res\\sound\\" + fileName + ".wav");
        try {
            File file = new File(path);
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            clip.open(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {
        if (!isMute && !running) {
            clip.setFramePosition(0);
            if (fileName.equals(soundGame) || fileName.equals(soundMenu)) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
            running = true;
        }
    }

    public void stop() {
        clip.stop();
        running = false;
    }

    /**
     * Dùng cho bom nổ, đặt bom, ăn item.
     * @param fileName
     */

    public static void playSound(String fileName) {
        if (!isMute) {
            String path = (System.getProperty("user.dir") + "\\res\\sound\\" + fileName + ".wav");

            try {
                File file = new File(path);
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                clip.open(inputStream);

                clip.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }
}

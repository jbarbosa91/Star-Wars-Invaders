package org.academiadecodigo.spaceinvaders;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    public Clip clip;
    URL soundURL[] = new URL[20];
    public Sound() {
        soundURL[0] = getClass().getResource("/Sounds/Music_start.wav");
        soundURL[1] = getClass().getResource("/Sounds/Music_loop.wav");
        soundURL[2] = getClass().getResource("/Sounds/MF_Shot.wav");
        soundURL[3] = getClass().getResource("/Sounds/Explosion.wav");
        soundURL[4] = getClass().getResource("/Sounds/Game_Over_Music.wav");
        soundURL[5] = getClass().getResource("/Sounds/Victory_Music.wav");
        soundURL[6] = getClass().getResource("/Sounds/Boss_Start.wav");
        soundURL[7] = getClass().getResource("/Sounds/Boss_Loop.wav");
    }

    public void setFile (int i) {
        try {
            AudioInputStream as = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(as);
        } catch (Exception e) {
            System.out.println("shit");
        }
    }
    public void play() {
        clip.start();
    }
    public void loop () {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop () {
        clip.stop();
    }
}

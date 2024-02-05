package main;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    private Random random;

    public Sound() {
        random = new Random();
    }

    public void PlayClick() {
        PlaySound("click.wav");
    }

    public void PlayChat() {
        PlaySound("chat.wav");
    }

    public void PlayPush() {
        int clip = random.nextInt(0, 4);

        switch (clip) {
            case 0:
                PlaySound("pitchC.wav");
                break;

            case 1:
                PlaySound("pitchD.wav");

                break;
            case 2:
                PlaySound("pitchE.wav");

                break;
            case 3:
                PlaySound("pitchG.wav");

                break;
            case 4:
                PlaySound("pitchA.wav");
                break;

            default:
                break;
        }
    }

    private void PlaySound(String filepath) {
        try {
            File soundPath = new File(filepath);

            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("File Does Not Exist");
            }
        } catch (javax.sound.sampled.LineUnavailableException e) {
            System.out.println("Audio format not supported: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

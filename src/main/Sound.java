package main;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    private Random random;

    private String[] pushFiles = {
            // Pentatonic
            "pitchC.wav",
            "pitchD.wav",
            "pitchE.wav",
            "pitchG.wav",
            "pitchA.wav",
            // Major
            "pitchF.wav",
            "pitchB.wav",
    };

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
        int clip = 0;
        if (Settings.PentatonicScale)
            clip = random.nextInt(0, 4);

        else if (Settings.MajorScale)
            clip = random.nextInt(0, 7);

        var file = pushFiles[clip];
        PlaySound(file);

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

package main;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

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
        if (!Settings.MusicEnabled)
            return;

        var scale = Settings.MusicScale[Settings.MusicIndex];

        int clip = random.nextInt(0, scale.length);

        var index = scale[clip];

        PlaySoundPitchShift("pitchC.wav", index);
    }

    public void PlayBass() {
        if (!Settings.MusicEnabled)
            return;

        var scale = Settings.MusicScale[Settings.MusicIndex];

        int clip = random.nextInt(0, scale.length);

        var index = scale[clip];

        PlaySoundPitchShift("bassC.wav", index);
    }

    public void PlayDrums() {
        if (!Settings.MusicEnabled)
            return;

        int clip = random.nextInt(0, 3);

        switch (clip) {
            case 0:
                PlaySound("kick.wav");
                break;
            case 1:
                PlaySound("snare.wav");
                break;
            case 3:
                PlaySound("hat.wav");
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

    public void PlaySoundPitchShift(String filepath, int cents) {
        try {
            File soundFile = new File(filepath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat originalFormat = audioInputStream.getFormat();

            // Calculate the new sample rate based on the speed factor in cents
            double factor = Math.pow(2, cents / 1200.0);
            float newSampleRate = (float) (originalFormat.getSampleRate() * factor);

            // System.out.println("Original Sample Rate: " +
            // originalFormat.getSampleRate());
            // System.out.println("New Sample Rate: " + newSampleRate);

            AudioFormat newFormat = new AudioFormat(
                    originalFormat.getEncoding(),
                    newSampleRate,
                    originalFormat.getSampleSizeInBits(),
                    originalFormat.getChannels(),
                    originalFormat.getFrameSize(),
                    newSampleRate,
                    originalFormat.isBigEndian());

            if (!AudioSystem.isLineSupported(new DataLine.Info(Clip.class, newFormat))) {
                System.out.println("The new format is not supported.");
                return; // Early exit if the format is not supported
            }

            AudioInputStream newAudioInputStream = new AudioInputStream(audioInputStream, newFormat,
                    audioInputStream.getFrameLength());
            Clip clip = AudioSystem.getClip();
            clip.open(newAudioInputStream);
            clip.start(); // Start the clip to play the sound

            // Wait for the clip to finish playing
            clip.drain(); // Ensure the audio playback is completed before returning

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

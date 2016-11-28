import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.Paths;

public class Audio {
    String hit = "NFF-punch.wav";
    String miss = "NFF-slam.wav";
    String menuSound = "NFF-squirt.wav";
    String modifier = "NFF-laser-zapping.wav";
    String song1 = "Energy-Loop.mp3";
    String song2 = "House-Loop-2016.mp3";
    String song3 = "Solid-Beat-Loop.mp3";
    MediaPlayer mediaPlayer;

    public Audio(){
        JFXPanel fxPanel = new JFXPanel();
    }

    public void playSound(String sound){
        if(!Settings.soundOn){
            return;
        }
        new Thread((new Runnable() {
            @Override
            public void run() {
                try{
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(sound));
                    clip.open(inputStream);
                    clip.start();
                }
                catch (Exception e){
                    System.out.println("Exception during playsound: " + e.toString());
                }
            }
        })).start();
    }

    public void playHit(){
        playSound(hit);
    }

    public void playSpecialHit(){
        playSound(modifier);
    }

    public void playMiss(){
        playSound(miss);
    }

    public void playMenuSound(){
        playSound(menuSound);
    }

    public void playMusic(){
        if(!Settings.soundOn){
            return;
        }
        int rand = Main.random.nextInt(3);
        String currentAudioFile = Paths.get(song1).toUri().toString();
        System.out.println("Audio file to play: " + currentAudioFile);

        if(rand==1){
            currentAudioFile = Paths.get(song2).toUri().toString();;
        }
        else if(rand==2){
            currentAudioFile = Paths.get(song3).toUri().toString();
        }
        Media song = new Media(currentAudioFile);
        mediaPlayer = new MediaPlayer(song);
        try {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        }
        catch (Exception e){
            System.out.println("Exception: " + e);
        }
        mediaPlayer.play();
    }

    public void resumeMusic(){
        if(mediaPlayer!=null && mediaPlayer.getStatus()== MediaPlayer.Status.PAUSED){
            mediaPlayer.play();
        }
    }

    public void pauseMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }
}

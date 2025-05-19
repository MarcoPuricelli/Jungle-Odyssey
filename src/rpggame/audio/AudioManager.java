package rpggame.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private Clip currentMusic;
    private Map<String, Clip> soundEffects;
    
    public AudioManager() {
        soundEffects = new HashMap<>();
        preloadSoundEffects();
    }
    
    private void preloadSoundEffects() {
        //precarica gli effetti sonori comuni
        loadSoundEffect("attack", "/sounds/attack.wav");
        loadSoundEffect("hit", "/sounds/hit.wav");
        loadSoundEffect("miss", "/sounds/miss.wav");
        loadSoundEffect("critical", "/sounds/critical.wav");
    }
    
    private void loadSoundEffect(String name, String path) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                soundEffects.put(name, clip);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Impossibile caricare l'effetto sonoro: " + path);
        }
    }
    
    public void playHomeMusic() {
        playMusic("/sounds/home_music.wav");
    }
    
    public void playWorldMusic(String worldName) {
        switch (worldName) {
            case "Sporecradle Woods":
                playMusic("/sounds/forest_music.wav");
                break;
            case "Embervault Depths":
                playMusic("/sounds/cave_music.wav");
                break;
            case "Duskveil Reach":
                playMusic("/sounds/dark_music.wav");
                break;
            case "Boss":
                playMusic("/sounds/boss_music.wav");
                break;
            default:
                playMusic("/sounds/battle_music.wav");
        }
    }
    
    public void playVictoryMusic() {
        playMusic("/sounds/victory_music.wav");
    }
    
    public void playDefeatMusic() {
        playMusic("/sounds/defeat_music.wav");
    }
    
    private void playMusic(String path) {
        //ferma la musica corrente se in riproduzione
        stopMusic();
        
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                currentMusic = AudioSystem.getClip();
                currentMusic.open(audioIn);
                currentMusic.loop(Clip.LOOP_CONTINUOUSLY);
                currentMusic.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Impossibile riprodurre la musica: " + path);
        }
    }
    
    public void stopMusic() {
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
            currentMusic.close();
        }
    }
    
    public void playSoundEffect(String name) {
        Clip clip = soundEffects.get(name);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
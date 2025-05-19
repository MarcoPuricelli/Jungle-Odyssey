package rpggame.data;

import rpggame.characters.Character;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//classe GameData che gestisce i dati di gioco e implementa Serializable per poter salvare/caricare lo stato
public class GameData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; //id di versione per la serializzazione

    //campi relativi ai dati di gioco
    private String username; //nome utente del giocatore
    private int victories; //numero di vittorie
    private List<String> completedWorlds; //lista di mondi completati
    private Character playerCharacter; //personaggio giocante

    //dati per la benedizione
    private LocalDate blessingDate; //data della benedizione
    private boolean blessingActive; //stato attivo/inattivo della benedizione
    private String blessedCharacter; //nome del personaggio benedetto
    private int blessingGamesLeft; //numero di partite rimanenti con la benedizione attiva

    //costruttore di default che inizializza valori di base
    public GameData() {
        username = "Giocatore"; //nome di default
        victories = 0; //zero vittorie iniziali
        completedWorlds = new ArrayList<>(); //lista vuota di mondi completati
        blessingActive = false; //benedizione non attiva all'inizio
        blessingGamesLeft = 0; //nessuna partita con benedizione
    }

    //getter e setter per username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //getter e metodo per incrementare le vittorie
    public int getVictories() {
        return victories;
    }

    public void incrementVictories() {
        victories++;
    }

    //getter e setter per la lista dei mondi completati
    public List<String> getCompletedWorlds() {
        return completedWorlds;
    }

    public void setCompletedWorlds(List<String> completedWorlds) {
        this.completedWorlds = completedWorlds;
    }

    //getter e setter per il personaggio giocante
    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(Character playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    //getter e setter per la data della benedizione
    public LocalDate getBlessingDate() {
        return blessingDate;
    }

    public void setBlessingDate(LocalDate blessingDate) {
        this.blessingDate = blessingDate;
    }

    //getter e setter per lo stato della benedizione
    public boolean isBlessingActive() {
        return blessingActive;
    }

    public void setBlessingActive(boolean blessingActive) {
        this.blessingActive = blessingActive;
    }

    //getter e setter per il nome del personaggio benedetto
    public String getBlessedCharacter() {
        return blessedCharacter;
    }

    public void setBlessedCharacter(String blessedCharacter) {
        this.blessedCharacter = blessedCharacter;
    }

    //getter e setter per le partite rimanenti con benedizione
    public int getBlessingGamesLeft() {
        return blessingGamesLeft;
    }

    public void setBlessingGamesLeft(int blessingGamesLeft) {
        this.blessingGamesLeft = blessingGamesLeft;
    }

    //metodo per salvare i dati di gioco su file usando serializzazione
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("gamedata.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //metodo per caricare i dati di gioco da file se presente
    public void loadData() {
        File file = new File("gamedata.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                GameData loadedData = (GameData) ois.readObject();

                //assegna i dati caricati all'istanza corrente
                this.username = loadedData.username;
                this.victories = loadedData.victories;
                this.completedWorlds = loadedData.completedWorlds;
                this.blessingDate = loadedData.blessingDate;
                this.blessingActive = loadedData.blessingActive;
                this.blessedCharacter = loadedData.blessedCharacter;
                this.blessingGamesLeft = loadedData.blessingGamesLeft;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
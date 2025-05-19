package rpggame.ui;

import rpggame.audio.AudioManager;
import rpggame.data.GameData;
import rpggame.ui.screens.*;

import javax.swing.*;

public class GameFrame extends JFrame {
    private static final int WIDTH=800;
    private static final int HEIGHT=1600;

    private JPanel currentScreen;
    private GameData gameData;
    private AudioManager audioManager;

    public GameFrame(){
        //inizializza i dati di gioco
        gameData=new GameData();
        gameData.loadData();

        //inizializza l'audio manager
        audioManager=new AudioManager();

        //configura il frame
        setTitle("Jungle Odyssey");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        //imposta lo schermo iniziale
        showHomeScreen();

        //rendi visibile il frame
        setVisible(true);

        //avvia la musica della home
        audioManager.playHomeMusic();
    }

    public void showHomeScreen(){
        if(currentScreen!=null){
            //rimuove lo schermo corrente se esiste
            remove(currentScreen);
        }

        //imposta lo schermo della home
        currentScreen=new HomeScreen(this);
        add(currentScreen);

        revalidate();
        repaint();

        //assicura che la musica della home sia in riproduzione
        audioManager.playHomeMusic();
    }

    public void showWorldMapScreen(){
        if(currentScreen!=null){
            //rimuove lo schermo corrente se esiste
            remove(currentScreen);
        }

        //riproduce la musica della home
        audioManager.playHomeMusic();

        //imposta lo schermo della mappa del mondo
        currentScreen=new WorldMapScreen(this);
        add(currentScreen);

        revalidate();
        repaint();
    }

    public void showBattleScreen(String worldName){
        if(currentScreen!=null){
            //rimuove lo schermo corrente se esiste
            remove(currentScreen);
        }

        //imposta lo schermo di battaglia per il mondo specificato
        currentScreen=new BattleScreen(this, worldName);
        add(currentScreen);

        revalidate();
        repaint();

        //cambia la musica in base al mondo
        audioManager.playWorldMusic(worldName);
    }

    public void showVictoryScreen(){
        if(currentScreen!=null){
            //rimuove lo schermo corrente se esiste
            remove(currentScreen);
        }

        //imposta lo schermo della vittoria
        currentScreen=new VictoryScreen(this);
        add(currentScreen);

        revalidate();
        repaint();

        //riproduce la musica della vittoria
        audioManager.playVictoryMusic();
    }

    public void showDefeatScreen(){
        if(currentScreen!=null){
            //rimuove lo schermo corrente se esiste
            remove(currentScreen);
        }

        //imposta lo schermo della sconfitta
        currentScreen=new DefeatScreen(this);
        add(currentScreen);

        revalidate();
        repaint();

        //riproduce la musica della sconfitta
        audioManager.playDefeatMusic();
    }

    public GameData getGameData(){
        //restituisce i dati di gioco
        return gameData;
    }
}
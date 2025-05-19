package rpggame;

import rpggame.ui.GameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        //avvia un nuovo thread per l'interfaccia grafica (thread dell'Event Dispatch)
        SwingUtilities.invokeLater(() -> {
            try{
                //imposta il look and feel del sistema operativo corrente
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }catch(Exception e){
                //stampa lo stack trace in caso di errore nell'impostazione del look and feel
                e.printStackTrace();
            }

            //crea e avvia il frame principale del gioco
            new GameFrame();
        });
    }
}
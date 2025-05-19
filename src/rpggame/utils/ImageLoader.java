package rpggame.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static Image loadImage(String path){
        try{
            //ottiene l'URL dell'immagine dal percorso specificato
            URL url=ImageLoader.class.getResource(path);
            if(url!=null){
                //carica e restituisce l'immagine dall'URL
                return ImageIO.read(url);
            }else{
                //stampa un messaggio di errore se l'immagine non viene trovata
                System.err.println("Impossibile trovare l'immagine: "+path);
                return null;
            }
        }catch(IOException e){
            //stampa un messaggio di errore e lo stack trace se il caricamento fallisce
            System.err.println("Errore nel caricamento dell'immagine: "+path);
            e.printStackTrace();
            return null;
        }
    }
}
package personaggi;

import azioni.AttaccoSpeciale;
import azioni.Cura;

import java.util.Arrays;

public class Rotcap extends Personaggio {
    //costruttore della classe personaggi.Pyrewrath
    public Rotcap(String nome){
        super(nome,15, 70, Arrays.asList(new AttaccoSpeciale(), new Cura()));
    }
}
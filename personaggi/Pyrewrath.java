package personaggi;

import azioni.AttaccoSpeciale;
import azioni.Cura;

import java.util.Arrays;

public class Pyrewrath extends Personaggio {
    //costruttore della classe personaggi.Pyrewrath
    public Pyrewrath(String nome){
        super(nome,11, 60, Arrays.asList(new AttaccoSpeciale(), new Cura()));
    }
}
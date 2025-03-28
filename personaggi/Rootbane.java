package personaggi;

import azioni.AttaccoSpeciale;
import azioni.Cura;

import java.util.Arrays;

public class Rootbane extends Personaggio{

    //costruttore della classe personaggi.Rootbane
    public Rootbane(String nome){
        super(nome,7, 70, Arrays.asList(new AttaccoSpeciale(), new Cura()));
    }
}
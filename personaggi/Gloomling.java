package personaggi;

import azioni.AttaccoSpeciale;
import azioni.Cura;
import azioni.OmbraOscura;

import java.util.Arrays;

public class Gloomling extends Personaggio {
    //costruttore della classe personaggi.Gloomling
    public Gloomling(String nome){
        super(nome,5, 50, Arrays.asList(new AttaccoSpeciale(), new Cura(), new OmbraOscura()));
    }
}

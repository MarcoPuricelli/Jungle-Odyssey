package personaggi;

import azioni.AttaccoSpeciale;
import azioni.Cura;

import java.util.Arrays;

public class Agrimancer extends Personaggio{

    //costruttore della classe personaggi.Agrimancer
    public Agrimancer(String nome){
        super(nome,5, 150, Arrays.asList(new AttaccoSpeciale(), new Cura()));
    }
}
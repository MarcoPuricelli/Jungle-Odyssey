package personaggi;

import azioni.AttaccoSpeciale;
import azioni.Cura;

import java.util.Arrays;

public class Trailblazer extends Personaggio{

    //costruttore della classe personaggi.Trailblazer
    public Trailblazer(String nome){
        super(nome, 2, 50, Arrays.asList(new AttaccoSpeciale(), new Cura()));
    }
}
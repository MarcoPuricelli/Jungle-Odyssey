package personaggi;

import azioni.AttaccoSpeciale;
import azioni.Cura;

import java.util.Arrays;

public class Voltavoc extends Personaggio {

    //costruttore dell classe personaggi.Voltavoc
    public Voltavoc(String nome) {
        super(nome, 3, 1000, Arrays.asList(new AttaccoSpeciale(), new Cura()));
    }
}
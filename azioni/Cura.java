package azioni;

import interfacce.Abilita;
import personaggi.Personaggio;

public class Cura implements Abilita {

    @Override
    public void usa(Personaggio bersaglio) {
        int curaBase = 20;
        bersaglio.ripristinaHp(curaBase);
        System.out.println(bersaglio.getNome() + " si cura di " + curaBase + " HP!");
    }
}
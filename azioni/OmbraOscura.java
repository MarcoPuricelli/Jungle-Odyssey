package azioni;

import interfacce.Abilita;
import personaggi.Personaggio;

public class OmbraOscura implements Abilita {
    @Override
    public void usa(Personaggio bersaglio) {
        int danno = 1000;
        bersaglio.subisciDanno(danno);
        System.out.println(bersaglio.getNome() + " ha perso " + danno + " HP!");
    }
}

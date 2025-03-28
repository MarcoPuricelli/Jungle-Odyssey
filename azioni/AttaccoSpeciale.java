package azioni;

import interfacce.Abilita;
import personaggi.Personaggio;

import java.security.SecureRandom;

public class AttaccoSpeciale implements Abilita {
    private final SecureRandom random = new SecureRandom();

    @Override
    public void usa(Personaggio bersaglio) {
        int dannoBase = 30;
        int danno = dannoBase + random.nextInt(10); // Danno tra 30 e 40
        System.out.println("Attacco speciale! Infligge " + danno + " danni a " + bersaglio.getNome());
        bersaglio.subisciDanno(danno);
    }
}
package combattimento;

import personaggi.Personaggio;
import statoGioco.StatoGioco;

import java.security.SecureRandom;
import java.util.Scanner;

public class Combattimento {
    //NON è, come nella classe di test, in un metodo. Quindi aggiungo il modificatore private. Dato che non cambia
    //posso mettere anche final.
    private final Scanner input = new Scanner(System.in);

    public void iniziaScontro(Personaggio p1, Personaggio p2) {
        System.out.println("Il combattimento tra " + p1.getNome() + " e " + p2.getNome() + " inizia!");

        //si decide chi attacca per primo con il valore dell'attributo iniziativa (ereditato da personaggi.Personaggio)
        Personaggio primoAttaccante;

        if (p1.getIniziativa() > p2.getIniziativa()) {
            primoAttaccante = p1;
        } else if (p2.getIniziativa() > p1.getIniziativa()) {
            primoAttaccante = p2;
        } else {
            //in caso di pareggio, si sorteggia
            SecureRandom numeriRandom = new SecureRandom();
            primoAttaccante = numeriRandom.nextBoolean() ? p1 : p2;
        }

        //meccanica del combattimento
        while (p1.getHp() > 0 && p2.getHp() > 0) {
            if (primoAttaccante == p1) {
                turno(p1, p2);
                if (p2.getHp() > 0) {
                    turno(p2, p1);
                }
            } else {
                turno(p2, p1);
                if (p1.getHp() > 0) {
                    turno(p1, p2);
                }
            }
        }

        //il vincitore
        if (p1.getHp() > 0) {
            //il player ha vinto: deve combattere contro mob del mondo successivo
            //statoGioco.StatoGioco.getIstanza() ottiene l'unica istanza della classe
            System.out.println(p1.getNome() + " ha vinto!");
            StatoGioco.getIstanza().aggiornaMondo(true);
        } else {
            //il player ha perso: azzero il contatore del mondo per uscire dal do-while
            System.out.println(p2.getNome() + " ha vinto!");
            StatoGioco.getIstanza().aggiornaMondo(false);
        }
    }

    private void turno(Personaggio attaccante, Personaggio difensore) {
        System.out.println(attaccante.getNome() + " sta per attaccare!");
        System.out.println("1. Attacco normale\n2. Usa un'abilità");
        int scelta = input.nextInt();

        if (scelta == 1) {
            attaccante.attacca(difensore);
        } else if (scelta == 2) {
            System.out.println("Scegli un'abilità:");
            for (int i = 0; i < attaccante.getAbilita().size(); i++) {
                System.out.println((i + 1) + ". " + attaccante.getAbilita().get(i).getClass().getSimpleName());
            }
            int abilitaScelta = input.nextInt() - 1;
            attaccante.usaAbilita(abilitaScelta, difensore);
        }

        System.out.println("\n" + attaccante.getNome() + " ha " + attaccante.getHp() + " HP.");
        System.out.println(difensore.getNome() + " ha " + difensore.getHp() + " HP.\n");
    }

}
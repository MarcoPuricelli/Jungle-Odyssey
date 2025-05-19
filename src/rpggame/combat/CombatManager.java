package rpggame.combat;

import rpggame.audio.AudioManager;
import rpggame.characters.Character;
import rpggame.mobs.Mob;

import java.security.SecureRandom;

//classe CombatManager gestisce il combattimento tra giocatore e nemico
public class CombatManager {
    private Character player; //personaggio giocante
    private Mob enemy; //mob nemico
    private SecureRandom random; //generatore di numeri casuali per il combattimento
    private AudioManager audioManager = new AudioManager(); //gestore audio per effetti sonori

    //costruttore che inizializza i partecipanti al combattimento e il generatore random
    public CombatManager(Character player, Mob enemy, SecureRandom random) {
        this.player = player;
        this.enemy = enemy;
        this.random = random;
    }

    //calcola il danno di un attacco tenendo conto di un tiro casuale (dado 20)
    public int calculateDamage(Attack attack) {
        //genera un numero casuale tra 1 e 20 (simulando un tiro di dado)
        int roll = 1 + random.nextInt(20);

        //calcola il danno in base al risultato del tiro
        if (roll == 1) {
            //miss: attacco fallito, nessun danno inflitto
            audioManager.playSoundEffect("miss");
            return 0;
        } else if (roll == 20) {
            //critical hit: danno massimo fisso
            audioManager.playSoundEffect("critical");
            return 40;
        } else {
            //danno normale: base più valore del dado
            audioManager.playSoundEffect("attack");
            audioManager.playSoundEffect("hit");
            return attack.getBaseDamage() + roll;
        }
    }
}
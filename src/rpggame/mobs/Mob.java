package rpggame.mobs;

import rpggame.combat.Attack;

import java.security.SecureRandom;

//classe astratta Mob che rappresenta un'entità generica con nome, salute e iniziativa
public abstract class Mob {
    //attributi privati del mob
    private String name; //nome del mob
    private int maxHealth; //salute massima
    private int currentHealth; //salute attuale
    private int initiative; //valore di iniziativa

    //costruttore per inizializzare i valori del mob
    public Mob(String name, int maxHealth, int initiative) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth; //inizializza salute attuale a quella massima
        this.initiative = initiative;
    }

    //getter per il nome
    public String getName() {
        return name;
    }

    //getter per la salute massima
    public int getMaxHealth() {
        return maxHealth;
    }

    //getter per la salute attuale
    public int getCurrentHealth() {
        return currentHealth;
    }

    //getter per l'iniziativa
    public int getInitiative() {
        return initiative;
    }

    //metodo per infliggere danno e aggiornare la salute attuale senza scendere sotto zero
    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
    }

    //metodi astratti da implementare nelle sottoclassi che restituiscono gli attacchi del mob
    public abstract Attack getBasicAttack();
    public abstract Attack getSpecialAttack();
    public abstract Attack getAbility();

    //metodo per ottenere un attacco casuale tra base, speciale e abilità
    public Attack getRandomAttack(SecureRandom random) {
        int choice = random.nextInt(3);  //sceglie un numero da 0 a 2

        switch (choice) {
            case 0:
                return getBasicAttack();
            case 1:
                return getSpecialAttack();
            case 2:
                return getAbility();
            default:
                return getBasicAttack();
        }
    }
}
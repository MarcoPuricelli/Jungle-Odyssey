package rpggame.characters;

import rpggame.combat.Attack;

import java.awt.*;

public class Agrimancer extends Character {
    
    public Agrimancer() {
        super("Agrimancer", 280, 10);
    }
    
    @Override
    public Attack getBasicAttack() {
        Attack attack = new Attack("Lancia Spine", 18, Color.GREEN);
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
    
    @Override
    public Attack getSpecialAttack() {
        Attack attack = new Attack("Esplosione mistica", 29, new Color(0, 150, 0));
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
    
    @Override
    public Attack getAbility() {
        Attack attack = new Attack("Cura", 0, new Color(100, 200, 100));
        //effetto speciale: cura il personaggio di 15 punti vita
        int healAmount = 15;
        if (isBlessed()) {
            healAmount = (int) (healAmount * 1.1);
        }
        heal(healAmount);
        return attack;
    }
}

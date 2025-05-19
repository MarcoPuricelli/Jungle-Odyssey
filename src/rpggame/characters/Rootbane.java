package rpggame.characters;

import rpggame.combat.Attack;

import java.awt.*;
import java.io.Serializable;

public class Rootbane extends Character implements Serializable {
    
    public Rootbane() {
        super("Rootbane", 210, 8);
    }
    
    @Override
    public Attack getBasicAttack() {
        Attack attack = new Attack("Colpo di Terra", 9, new Color(139, 69, 19));
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
    
    @Override
    public Attack getSpecialAttack() {
        Attack attack = new Attack("Terremoto", 14, new Color(160, 82, 45));
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
    
    @Override
    public Attack getAbility() {
        Attack attack = new Attack("Cura", 0, new Color(105, 105, 105));
        int healAmount = 10;
        if (isBlessed()) {
            healAmount = (int) (healAmount * 1.1);
        }
        heal(healAmount);
        return attack;
    }
}

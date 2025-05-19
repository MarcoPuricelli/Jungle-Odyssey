package rpggame.characters;

import rpggame.combat.Attack;

import java.awt.*;

public class Trailblazer extends Character {
    
    public Trailblazer() {
        super("Trailblazer", 240, 18);
    }
    
    @Override
    public Attack getBasicAttack() {
        Attack attack = new Attack("Colpo Rapido", 7, Color.ORANGE);
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
    
    @Override
    public Attack getSpecialAttack() {
        Attack attack = new Attack("Raffica di Colpi", 13, new Color(255, 165, 0));
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
    
    @Override
    public Attack getAbility() {
        Attack attack = new Attack("Schivata Perfetta", 5, new Color(255, 215, 0));
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
}

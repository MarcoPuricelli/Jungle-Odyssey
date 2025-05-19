package rpggame.characters;

import rpggame.combat.Attack;

import java.awt.*;

public class Voltavoc extends Character {
    
    public Voltavoc() {
        super("Voltavoc", 190, 15);
        //vita default: 80
    }
    
    @Override
    public Attack getBasicAttack() {
        Attack attack = new Attack("Scossa Elettrica", 10, Color.YELLOW);
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
    
    @Override
    public Attack getSpecialAttack() {
        Attack attack = new Attack("Fulmine Devastante", 15, Color.CYAN);
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
    
    @Override
    public Attack getAbility() {
        Attack attack = new Attack("Campo Elettrico", 25, Color.BLUE);
        if (isBlessed()) {
            attack.setBaseDamage((int) (attack.getBaseDamage() * 1.1));
        }
        return attack;
    }
}

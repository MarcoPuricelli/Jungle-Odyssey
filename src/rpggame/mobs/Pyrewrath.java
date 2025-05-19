package rpggame.mobs;

import rpggame.combat.Attack;

import java.awt.*;

public class Pyrewrath extends Mob {
    
    public Pyrewrath() {
        super("Pyrewrath", 90, 12);
    }
    
    @Override
    public Attack getBasicAttack() {
        return new Attack("Fiammata", 8, Color.ORANGE);
    }
    
    @Override
    public Attack getSpecialAttack() {
        return new Attack("Esplosione di Lava", 14, Color.RED);
    }
    
    @Override
    public Attack getAbility() {
        return new Attack("Scudo di Fiamme", 6, new Color(255, 100, 0));
    }
}

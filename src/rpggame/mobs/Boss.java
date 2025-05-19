package rpggame.mobs;

import rpggame.combat.Attack;

import java.awt.*;

public class Boss extends Mob {
    
    public Boss() {
        super("Wwyldrax", 200, 20);
    }
    
    @Override
    public Attack getBasicAttack() {
        return new Attack("Colpo Devastante", 12, Color.RED);
    }
    
    @Override
    public Attack getSpecialAttack() {
        return new Attack("Furia Primordiale", 18, new Color(255, 0, 0));
    }
    
    @Override
    public Attack getAbility() {
        return new Attack("Maledizione Antica", 35, new Color(128, 0, 128));
    }
}

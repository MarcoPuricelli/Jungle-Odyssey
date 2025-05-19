package rpggame.mobs;

import rpggame.combat.Attack;

import java.awt.*;

public class Rotcap extends Mob {
    
    public Rotcap() {
        super("Rotcap", 70, 8);
    }
    
    @Override
    public Attack getBasicAttack() {
        return new Attack("Spore Tossiche", 7, new Color(0, 100, 0));
    }
    
    @Override
    public Attack getSpecialAttack() {
        return new Attack("Nube Velenosa", 12, new Color(50, 150, 50));
    }
    
    @Override
    public Attack getAbility() {
        return new Attack("Rigenerazione Fungina", 5, new Color(100, 200, 100));
    }
}

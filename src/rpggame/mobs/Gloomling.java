package rpggame.mobs;

import rpggame.combat.Attack;

import java.awt.*;

public class Gloomling extends Mob {
    
    public Gloomling() {
        super("Gloomling", 100, 15);
    }
    
    @Override
    public Attack getBasicAttack() {
        return new Attack("Tocco Oscuro", 9, new Color(75, 0, 130));
    }
    
    @Override
    public Attack getSpecialAttack() {
        return new Attack("Raggio d'Ombra", 15, new Color(50, 0, 80));
    }
    
    @Override
    public Attack getAbility() {
        return new Attack("Urlo nero", 7, new Color(100, 0, 150));
    }
}

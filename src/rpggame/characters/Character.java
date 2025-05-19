package rpggame.characters;

import rpggame.combat.Attack;

import java.io.Serializable;

public abstract class Character implements Serializable {
    private String name;
    private int maxHealth;
    private int currentHealth;
    private int initiative;
    private boolean blessed;
    
    public Character(String name, int maxHealth, int initiative) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.initiative = initiative;
        this.blessed = false;
    }
    
    public String getName() {
        return name;
    }
    
    public int getMaxHealth() {
        return maxHealth;
    }
    
    public int getCurrentHealth() {
        return currentHealth;
    }
    
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.min(currentHealth, maxHealth);
    }
    
    public int getInitiative() {
        return initiative;
    }
    
    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
    }
    
    public void heal(int amount) {
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }
    
    public void restoreFullHealth() {
        currentHealth = maxHealth;
    }
    
    public void applyBlessing() {
        if (!blessed) {
            maxHealth = (int) (maxHealth * 1.1);
            currentHealth = maxHealth;
            initiative = (int) (initiative * 1.1);
            blessed = true;
        }
    }
    
    public boolean isBlessed() {
        return blessed;
    }
    
    public abstract Attack getBasicAttack();
    public abstract Attack getSpecialAttack();
    public abstract Attack getAbility();
}

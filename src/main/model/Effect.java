package model;

public class Effect {

    private int damage;
    private int resistance;
    private int effectType;

    public Effect(int damage, int resistance, int effectType) {
        this.damage = damage;
        this.resistance = resistance;
        this.effectType = effectType;
    }

    public int getDamage() {
        return damage;
    }

    public int getResistance() {
        return resistance;
    }

    public int getEffectType() {
        return effectType;
    }

    public boolean isResistance() {

        return effectType == 2;
    }

    public boolean isStrength() {

        return effectType == 3;
    }
}

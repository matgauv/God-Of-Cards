package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an Effect Object with a damage and resistance component and an effect type.
public class Effect implements Writable {

    private int damage;     // damage component of the effect (only applicable for attack or pierce cards).
    private int resistance; // resistance component of the effect (only applicable for shield cards).
    private int effectType; // type of effect; 1 = attack effect
                            //                 2 = resistance effect
                            //                 3 = strengthen effect

    // REQUIRES: effectType must be 1, 2, or 3.
    // EFFECTS: instantiates a new Effect with a damage component, resistance component, and an effectType.
    public Effect(int damage, int resistance, int effectType) {
        this.damage = damage;
        this.resistance = resistance;
        this.effectType = effectType;
    }

    // EFFECTS: returns damage component
    public int getDamage() {
        return damage;
    }

    // EFFECTS: returns resistance component
    public int getResistance() {
        return resistance;
    }

    // EFFECTS: returns effectType.
    public int getEffectType() {
        return effectType;
    }

    // EFFECTS: returns true if effectType is resistance (=2)
    //          returns false otherwise.
    public boolean isResistance() {

        return effectType == 2;
    }

    // EFFECTS: returns true if effectType is strengthen (=3)
    //          returns false otherwise.
    public boolean isStrength() {

        return effectType == 3;
    }

    // EFFECTS: converts an effect to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("damage", damage);
        json.put("resistance", resistance);
        json.put("effectType", effectType);

        return json;
    }
}

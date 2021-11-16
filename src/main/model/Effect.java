package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an Effect Object with a damage and resistance component and an effect type.
public class Effect implements Writable {

    private int offenseComp;     // offense component of the effect (only applicable for attack or pierce cards).
    private int defenseComp; // defense component of the effect (only applicable for shield cards).
    private int effectType; // type of effect; 1 = attack effect
                            //                 2 = resistance effect
                            //                 3 = strengthen effect
                            //                 4 = healing effect

    // REQUIRES: effectType must be 1, 2, 3, or 4.
    // EFFECTS: instantiates a new Effect with a offense component, defense component,
    //          and an effectType.
    public Effect(int offenseComp, int defenseComp, int effectType) {
        this.offenseComp = offenseComp;
        this.defenseComp = defenseComp;
        this.effectType = effectType;
    }

    // EFFECTS: returns damage/strength component
    public int getOffenseComp() {
        return offenseComp;
    }

    // EFFECTS: returns resistance/healing component
    public int getDefenseComp() {
        return defenseComp;
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

    // EFFECTS: returns true if effectType is healing(=4)
    //          returns false otherwise.
    public boolean isHealing() {
        return effectType == 4;
    }

    // EFFECTS: converts an effect to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject effectObject = new JSONObject();
        effectObject.put("damage", offenseComp);
        effectObject.put("resistance", defenseComp);
        effectObject.put("effectType", effectType);

        return effectObject;
    }
}

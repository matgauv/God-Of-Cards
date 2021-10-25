package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a card containing a unique name and card effect
public class Card implements Writable {

    private String name;   // the name of a card; each card has a unique name.
    private Effect effect; // the effect that the card triggers when played; a card only has one effect

    // EFFECTS: A new Card object is instantiated with the given name and effect.
    public Card(String name, Effect effect) {

        this.name = name;
        this.effect = effect;
    }

    // EFFECTS: returns effect of card
    public Effect getEffect() {
        return effect;
    }

    // EFFECTS: returns name of card.
    public String getName() {
        return name;
    }

    // EFFECTS: converts a card object into a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("card name", name);
        json.put("effect", effect.toJson());
        return json;
    }
}

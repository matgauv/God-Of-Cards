package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a Character with a name, health, a CardDeck, and a list of applied effects.
public class Character implements Writable {

    private String name;                 // the name of a Character.
    private int health;                  // the health of a Character.
    private CardDeck cardDeck;           // the Character's CardDeck; represents a list of cards they can use
    private List<Effect> effectsApplied; // a list of effects that are currently applied on to the Character.

    // Instantiates a new Character object with a unique name and health, an empty CardDeck,
    //                                          and no effects applied.
    public Character(String name, int health) {

        this.name = name;
        this.health = health;
        cardDeck = new CardDeck();
        effectsApplied = new ArrayList<>();

    }

    // MODIFIES: this
    // EFFECTS: character health decreases by the amount of damage they take
    //          or goes to zero if damage > health remaining.
    public void takeDamage(int damage) {

        damage -= applyResistance();
        if (damage < 0) {
            damage = 0;
        }

        if (damage > health) {
            health = 0;
        } else {
            health -= damage;
        }
    }

    // EFFECTS: deals random damage from card (cardDamage - 25 <= damage <= cardDamage + 25)
    //          with strength effects to another character.
    public String attack(Card c, Character b) {

        int cardDamage = c.getEffect().getDamage();
        int maxDamage = cardDamage + 25;
        int minDamage = cardDamage - 25;
        int damageDealt = (int) Math.floor(Math.random() * (maxDamage - minDamage + 1) + minDamage);
        damageDealt += applyStrength();
        b.takeDamage(damageDealt);
        String toString = name + " used " + c.getName() + " and dealt "
                + damageDealt + " damage to " + b.getName();
        return toString;
    }

    // MODIFIES: this
    // EFFECTS: Adds shield (resistance) or pierce (strengthen) effect to character's applied effects.
    public void addCardEffect(Card c) {
        effectsApplied.add(c.getEffect());
    }

    // MODIFIES: this
    // EFFECTS: if strength effect(s) are currently applied on character, returns total strength damage
    //          and removes all strength effects from character.
    //          Otherwise, returns 0.
    public int applyStrength() {
        int strength = 0;

        for (int i = 0; i < effectsApplied.size(); i++) {
            if (effectsApplied.get(i).isStrength()) {
                strength += effectsApplied.get(i).getDamage();
                effectsApplied.remove(effectsApplied.get(i));
                i--;
            }

        }
        return strength;
    }

    // MODIFIES: this
    // EFFECTS: if resistance effect(s) are applied on character, return total resistance
    //          and remove all resistance effects from character.
    //          Otherwise, return 0;
    public int applyResistance() {

        int resist = 0;

        for (int i = 0; i < effectsApplied.size(); i++) {

            if (effectsApplied.get(i).isResistance()) {
                resist += effectsApplied.get(i).getResistance();
                effectsApplied.remove(effectsApplied.get(i));
                i--;
            }
        }


        return resist;
    }

    // EFFECTS: returns true if card with given name is found within character's card deck
    //          returns false otherwise.
    public boolean hasCard(String c) {
        return cardDeck.contains(c);
    }

    // EFFECTS: returns card with name s from card deck.
    public Card getCard(String s) {
        return cardDeck.getCard(s);
    }

    // MODIFIES: this
    // EFFECTS: adds card to card deck and returns true.
    //          if card is already in deck, returns false.
    public boolean addCard(Card c) {
        return cardDeck.addCard(c);
    }

    // MODIFIES: this
    // EFFECTS: sets health to given value.
    public void setHealth(int health) {
        this.health = health;
    }

    // EFFECTS: returns character health.
    public int getHealth() {
        return health;
    }

    // EFFECTS: returns character name.
    public String getName() {
        return name;
    }

    // EFFECTS: returns character's card deck.
    public CardDeck getCardDeck() {
        return cardDeck;
    }

    // EFFECTS: returns effectsApplied on player
    public List<Effect> getEffectsApplied() {
        return effectsApplied;
    }

    // EFFECTS: converts a character object into a JSONObject.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONObject cardDeck2 = cardDeck.toJson();
        json.put("player name", name);
        json.put("health", health);
        json.put("Card Deck", cardDeck2);
        json.put("Effects Applied", effectsToJson());

        return json;
    }

    // EFFECTS: converts all effectsApplied on player to JSon Objects.
    public JSONArray effectsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Effect e : effectsApplied) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;

    }
}


package model;

// Represents a card containing a unique name and card effect
public class Card {

    private String name;   // the name of a card; each card has a unique name.
    private Effect effect; // the effect that the card triggers when played; a card only has one effect

    // EFFECTS: A new Card object is instantiated with the given name and effect.
    public Card(String name, Effect effect) {

        this.name = name;
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }
}

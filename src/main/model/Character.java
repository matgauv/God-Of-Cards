package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Character {

    private String name;
    private int health;
    private CardDeck cardDeck;
    private List<Effect> effectsApplied;

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
    public void attack(Card c, Character b) {

        int cardDamage = c.getEffect().getDamage();
        int maxDamage = cardDamage + 25;
        int minDamage = cardDamage - 25;
        int damageDealt = (int) Math.floor(Math.random() * (maxDamage - minDamage + 1) + minDamage);
        damageDealt += applyStrength();
        b.takeDamage(damageDealt);
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

        for (Effect e : effectsApplied) {

            if (e.isStrength()) {
                strength += e.getDamage();
                effectsApplied.remove(e);
                if (effectsApplied.size() == 0) {
                    break;
                }

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

        for (Effect e : effectsApplied) {

            if (e.isResistance()) {
                resist += e.getResistance();
                effectsApplied.remove(e);
                if (effectsApplied.size() == 0) {
                    break;
                }
            }
        }

        return resist;
    }

    // EFFECTS: returns true if card with given name is found within character's card deck
    //          returns false otherwise.
    public boolean hasCard(String c) {
        return cardDeck.contains(c);
    }

    // REQUIRES: card with name s must be in card deck.
    // EFFECTS: returns card with name s from card deck.
    public Card getCard(String s) {
        return cardDeck.getCard(s);
    }

    // MODIFIES: this
    // EFFECTS: adds card to card deck if card is not already in deck
    public void addCard(Card c) {
        cardDeck.addCard(c);
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
}


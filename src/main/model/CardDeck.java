package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a CardDeck that holds Card objects in a list and can act on them.
public class CardDeck implements Writable {

    private List<Card> cards; // A list of cards in the card deck.

    // A new CardDeck object is instantiated with an empty list of Cards.
    public CardDeck() {
        cards = new ArrayList<>();
    }

    // EFFECTS: returns number of cards in deck.
    public int numCards() {
        return cards.size();
    }

    // MODIFIES: this
    // EFFECTS: adds a card into the CardDeck and returns true
    //          if card is already in deck, returns false
    public boolean addCard(Card c) {
        if (!cards.contains(c)) {
            cards.add(c);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: removes the card from the deck and returns true.
    //          if card is not in deck, returns false.
    public boolean removeCard(Card c) {
        if (cards.contains(c)) {
            cards.remove(c);
            return true;
        }
        return false;
    }

    // EFFECTS: returns true if card with name c is already in deck, false otherwise.
    public boolean contains(String c) {
        for (Card i : cards) {
            if (i.getName().equals(c)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns card with name s in card deck.
    //          if card not in deck, returns null;
    public Card getCard(String s) {

        for (Card c : cards) {

            if (c.getName().equals(s)) {

                return c;
            }

        }

        return null;
    }

    // EFFECTS: returns list of cards in card deck.
    public List<Card> getCards() {
        return cards;
    }

    // EFFECTS: converts a CardDeck to a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject cardDeckObject = new JSONObject();
        cardDeckObject.put("cards", cardsToJson());
        return cardDeckObject;
    }

    // EFFECTS: converts all cards in cardDeck to JSONObjects.
    public JSONArray cardsToJson() {
        JSONArray cardsArray = new JSONArray();
        for (Card c : cards) {
            cardsArray.put(c.toJson());
        }

        return cardsArray;
    }





}

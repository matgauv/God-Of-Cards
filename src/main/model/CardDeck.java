package model;

import java.util.ArrayList;
import java.util.List;

public class CardDeck {

    private List<Card> cards;

    public CardDeck() {
        cards = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a card into the CardDeck unless the card is already in there.
    public void addCard(Card c) {
        if (!cards.contains(c)) {
            cards.add(c);
        }
    }

    // REQUIRES: card must be in the deck
    // MODIFIES: this
    // EFFECTS: removes the card from the deck
    public void removeCard(Card c) {
        cards.remove(c);
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

    // REQUIRES: card with name s must be in deck
    // EFFECTS: returns card with name s in card deck.
    public Card getCard(String s) {

        for (Card c : cards) {

            if (c.getName().equals(s)) {

                return c;
            }

        }

        return null;
    }



}

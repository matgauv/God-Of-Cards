package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardDeckTest {
    private CardDeck cardDeck;

    @BeforeEach
    public void setUpCardDeck() {
        cardDeck = new CardDeck();
    }

    @Test
    public void testAddCardNotInDeck() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        assertTrue(cardDeck.addCard(c1));
        assertEquals(1, cardDeck.numCards());
        assertTrue(cardDeck.contains("Attack Card"));
    }

    @Test
    public void testAddCardAlreadyInDeck() {
        Card c1 = new Card("Shield Card", new Effect(0, 100, 2));
        assertTrue(cardDeck.addCard(c1));
        assertFalse(cardDeck.addCard(c1));
        assertEquals(1, cardDeck.numCards());
        assertTrue(cardDeck.contains("Shield Card"));
    }

    @Test
    public void testAddMultipleCards() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        Card c2 = new Card("Shield Card", new Effect(0, 100, 2));

        assertTrue(cardDeck.addCard(c1));
        assertTrue(cardDeck.addCard(c2));
        assertEquals(2, cardDeck.numCards());
        assertTrue(cardDeck.contains("Attack Card"));
        assertTrue(cardDeck.contains("Shield Card"));

    }

    @Test
    public void testRemoveCardInDeck() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        assertTrue(cardDeck.addCard(c1));
        assertEquals(1, cardDeck.numCards());
        assertTrue(cardDeck.removeCard(c1));
        assertEquals(0, cardDeck.numCards());
    }

    @Test
    public void testRemoveCardNotInDeck() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        assertFalse(cardDeck.removeCard(c1));
    }

    @Test
    public void testRemoveMultipleCards() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        Card c2 = new Card("Shield Card", new Effect(0, 100, 2));

        cardDeck.addCard(c1);
        cardDeck.addCard(c2);
        assertEquals(2, cardDeck.numCards());
        assertTrue(cardDeck.removeCard(c1));
        assertEquals(1, cardDeck.numCards());
        assertTrue(cardDeck.removeCard(c2));
        assertEquals(0, cardDeck.numCards());

    }

    @Test
    public void testContainsInDeck() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        cardDeck.addCard(c1);
        assertTrue(cardDeck.contains("Attack Card"));

    }

    @Test
    public void testContainsNotInDeck() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        Card c2 = new Card("Shield Card", new Effect(0, 100, 2));
        cardDeck.addCard(c2);
        assertFalse(cardDeck.contains("Attack Card"));
    }

    @Test
    public void testGetCardInDeck() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        cardDeck.addCard(c1);
        Card theCard = cardDeck.getCard("Attack Card");
        assertEquals(c1, theCard);

    }

    @Test
    public void testGetCardNotInDeck() {
        Card c1 = new Card("Attack Card", new Effect(100, 0, 1));
        Card c2 = new Card("Shield Card", new Effect(0, 100, 2));

        cardDeck.addCard(c1);

        Card theCard = cardDeck.getCard("Shield Card");
        assertEquals(null, theCard);

    }

    @Test
    public void testGetCards() {
        List<Card> cardsList = cardDeck.getCards();
        assertEquals(cardsList.size(), cardDeck.getCards().size());
    }


}

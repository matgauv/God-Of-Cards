package persistence;

import model.Card;
import model.CardDeck;
import model.Character;
import model.Effect;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriteInInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("/.data\0InvalidFileName123.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // passes
        }

    }

    @Test
    public void testWriterDefaultPlayer() {
        try {
            Character p = new Character("Smeagol", 300);
            JsonWriter writer = new JsonWriter("./data/testWriterDefaultPlayer.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultPlayer.json");
            p = reader.readPlayer();
            List<Effect> effects = p.getEffectsApplied();
            CardDeck cards = p.getCardDeck();
            assertEquals("Smeagol", p.getName());
            assertEquals(300, p.getHealth());
            assertEquals(0, effects.size());
            assertEquals(0, cards.numCards());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterEmptyCardDeck() {
        try {
            CardDeck cd = new CardDeck();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCardDeck.json");
            writer.open();
            writer.write(cd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCardDeck.json");
            cd = reader.readCardDeck();

            assertEquals(0, cd.numCards());
        } catch (IOException e) {
            fail("Exception should not have been thrown here");
        }
    }

    @Test
    public void testWriterPlayerWithCards() {
        try {
            Character p = new Character("Rowan", 678);
            p.addCard(new Card("Spear Jab", new Effect(100, 0, 1)));
            p.addCard(new Card("Iron Shield", new Effect(0, 100, 2)));
            JsonWriter writer = new JsonWriter("./data/testWriterPlayerWithCards.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterPlayerWithCards.json");
            p = reader.readPlayer();

            assertEquals("Rowan", p.getName());
            assertEquals(678, p.getHealth());
            assertEquals(0, p.getEffectsApplied().size());
            List<Card> playerCards = p.getCardDeck().getCards();
            checkCard(playerCards.get(0), "Spear Jab");
            checkCard(playerCards.get(1), "Iron Shield");
            checkEffect(100, 0, 1, playerCards.get(0).getEffect());
            checkEffect(0, 100, 2, playerCards.get(1).getEffect());

        } catch (IOException e) {
            fail("IOException should not have been thrown.");
        }
    }

    @Test
    public void testWriterCardDeckWithCards() {
        try {
            CardDeck cd = new CardDeck();
            cd.addCard(new Card("Platinum Shield", new Effect(0, 200, 2)));
            cd.addCard(new Card("Trident Slash", new Effect(250, 0, 1)));
            cd.addCard(new Card("Platinum Pierce", new Effect(200, 0, 3)));

            JsonWriter writer = new JsonWriter("./data/testWriterCardDeckWithCards.json");
            writer.open();
            writer.write(cd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterCardDeckWithCards.json");
            cd = reader.readCardDeck();

            assertEquals(3, cd.numCards());
            checkCard(cd.getCards().get(0), "Platinum Shield");
            checkCard(cd.getCards().get(1), "Trident Slash");
            checkCard(cd.getCards().get(2), "Platinum Pierce");
            checkEffect(0, 200, 2, cd.getCards().get(0).getEffect());
            checkEffect(250, 0, 1, cd.getCards().get(1).getEffect());
            checkEffect(200, 0, 3, cd.getCards().get(2).getEffect());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    public void testWriterPlayerWithEffectsApplied() {
        try {
            Character p = new Character("Smeagol", 300);
            p.addCardEffect(new Card("Iron Shield", new Effect(0, 100, 2)));
            p.addCardEffect(new Card("Iron Shield", new Effect(0, 100, 2)));
            JsonWriter writer = new JsonWriter("./data/testWriterPlayerWithEffectsApplied.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterPlayerWithEffectsApplied.json");
            p = reader.readPlayer();
            List<Effect> effects = p.getEffectsApplied();
            CardDeck cards = p.getCardDeck();
            assertEquals("Smeagol", p.getName());
            assertEquals(300, p.getHealth());
            assertEquals(2, effects.size());
            assertEquals(0, cards.numCards());
            checkEffect(0, 100, 2, effects.get(0));
            checkEffect(0, 100, 2, effects.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }
}

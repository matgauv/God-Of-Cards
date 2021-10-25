package persistence;

import model.Card;
import model.CardDeck;
import model.Character;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testNonExistentFilePlayer() {
        JsonReader reader = new JsonReader("./data/NonExistentFile.json");
        try {
            Character p = reader.readPlayer();
            fail("IOException Expected");
        } catch(IOException e) {
            // passes
        }

    }

    @Test
    public void testNonExistentFileCardDeck() {
        JsonReader reader = new JsonReader("./data/NonExistentFile.json");
        try {
            CardDeck cd = reader.readCardDeck();
            fail("IOException Expected");
        } catch(IOException e) {
            // passes
        }
    }

    @Test
    public void testPlayerWithEmptyCardDeck() {
        JsonReader reader = new JsonReader("./data/testReaderPlayerWithEmptyCardDeck.json");
        try {
            Character p = reader.readPlayer();
            assertEquals("Samuel", p.getName());
            assertEquals(1000, p.getHealth());
            assertEquals(0, p.getEffectsApplied().size());
            assertEquals(0, p.getCardDeck().numCards());
        } catch (IOException e) {
            fail("File could not be read...");
        }
    }

    @Test
    public void testEmptyCardDeck() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCardDeck.json");
        try {
            CardDeck cd = reader.readCardDeck();
            assertEquals(0, cd.numCards());
        } catch (IOException e) {
            fail("File could not be read...");
        }
    }

    @Test
    public void testPlayerWithCards() {
        JsonReader reader = new JsonReader("./data/testReaderPlayerWithCards.json");
        try {
            Character p = reader.readPlayer();
            assertEquals("Poppy", p.getName());
            assertEquals(946, p.getHealth());
            List<Card> cards = p.getCardDeck().getCards();
            assertEquals(2, cards.size());
            checkCard(cards.get(0), "Dagger Slash");
            checkCard(cards.get(1), "Platinum Shield");
            checkEffect(200, 0, 1, cards.get(0).getEffect());
            checkEffect(0, 200, 2, cards.get(1).getEffect());
        } catch (IOException e) {
            fail("File could not be read...");
        }
    }

    @Test
    public void testCardDeckWithCards() {
        JsonReader reader = new JsonReader("./data/testReaderCardDeckWithCards.json");
        try {
            CardDeck cd = reader.readCardDeck();
            List<Card> cards = cd.getCards();
            assertEquals(2, cards.size());
            checkCard(cards.get(0), "Bolt Cutter");
            checkCard(cards.get(1), "Platinum Pierce");
            checkEffect(400, 0, 1, cards.get(0).getEffect());
            checkEffect(200, 0, 3, cards.get(1).getEffect());
        } catch (IOException e) {
            fail("File could not be read...");
        }

    }




}






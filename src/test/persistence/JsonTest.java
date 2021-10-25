package persistence;

import model.Card;
import model.CardDeck;
import model.Character;
import model.Effect;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    public void checkCard(Card c, String s) {
        assertEquals(s, c.getName());
    }


    public void checkEffect(int damage, int resistance, int effectType, Effect e) {
        assertEquals(damage, e.getDamage());
        assertEquals(resistance, e.getResistance());
        assertEquals(effectType, e.getEffectType());
    }
}

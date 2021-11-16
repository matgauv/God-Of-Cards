package persistence;

import model.Card;
import model.Effect;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    public void checkCard(Card c, String s) {
        assertEquals(s, c.getName());
    }


    public void checkEffect(int damage, int resistance, int effectType, Effect e) {
        assertEquals(damage, e.getOffenseComp());
        assertEquals(resistance, e.getDefenseComp());
        assertEquals(effectType, e.getEffectType());
    }
}

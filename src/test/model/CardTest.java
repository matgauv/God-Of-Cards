package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    private Card card;
    private static final Effect effect = new Effect(1000, 0 , 3);

    @BeforeEach
    public void setUpCard() {
        card = new Card("Card Name", effect);
    }

    @Test
    public void testGetName() {
        String name = card.getName();
        assertEquals("Card Name", name);
    }

    @Test
    public void testGetEffect() {
        Effect effect1 = card.getEffect();
        assertEquals(1000, effect1.getDamage());
        assertEquals(0, effect1.getResistance());
        assertEquals(3, effect1.getEffectType());
        assertEquals(effect,effect1);
    }

}

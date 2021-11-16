package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EffectTest {

    private Effect effect;

    @BeforeEach
    public void setUpEffect() {
        effect = new Effect(1000, 0, 3);
    }

    @Test
    public void testGetDamage() {
        int damage = effect.getOffenseComp();
        assertEquals(1000, damage);
    }

    @Test
    public void testGetResistance() {
        int resist = effect.getDefenseComp();
        assertEquals(0, resist);
    }

    @Test
    public void testGetEffectType() {
        int type = effect.getEffectType();
        assertEquals(3, type);
    }

    @Test
    public void testIsResistance() {
        Effect effect1 = new Effect(0, 1000, 2);
        assertTrue(effect1.isResistance());
        assertFalse(effect.isResistance());
    }

    @Test
    public void testIsStrength() {
        Effect effect1 = new Effect(0, 1000, 2);
        Effect effect2 = new Effect(1000, 0 , 1);

        assertFalse(effect1.isStrength());
        assertFalse(effect2.isStrength());
        assertTrue(effect.isStrength());
    }
}

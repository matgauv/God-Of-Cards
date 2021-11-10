package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {

    private static final Card AttackCard = new Card("Attack Card",
            new Effect(100, 0, 1));

    private static final Card ShieldCard = new Card("Shield Card",
            new Effect(0, 100, 2));

    private static final Card StrengthCard = new Card("Pierce Card",
            new Effect(50, 0, 3));

    private Character p;
    private Character b;

    @BeforeEach
    public void setUpCharacters() {
        p = new Character("Player", 1000);
        b = new Character("Boss-man", 1250);
    }

    @Test
    public void testAddCards() {
        assertTrue(p.addCard(AttackCard));
        assertTrue(p.addCard(ShieldCard));
        assertEquals(2, p.getCardDeck().numCards());
    }

    @Test
    public void testAddCardsAlreadyInDeck() {
        assertTrue(p.addCard(AttackCard));
        assertFalse(p.addCard(AttackCard));
        assertEquals(1, p.getCardDeck().numCards());
    }

    @Test
    public void testTakeDamage() {
        int damage = AttackCard.getEffect().getDamage();
        p.takeDamage(damage);
        assertEquals(900, p.getHealth());
    }

    @Test
    public void testTakeDamageMoreThanHealth() {
        p.setHealth(400);
        p.takeDamage(500);
        assertEquals(0, p.getHealth());
    }

    @Test
    public void testTakeDamageWithSingleResistanceApplied() {
        p.addCardEffect(ShieldCard);
        p.takeDamage(500);
        assertEquals(600, p.getHealth());
    }

    @Test
    public void testTakeDamageWithMultipleResistanceApplied() {
        p.addCardEffect(ShieldCard);
        p.addCardEffect(ShieldCard);
        p.addCardEffect(StrengthCard);
        p.takeDamage(500);
        assertEquals(700, p.getHealth());
    }

    @Test
    public void testTakeDamageLessThanZero() {
        p.addCardEffect(ShieldCard);
        p.addCardEffect(ShieldCard);
        p.addCardEffect(ShieldCard);
        p.takeDamage(200);
        assertEquals(1000, p.getHealth());
    }

    @Test
    public void testAttack() {
        p.attack(AttackCard, b);
        assertTrue(b.getHealth() <= 1175);
        assertTrue(b.getHealth() >= 1125);
    }

    @Test
    public void testAttackWithSingleStrengthApplied() {
        p.addCardEffect(StrengthCard);
        p.attack(AttackCard, b);
        assertTrue(b.getHealth() <= 1125);
        assertTrue(b.getHealth() >= 1075);
    }

    @Test
    public void testAttackWithMultipleStrengthApplied() {
        p.addCardEffect(StrengthCard);
        p.addCardEffect(StrengthCard);
        p.addCardEffect(StrengthCard);
        p.attack(AttackCard, b);
        assertTrue(b.getHealth() <= 1025);
        assertTrue(b.getHealth() >= 975);
    }

    @Test
    public void testAttackWithMultipleStrengthAndResistanceApplied() {
        p.addCardEffect(StrengthCard);
        p.addCardEffect(StrengthCard);
        p.addCardEffect(StrengthCard);
        b.addCardEffect(ShieldCard);
        b.addCardEffect(ShieldCard);

        p.attack(AttackCard, b);

        assertTrue(b.getHealth() <= 1225);
        assertTrue(b.getHealth() >= 1175);
    }

    @Test
    public void testHasCard() {
        p.addCard(AttackCard);
        assertTrue(p.hasCard("Attack Card"));
    }

    @Test
    public void testDoesNotHaveCard() {
        p.addCard(AttackCard);
        assertFalse(p.hasCard("Shield Card"));
    }

    @Test
    public void testGetCard() {
        p.addCard(AttackCard);
        Card card = p.getCard("Attack Card");
        Card card1 = p.getCard("Shield Card");
        assertEquals(AttackCard, card);
        assertEquals(null, card1);
    }

    @Test
    public void testAddCardEffect() {
        p.addCardEffect(AttackCard);
        p.addCardEffect(StrengthCard);
        p.addCardEffect(AttackCard);

        assertEquals(3, p.getEffectsApplied().size());
    }

    @Test
    public void testGetName() {
        String name = p.getName();
        assertEquals("Player", name);
    }

    @Test
    public void testSetHealth() {
        p.setHealth(500);
        assertEquals(500, p.getHealth());
    }

    @Test
    public void testApplyStrength() {
        assertEquals(0, p.applyStrength());

        p.addCardEffect(ShieldCard);
        p.addCardEffect(StrengthCard);
        p.addCardEffect(StrengthCard);
        assertEquals(3, p.getEffectsApplied().size());

        assertEquals(100, p.applyStrength());
        assertEquals(1, p.getEffectsApplied().size());
    }

    @Test
    public void testApplyResistance() {
        assertEquals(0, p.applyResistance());

        p.addCardEffect(ShieldCard);
        p.addCardEffect(ShieldCard);
        assertEquals(2, p.getEffectsApplied().size());

        assertEquals(200, p.applyResistance());
        assertEquals(0, p.getEffectsApplied().size());
    }

    @Test
    public void testClearEffectsApplied() {
        p.addCardEffect(ShieldCard);
        p.addCardEffect(StrengthCard);

        assertEquals(2, p.getEffectsApplied().size());

        p.clearEffectsApplied();

        assertEquals(0, p.getEffectsApplied().size());
    }




}

package ui;

import model.Card;
import model.Effect;

// A class that stores all boss cards
public class BossCards {

    public static final Card HADES_ATTACK = new Card("Soul Extract",
            new Effect(150, 0, 1));

    public static final Card HADES_SHIELD = new Card("Shield of Souls",
            new Effect(0, 75, 2));

    public static final Card HADES_PIERCE = new Card("Soul Sharpen",
            new Effect(50, 0, 3));

    public static final Card APHRODITE_ATTACK = new Card("Kiss Of Death",
            new Effect(200, 0, 1));

    public static final Card APHRODITE_SHIELD = new Card("Love's Gaze",
            new Effect(0, 100, 2));

    public static final Card APHRODITE_PIERCE = new Card("Heart-throb",
            new Effect(75, 0, 3));

    public static final Card POSEIDON_ATTACK = new Card("Trident Slash",
            new Effect(225, 0, 1));

    public static final Card POSEIDON_SHIELD = new Card("Coral Shell",
            new Effect(0, 200, 2));

    public static final Card POSEIDON_PIERCE = new Card("Tsunami",
            new Effect(100, 0, 3));

    public static final Card ATHENA_ATTACK = new Card("Brain Bash",
            new Effect(250, 0, 1));

    public static final Card ATHENA_SHIELD = new Card("Golden Armor",
            new Effect(0, 250, 2));

    public static final Card ATHENA_PIERCE = new Card("Shield Shatter",
            new Effect(125, 0, 3));

    public static final Card ZEUS_ATTACK = new Card("Olympus' Wrath",
            new Effect(350, 0, 1));

    public static final Card ZEUS_SHIELD = new Card("Immortality",
            new Effect(0, 350, 2));

    public static final Card ZEUS_PIERCE = new Card("Thunderstorm",
            new Effect(150, 0, 3));

}

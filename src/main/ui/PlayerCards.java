package ui;

import model.Card;
import model.Effect;

public class PlayerCards {

    public static final Card PLAYER_ATTACK_1 = new Card("Spear Jab",
            new Effect(150, 0, 1));

    public static final Card PLAYER_PIERCE_1 = new Card("Iron Pierce",
            new Effect(100, 0, 3));

    public static final Card PLAYER_ATTACK_2 = new Card("Dagger Slash",
            new Effect(300, 0, 1));

    public static final Card PLAYER_SHIELD_1 = new Card("Iron Shield",
            new Effect(0, 100, 2));

    public static final Card PLAYER_ATTACK_3 = new Card("Bolt Cutter",
            new Effect(500, 0, 1));

    public static final Card PLAYER_SHIELD_2 = new Card("Platinum Shield",
            new Effect(0, 200, 2));


}

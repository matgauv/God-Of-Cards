package model;

public class Player extends Character {

    public static final int MAX_HEALTH = 1000;

    // Instantiates a new Player object with a unique name, max health,
    //                       an empty card deck, and no applied effects.
    public Player(String name) {
        super(name, MAX_HEALTH);
    }
}

package model;

public class Card {

    private String name;
    private Effect effect;

    public Card(String name, Effect effect) {

        this.name = name;
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }
}

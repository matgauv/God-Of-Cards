package ui;

import model.Card;
import model.Character;
import model.Effect;
import java.util.Scanner;

public class Game {

    private static final int MAX_HEALTH = 1000;

    private static final Character HADES = new Character("HADES, GOD OF THE UNDERWORLD",
            750);

    private static final Character APHRODITE = new Character("APHRODITE, GODDESS OF LOVE",
            1000);

    private static final Character ATHENA = new Character("ATHENA, GODDESS OF WAR",
            1250);

    private static final Character ZEUS = new Character("ZEUS, FATHER GOD OF THE SKY",
            1500);


    private Character player;
    private Character boss;
    private Scanner input;

    public Game() {
        runGame();
    }

    public void runGame() {
        boolean gameOn = true;

        setUpGame();
        initiateBattle(HADES);
    }

    public void initiateBattle(Character boss) {
        this.boss = boss;
        setUpBoss();
        System.out.println("\n" + boss.getName() + " has challenged " + player.getName() + " to a duel...");

    }

    public void setUpGame() {
        System.out.println("Please enter your name: ");
        input = new Scanner(System.in);
        String name = input.nextLine();
        System.out.println("God of?: ");
        String godOf = input.nextLine();
        player = new Character(name.toUpperCase() + ", GOD OF " + godOf.toUpperCase(), MAX_HEALTH);
        System.out.println("\nA new challenger has emerged... ");
        System.out.println("Welcome to Mount Olympus-- " + player.getName());
        getRightInput("begin", "Type 'begin' to begin your journey...");
        newCard(player, PlayerCards.PLAYER_ATTACK_1);
    }

    public void setUpBoss() {
        if (boss.getName().equals("HADES, GOD OF THE UNDERWORLD")) {
            boss.addCard(BossCards.HADES_ATTACK);
            boss.addCard(BossCards.HADES_SHIELD);
        }
    }

    public void newCard(Character ch, Card c) {
        ch.addCard(c);
        System.out.println("\nA new card has been added to your deck...");
        getRightInput("view", "Type 'view' to view...");
        printCard(c);
        getRightInput("next", "Type 'next'");
    }

    public void getRightInput(String right, String msg) {

        boolean wrongInput = true;

        while (wrongInput) {
            System.out.println("\n" + msg);
            String str = input.nextLine();
            if (!(str.equals(right))) {
                System.out.println("\nSorry, that is an invalid input...");
            } else {
                wrongInput = false;
            }
        }

    }

    public void printCard(Card c) {
        System.out.println("\n" + "-=-=-=" + c.getName() + "=-=-=-");
        System.out.println("\n\tDamage: " + c.getEffect().getDamage());
        System.out.println("\n\tResistance: " + c.getEffect().getResistance());
        String type = null;
        if (c.getEffect().getEffectType() == 1) {
            type = "Attack";
        } else if (c.getEffect().getEffectType() == 2) {
            type = "Shield";
        } else {
            type = "Pierce";
        }
        System.out.println("\n\tCard type: " + type);
        System.out.println("\n" + "-=-=-=" + c.getName() + "=-=-=-\n");
    }
}

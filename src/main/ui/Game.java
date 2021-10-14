package ui;

import model.Card;
import model.CardDeck;
import model.Character;
import model.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    private static final int MAX_HEALTH = 1000;

    private static final Character HADES = new Character("HADES, GOD OF THE UNDERWORLD",
            750);

    private static final Character APHRODITE = new Character("APHRODITE, GODDESS OF LOVE",
            1000);

    private static final Character POSEIDON = new Character("POSEIDON, GOD OF THE SEA",
            1250);

    private static final Character ATHENA = new Character("ATHENA, GODDESS OF WAR",
            1500);


    private static final Character ZEUS = new Character("ZEUS, FATHER GOD OF THE SKY",
            2000);


    private Character player;
    private Character boss;
    private Scanner input;
    private CardDeck rewardCards;
    private List<Character> listOfGods;

    public Game() {
        runGame();
    }

    public void runGame() {

        setUpGame();
        beginGame();

        for (Character god : listOfGods) {
            initiateBattle(god);
            if (player.getHealth() == 0) {
                break;
            }
        }
        if (player.getHealth() > 0) {
            gameWin();
        }


        gameOver();
    }

    public void initiateBattle(Character boss) {
        this.boss = boss;
        player.setHealth(MAX_HEALTH);
        setUpBoss();
        printBorders();
        System.out.println(boss.getName() + " has challenged " + player.getName() + " to a duel...");
        printBorders();
        getRightInput("accept", "Type 'accept' to accept the challenge");
        playerTurn();

    }

    public void playerTurn() {
        if (player.getHealth() == 0) {
            printBorders();
            System.out.println(player.getName() + " have been defeated by " + boss.getName());
            System.out.println("\nYou are not worthy to be the GOD OF CARDS...");
            printBorders();
        } else {
            System.out.println("\nIt's your turn. Select a card to play:");
            displayCards();
            String cardChosen = input.nextLine();

            if (player.hasCard(cardChosen)) {
                processCard(cardChosen, player, boss);
                bossTurn();
            } else {
                System.out.println("\nYou do not have a card with the name, " + cardChosen);
                playerTurn();
            }
        }
    }

    public void bossTurn() {
        if (boss.getHealth() == 0) {
            printBorders();
            System.out.println(player.getName() + " has defeated " + boss.getName());
            printBorders();
            afterBossDefeat();

        } else {
            int bossMove = (int) Math.floor(Math.random() * 3);
            List<Card> bossCards = boss.getCardDeck().getCards();
            String cardChosen = bossCards.get(bossMove).getName();
            processCard(cardChosen, boss, player);
            playerTurn();
        }
    }

    public void processCard(String cardChosen, Character currentPlayer, Character opponent) {

        System.out.println("\n()()()()()()()()()()()()()()()()()()()()()()()()()");
        Card playedCard = currentPlayer.getCard(cardChosen);
        Effect cardEffect = playedCard.getEffect();

        if (cardEffect.isStrength() || cardEffect.isResistance()) {

            currentPlayer.addCardEffect(playedCard);
            System.out.println(currentPlayer.getName() + " used " + playedCard.getName());

            if (cardEffect.isStrength()) {
                System.out.println(currentPlayer.getName() + "'S pierce has increased!");
            } else {
                System.out.println(currentPlayer.getName() + " 'S resistance has increased!");
            }
        } else {
            int startHealth = opponent.getHealth();
            System.out.println(currentPlayer.attack(playedCard, opponent));
            int totalHealthLost = startHealth - opponent.getHealth();

            System.out.println("\n" + opponent.getName() + " took " + totalHealthLost
                    + " damage and now has " + opponent.getHealth() + " health ");
        }
        System.out.println("()()()()()()()()()()()()()()()()()()()()()()()()()");
        getRightInput("", "Press 'Enter' to continue...");
    }

    public void afterBossDefeat() {
        if (!(boss.getName().equals("ZEUS, FATHER GOD OF THE SKY"))) {
            System.out.println("\nYou have received a reward for your success...");
            getRightInput("view", "Type 'view' to view your reward");
            displayNewCard();
        }
    }

    public void setUpGame() {
        listOfGods = new ArrayList();
        rewardCards = new CardDeck();

        listOfGods.add(HADES);
        listOfGods.add(APHRODITE);
        listOfGods.add(POSEIDON);
        listOfGods.add(ATHENA);
        listOfGods.add(ZEUS);

        rewardCards.addCard(PlayerCards.PLAYER_SHIELD_1);
        rewardCards.addCard(PlayerCards.PLAYER_PIERCE_1);
        rewardCards.addCard(PlayerCards.PLAYER_ATTACK_2);
        rewardCards.addCard(PlayerCards.PLAYER_SHIELD_2);
        rewardCards.addCard(PlayerCards.PLAYER_ATTACK_3);
    }



    public void beginGame() {
        System.out.println("Please enter your name: ");
        input = new Scanner(System.in);
        String name = input.nextLine();
        System.out.println("God of?: ");
        String godOf = input.nextLine();
        player = new Character(name.toUpperCase() + ", GOD OF " + godOf.toUpperCase(), MAX_HEALTH);
        System.out.println("\nA new challenger has emerged... ");
        System.out.println("Welcome to Mount Olympus-- " + player.getName());
        getRightInput("begin", "Type 'begin' to begin your journey...");
        newCard(PlayerCards.PLAYER_ATTACK_1);
    }


    public void setUpBoss() {
        if (boss.getName().equals("HADES, GOD OF THE UNDERWORLD")) {
            boss.addCard(BossCards.HADES_ATTACK);
            boss.addCard(BossCards.HADES_SHIELD);
            boss.addCard(BossCards.HADES_PIERCE);
        } else if (boss.getName().equals("APHRODITE, GODDESS OF LOVE")) {
            boss.addCard(BossCards.APHRODITE_ATTACK);
            boss.addCard(BossCards.APHRODITE_SHIELD);
            boss.addCard(BossCards.APHRODITE_PIERCE);
        } else if (boss.getName().equals("POSEIDON, GOD OF THE SEA")) {
            boss.addCard(BossCards.POSEIDON_ATTACK);
            boss.addCard(BossCards.POSEIDON_SHIELD);
            boss.addCard(BossCards.POSEIDON_PIERCE);
        } else if (boss.getName().equals("ATHENA, GODDESS OF WAR")) {
            boss.addCard(BossCards.ATHENA_ATTACK);
            boss.addCard(BossCards.ATHENA_SHIELD);
            boss.addCard(BossCards.ATHENA_PIERCE);
        } else {
            boss.addCard(BossCards.ZEUS_ATTACK);
            boss.addCard(BossCards.ZEUS_SHIELD);
            boss.addCard(BossCards.ZEUS_PIERCE);
        }
    }

    public void chooseNewCard() {

        List<Card> cardsToChoose = rewardCards.getCards();
        System.out.println("\nType the name of the card you would like to add to your deck:");
        String cardChosen = input.nextLine();
        boolean isName = false;
        for (int i = 0; i < cardsToChoose.size(); i++) {
            isName = cardsToChoose.get(i).getName().equals(cardChosen);
            if (i == 1 || isName) {
                break;
            }
        }

        if (isName) {
            Card addedCard = rewardCards.getCard(cardChosen);
            removeWeakerCardOfType(addedCard);
            newCard(addedCard);
            rewardCards.removeCard(addedCard);
        } else {
            System.out.println("\nSorry, this name is invalid name... Please try again.");
            chooseNewCard();
        }
    }

    public void displayNewCard() {
        System.out.println("\nPlease choose a new card to add to your deck:");
        List<Card> cardsToChoose = rewardCards.getCards();

        for (int i = 0; i < cardsToChoose.size(); i++) {
            printCard(cardsToChoose.get(i));
            if (i == 1) {
                break;
            }
        }

        chooseNewCard();
    }

    public void removeWeakerCardOfType(Card c) {

        for (Card i : player.getCardDeck().getCards()) {
            if (i.getEffect().getEffectType() == c.getEffect().getEffectType()) {
                player.getCardDeck().removeCard(i);
            }
        }

    }

    public void newCard(Card c) {
        player.addCard(c);
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

    public void displayCards() {
        for (Card c : player.getCardDeck().getCards()) {
            printCard(c);
        }
        System.out.println("\nType the name of the card you want to play (case sensitive): ");
    }

    public void printCard(Card c) {
        String type;
        String damage;
        if (c.getEffect().getEffectType() == 1) {
            type = "Attack";
            int min = c.getEffect().getDamage() - 25;
            int max = c.getEffect().getDamage() + 25;
            damage = min + "-" + max;
        } else if (c.getEffect().getEffectType() == 2) {
            type = "Shield";
            damage = "0";
        } else {
            type = "Pierce";
            damage = Integer.toString(c.getEffect().getDamage());
        }
        System.out.println("\n" + "-=-=-=" + c.getName() + "=-=-=-");
        System.out.println("\n\tDamage: " + damage);
        System.out.println("\n\tResistance: " + c.getEffect().getResistance());
        System.out.println("\n\tCard type: " + type);
        System.out.println("\n" + "-=-=-=" + c.getName() + "=-=-=-\n");
    }

    public void printBorders() {
        System.out.println("========================================================================");
    }

    public void gameOver() {
        System.out.println("\nThanks for playing " + player.getName() + "!");

    }


    public void gameWin() {
        System.out.println("\nCongratulations! You have beaten all of the Gods of Olympus...\n");
        getRightInput("", "Press 'Enter' to continue...");
        printBorders();
        System.out.println("You are worthy of being deemed " + player.getName() + " AND CARDS");
        printBorders();
    }
}

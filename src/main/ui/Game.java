package ui;

import model.Card;
import model.CardDeck;
import model.Character;
import model.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// A game class representing the console user interface that player's interact with
public class Game {

    private static final int MAX_HEALTH = 1000; //MAX HEALTH FOR PLAYER

    private Character player;           // the player character who interacts with the game.
    private Character boss;             // the current boss character that player is fighting
    private Scanner input;              // player input
    private CardDeck rewardCards;       // a list of cards that player can choose to add to their deck
    private List<Character> listOfGods; // a list of boss characters that player has to fight

    // Instantiates a new game and runs it.
    public Game() {
        runGame();
    }

    // EFFECTS: starts the game, initializes fields, iterates player through all boss battles,
    //          tracks if player has won or lost the game.
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

    // MODIFIES: this
    // EFFECTS: initiates battle with given boss, sets player health to max,
    //          initializes boss field to given boss.
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

    // EFFECTS: represents a players turn; spawns death message if player health = 0,
    //          displays playable cards in player's card deck, prompts player to play a card
    //          and processes that move.
    public void playerTurn() {
        if (player.getHealth() == 0) {
            printBorders();
            System.out.println(player.getName() + " has been defeated by " + boss.getName());
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

    // EFFECTS: represents a boss' turn; spawns defeat message if boss health = 0,
    //          picks a boss' move at random and processes it.
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

    // EFFECTS: processes given card and uses it on currentPlayer or opponent (wherever applicable);
    //          determines whether it is an effect or attack card and either applies effect or attacks
    //          opponent with the given card statistics.
    public void processCard(String cardChosen, Character currentPlayer, Character opponent) {

        System.out.println("\n()()()()()()()()()()()()()()()()()()()()()()()()()");
        Card playedCard = currentPlayer.getCard(cardChosen);
        Effect cardEffect = playedCard.getEffect();

        if (cardEffect.isStrength() || cardEffect.isResistance()) {

            currentPlayer.addCardEffect(playedCard);
            System.out.println(currentPlayer.getName() + " used " + playedCard.getName());

            if (cardEffect.isStrength()) {
                System.out.println(currentPlayer.getName() + "'S pierce has increased by "
                        + cardEffect.getDamage());
            } else {
                System.out.println(currentPlayer.getName() + "'S resistance has increased by "
                        + cardEffect.getResistance());
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

    // EFFECTS: if the last boss hasn't been defeated, prints a message prompting a player
    //          to view a reward and allows player to choose a new card to add to deck.
    public void afterBossDefeat() {
        if (!(boss.getName().equals("ZEUS, FATHER GOD OF THE SKY"))) {
            System.out.println("\nYou have received a reward for your success...");
            getRightInput("view", "Type 'view' to view your reward");
            displayNewCard();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes and modifies rewardCards and listOfGods fields.
    public void setUpGame() {
        listOfGods = new ArrayList();
        rewardCards = new CardDeck();

        listOfGods.add(new Character("HADES, GOD OF THE UNDERWORLD",
                750));
        listOfGods.add(new Character("APHRODITE, GODDESS OF LOVE",
                1000));
        listOfGods.add(new Character("POSEIDON, GOD OF THE SEA",
                1250));
        listOfGods.add(new Character("ATHENA, GODDESS OF WAR",
                1500));
        listOfGods.add(new Character("ZEUS, FATHER GOD OF THE SKY",
                2000));

        rewardCards.addCard(PlayerCards.PLAYER_SHIELD_1);
        rewardCards.addCard(PlayerCards.PLAYER_PIERCE_1);
        rewardCards.addCard(PlayerCards.PLAYER_ATTACK_2);
        rewardCards.addCard(PlayerCards.PLAYER_SHIELD_2);
        rewardCards.addCard(PlayerCards.PLAYER_PIERCE_2);
        rewardCards.addCard(PlayerCards.PLAYER_ATTACK_3);
    }

    // MODIFIES: this
    // EFFECTS: initializes input and player fields with user inputted data.
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

    // MODIFIES: this
    // EFFECTS: updates boss' card deck; sets up boss with specific cards based
    //          on their name.
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

    // MODIFIES: this
    // EFFECTS: chooses user inputted card and adds it to player's card deck;
    //          chosen card is removed from rewardCards field and the weaker type
    //          of chosen card is removed from player's deck (where applicable)
    public void chooseNewCard() {

        System.out.println("\nType the name of the card you would like to add to your deck:");
        String cardChosen = input.nextLine();
        boolean isName = false;
        for (int i = 0; i < rewardCards.getCards().size(); i++) {
            isName = rewardCards.getCards().get(i).getName().equals(cardChosen);
            if (i == 1 || isName) {
                break;
            }
        }
        if (isName) {
            Card addedCard = rewardCards.getCard(cardChosen);
            removeWeakerCardOfType(addedCard);
            newCard(addedCard);
            rewardCards.removeCard(addedCard);
            removeWeakerCardsNotChosen();
        } else {
            System.out.println("\nSorry, this name is invalid... Please try again.");
            chooseNewCard();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the weaker version of card type in rewardCards if it has not been chosen
    //          by the time the stronger version appears as a choice to player.
    public void removeWeakerCardsNotChosen() {
        if (rewardCards.contains("Iron Shield") && rewardCards.getCards().size() == 5) {
            rewardCards.removeCard(rewardCards.getCard("Iron Shield"));
        } else if (rewardCards.contains("Iron Pierce") && rewardCards.getCards().size() == 5) {
            rewardCards.removeCard(rewardCards.getCard("Iron Pierce"));
        }
    }

    // EFFECTS: displays the next two possible cards that a player can choose from
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

    // MODIFIES: this
    // EFFECTS: removes any card from player card deck that is of same type as c
    public void removeWeakerCardOfType(Card c) {

        List<Card> playerCards = player.getCardDeck().getCards();
        for (int i = 0; i < playerCards.size(); i++) {
            if (playerCards.get(i).getEffect().getEffectType() == c.getEffect().getEffectType()) {
                player.getCardDeck().removeCard(playerCards.get(i));
                i--;
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: adds card c to player's card deck and prints it out.
    public void newCard(Card c) {
        player.addCard(c);
        System.out.println("\nA new card has been added to your deck...");
        getRightInput("view", "Type 'view' to view...");
        printCard(c);
        getRightInput("next", "Type 'next'");
    }

    // EFFECTS: prompts player to input the right string; if right string is not inputted,
    //          player is prompted infinitely until right string is inputted
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

    // EFFECTS: prints all cards in player's card deck.
    public void displayCards() {
        for (Card c : player.getCardDeck().getCards()) {
            printCard(c);
        }
        System.out.println("\nType the name of the card you want to play (case sensitive): ");
    }

    // EFFECTS: prints card c.
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

    // EFFECTS: prints a border (for formatting)
    public void printBorders() {
        System.out.println("========================================================================");
    }

    // EFFECTS: represents the gameOver state
    public void gameOver() {
        System.out.println("\nWould you like to play again? [yes/no]");
        String again = input.nextLine();
        if (again.equals("yes")) {
            runGame();
        } else if (again.equals("no")) {
            System.out.println("Thanks for playing!");
        } else {
            System.out.println("\nError-- Invalid Input...");
            gameOver();
        }
    }

    // EFFECTS: represents the state where player wins.
    public void gameWin() {
        System.out.println("\nCongratulations! You have beaten all of the Gods of Olympus...\n");
        getRightInput("", "Press 'Enter' to continue...");
        printBorders();
        System.out.println("You are worthy of being deemed " + player.getName() + " ...AND CARDS!");
        printBorders();
    }
}

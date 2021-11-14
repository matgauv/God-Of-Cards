package ui;


import model.Card;
import model.CardDeck;
import model.Character;
import model.Effect;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


// A game class that runs and represents the graphical user interface that players interact with.
public class GameGUI implements ActionListener {

    private static final int MAX_HEALTH = 1000; //MAX HEALTH FOR PLAYER
    private static final String PLAYER_SOURCE = "./data/savedPlayer.json"; // source file for saved player data
    private static final String CARDS_SOURCE = "./data/savedCards.json"; // source file for saved card data

    private Character player;           // the player character who interacts with the game.
    private Character boss;             // the current boss character that player is fighting
    private CardDeck rewardCards;       // a list of cards that player can choose to add to their deck
    private List<Character> listOfGods; // a list of boss characters that player has to fight
    private JsonReader reader;          // JsonReader used for loading saved games.
    private JsonWriter writer;          // JsonWriter for saving games.
    private JFrame frame;
    private JPanel backgroundPanel;
    private JPanel mainPanel;
    private JPanel bossPanel;
    private JPanel bossAttributes;
    private JPanel playerPanel;
    private JPanel playerAttributes;
    private JPanel eventLogPanel;
    private boolean continueGame;

    public static void main(String[] args) {
        new GameGUI();
    }

    public GameGUI() {
        initGameFrame();
        newGameOrLoad();
    }

    public void runGame() {
        continueGame = true;
        for (Character god : listOfGods) {
            initiateBattle(god);
            if (player.getHealth() == 0) {
                break;
            }
        }
        if (player.getHealth() > 0) {
            gameWin();
            promptPlayAgain();
        } else if (continueGame) {
            gameOver();
        }

        int select = JOptionPane.showConfirmDialog(frame, "Quit Game?", "Quit?",
                JOptionPane.YES_NO_OPTION);
        if (select == JOptionPane.YES_OPTION) {
            frame.dispose();
        } else {
            initGods();
            runGame();
        }


    }

    public void initGameFrame() {
        initPanels();
        frame = new JFrame("God of Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(1380, 775);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.add(playerPanel);
        frame.add(bossPanel);
        frame.add(mainPanel);
        frame.add(backgroundPanel);
        frame.setVisible(true);
        centerScreen();
    }

    public void newGameOrLoad() {
        JButton newGame = new JButton("New Game");
        JButton loadGame = new JButton("Load Game");
        newGame.setBounds(525, 200, 150, 50);
        loadGame.setBounds(725, 200, 150, 50);
        newGame.setFont(new Font("Arial", Font.BOLD, 14));
        loadGame.setFont(new Font("Arial", Font.BOLD, 14));
        newGame.setActionCommand("New Game");
        loadGame.setActionCommand("Load Game");
        newGame.addActionListener(this);
        loadGame.addActionListener(this);
        mainPanel.add(newGame);
        mainPanel.add(loadGame);
    }

    public void newGame() {
        String nameInput = null;
        int i = -1;
        while (i < 0) {
            nameInput = JOptionPane.showInputDialog("Please Enter Your Name:");
            if (nameInput.length() > 0) {
                i++;
            }
        }
        Object[] possibleValues = { "God", "Goddess"};
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Are you a God or a Goddess?", "God or Goddess?",
                JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
        nameInput += (", " + selectedValue.toString() + " OF ");
        int h = -1;
        while (h < 0) {
            String godInput = JOptionPane.showInputDialog("God/Goddess of?");
            if (godInput.length() > 0) {
                h++;
            }
            nameInput += godInput;
        }

        player = new Character(nameInput.toUpperCase(), MAX_HEALTH);
        setUpGame();
    }

    public void setUpGame() {
        listOfGods = new ArrayList<>();
        rewardCards = new CardDeck();

        listOfGods.add(new Character("HADES, GOD OF THE UNDERWORLD",
                750));
        listOfGods.add(new Character("APHRODITE, GODDESS OF LOVE",
                1000));
        listOfGods.add(new Character("POSEIDON, GOD OF THE SEA",
                1500));
        listOfGods.add(new Character("ATHENA, GODDESS OF WAR",
                1750));
        listOfGods.add(new Character("ZEUS, FATHER GOD OF THE SKY",
                3000));

        rewardCards.addCard(PlayerCards.PLAYER_SHIELD_1);
        rewardCards.addCard(PlayerCards.PLAYER_PIERCE_1);
        rewardCards.addCard(PlayerCards.PLAYER_ATTACK_2);
        rewardCards.addCard(PlayerCards.PLAYER_SHIELD_2);
        rewardCards.addCard(PlayerCards.PLAYER_PIERCE_2);
        rewardCards.addCard(PlayerCards.PLAYER_ATTACK_3);

        newCard(PlayerCards.PLAYER_ATTACK_1);

    }

    public void initiateBattle(Character boss) {
        this.boss = boss;
        player.setHealth(MAX_HEALTH);
        player.clearEffectsApplied();
        setUpBoss();
        setUpBossFrame();
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

    public void setUpBossFrame() {
        clearFrame();
        ImageIcon currentBoss = createImageIcon(boss.getName(), "Backgrounds");
        JLabel background = createBackground(currentBoss);
        backgroundPanel.add(background);
        frame.revalidate();
        frame.repaint();
        int selected = JOptionPane.showConfirmDialog(null, boss.getName()
                + " has challenged " + player.getName() + " to a duel. Accept the challenge?", "New Battle",
                JOptionPane.YES_NO_OPTION);

        if (selected == JOptionPane.YES_OPTION) {
            updateBattleSpace();
            playerTurn();
        } else {
            player.setHealth(0);
            continueGame = false;
        }
    }

    public void updateBattleSpace() {
        printBossName();
        createAttributesPanels();
        updateAttributePanel(player);
        updateAttributePanel(boss);
        createEventLogPanel();
        printCharacterCards(player);
        printCharacterCards(boss);
        frame.revalidate();
        frame.repaint();

    }

    public void playerTurn() {
        if (player.getHealth() == 0) {
            JOptionPane.showMessageDialog(null, player.getName() + " has been defeated by "
                    + boss.getName(), "Defeat", JOptionPane.ERROR_MESSAGE, null);
        } else {
            String selectedCard = produceCardSelectionPane();
            processCard(selectedCard, player, boss);
            updateAttributePanel(player);
            updateAttributePanel(boss);
            JOptionPane confirm = new JOptionPane(selectedCard + " was used successfully!",
                    JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, null, "");
            JDialog dialog = confirm.createDialog(frame,"Move Confirmed");
            dialog.setLocation(1150, 485);
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);
            bossTurn();
        }
    }


    public void bossTurn() {
        if (boss.getHealth() == 0) {
            JOptionPane.showMessageDialog(null, player.getName() + " has defeated "
                    + boss.getName(), "Successful Battle", JOptionPane.INFORMATION_MESSAGE, null);
            afterBossDefeat();
        } else {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Error-- Time Delay was unsuccessful");
            }
            int bossMove = (int) Math.floor(Math.random() * 3);
            List<Card> bossCards = boss.getCardDeck().getCards();
            String cardChosen = bossCards.get(bossMove).getName();
            processCard(cardChosen, boss, player);
            updateAttributePanel(boss);
            updateAttributePanel(player);
            playerTurn();
        }
    }

    public String produceCardSelectionPane() {
        List<String> nameList = new ArrayList<>();
        Object[] possibleValues;
        for (Card c : player.getCardDeck().getCards()) {
            nameList.add(c.getName());
        }
        if (nameList.size() == 1) {
            possibleValues = new Object[]{ nameList.get(0) };
        } else if (nameList.size() == 2) {
            possibleValues = new Object[]{ nameList.get(0), nameList.get(1) };
        } else {
            possibleValues = new Object[]{ nameList.get(0), nameList.get(1), nameList.get(2) };
        }

        JOptionPane optionPane = new JOptionPane("Select a card to play:",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, possibleValues,
                possibleValues[0]);

        JDialog dialog = optionPane.createDialog(frame, "Select a Card");
        dialog.setLocation(1150, 485);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Object selected = optionPane.getValue();

        return selected.toString();
    }

    // EFFECTS: processes given card and uses it on currentPlayer or opponent (wherever applicable);
    //          determines whether it is an effect or attack card and either applies effect or attacks
    //          opponent with the given card statistics.
    public void processCard(String cardChosen, Character currentPlayer, Character opponent) {

        Card playedCard = currentPlayer.getCard(cardChosen);
        Effect cardEffect = playedCard.getEffect();
        String lineOne = currentPlayer.getName() + " used " + playedCard.getName();
        String lineTwo;

        if (cardEffect.isStrength() || cardEffect.isResistance()) {

            currentPlayer.addCardEffect(playedCard);

            if (cardEffect.isStrength()) {
                lineTwo = currentPlayer.getName() + "'S pierce has increased by "
                        + cardEffect.getDamage();
            } else {
                lineTwo = currentPlayer.getName() + "'S resistance has increased by "
                        + cardEffect.getResistance();
            }
        } else {
            int startHealth = opponent.getHealth();
            currentPlayer.attack(playedCard, opponent);
            int totalHealthLost = startHealth - opponent.getHealth();

            lineTwo = opponent.getName() + " took " + totalHealthLost
                    + " damage";
        }
        updateEventLog(lineOne, lineTwo);
    }

    public void updateEventLog(String lineOne, String lineTwo) {
        eventLogPanel.removeAll();
        JLabel firstLine = new JLabel(lineOne);
        JLabel secondLine = new JLabel(lineTwo);
        firstLine.setFont(new Font("Arial", Font.BOLD, 20));
        secondLine.setFont(new Font("Arial", Font.BOLD, 20));
        firstLine.setForeground(Color.WHITE);
        secondLine.setForeground(Color.WHITE);
        eventLogPanel.add(firstLine);
        eventLogPanel.add(secondLine);
        frame.revalidate();
        frame.repaint();
    }

    public void updateAttributePanel(Character c) {
        JPanel healthPanel = createAttributePanel("health", c);
        JPanel shieldPanel = createAttributePanel("resist", c);
        shieldPanel.setBounds(0, 84, 250, 83);
        JPanel piercePanel = createAttributePanel("pierce", c);
        piercePanel.setBounds(0, 172, 250, 83);

        if (c.equals(player)) {
            playerAttributes.removeAll();
            playerAttributes.add(healthPanel);
            playerAttributes.add(shieldPanel);
            playerAttributes.add(piercePanel);
        } else {
            bossAttributes.removeAll();
            bossAttributes.add(healthPanel);
            bossAttributes.add(shieldPanel);
            bossAttributes.add(piercePanel);
        }

        frame.revalidate();
        frame.repaint();
    }

    public JPanel createAttributePanel(String imgString, Character c) {
        ImageIcon img = createImageIcon(imgString, "OtherImages");
        ImageIcon imgScaled = new ImageIcon(img.getImage().getScaledInstance(75, 75,
                Image.SCALE_SMOOTH));
        JLabel imgLabel = new JLabel(imgScaled);
        imgLabel.setBounds(0, 0, 83,83);
        JLabel attributeVal;
        if (imgString.equals("health")) {
            attributeVal = new JLabel(Integer.toString(c.getHealth()));
        } else if (imgString.equals("resist")) {
            attributeVal = new JLabel(Integer.toString(c.getResistInt()));
        } else {
            attributeVal = new JLabel(Integer.toString(c.getPierceInt()));
        }

        attributeVal.setFont(new Font("Arial", Font.BOLD, 25));
        attributeVal.setForeground(Color.WHITE);
        attributeVal.setBounds(84, 0, 150, 83);
        JPanel attributePanel = new JPanel();
        attributePanel.setLayout(null);
        attributePanel.setSize(250, 83);
        attributePanel.setOpaque(false);
        attributePanel.add(imgLabel);
        attributePanel.add(attributeVal);
        return attributePanel;
    }

    public void createAttributesPanels() {
        bossAttributes = new JPanel();
        bossAttributes.setBackground(new Color(0, 0, 0, 150));
        bossAttributes.setBounds(600, 70, 250, 250);
        bossAttributes.setLayout(null);
        mainPanel.add(bossAttributes);

        playerAttributes = new JPanel();
        playerAttributes.setBackground(new Color(0, 0, 0, 150));
        playerAttributes.setBounds(600, 415, 250, 250);
        playerAttributes.setLayout(null);
        mainPanel.add(playerAttributes);
    }

    public void createEventLogPanel() {
        eventLogPanel = new JPanel();
        eventLogPanel.setBackground(new Color(0, 0, 0, 150));
        eventLogPanel.setBounds(60, 330, 790, 75);
        eventLogPanel.setLayout(new FlowLayout());
        mainPanel.add(eventLogPanel);
    }


    public void afterBossDefeat() {
        if (!boss.getName().equals("ZEUS, FATHER GOD OF THE SKY")) {
            ImageIcon back = createImageIcon("PICK A CARD BACKGROUND", "Backgrounds");
            JLabel background = createBackground(back);
            clearFrame();
            backgroundPanel.add(background);
            displayNewCards();
            chooseNewCard();
            promptSave();
        }
    }

    public void printBossName() {
        ImageIcon bossNameImg = createImageIcon(boss.getName() + " NAME", "OtherImages");
        JLabel bossName = new JLabel(bossNameImg);
        bossName.setBounds(0, 10, bossNameImg.getIconWidth(), bossNameImg.getIconHeight());
        mainPanel.add(bossName);

    }


    public void initPanels() {
        ImageIcon back = createImageIcon("TITLE SCREEN", "Backgrounds");
        JLabel background = createBackground(back);
        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(0, 0, 1380, 775);
        backgroundPanel.setLayout(null);
        backgroundPanel.add(background);
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1380, 775);
        mainPanel.setLayout(null);
        mainPanel.setOpaque(false);
        bossPanel = new JPanel();
        bossPanel.setBounds(0,50, 650, 400);
        bossPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 20));
        bossPanel.setBackground(new Color(0, 0, 0, 0));
        playerPanel = new JPanel();
        playerPanel.setBounds(0,395, 650, 400);
        playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 20));
        playerPanel.setOpaque(false);
    }


    public void centerScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screen.width - frame.getWidth()) / 2, (screen.height - frame.getHeight()) / 2);
    }

    public void newCard(Card c) {
        player.addCard(c);
        ImageIcon cardImg = createImageIcon(c.getName(), "GameCards");
        Object[] choice = { "OK" };
        JOptionPane.showOptionDialog(null, c.getName() + " has been added to your card deck!",
                "New Card Alert", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, cardImg, choice,
                choice[0]);
    }

    public void printCharacterCards(Character c) {
        for (Card i : c.getCardDeck().getCards()) {

            ImageIcon c1 = createImageIcon(i.getName(), "GameCards");
            ImageIcon ca1 = new ImageIcon(c1.getImage().getScaledInstance(160, 250, Image.SCALE_SMOOTH));

            JLabel card = new JLabel(ca1);
            if (c.getName().equals(player.getName())) {
                playerPanel.add(card);
            } else {
                bossPanel.add(card);
            }
        }
    }

    public void displayNewCards() {

        JPanel cardPanel = new JPanel();
        cardPanel.setBounds(0, 220, 1400, 500);
        cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 450, 0));
        cardPanel.setBackground(new Color(0,0,0,0));


        for (int i = 0; i < 2; i++) {
            String cardName = rewardCards.getCards().get(i).getName();

            ImageIcon c1 = createImageIcon(cardName, "GameCards");

            JLabel card = new JLabel(c1);
            cardPanel.add(card);
        }
        mainPanel.add(cardPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void chooseNewCard() {
        Object[] cards = { rewardCards.getCards().get(0).getName(), rewardCards.getCards().get(1).getName() };
        JOptionPane optionPane = new JOptionPane("Choose a new card to add to your deck:",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, cards,
                cards[0]);
        JDialog cardDialog = optionPane.createDialog(frame, "Choose a new card");
        cardDialog.setLocation(835, 490);
        cardDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        cardDialog.setVisible(true);

        String chosenCard = optionPane.getValue().toString();
        Card addedCard = rewardCards.getCard(chosenCard);
        removeWeakerCardOfType(addedCard);
        rewardCards.getCards().remove(addedCard);
        removeWeakerCardsNotChosen();
        newCard(addedCard);
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
    // EFFECTS: removes the weaker version of card type in rewardCards if it has not been chosen
    //          by the time the stronger version appears as a choice to player.
    public void removeWeakerCardsNotChosen() {
        if (rewardCards.contains("Iron Shield") && rewardCards.getCards().size() == 5) {
            rewardCards.removeCard(rewardCards.getCard("Iron Shield"));
        } else if (rewardCards.contains("Iron Pierce") && rewardCards.getCards().size() == 5) {
            rewardCards.removeCard(rewardCards.getCard("Iron Pierce"));
        }
    }

    public ImageIcon createImageIcon(String img, String fileDir) {

        String sep = System.getProperty("file.separator");
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + sep + "data" + sep + fileDir
                + sep + img + ".png");

        return icon;
    }

    public JLabel createBackground(ImageIcon img) {
        JLabel background = new JLabel(img);
        background.setBounds(0, 0, 1380, 775);
        return background;
    }

    public void promptSave() {
        Object[] options = {"Save", "Don't Save"};
        JOptionPane optionPane = new JOptionPane("Would you like to save the game?",
                JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options,
                options[0]);
        JDialog dialog = optionPane.createDialog(null, "Save Game");
        dialog.setLocation(835, 490);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);

        if (optionPane.getValue().toString().equals("Save")) {
            saveGame();
        }
    }

    public void loadGame() {
        try {
            reader = new JsonReader(PLAYER_SOURCE);
            player = reader.readPlayer();
            reader = new JsonReader(CARDS_SOURCE);
            rewardCards = reader.readCardDeck();
            initGods();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error-No Save File Could be found");
            new GameGUI();
        }

    }

    public void saveGame() {
        try {
            writer = new JsonWriter(PLAYER_SOURCE);
            writer.open();
            writer.write(player);
            writer.close();
            writer = new JsonWriter(CARDS_SOURCE);
            writer.open();
            writer.write(rewardCards);
            writer.close();
            JOptionPane.showMessageDialog(null, "Your progress has been saved!");
        } catch (IOException e) {
            System.out.println("Error-- file not able to save...");
        }
    }

    public void clearFrame() {
        backgroundPanel.removeAll();
        mainPanel.removeAll();
        playerPanel.removeAll();
        bossPanel.removeAll();
    }

    public void initGods() {
        listOfGods = new ArrayList<>();
        if (rewardCards.numCards() == 4) {
            listOfGods.add(new Character("APHRODITE, GODDESS OF LOVE",
                    1000));
            listOfGods.add(new Character("POSEIDON, GOD OF THE SEA",
                    1500));
            listOfGods.add(new Character("ATHENA, GODDESS OF WAR",
                    1750));
            listOfGods.add(new Character("ZEUS, FATHER GOD OF THE SKY",
                    2250));
        } else if (rewardCards.numCards() == 3) {
            listOfGods.add(new Character("POSEIDON, GOD OF THE SEA",
                    1500));
            listOfGods.add(new Character("ATHENA, GODDESS OF WAR",
                    1750));
            listOfGods.add(new Character("ZEUS, FATHER GOD OF THE SKY",
                    3000));
        } else {
            initGodsContd();
        }
    }

    public void initGodsContd() {
        if (rewardCards.numCards() == 2) {
            listOfGods.add(new Character("ATHENA, GODDESS OF WAR",
                    1750));
            listOfGods.add(new Character("ZEUS, FATHER GOD OF THE SKY",
                    2250));
        } else if (rewardCards.numCards() == 1) {
            listOfGods.add(new Character("ZEUS, FATHER GOD OF THE SKY",
                    3000));
        } else {
            listOfGods.add(new Character("HADES, GOD OF THE UNDERWORLD",
                    750));
            listOfGods.add(new Character("APHRODITE, GODDESS OF LOVE",
                    1000));
            listOfGods.add(new Character("POSEIDON, GOD OF THE SEA",
                    1500));
            listOfGods.add(new Character("ATHENA, GODDESS OF WAR",
                    1750));
            listOfGods.add(new Character("ZEUS, FATHER GOD OF THE SKY",
                    3000));

        }
    }

    public void gameWin() {

    }

    public void gameOver() {
        int selected = JOptionPane.showConfirmDialog(frame,
                "Would you like to continue playing from your last save?", "Continue Playing?",
                JOptionPane.YES_NO_OPTION);

        if (selected == JOptionPane.YES_OPTION) {
            loadGame();
            runGame();
        } else {
            continueGame = false;
        }
    }

    public void promptPlayAgain() {

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Game")) {
            newGame();
            runGame();
        } else if (e.getActionCommand().equals("Load Game")) {
            loadGame();
            JOptionPane.showMessageDialog(null, "--SAVE FILE SUCCESSFULLY LOADED--"
                    + "\nWelcome back, " + player.getName());
            runGame();
        }
    }


}

package ui;


import model.Card;
import model.CardDeck;
import model.Character;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


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
    private JPanel playerPanel;

    public static void main(String[] args) {
        new GameGUI();
    }

    public GameGUI() {
        runGame();
    }

    public void runGame() {
        initGameFrame();
        newGameOrLoad();
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
        System.out.println("You're doing great!");

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
        bossPanel.setBounds(0,0, 650, 400);
        bossPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 20));
        bossPanel.setBackground(new Color(0, 0, 0, 0));
        playerPanel = new JPanel();
        playerPanel.setBounds(0,425, 650, 400);
        playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 20));
        playerPanel.setBackground(new Color(0, 0, 0, 0));
    }




    public void bleh() {

        rewardCards = new CardDeck();
        rewardCards.addCard(PlayerCards.PLAYER_SHIELD_2);
        rewardCards.addCard(PlayerCards.PLAYER_PIERCE_2);
        rewardCards.addCard(PlayerCards.PLAYER_ATTACK_3);

        Character c = new Character("Poseidon", 1000);
        c.addCard(BossCards.ZEUS_ATTACK);
        c.addCard(BossCards.ZEUS_PIERCE);
        c.addCard(BossCards.ZEUS_SHIELD);

        Character p = new Character("Player", 1000);
        p.addCard(PlayerCards.PLAYER_ATTACK_1);
        p.addCard(PlayerCards.PLAYER_PIERCE_1);
        p.addCard(PlayerCards.PLAYER_SHIELD_2);


        printCharacterCards(c);
        printCharacterCards(p);

    }

    public void centerScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screen.width - frame.getWidth()) / 2, (screen.height - frame.getHeight()) / 2);
    }

    public void printCharacterCards(Character c) {
        for (Card i : c.getCardDeck().getCards()) {

            ImageIcon c1 = createImageIcon(i.getName(), "GameCards");

            ImageIcon ca1 = new ImageIcon(c1.getImage().getScaledInstance(160, 250, Image.SCALE_SMOOTH));

            JLabel card = new JLabel(ca1);
            if (c.getName().equals("Player")) {
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
        frame.add(cardPanel);
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

    public void loadGame() {

    }

    public void clearFrame() {
        backgroundPanel.removeAll();
        mainPanel.removeAll();
        playerPanel.removeAll();
        bossPanel.removeAll();
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New Game")) {
            newGame();
        } else if (e.getActionCommand().equals("Load Game")) {
            loadGame();
        }
    }


}

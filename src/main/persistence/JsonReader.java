package persistence;

import model.*;
import model.Character;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// represents a reader that reads player and cardDeck from JSON data stored in file.
// a majority of this class was modeled after the following repository:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {

    private String source; // field representing the source of the JSON data.


    // constructs a JsonReader to read from source file.
    public JsonReader(String source) {
        this.source = source;
    }

    // modeled after read() method in JsonReader class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads player from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Character readPlayer() throws IOException {
        String savedPlayerData = readFile(source);
        JSONObject savedPlayerObject = new JSONObject(savedPlayerData);
        return parsePlayer(savedPlayerObject);
    }

    // modeled after read() method in JsonReader class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads cardDeck from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CardDeck readCardDeck() throws IOException {
        String savedCardDeckData = readFile(source);
        JSONObject savedCardDeckObject = new JSONObject(savedCardDeckData);
        return parseCardDeck(savedCardDeckObject);
    }

    // copied from readFile() method in JsonReader class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses cardDeck from JSONObject and returns it.
    private CardDeck parseCardDeck(JSONObject cardDeckObject) {
        CardDeck cardDeck = new CardDeck();
        JSONArray cards = cardDeckObject.getJSONArray("cards");
        for (Object card : cards) {
            JSONObject nextCard = (JSONObject) card;
            addCard(cardDeck, nextCard);

        }

        return cardDeck;

    }

    // EFFECTS: parses player from JSONObject and returns it.
    private Character parsePlayer(JSONObject playerObject) {
        String name = playerObject.getString("player name");
        int health = playerObject.getInt("health");
        Character player = new Character(name, health);
        addCards(player, playerObject);
        for (Card c : player.getCardDeck().getCards()) {
            Event e = new Event(c.getName() + " loaded to " + player.getName() + "'S CardDeck");
            EventLog.getInstance().logEvent(e);
        }
        addEffectsApplied(player, playerObject);
        return player;
    }

    // MODIFIES: c
    // EFFECTS: parses cards from JSONObject and adds them to player CardDeck.
    private void addCards(Character c, JSONObject playerObject) {
        JSONObject cardDeck = playerObject.getJSONObject("Card Deck");
        JSONArray cards = cardDeck.getJSONArray("cards");
        for (Object card : cards) {
            JSONObject nextCard = (JSONObject) card;
            addCard(c.getCardDeck(), nextCard);

        }

    }

    // MODIFIES: c
    // EFFECTS: parses effects from JSONObject and adds them to player effectsApplied.
    private void addEffectsApplied(Character c, JSONObject playerObject) {
        JSONArray effectsApplied = playerObject.getJSONArray("Effects Applied");
        for (Object effect : effectsApplied) {
            JSONObject nextEffect = (JSONObject) effect;
            addEffect(c, nextEffect);
        }
    }

    // MODIFIES: cd
    // parses an individual card and adds it to cardDeck.
    private void addCard(CardDeck cd, JSONObject cardObject) {
        String cardName = cardObject.getString("card name");
        JSONObject effect = cardObject.getJSONObject("effect");
        int effectDamage = effect.getInt("damage");
        int effectResistance = effect.getInt("resistance");
        int effectType = effect.getInt("effectType");
        Effect cardEffect = new Effect(effectDamage, effectResistance, effectType);

        Card card = new Card(cardName, cardEffect);
        cd.addCard(card);
    }

    // MODIFIES: c
    // parses an individual effect and adds it to effectsApplied.
    private void addEffect(Character c, JSONObject effectObject) {
        int effectDamage = effectObject.getInt("damage");
        int effectResistance = effectObject.getInt("resistance");
        int effectType = effectObject.getInt("effectType");
        Effect effect = new Effect(effectDamage, effectResistance, effectType);
        c.getEffectsApplied().add(effect);
    }





}

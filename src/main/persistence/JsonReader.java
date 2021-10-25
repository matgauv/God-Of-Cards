package persistence;

import model.Card;
import model.CardDeck;
import model.Character;
import model.Effect;
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

    // modeled after read() method in JsonWriter class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads player from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Character readPlayer() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // reads card deck from file and returns it.
    // modeled after read() method in JsonWriter class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads cardDeck from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CardDeck readCardDeck() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCardDeck(jsonObject);
    }

    // copied from readFile() method in JsonWriter class:
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
    private CardDeck parseCardDeck(JSONObject jsonObject) {
        CardDeck cardDeck = new CardDeck();
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(cardDeck, nextCard);

        }

        return cardDeck;

    }

    // EFFECTS: parses player from JSONObject and returns it.
    private Character parsePlayer(JSONObject jsonObject) {
        String name = jsonObject.getString("player name");
        int health = jsonObject.getInt("health");
        Character player = new Character(name, health);
        addCards(player, jsonObject);
        return player;
    }

    // MODIFIES: c
    // EFFECTS: parses cards from JSONObject and adds them to player CardDeck.
    private void addCards(Character c, JSONObject jsonObject) {
        JSONObject cardDeck = jsonObject.getJSONObject("Card Deck");
        JSONArray jsonArray = cardDeck.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(c.getCardDeck(), nextCard);

        }

    }

    // MODIFIES: cd
    // parses an individual card and adds it to cardDeck.
    private void addCard(CardDeck cd, JSONObject jsonObject) {
        String cardName = jsonObject.getString("card name");
        JSONObject effect = jsonObject.getJSONObject("effect");
        int effectDamage = effect.getInt("damage");
        int effectResistance = effect.getInt("resistance");
        int effectType = effect.getInt("effectType");
        Effect cardEffect = new Effect(effectDamage, effectResistance, effectType);

        Card card = new Card(cardName, cardEffect);
        cd.addCard(card);
    }





}

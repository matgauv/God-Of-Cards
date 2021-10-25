package persistence;

import model.CardDeck;
import model.Character;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// represents a writer that writes a representation of player or cardDeck to file.
// This class was modeled after the Json Writer class in the following repository:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // copied from open() method in JsonWriter class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: opens writer
    // throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // modeled after write() method in JsonWriter class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes JSON representation of Player to file
    public void write(Character p) {
        JSONObject playerObject = p.toJson();
        saveToFile(playerObject.toString(TAB));
    }

    // modeled after write() method in JsonWriter class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes JSON representation of CardDeck to file
    public void write(CardDeck cd) {
        JSONObject cardDeckObject = cd.toJson();
        saveToFile(cardDeckObject.toString(TAB));
    }

    // copied from saveToFile() method in JsonWriter class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // copied from close() method in JsonWriter class:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }




}

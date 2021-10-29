package persistence;

import org.json.JSONObject;

// represents an interface to make Objects writable for JSon conversion...
// based off of Writable interface found in the following repository:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {

    // EFFECTS: converts an object to a JSONObject
    JSONObject toJson();
}

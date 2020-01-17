package uk.co.adamsantiago.menu.models;

import static uk.co.adamsantiago.menu.Constants.*;
import org.json.simple.JSONObject;

public class Course {

    private String name;

    public Course(String name) {
        this.name = name;
    }

    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME, name);
        return jsonObject.toString();
    }
}
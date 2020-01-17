package uk.co.adamsantiago.menu.models;

import static uk.co.adamsantiago.menu.Constants.*;
import org.json.simple.JSONObject;

public class Dish {

    private String courseId;
    private String name;
    private String vegetarian;

    public Dish(String courseId, String name, String vegetarian) {
        this.courseId = courseId;
        this.name = name;
        this.vegetarian = vegetarian;
    }

    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(COURSE_ID, courseId);
        jsonObject.put(NAME, name);
        jsonObject.put(VEGETARIAN, vegetarian);
        return jsonObject.toString();
    }
}
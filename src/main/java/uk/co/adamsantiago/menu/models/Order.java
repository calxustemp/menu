package uk.co.adamsantiago.menu.models;

import static uk.co.adamsantiago.menu.Constants.*;
import org.json.simple.JSONObject;

public class Order {

    private String dishId;
    private String userId;

    public Order(String dishId, String userId) {
        this.dishId = dishId;
        this.userId = userId;
    }

    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(DISH_ID, dishId);
        jsonObject.put(USER_ID, userId);
        return jsonObject.toString();
    }
}
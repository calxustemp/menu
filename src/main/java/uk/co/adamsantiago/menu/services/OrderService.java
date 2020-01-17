package uk.co.adamsantiago.menu.services;

import uk.co.adamsantiago.common.utils.StatementGenerator;
import uk.co.adamsantiago.common.services.DBConnection;
import uk.co.adamsantiago.common.models.Insert;
import uk.co.adamsantiago.menu.models.Order;
import static uk.co.adamsantiago.menu.Constants.*;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderService {

    final static Logger logger = Logger.getLogger(OrderService.class);

    public static boolean createOrder(DBConnection connection, JSONObject postData) {
        String insertStatement = populateInsertData(postData);
        connection.executeQuery(insertStatement);
        return true;
    }

    public static String getOrder(DBConnection connection, JSONObject getData) {
        String getStatement = populateGetData(getData);
        ResultSet rs = connection.executeQuery(getStatement);
        ArrayList<Order> orders = new ArrayList<Order>();
        try {
            while(rs.next()) {
                String dishId = rs.getString(DISH_ID);
                String userId = rs.getString(USER_ID);
                Order order = new Order(dishId, userId);
                orders.add(order);
            }
        } catch(SQLException sqle) {
            logger.error("Failed to retrieve query results for query: " + getStatement);
            logger.debug(sqle.toString());
        }
        JSONArray jsonArray = new JSONArray();
        for (Order order : orders) {
            jsonArray.add(order.toString());
        }
        return jsonArray.toString();
    }

    public static boolean updateOrder(DBConnection connection, JSONObject putData) {
        String updateStatement = populateUpdateData(putData);
        connection.executeQuery(updateStatement);
        return true;
    }

    public static boolean deleteOrder(DBConnection connection, JSONObject deleteData) {
        String deleteStatement = populateDeleteData(deleteData);
        connection.executeQuery(deleteStatement);
        return true;
    }

    private static String populateInsertData(JSONObject postData) {
        String dishId = (String)postData.get(DISH_ID);
        String userId = (String)postData.get(USER_ID);

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        columns.add(DISH_ID);
        columns.add(USER_ID);
        values.add(dishId);
        values.add(userId);
        return StatementGenerator.insert(ORDER, columns, values);
    }

    private static String populateUpdateData(JSONObject putData) {
        String id = (String)putData.get(ID);
        String dishId = (String)putData.get(DISH_ID);
        String userId = (String)putData.get(USER_ID);

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        columns.add(DISH_ID);
        columns.add(USER_ID);
        values.add(dishId);
        values.add(userId);

        return StatementGenerator.update(ORDER, columns, values, ID, id);
    }

    private static String populateDeleteData(JSONObject deleteData) {
        String id = (String)deleteData.get(ID);
        return StatementGenerator.delete(ORDER, ID, id);
    }

    private static String populateGetData(JSONObject getData) {
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> conditionColumns = new ArrayList<>();
        ArrayList<String> conditionValues = new ArrayList<>();

        columns.add(DISH_ID);
        columns.add(USER_ID);

        String id = (String)getData.get(ID);
        if (id != null) {
            conditionColumns.add(ID);
            conditionValues.add(id);
        }

        return StatementGenerator.select(columns, ORDER, conditionColumns, conditionValues);
    }

}

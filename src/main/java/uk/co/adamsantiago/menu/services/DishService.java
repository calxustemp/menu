package uk.co.adamsantiago.menu.services;

import uk.co.adamsantiago.common.utils.StatementGenerator;
import uk.co.adamsantiago.common.services.DBConnection;
import uk.co.adamsantiago.common.models.Insert;
import uk.co.adamsantiago.menu.models.Dish;
import static uk.co.adamsantiago.menu.Constants.*;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DishService {

    final static Logger logger = Logger.getLogger(DishService.class);

    public static boolean createDish(DBConnection connection, JSONObject postData) {
        String insertStatement = populateInsertData(postData);
        connection.executeQuery(insertStatement);
        return true;
    }

    public static String getDish(DBConnection connection, JSONObject getData) {
        String getStatement = populateGetData(getData);
        ResultSet rs = connection.executeQuery(getStatement);
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        try {
            while(rs.next()) {
                String courseId = rs.getString(COURSE_ID);
                String name = rs.getString(NAME);
                String vegetarian = rs.getString(VEGETARIAN);
                Dish dish = new Dish(courseId, name, vegetarian);
                dishes.add(dish);
            }
        } catch(SQLException sqle) {
            logger.error("Failed to retrieve query results for query: " + getStatement);
            logger.debug(sqle.toString());
        }
        JSONArray jsonArray = new JSONArray();
        for (Dish dish : dishes) {
            jsonArray.add(dish.toString());
        }
        return jsonArray.toString();
    }

    public static boolean updateDish(DBConnection connection, JSONObject putData) {
        String updateStatement = populateUpdateData(putData);
        connection.executeQuery(updateStatement);
        return true;
    }

    public static boolean deleteDish(DBConnection connection, JSONObject deleteData) {
        String deleteStatement = populateDeleteData(deleteData);
        connection.executeQuery(deleteStatement);
        return true;
    }

    private static String populateInsertData(JSONObject postData) {
        String courseId = (String)postData.get(COURSE_ID);
        String name = (String)postData.get(NAME);
        String vegetarian = (String)postData.get(VEGETARIAN);

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        columns.add(COURSE_ID);
        columns.add(NAME);
        columns.add(VEGETARIAN);
        values.add(courseId);
        values.add(name);
        values.add(vegetarian);
        return StatementGenerator.insert(DISH, columns, values);
    }

    private static String populateUpdateData(JSONObject putData) {
        String id = (String)putData.get(ID);
        String courseId = (String)putData.get(COURSE_ID);
        String name = (String)putData.get(NAME);
        String vegetarian = (String)putData.get(VEGETARIAN);

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        columns.add(COURSE_ID);
        columns.add(NAME);
        columns.add(VEGETARIAN);
        values.add(courseId);
        values.add(name);
        values.add(vegetarian);

        return StatementGenerator.update(DISH, columns, values, ID, id);
    }

    private static String populateDeleteData(JSONObject deleteData) {
        String id = (String)deleteData.get(ID);
        return StatementGenerator.delete(DISH, ID, id);
    }

    private static String populateGetData(JSONObject getData) {
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> conditionColumns = new ArrayList<>();
        ArrayList<String> conditionValues = new ArrayList<>();

        columns.add(COURSE_ID);
        columns.add(NAME);
        columns.add(VEGETARIAN);

        String id = (String)getData.get(ID);
        if (id != null) {
            conditionColumns.add(ID);
            conditionValues.add(id);
        }

        return StatementGenerator.select(columns, DISH, conditionColumns, conditionValues);
    }

}

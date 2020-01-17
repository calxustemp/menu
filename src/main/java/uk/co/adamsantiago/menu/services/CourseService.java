package uk.co.adamsantiago.menu.services;

import uk.co.adamsantiago.common.utils.StatementGenerator;
import uk.co.adamsantiago.common.services.DBConnection;
import uk.co.adamsantiago.common.models.Insert;
import uk.co.adamsantiago.menu.models.Course;
import static uk.co.adamsantiago.menu.Constants.*;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseService {

    final static Logger logger = Logger.getLogger(CourseService.class);

    public static boolean createCourse(DBConnection connection, JSONObject postData) {
        String insertStatement = populateInsertData(postData);
        connection.executeQuery(insertStatement);
        return true;
    }

    public static String getCourse(DBConnection connection, JSONObject getData) {
        String getStatement = populateGetData(getData);
        ResultSet rs = connection.executeQuery(getStatement);
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            while(rs.next()) {
                String name = rs.getString(NAME);
                Course course = new Course(name);
                courses.add(course);
            }
        } catch(SQLException sqle) {
            logger.error("Failed to retrieve query results for query: " + getStatement);
            logger.debug(sqle.toString());
        }
        JSONArray jsonArray = new JSONArray();
        for (Course course : courses) {
            jsonArray.add(course.toString());
        }
        return jsonArray.toString();
    }

    public static boolean updateCourse(DBConnection connection, JSONObject putData) {
        String updateStatement = populateUpdateData(putData);
        connection.executeQuery(updateStatement);
        return true;
    }

    public static boolean deleteCourse(DBConnection connection, JSONObject deleteData) {
        String deleteStatement = populateDeleteData(deleteData);
        connection.executeQuery(deleteStatement);
        return true;
    }

    private static String populateInsertData(JSONObject postData) {
        String name = (String)postData.get(NAME);

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        columns.add(NAME);
        values.add(name);

        return StatementGenerator.insert(COURSE, columns, values);
    }

    private static String populateUpdateData(JSONObject putData) {
        String id = (String)putData.get(ID);
        String name = (String)putData.get(NAME);

        ArrayList<String> columns = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        columns.add(NAME);
        values.add(name);

        return StatementGenerator.update(COURSE, columns, values, ID, id);
    }

    private static String populateDeleteData(JSONObject deleteData) {
        String id = (String)deleteData.get(ID);
        return StatementGenerator.delete(COURSE, ID, id);
    }

    private static String populateGetData(JSONObject getData) {
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<String> conditionColumns = new ArrayList<>();
        ArrayList<String> conditionValues = new ArrayList<>();

        columns.add(NAME);

        String id = (String)getData.get(ID);
        if (id != null) {
            conditionColumns.add(ID);
            conditionValues.add(id);
        }

        return StatementGenerator.select(columns, COURSE, conditionColumns, conditionValues);
    }

}

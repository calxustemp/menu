package uk.co.adamsantiago.menu;

import uk.co.adamsantiago.common.services.DBConnection;

import uk.co.adamsantiago.menu.services.CourseService;
import uk.co.adamsantiago.menu.services.DishService;
import uk.co.adamsantiago.menu.services.OrderService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.log4j.Logger;

import spark.Request;
import spark.Response;
import static spark.Spark.path;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;


public class Routes {

    final static Logger logger = Logger.getLogger(Routes.class);

    public static void main(String[] args) {

        path("/menu", () -> {

            get("/course", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject getData = parseQueryParams(req);
                String courseJson = CourseService.getCourse(connection, getData);
                connection.close();
                res.status(200);
                return courseJson;
            });
            post("/course", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject postData = parseJson(req);
                CourseService.createCourse(connection, postData);
                connection.close();
                res.status(200);
                return "";
            });
            put("/course", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject putData = parseJson(req);
                CourseService.updateCourse(connection, putData);
                connection.close();
                res.status(200);
                return "";
            });
            delete("/course", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject deleteData = parseJson(req);
                CourseService.deleteCourse(connection, deleteData);
                connection.close();
                res.status(200);
                return "";
            });

            get("/dish", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject getData = parseQueryParams(req);
                String dishJson = DishService.getDish(connection, getData);
                connection.close();
                res.status(200);
                return dishJson;
            });
            post("/dish", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject postData = parseJson(req);
                DishService.createDish(connection, postData);
                connection.close();
                res.status(200);
                return "";
            });
            put("/dish", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject putData = parseJson(req);
                DishService.updateDish(connection, putData);
                connection.close();
                res.status(200);
                return "";
            });
            delete("/dish", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject deleteData = parseJson(req);
                DishService.deleteDish(connection, deleteData);
                connection.close();
                res.status(200);
                return "";
            });

            get("/order", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject getData = parseQueryParams(req);
                String orderJson = OrderService.getOrder(connection, getData);
                connection.close();
                res.status(200);
                return orderJson;
            });
            post("/order", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject postData = parseJson(req);
                OrderService.createOrder(connection, postData);
                connection.close();
                res.status(200);
                return "";
            });
            put("/order", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject putData = parseJson(req);
                OrderService.updateOrder(connection, putData);
                connection.close();
                res.status(200);
                return "";
            });
            delete("/order", (req, res) -> {
                DBConnection connection = new DBConnection();
                JSONObject deleteData = parseJson(req);
                OrderService.deleteOrder(connection, deleteData);
                connection.close();
                res.status(200);
                return "";
            });

        });

    }

    private static JSONObject parseJson(Request req) {
        JSONParser parser = new JSONParser();
        String body = req.body();
        try {
            return (JSONObject)parser.parse(body);
        } catch(ParseException pe) {
            logger.error("Failed to parse data");
            return null;
        }
    }

    private static JSONObject parseQueryParams(Request req) {
        JSONObject jsonObject = new JSONObject();
        for (String queryParam : req.queryParams()) {
            jsonObject.put(queryParam, req.queryParams(queryParam));
        }
        return jsonObject;
    }

}

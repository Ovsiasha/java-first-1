package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw  new RuntimeException(e);
        }
    }

    private ConnectionManager(){}

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getProperties(URL_KEY),
                    PropertiesUtil.getProperties(USERNAME_KEY),
                    PropertiesUtil.getProperties(PASSWORD_KEY));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

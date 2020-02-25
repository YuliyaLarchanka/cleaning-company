package by.larchanka.tiptopcleaning.connection;

import java.util.ResourceBundle;

import static by.larchanka.tiptopcleaning.connection.ConnectionConstant.BUNDLE_NAME;
import static by.larchanka.tiptopcleaning.connection.ConnectionConstant.CONNECTION_COUNT;
import static by.larchanka.tiptopcleaning.connection.ConnectionConstant.DB_LOGIN;
import static by.larchanka.tiptopcleaning.connection.ConnectionConstant.DB_PASSWORD;
import static by.larchanka.tiptopcleaning.connection.ConnectionConstant.DRIVER;
import static by.larchanka.tiptopcleaning.connection.ConnectionConstant.JDBC_URL;

public class ConnectionParameter {
    private static final ConnectionParameter INSTANCE = new ConnectionParameter();

    public static ConnectionParameter getInstance() {
        return INSTANCE;
    }

    private ConnectionParameter() {
    }

    private static final String DB_PROPERTIES_FILE = BUNDLE_NAME;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES_FILE);

    static String getDriver() {
        return resourceBundle.getString(DRIVER);
    }

    static String getURL() {
        return resourceBundle.getString(JDBC_URL);
    }

    static String getDBLogin() {
        return resourceBundle.getString(DB_LOGIN);
    }

    static String getDBPassword() {
        return resourceBundle.getString(DB_PASSWORD);
    }

    static String getPollSize() {
        return resourceBundle.getString(CONNECTION_COUNT);
    }
}

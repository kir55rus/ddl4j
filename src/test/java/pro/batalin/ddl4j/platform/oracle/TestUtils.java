package pro.batalin.ddl4j.platform.oracle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by ilya on 08.05.17.
 */
public class TestUtils {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(TestUtils.class.getResourceAsStream("/oracle/dbConnectionInfo"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        String host = properties.getProperty("hostname");
        String port = properties.getProperty("port");
        String sid = properties.getProperty("sid");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;

        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(url, username, password);
    }
}

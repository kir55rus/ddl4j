package pro.batalin.ddl4j.platform;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public class OraclePlatformTests {
    private Properties properties;

    @Before
    public void loadProperties() throws IOException {
        properties = new Properties();
        properties.load(getClass().getResourceAsStream("/dbConnectionInfo"));
    }

    @Test
    public void connectionTest() throws SQLException, ClassNotFoundException {
        String host = properties.getProperty("hostname");
        String port = properties.getProperty("port");
        String sid = properties.getProperty("sid");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.close();
    }
}

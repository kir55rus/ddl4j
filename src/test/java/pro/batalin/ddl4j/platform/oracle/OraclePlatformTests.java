package pro.batalin.ddl4j.platform.oracle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pro.batalin.ddl4j.platforms.Platform;
import pro.batalin.ddl4j.platforms.PlatformFactory;
import pro.batalin.ddl4j.platforms.oracle.OraclePlatform;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
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
        Connection connection = createConnection();
        connection.close();
    }

    private Connection createConnection() throws SQLException, ClassNotFoundException {
        String host = properties.getProperty("hostname");
        String port = properties.getProperty("port");
        String sid = properties.getProperty("sid");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;

        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(url, username, password);
    }

    @Test
    public void factoryTest() throws Exception {
        try (Connection connection = createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            Assert.assertEquals("Platform type", platform.getClass(), OraclePlatform.class);
        }
    }

    @Test
    public void queryTest() throws Exception {
        try (Connection connection = createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            String query = "SELECT TO_CHAR(SYSDATE, 'YYYY') as NOW FROM DUAL";
            ResultSet resultSet = platform.executeQuery(query);

            Assert.assertTrue("Year selecting", resultSet.next());

            int dbYear = Integer.valueOf(resultSet.getString("NOW"));
            Assert.assertEquals("Year selecting equals", Year.now().getValue(), dbYear);
        }
    }
}

package pro.batalin.ddl4j.platform.oracle;

import org.junit.Assert;
import org.junit.Test;
import pro.batalin.ddl4j.model.constraints.Check;
import pro.batalin.ddl4j.model.constraints.ForeignKey;
import pro.batalin.ddl4j.model.constraints.PrimaryKey;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.constraints.Unique;
import pro.batalin.ddl4j.platforms.Platform;
import pro.batalin.ddl4j.platforms.PlatformFactory;
import pro.batalin.ddl4j.platforms.oracle.OraclePlatform;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public class OraclePlatformTests {

    @Test
    public void connectionTest() throws SQLException, ClassNotFoundException {
        Connection connection = TestUtils.createConnection();
        connection.close();
    }


    @Test
    public void factoryTest() throws Exception {
        try (Connection connection = TestUtils.createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            Assert.assertEquals("Platform type", platform.getClass(), OraclePlatform.class);
        }
    }

    @Test
    public void queryTest() throws Exception {
        try (Connection connection = TestUtils.createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            String query = "SELECT TO_CHAR(SYSDATE, 'YYYY') as NOW FROM DUAL";
            ResultSet resultSet = platform.executeQuery(query);

            Assert.assertTrue("Year selecting", resultSet.next());

            int dbYear = Integer.valueOf(resultSet.getString("NOW"));
            Assert.assertEquals("Year selecting equals", Year.now().getValue(), dbYear);
        }
    }

    @Test
    public void loadTableTest() throws Exception {
        try (Connection connection = TestUtils.createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            Table table = platform.loadTable("TEST_TABLE");
        }
    }

    @Test
    public void loadTablesTest() throws Exception {
        try (Connection connection = TestUtils.createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            List<String> tables = platform.loadTables("SYSTEM");
            Assert.assertFalse("Load system tables", tables.isEmpty());
        }
    }

    @Test
    public void loadPrimaryKeys() throws Exception {
        try (Connection connection = TestUtils.createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            List<String> primaryKeys = platform.loadPrimaryKeys("TEST_TABLE");
            Assert.assertFalse("Load primary keys", primaryKeys.isEmpty());

            PrimaryKey primaryKey = platform.loadPrimaryKey(primaryKeys.get(0));
            Assert.assertFalse("Load primary key", primaryKey == null);
        }
    }

    @Test
    public void loadUnique() throws Exception {
        try (Connection connection = TestUtils.createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            List<String> uniques = platform.loadUniques("TEST_TABLE");
            Assert.assertFalse("Load uniques", uniques.isEmpty());

            Unique unique = platform.loadUnique(uniques.get(0));
            Assert.assertFalse("Load unique", unique == null);
        }
    }

    @Test
    public void loadForeignKeys() throws Exception {
        try (Connection connection = TestUtils.createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            List<String> foreignKeys = platform.loadForeignKeys("TEST_TABLE");
            Assert.assertFalse("Load foreign keys", foreignKeys.isEmpty());

            ForeignKey foreignKey = platform.loadForeignKey(foreignKeys.get(0));
            Assert.assertFalse("Load foreign key", foreignKey == null);
        }
    }

    @Test
    public void loadChecks() throws Exception {
        try (Connection connection = TestUtils.createConnection()) {
            PlatformFactory factory = new PlatformFactory();
            Platform platform = factory.create("oracle", connection);

            List<String> checks = platform.loadChecks("TEST_TABLE");
            Assert.assertFalse("Load checks", checks.isEmpty());

            Check check = platform.loadCheck(checks.get(0));
            Assert.assertFalse("Load foreign key", check == null);
        }
    }
}

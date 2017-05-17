package pro.batalin.ddl4j.platform.oracle.converters;

import org.junit.Test;
import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AddColumnAlter;
import pro.batalin.ddl4j.platform.oracle.TestUtils;
import pro.batalin.ddl4j.platforms.Platform;
import pro.batalin.ddl4j.platforms.PlatformFactory;

import java.sql.Connection;
import java.sql.JDBCType;

/**
 * Created by ilya on 08.05.17.
 */
public class SQLAddColumnAlterConverterTests {

    @Test
    public void addColumn() throws Exception {
        Connection connection = TestUtils.createConnection();

        PlatformFactory platformFactory = new PlatformFactory();
        Platform oraclePlatform = platformFactory.create("oracle", connection);

        Table testTable = new Table();
        testTable.setName("TEST_TABLE");

        Column testColumn = new Column();
        testColumn.setName("TEST_COLUMN");
        testColumn.setType(JDBCType.INTEGER);

        AddColumnAlter addColumnAlter = new AddColumnAlter(testTable, testColumn);
        oraclePlatform.executeAlter(addColumnAlter);

    }
}

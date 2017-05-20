package pro.batalin.ddl4j.platform.oracle.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.DBType;
import pro.batalin.ddl4j.model.SQLConvertible;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.platform.oracle.TestUtils;
import pro.batalin.ddl4j.platforms.Platform;
import pro.batalin.ddl4j.platforms.PlatformFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.table.SQLColumnConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.table.SQLCreateTableConverter;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGenerator;

import java.sql.Connection;
import java.sql.JDBCType;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
public class SQLTableConverterTests {
    private Platform platform;

    @Before
    public void before() throws Exception {
        Connection connection = TestUtils.createConnection();

        PlatformFactory platformFactory = new PlatformFactory();
        platform = platformFactory.create("oracle", connection);
    }

    @Test
    public void factoryTest() throws Exception {
        SQLConvertible table = new Table();

        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter converter = factory.create(table);

        Assert.assertEquals("SQLConverter type", SQLCreateTableConverter.class, converter.getClass());
    }

    @Test
    public void createDropTableTest() throws Exception {
        Table table = new Table();
        table.setName("testTable");

        Column  column = new Column();
        column.setName("column2");
        column.setType(new DBType("INTEGER"));
        column.setDefaultValue("50");
        table.addColumn(column);

        platform.createTable(table);
        platform.dropTable(table);
    }
}

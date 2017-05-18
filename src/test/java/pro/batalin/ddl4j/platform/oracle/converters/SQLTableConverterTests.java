package pro.batalin.ddl4j.platform.oracle.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pro.batalin.ddl4j.model.Column;
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
    public void columnSQLTest() throws Exception {
        Column column = new Column();
        column.setName("testName");
        column.setType(JDBCType.INTEGER);

        SQLConverter sqlConverter = new SQLColumnConverter(column);
        String test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("column name + type", "testName INTEGER", test.trim());

        column.setSize(2);
        sqlConverter = new SQLColumnConverter(column);
        test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("column name + type + size", "testName INTEGER(2)", test.trim());

        column.setDefaultValue("100");
        sqlConverter = new SQLColumnConverter(column);
        test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("column name + type + size + default", "testName INTEGER(2) DEFAULT 100", test.trim());

        column.setSize(null);
        sqlConverter = new SQLColumnConverter(column);
        test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("column name + type + default", "testName INTEGER DEFAULT 100", test.trim());
    }

    @Test
    public void tableSQLTest() throws Exception {
        SQLConverterFactory factory = new SQLConverterFactory();

        Table table = new Table();
        table.setName("testTable");

        SQLConverter sqlConverter = factory.create(table);
        String test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("table name", "CREATE TABLE testTable ()", test.trim());

        Column column = new Column();
        column.setName("column1");
        column.setType(JDBCType.VARCHAR);
        column.setSize(10);
        table.addColumn(column);

        sqlConverter = factory.create(table);
        test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("table name + column", "CREATE TABLE testTable (column1 VARCHAR(10))", test.trim());

        column = new Column();
        column.setName("column2");
        column.setType(JDBCType.INTEGER);
        column.setDefaultValue("50");
        table.addColumn(column);

        sqlConverter = factory.create(table);
        test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("table name + column", "CREATE TABLE testTable (column1 VARCHAR(10), column2 INTEGER DEFAULT 50)", test.trim());
    }

    @Test
    public void createTableTest() throws Exception {
        Table table = new Table();
        table.setName("testTable");

        Column  column = new Column();
        column.setName("column2");
        column.setType(JDBCType.INTEGER);
        column.setDefaultValue("50");
        table.addColumn(column);

        platform.createTable(table);
    }
}

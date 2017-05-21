package pro.batalin.ddl4j.platform.statement_generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.DBType;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.column.AddColumnAlter;
import pro.batalin.ddl4j.model.alters.column.DropColumnAlter;
import pro.batalin.ddl4j.model.alters.column.ModifyColumnAlter;
import pro.batalin.ddl4j.model.alters.column.RenameColumnAlter;
import pro.batalin.ddl4j.model.alters.constraint.AddConstraintCheckAlter;
import pro.batalin.ddl4j.model.alters.constraint.AddConstraintForeignKeyAlter;
import pro.batalin.ddl4j.model.alters.constraint.AddConstraintPrimaryAlter;
import pro.batalin.ddl4j.model.alters.constraint.AddConstraintUniqueAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.table.SQLColumnConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.table.SQLDropTableConverter;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGenerator;

import java.sql.JDBCType;
import java.util.Arrays;

/**
 * Created by ilya on 11.05.17.
 */
public class StatementGeneratorTests {

    private Table table;
    private Column column;

    @Before
    public void before() {
        table = new Table();
        table.setName("TEST_TABLE");

        column = new Column();
        column.setName("TEST_COLUMN_1");
        column.setType(new DBType("INTEGER"));
    }

    @Test
    public void addAlterStatementTest() throws Exception {

        AddColumnAlter addColumnAlter = new AddColumnAlter(table, column);

        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter addAlterConverter = factory.create(addColumnAlter);

        String query = StatementGenerator.generate(addAlterConverter);
        Assert.assertEquals("Generating add alter statement",
                "ALTER TABLE TEST_TABLE ADD (TEST_COLUMN_1 INTEGER)",
                query);

    }

    @Test
    public void dropAlterStatementTest() throws Exception {
        DropColumnAlter dropColumnAlter = new DropColumnAlter(table, column);

        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter dropAlterConverter = factory.create(dropColumnAlter);

        String query = StatementGenerator.generate(dropAlterConverter);
        Assert.assertEquals("Generating drop alter statement",
                "ALTER TABLE TEST_TABLE DROP COLUMN TEST_COLUMN_1",
                query);
    }

    @Test
    public void modifyAlterStatementTest() throws Exception {
        Column newColumn = column.clone();
        newColumn.setType(new DBType("BOOLEAN"));
        newColumn.setDefaultValue("FALSE");
        ModifyColumnAlter modifyColumnAlter = new ModifyColumnAlter(table, column, newColumn);

        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter modifyAlterConverter = factory.create(modifyColumnAlter);

        String query = StatementGenerator.generate(modifyAlterConverter);
        Assert.assertEquals("Generating modify alter statement",
                "ALTER TABLE TEST_TABLE MODIFY TEST_COLUMN_1 BOOLEAN DEFAULT FALSE",
                query);
    }

    @Test
    public void renameAlterStatementTest() throws Exception {
        Column newColumn = column.clone();
        newColumn.setName("NEW_COLUMN");

        RenameColumnAlter renameColumnAlter = new RenameColumnAlter(table, column, newColumn);
        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter renameAlterConverter = factory.create(renameColumnAlter);

        String query = StatementGenerator.generate(renameAlterConverter);
        Assert.assertEquals("Generating rename alter statement",
                "ALTER TABLE TEST_TABLE RENAME COLUMN TEST_COLUMN_1 TO NEW_COLUMN",
                query);

    }

    @Test
    public void addUniqueConstraintStatementTest() throws Exception {
        Column col1 = column.clone();
        col1.setName("col1");
        Column col2 = column.clone();
        col2.setName("col2");
        Column col3 = column.clone();
        col3.setName("col3");

        AddConstraintUniqueAlter addConstraintUniqueAlter =
                new AddConstraintUniqueAlter(table, "unique_constraint", Arrays.asList(col1, col2, col3));
        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter addConstraintUniqueAlterConverter = factory.create(addConstraintUniqueAlter);

        String query = StatementGenerator.generate(addConstraintUniqueAlterConverter);
        Assert.assertEquals("Generating add unique constraint alter statement",
                "ALTER TABLE TEST_TABLE ADD CONSTRAINT unique_constraint UNIQUE ( col1, col2, col3 )",
                query);

    }

    @Test
    public void addPrimaryConstraintStatementTest() throws Exception {
        Column col1 = column.clone();
        col1.setName("col1");
        Column col2 = column.clone();
        col2.setName("col2");
        Column col3 = column.clone();
        col3.setName("col3");

        AddConstraintPrimaryAlter addConstraintPrimaryAlter =
                new AddConstraintPrimaryAlter(table, "primary_constraint", Arrays.asList(col1, col2, col3));
        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter addConstraintPrimaryAlterConverter = factory.create(addConstraintPrimaryAlter);

        String query = StatementGenerator.generate(addConstraintPrimaryAlterConverter);
        Assert.assertEquals("Generating add primary constraint alter statement",
                "ALTER TABLE TEST_TABLE ADD CONSTRAINT primary_constraint PRIMARY KEY ( col1, col2, col3 )",
                query);
    }

    @Test
    public void columnSQLTest() throws Exception {
        Column column = new Column();
        column.setName("testName");
        column.setType(new DBType("INTEGER"));

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
        column.setType(new DBType("VARCHAR"));
        column.setSize(10);
        table.addColumn(column);

        sqlConverter = factory.create(table);
        test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("table name + column", "CREATE TABLE testTable (column1 VARCHAR(10))", test.trim());

        column = new Column();
        column.setName("column2");
        column.setType(new DBType("INTEGER"));
        column.setDefaultValue("50");
        table.addColumn(column);

        sqlConverter = factory.create(table);
        test = StatementGenerator.generate(sqlConverter);
        Assert.assertEquals("table name + column", "CREATE TABLE testTable (column1 VARCHAR(10), column2 INTEGER DEFAULT 50)", test.trim());
    }

    @Test
    public void dropTableTest() throws Exception {
        Table table = new Table();
        table.setName("testTable");
        SQLConverter converter = new SQLDropTableConverter(table);
        String test = StatementGenerator.generate(converter);
        Assert.assertEquals("drop table test", "DROP TABLE testTable", test.trim());

        table = new Table();
        table.setName("testTable");
        converter = new SQLDropTableConverter(table);
        test = StatementGenerator.generate(converter);
        Assert.assertEquals("drop table test", "DROP TABLE testTable", test.trim());

    }

    @Test
    public void foreignKeyTest() throws Exception {
        Table refTable = new Table();
        refTable.setName("REF_TABLE");

        Column refColumn = column.clone();
        refColumn.setName("ref_column");

        AddConstraintForeignKeyAlter alter = new AddConstraintForeignKeyAlter(table, column, refTable, refColumn, "fk");
        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter alterConverter = factory.create(alter);

        String query = StatementGenerator.generate(alterConverter);
        Assert.assertEquals("Generating add foreign key constraint alter statement",
                "ALTER TABLE TEST_TABLE ADD CONSTRAINT fk FOREIGN KEY ( TEST_COLUMN_1 ) REFERENCES REF_TABLE ( ref_column )",
                query);
    }

    @Test
    public void checkTest() throws Exception {
        AddConstraintCheckAlter alter = new AddConstraintCheckAlter(table, "TEST_COLUMN > 0", "ch");

        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter alterConverter = factory.create(alter);

        String query = StatementGenerator.generate(alterConverter);
        Assert.assertEquals("Generating add check constraint alter statement",
                "ALTER TABLE TEST_TABLE ADD CONSTRAINT ch CHECK ( TEST_COLUMN > 0 )",
                query);
    }

}

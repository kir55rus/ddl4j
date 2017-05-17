package pro.batalin.ddl4j.platform.statement_generator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AddColumnAlter;
import pro.batalin.ddl4j.model.alters.DropColumnAlter;
import pro.batalin.ddl4j.model.alters.ModifyColumnAlter;
import pro.batalin.ddl4j.model.alters.RenameColumnAlter;
import pro.batalin.ddl4j.platform.oracle.TestUtils;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGenerator;

import java.sql.Connection;
import java.sql.JDBCType;

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
        column.setType(JDBCType.INTEGER);
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
        newColumn.setType(JDBCType.BOOLEAN);
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
}

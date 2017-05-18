package pro.batalin.ddl4j.platform.oracle.converters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AddColumnAlter;
import pro.batalin.ddl4j.model.alters.DropColumnAlter;
import pro.batalin.ddl4j.model.alters.ModifyColumnAlter;
import pro.batalin.ddl4j.model.alters.RenameColumnAlter;
import pro.batalin.ddl4j.platform.oracle.TestUtils;
import pro.batalin.ddl4j.platforms.Platform;
import pro.batalin.ddl4j.platforms.PlatformFactory;

import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.JDBCType;

/**
 * Created by ilya on 08.05.17.
 */
public class SQLColumnAlterConverterTests {

    private Connection connection;
    private Platform platform;
    private Table testTable;
    private Column testColumn;

    @Before
    public void before() throws Exception {
        connection = TestUtils.createConnection();

        PlatformFactory platformFactory = new PlatformFactory();
        platform = platformFactory.create("oracle", connection);

        testTable = new Table();
        testTable.setName("TEST_TABLE");

        testColumn = new Column();
        testColumn.setName("TEST_COLUMN");
        testColumn.setType(JDBCType.INTEGER);
    }

    @Test
    public void addColumn() throws Exception {
        AddColumnAlter addColumnAlter = new AddColumnAlter(testTable, testColumn);
        platform.executeAlter(addColumnAlter);
    }

    @Test
    public void modifyColumn() throws Exception {
        Column newColumn = testColumn.clone();
        newColumn.setType(JDBCType.DECIMAL);
        newColumn.setDefaultValue("0");
        ModifyColumnAlter modifyColumnAlter = new ModifyColumnAlter(testTable, testColumn, newColumn);
        platform.executeAlter(modifyColumnAlter);
    }

    @Test
    public void dropColumn() throws Exception {
        DropColumnAlter dropColumnAlter = new DropColumnAlter(testTable, testColumn);
        platform.executeAlter(dropColumnAlter);
    }

    @Test
    public void renameColumn() throws Exception {
        Column newColumn = testColumn.clone();
        newColumn.setName("NEW_COLUMN");
        RenameColumnAlter renameColumnAlter =  new RenameColumnAlter(testTable, testColumn, newColumn);
        platform.executeAlter(renameColumnAlter);
    }
}

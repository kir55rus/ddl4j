package pro.batalin.ddl4j.platform.oracle.converters.constraint;

import org.junit.Before;
import org.junit.Test;
import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.DBType;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.column.AddColumnAlter;
import pro.batalin.ddl4j.model.alters.constraint.AddConstraintForeignKeyAlter;
import pro.batalin.ddl4j.model.alters.constraint.AddConstraintPrimaryAlter;
import pro.batalin.ddl4j.model.alters.constraint.AddConstraintUniqueAlter;
import pro.batalin.ddl4j.model.alters.constraint.DropConstraintAlter;
import pro.batalin.ddl4j.platform.oracle.TestUtils;
import pro.batalin.ddl4j.platforms.Platform;
import pro.batalin.ddl4j.platforms.PlatformFactory;

import java.sql.Connection;
import java.sql.JDBCType;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ilya on 18.05.17.
 */
public class SQLConstraintAlterConverterTests {
    private Connection connection;
    private Platform platform;
    private Table testTable;
    private List<Column> testColumns;

    @Before
    public void before() throws Exception {
        connection = TestUtils.createConnection();

        PlatformFactory platformFactory = new PlatformFactory();
        platform = platformFactory.create("oracle", connection);

        testTable = new Table();
        testTable.setName("TEST_TABLE");

        Column col1 = new Column();
        col1.setName("col1");
        col1.setType(new DBType("INTEGER"));
        Column col2 = new Column();
        col2.setName("col2");
        col2.setType(new DBType("INTEGER"));
        Column col3 = new Column();
        col3.setName("col3");
        col3.setType(new DBType("INTEGER"));

        testColumns = Arrays.asList(col1, col2, col3);

        AddColumnAlter addColumnAlter = new AddColumnAlter(testTable, col1);
        platform.executeAlter(addColumnAlter);

        addColumnAlter = new AddColumnAlter(testTable, col2);
        platform.executeAlter(addColumnAlter);

        addColumnAlter = new AddColumnAlter(testTable, col3);
        platform.executeAlter(addColumnAlter);
    }

    @Test
    public void addUniqueConstraint() throws Exception {
        AddConstraintUniqueAlter constraintUniqueAlter =
                new AddConstraintUniqueAlter(testTable, "unique_constraint", testColumns);
        platform.executeAlter(constraintUniqueAlter);
    }

    @Test
    public void addPrimaryConstraint() throws Exception {
        AddConstraintPrimaryAlter constraintPrimaryAlter =
                new AddConstraintPrimaryAlter(testTable, "primary_constraint", testColumns);
        platform.executeAlter(constraintPrimaryAlter);
    }

    @Test
    public void addForeignKeyConstraint() throws Exception {
        Table refTable = new Table();
        refTable.setName("REF_TABLE");

        Column refColumn = new Column();
        refColumn.setName("REF_COLUMN");
        refColumn.setType(new DBType("NUMBER"));

        AddConstraintForeignKeyAlter alter =
                new AddConstraintForeignKeyAlter(testTable, testColumns.get(0),refTable,refColumn,"fk");
        platform.executeAlter(alter);
    }

    @Test
    public void dropConstraintTest() throws Exception {
        AddConstraintPrimaryAlter constraintPrimaryAlter =
                new AddConstraintPrimaryAlter(testTable, "primary_constraint", testColumns);
        platform.executeAlter(constraintPrimaryAlter);

        DropConstraintAlter dropConstraintAlter = new DropConstraintAlter(testTable, "primary_constraint");
        platform.executeAlter(dropConstraintAlter);
    }

//    @After
//    public void after() throws Exception {
//        for (Column c : testColumns) {
//            DropColumnAlter dropColumnAlter = new DropColumnAlter(testTable, c);
//            platform.executeAlter(dropColumnAlter);
//        }
//    }
}

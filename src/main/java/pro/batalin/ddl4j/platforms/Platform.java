package pro.batalin.ddl4j.platforms;

import pro.batalin.ddl4j.DatabaseOperationException;
import pro.batalin.ddl4j.model.Schema;
import pro.batalin.ddl4j.model.constraints.Check;
import pro.batalin.ddl4j.model.constraints.ForeignKey;
import pro.batalin.ddl4j.model.constraints.PrimaryKey;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.Alter;
import pro.batalin.ddl4j.model.constraints.Unique;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public interface Platform {
    Connection getConnection();

    ResultSet executeQuery(String sql) throws DatabaseOperationException;

    void createTable(Table table) throws DatabaseOperationException;

    void dropTable(Table table) throws DatabaseOperationException;

    void executeAlter(Alter alter) throws DatabaseOperationException;

    List<Schema> loadSchemas() throws DatabaseOperationException;

    Table loadTable(String name) throws DatabaseOperationException;

    Table loadTable(Schema schema, String name) throws DatabaseOperationException;

    List<String> loadTables(String owner) throws DatabaseOperationException;

    List<String> loadPrimaryKeys(Table table) throws DatabaseOperationException;

    List<String> loadPrimaryKeys(String table) throws DatabaseOperationException;

    List<String> loadPrimaryKeys(Schema schema, String table) throws DatabaseOperationException;

    PrimaryKey loadPrimaryKey(String name) throws DatabaseOperationException;

    PrimaryKey loadPrimaryKey(Schema schema, String name) throws DatabaseOperationException;

    List<String> loadTableConstraints(Table table) throws DatabaseOperationException;

    List<String> loadTableConstraints(String table) throws DatabaseOperationException;

    List<String> loadUniques(Table table) throws DatabaseOperationException;

    List<String> loadUniques(String table) throws DatabaseOperationException;

    List<String> loadUniques(Schema schema, String table) throws DatabaseOperationException;

    Unique loadUnique(String name) throws DatabaseOperationException;

    Unique loadUnique(Schema schema, String name) throws DatabaseOperationException;

    List<String> loadForeignKeys(Table table) throws DatabaseOperationException;

    List<String> loadForeignKeys(String table) throws DatabaseOperationException;

    List<String> loadForeignKeys(Schema schema, String table) throws DatabaseOperationException;

    ForeignKey loadForeignKey(String name) throws DatabaseOperationException;

    ForeignKey loadForeignKey(Schema schema, String name) throws DatabaseOperationException;

    List<String> loadChecks(Table table) throws DatabaseOperationException;

    List<String> loadChecks(String table) throws DatabaseOperationException;

    List<String> loadChecks(Schema schema, String table) throws DatabaseOperationException;

    Check loadCheck(String name) throws DatabaseOperationException;

    Check loadCheck(Schema schema, String name) throws DatabaseOperationException;
}

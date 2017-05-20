package pro.batalin.ddl4j.platforms;

import pro.batalin.ddl4j.DatabaseOperationException;
import pro.batalin.ddl4j.model.constraints.Constraint;
import pro.batalin.ddl4j.model.constraints.PrimaryKey;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.Alter;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public interface Platform {
    ResultSet executeQuery(String sql) throws DatabaseOperationException;

    void createTable(Table table) throws DatabaseOperationException;

    void dropTable(Table table) throws DatabaseOperationException;

    void dropTable(String table) throws DatabaseOperationException;

    void executeAlter(Alter alter) throws DatabaseOperationException;

    Table loadTable(String name) throws DatabaseOperationException;

    List<String> loadTables(String owner) throws DatabaseOperationException;

    List<String> loadPrimaryKeys(Table table) throws DatabaseOperationException;

    List<String> loadPrimaryKeys(String table) throws DatabaseOperationException;

    PrimaryKey loadPrimaryKey(String name) throws DatabaseOperationException;

    List<String> loadTableConstraints(Table table) throws DatabaseOperationException;

    List<String> loadTableConstraints(String table) throws DatabaseOperationException;

//    Constraint loadConstraint(String name) throws DatabaseOperationException;
}

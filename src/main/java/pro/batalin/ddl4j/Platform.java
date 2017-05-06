package pro.batalin.ddl4j;

import pro.batalin.ddl4j.model.Table;

import java.sql.ResultSet;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public interface Platform {
    ResultSet query(String sql) throws DatabaseOperationException;

    void createTable(Table table) throws DatabaseOperationException;
}

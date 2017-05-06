package pro.batalin.ddl4j.platforms.oracle;

import pro.batalin.ddl4j.DatabaseOperationException;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.platforms.PlatformBaseImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Kirill Batalin (kir55rus) on 07.05.17.
 */
public class OraclePlatform extends PlatformBaseImpl {
    private Connection dbConnection;

    public OraclePlatform(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public ResultSet query(String sql) throws DatabaseOperationException {
        try {
            Statement statement = dbConnection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }

    @Override
    public void createTable(Table table) throws DatabaseOperationException {
        //todo
    }
}

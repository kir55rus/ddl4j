package pro.batalin.ddl4j.platforms.oracle;

import pro.batalin.ddl4j.DatabaseOperationException;
import pro.batalin.ddl4j.model.SQLConvertible;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.platforms.PlatformBaseImpl;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactoryException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Kirill Batalin (kir55rus) on 07.05.17.
 */
public class OraclePlatform extends PlatformBaseImpl {
    private Connection dbConnection;
    private SQLConverterFactory converterFactory;

    public OraclePlatform(Connection dbConnection) throws DatabaseOperationException {
        this.dbConnection = dbConnection;
        try {
            converterFactory = new SQLConverterFactory();
        } catch (SQLConverterFactoryException e) {
            throw new DatabaseOperationException("Converter config file error", e);
        }
    }

    @Override
    public ResultSet executeQuery(String sql) throws DatabaseOperationException {
        try {
            Statement statement = dbConnection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new DatabaseOperationException(e);
        }
    }

    private String convertToSQL(SQLConvertible convertibleObject) throws SQLConverterFactoryException {
        SQLConverter converter = converterFactory.create(convertibleObject);
        return converter.convert();
    }

    @Override
    public void createTable(Table table) throws DatabaseOperationException {

        try {
            convertToSQL(table);
        } catch (SQLConverterFactoryException e) {
            throw new DatabaseOperationException("Can't convert table to sql", e);
            
        }
    }

    @Override
    public Table loadTable(String name) throws DatabaseOperationException {
        return null;
    }
}

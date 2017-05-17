package pro.batalin.ddl4j.platforms.oracle;

import pro.batalin.ddl4j.DatabaseOperationException;
import pro.batalin.ddl4j.model.SQLConvertible;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.Alter;
import pro.batalin.ddl4j.platforms.PlatformBaseImpl;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactoryException;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameterStatement;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGenerator;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGeneratorException;

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
        try {
            SQLConverter converter = converterFactory.create(convertibleObject);
            return StatementGenerator.generate(converter);
        } catch (StatementGeneratorException e) {
            throw new SQLConverterFactoryException(e);
        }
    }

    @Override
    public void createTable(Table table) throws DatabaseOperationException {
        try {
            String tableSQL = convertToSQL(table);
            executeQuery(tableSQL);
        } catch (SQLConverterFactoryException e) {
            throw new DatabaseOperationException("Can't convert table to sql", e);
        }
    }

    @Override
    public void executeAlter(Alter alter) throws DatabaseOperationException {
        try {
            String alterSQL = convertToSQL(alter);
            executeQuery(alterSQL);
        } catch (SQLConverterFactoryException e) {
            throw new DatabaseOperationException("Can't convert alter to sql", e);
        }
    }

    @Override
    public Table loadTable(String name) throws DatabaseOperationException {
        return null;
    }
}

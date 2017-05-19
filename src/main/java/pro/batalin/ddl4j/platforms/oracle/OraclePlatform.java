package pro.batalin.ddl4j.platforms.oracle;

import pro.batalin.ddl4j.DatabaseOperationException;
import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.SQLConvertible;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.Alter;
import pro.batalin.ddl4j.platforms.PlatformBaseImpl;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactoryException;
import pro.batalin.ddl4j.platforms.oracle.converters.table.SQLDropTableConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameterStatement;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGenerator;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGeneratorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public void dropTable(Table table) throws DatabaseOperationException {
        dropTable(table.getName());
    }

    @Override
    public void dropTable(String table) throws DatabaseOperationException {
        try {
            SQLConverter sqlConverter = new SQLDropTableConverter(table);
            String sql = StatementGenerator.generate(sqlConverter);
            executeQuery(sql);
        } catch (StatementGeneratorException e) {
            throw new DatabaseOperationException("Can't convert dropping to sql", e);
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
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM ALL_TAB_COLUMNS WHERE TABLE_NAME=?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet == null || !resultSet.next()) {
                return null;
            }

            Table table = new Table();
            table.setName(name);

            do {
                Column column = new Column();
                column.setName(resultSet.getString("COLUMN_NAME"));
                column.setType(JDBCType.valueOf(resultSet.getString("DATA_TYPE")));
                column.setSize(Integer.valueOf(resultSet.getString("DATA_LENGTH")));
                column.setDefaultValue(resultSet.getString("DATA_DEFAULT"));
                table.addColumn(column);

            } while (resultSet.next());


            return table;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get table info", e);
        }
    }

    @Override
    public List<String> loadTables(String owner) throws DatabaseOperationException {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM ALL_TABLES WHERE OWNER=?");
            statement.setString(1, owner);
            ResultSet resultSet = statement.executeQuery();

            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString("TABLE_NAME"));
            }

            return tables;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get tables info", e);
        }
    }
}

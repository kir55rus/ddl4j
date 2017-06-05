package pro.batalin.ddl4j.platforms.oracle;

import pro.batalin.ddl4j.DatabaseOperationException;
import pro.batalin.ddl4j.model.*;
import pro.batalin.ddl4j.model.alters.Alter;
import pro.batalin.ddl4j.model.constraints.Check;
import pro.batalin.ddl4j.model.constraints.ForeignKey;
import pro.batalin.ddl4j.model.constraints.PrimaryKey;
import pro.batalin.ddl4j.model.constraints.Unique;
import pro.batalin.ddl4j.platforms.PlatformBaseImpl;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactoryException;
import pro.batalin.ddl4j.platforms.oracle.converters.table.SQLDropTableConverter;
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
    public Connection getConnection() {
        return dbConnection;
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
        return convertToSQL(convertibleObject, "CREATE");
    }


    private String convertToSQL(SQLConvertible convertibleObject, String action) throws SQLConverterFactoryException {
        try {
            SQLConverter converter = converterFactory.create(convertibleObject, action);
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
        try {
            String tableSQL = convertToSQL(table, "DROP");
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
    public List<Schema> loadSchemas() throws DatabaseOperationException {
        try {
            PreparedStatement statement = dbConnection.prepareStatement("SELECT * FROM ALL_USERS");
            ResultSet resultSet = statement.executeQuery();

            List<Schema> schemas = new ArrayList<>();
            while (resultSet.next()) {
                schemas.add(new Schema(resultSet.getString("USERNAME")));
            }

            return schemas;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get tables info", e);
        }
    }

    @Override
    public Table loadTable(Schema schema, String name) throws DatabaseOperationException {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM ALL_TAB_COLUMNS WHERE TABLE_NAME=?");
            if (schema != null) {
                sql.append(" AND OWNER=?");
            }

            PreparedStatement statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, name);
            if (schema != null) {
                statement.setString(2, schema.getName());
            }
            ResultSet resultSet = statement.executeQuery();

            if (resultSet == null || !resultSet.next()) {
                return null;
            }

            Table table = new Table();
            table.setName(name);

            table.setSchema(new Schema(resultSet.getString("OWNER")));

            do {
                Column column = new Column();
                column.setName(resultSet.getString("COLUMN_NAME"));
                column.setType(new DBType(resultSet.getString("DATA_TYPE")));
                column.setSize(Integer.valueOf(resultSet.getString("DATA_LENGTH")));
                column.setDefaultValue(resultSet.getString("DATA_DEFAULT"));
                column.setRequired("N".equals(resultSet.getString("NULLABLE")));
                table.addColumn(column);

            } while (resultSet.next());


            return table;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get table info", e);
        }
    }

    @Override
    public Table loadTable(String name) throws DatabaseOperationException {
        return loadTable(null, name);
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

    @Override
    public List<String> loadPrimaryKeys(Table table) throws DatabaseOperationException {
        return loadPrimaryKeys(table.getSchema(), table.getName());
    }

    @Override
    public List<String> loadPrimaryKeys(String table) throws DatabaseOperationException {
        return loadPrimaryKeys(null, table);
    }

    @Override
    public List<String> loadPrimaryKeys(Schema schema, String table) throws DatabaseOperationException {
        return loadTableConstraints(schema, table, "P");
    }

    @Override
    public PrimaryKey loadPrimaryKey(Schema schema, String name) throws DatabaseOperationException {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM SYS.ALL_CONS_COLUMNS WHERE CONSTRAINT_NAME=?");
            if (schema != null) {
                sql.append(" AND OWNER=?");
            }

            PreparedStatement statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, name);
            if (schema != null) {
                statement.setString(2, schema.getName());
            }
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Table table = loadTable(resultSet.getString("TABLE_NAME"));
            if (table == null) {
                return null;
            }

            List<Column> columns = new ArrayList<>();

            do {
                Column column = table.getColumn(resultSet.getString("COLUMN_NAME"));
                columns.add(column);
            } while (resultSet.next());

            PrimaryKey primaryKey = new PrimaryKey(name);
            primaryKey.setTable(table);
            primaryKey.setColumns(columns);

            return primaryKey;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get primary key", e);
        }
    }

    @Override
    public PrimaryKey loadPrimaryKey(String name) throws DatabaseOperationException {
        return loadPrimaryKey(null, name);
    }

    private List<String> loadTableConstraints(String table, String type) throws DatabaseOperationException {
        return loadTableConstraints(null, table, type);
    }

    private List<String> loadTableConstraints(Schema schema, String table, String type) throws DatabaseOperationException {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM SYS.ALL_CONSTRAINTS WHERE TABLE_NAME=?");
            if (type != null) {
                sql.append(" AND CONSTRAINT_TYPE=?");
            }
            if (schema != null) {
                sql.append(" AND OWNER=?");
            }

            PreparedStatement statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, table);
            if(type != null) {
                statement.setString(2, type);
            }
            if (schema != null) {
                statement.setString(3, schema.getName());
            }
            ResultSet resultSet = statement.executeQuery();

            List<String> constraints = new ArrayList<>();
            while (resultSet.next()) {
                constraints.add(resultSet.getString("CONSTRAINT_NAME"));
            }

            return constraints;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get constraints", e);
        }
    }

    @Override
    public List<String> loadTableConstraints(Table table) throws DatabaseOperationException {
        return loadTableConstraints(table.getSchema(), table.getName(), null);
    }

    @Override
    public List<String> loadTableConstraints(String table) throws DatabaseOperationException {
        return loadTableConstraints(table, null);
    }

    @Override
    public List<String> loadUniques(Table table) throws DatabaseOperationException {
        return loadUniques(table.getSchema(), table.getName());
    }

    @Override
    public List<String> loadUniques(Schema schema, String table) throws DatabaseOperationException {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM SYS.ALL_INDEXES WHERE TABLE_NAME=?");
            if (schema != null) {
                sql.append(" AND OWNER=?");
            }

            PreparedStatement statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, table);
            if (schema != null) {
                statement.setString(2, schema.getName());
            }

            ResultSet resultSet = statement.executeQuery();

            List<String> uniques = new ArrayList<>();
            while (resultSet.next()) {
                uniques.add(resultSet.getString("INDEX_NAME"));
            }

            return uniques;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get indexes", e);
        }
    }

    @Override
    public List<String> loadUniques(String table) throws DatabaseOperationException {
        return loadUniques(null, table);
    }

    @Override
    public Unique loadUnique(Schema schema, String name) throws DatabaseOperationException {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM SYS.ALL_IND_COLUMNS WHERE INDEX_NAME=?");
            if (schema != null) {
                sql.append(" AND TABLE_OWNER=?");
            }

            PreparedStatement statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, name);
            if (schema != null) {
                statement.setString(2, schema.getName());
            }
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Table table = loadTable(resultSet.getString("TABLE_NAME"));
            if (table == null) {
                return null;
            }

            Column column = table.getColumn(resultSet.getString("COLUMN_NAME"));

            Unique unique = new Unique(name);
            unique.setTable(table);
            unique.setColumn(column);

            return unique;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get unique", e);
        }
    }

    @Override
    public Unique loadUnique(String name) throws DatabaseOperationException {
        return loadUnique(null, name);
    }

    @Override
    public List<String> loadForeignKeys(Table table) throws DatabaseOperationException {
        return loadForeignKeys(table.getSchema(), table.getName());
    }

    @Override
    public List<String> loadForeignKeys(Schema schema, String table) throws DatabaseOperationException {
        return loadTableConstraints(schema, table, "R");
    }

    @Override
    public List<String> loadForeignKeys(String table) throws DatabaseOperationException {
        return loadTableConstraints(table, "R");
    }

    @Override
    public ForeignKey loadForeignKey(Schema schema, String name) throws DatabaseOperationException {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM SYS.ALL_CONSTRAINTS WHERE CONSTRAINT_NAME=?");
            if (schema != null) {
                sql.append(" AND OWNER=?");
            }

            PreparedStatement statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, name);
            if (schema != null) {
                statement.setString(2, schema.getName());
            }
            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next() || !"R".equals(resultSet.getString("CONSTRAINT_TYPE"))) {
                return null;
            }

            String secondConstraint = resultSet.getString("R_CONSTRAINT_NAME");

            sql = new StringBuilder("SELECT * FROM SYS.ALL_CONS_COLUMNS WHERE CONSTRAINT_NAME=?");
            if (schema != null) {
                sql.append(" AND OWNER=?");
            }

            statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, name);
            if (schema != null) {
                statement.setString(2, schema.getName());
            }
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Table firstTable = loadTable(resultSet.getString("TABLE_NAME"));
            if (firstTable == null) {
                return null;
            }

            Column firstColumn = firstTable.getColumn(resultSet.getString("COLUMN_NAME"));

            statement.setString(1, secondConstraint);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Table secondTable = loadTable(resultSet.getString("TABLE_NAME"));
            if (secondTable == null) {
                return null;
            }

            Column secondColumn = secondTable.getColumn(resultSet.getString("COLUMN_NAME"));

            ForeignKey foreignKey = new ForeignKey(name);
            foreignKey.setFirstTable(firstTable);
            foreignKey.setFirstColumn(firstColumn);
            foreignKey.setSecondTable(secondTable);
            foreignKey.setSecondColumn(secondColumn);

            return foreignKey;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get foreign key", e);
        }
    }

    @Override
    public ForeignKey loadForeignKey(String name) throws DatabaseOperationException {
        return loadForeignKey(null, name);
    }

    @Override
    public List<String> loadChecks(Table table) throws DatabaseOperationException {
        return loadChecks(table.getSchema(), table.getName());
    }

    @Override
    public List<String> loadChecks(Schema schema, String table) throws DatabaseOperationException {
        return loadTableConstraints(schema, table, "C");
    }

    @Override
    public List<String> loadChecks(String table) throws DatabaseOperationException {
        return loadTableConstraints(table, "C");
    }

    @Override
    public Check loadCheck(Schema schema, String name) throws DatabaseOperationException {
        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM SYS.ALL_CONSTRAINTS WHERE CONSTRAINT_NAME=?");
            if (schema != null) {
                sql.append(" AND OWNER=?");
            }

            PreparedStatement statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, name);
            if (schema != null) {
                statement.setString(2, schema.getName());
            }
            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next() || !"C".equals(resultSet.getString("CONSTRAINT_TYPE"))) {
                return null;
            }

            String condition = resultSet.getString("SEARCH_CONDITION");

            sql = new StringBuilder("SELECT * FROM SYS.ALL_CONS_COLUMNS WHERE CONSTRAINT_NAME=?");
            if (schema != null) {
                sql.append(" AND OWNER=?");
            }
            statement = dbConnection.prepareStatement(sql.toString());
            statement.setString(1, name);
            if (schema != null) {
                statement.setString(2, schema.getName());
            }
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Table table = loadTable(resultSet.getString("TABLE_NAME"));
            if (table == null) {
                return null;
            }

            Column column = table.getColumn(resultSet.getString("COLUMN_NAME"));

            Check check = new Check(name);
            check.setTable(table);
            check.setColumn(column);
            check.setCondition(condition);

            return check;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Can't get check", e);
        }
    }

    @Override
    public Check loadCheck(String name) throws DatabaseOperationException {
        return loadCheck(null, name);
    }
}

package pro.batalin.ddl4j.platform.statement_generator;

import oracle.jdbc.internal.OraclePreparedStatement;
import org.junit.Test;
import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AddColumnAlter;
import pro.batalin.ddl4j.platform.oracle.TestUtils;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.alters.SQLAddColumnAlterConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameterStatement;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGenerator;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by ilya on 11.05.17.
 */
public class StatementGeneratorTests {

    @Test
    public void addAlterStatementTest() throws Exception{
        try (Connection connection = TestUtils.createConnection()) {

//            Table table = new Table();
//            table.setName("TEST_TABLE");
//
//            Column c = new Column();
//            c.setName("TEST_COLUMN_1");
//            c.setType(JDBCType.INTEGER);
//
//            AddColumnAlter addColumnAlter = new AddColumnAlter(table, c);
//
//            SQLConverterFactory factory = new SQLConverterFactory();
//            SQLConverter addAlterConverter = factory.create(addColumnAlter);
//
//            NamedParameterStatement namedStatement =
//                    new NamedParameterStatement(connection, addAlterConverter.getTemplate());
//            StatementGenerator.generate(namedStatement, addAlterConverter);

//            ResultSet set = namedStatement.executeQuery();


            PreparedStatement ps = connection.prepareStatement("ALTER TABLE ? ADD ? ?");
            ps.setString(1, "TEST_TABLE");
            ps.setString(2, "TEST_COLUMN_1");
            ps.setString(3, "INTEGER");
            ps.execute();
        }
    }
}

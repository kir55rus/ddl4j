package pro.batalin.ddl4j.platforms.oracle.converters.table;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGenerator;
import pro.batalin.ddl4j.platforms.statement_generator.StatementGeneratorException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
@Converter(modelClass = Table.class)
public class SQLCreateTableConverter implements SQLConverter {
    private final String TEMPLATE = "CREATE TABLE :table (:columns)";
    private Table table;

    public SQLCreateTableConverter(Table table) {
        this.table = table;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return table.getFullName();
    }

    @NamedParameter(name = "columns")
    private String columns() throws StatementGeneratorException {
        List<String> columnsSQL = new ArrayList<>();
        for (Column column : table.getColumns()) {
            SQLConverter columnConverter = new SQLColumnConverter(column);
            columnsSQL.add(StatementGenerator.generate(columnConverter));
        }

        return String.join(", ", columnsSQL);
    }
}

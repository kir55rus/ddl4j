package pro.batalin.ddl4j.platforms.oracle.converters.table;

import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by Kirill Batalin (kir55rus).
 */
@Converter(modelClass = Table.class, action = "DROP")
public class SQLDropTableConverter implements SQLConverter {
    private final String TEMPLATE = "DROP TABLE :table";
    private Table table;

    public SQLDropTableConverter(Table table) {
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
}

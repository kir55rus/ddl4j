package pro.batalin.ddl4j.platforms.oracle.converters.table;

import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class SQLDropTableConverter implements SQLConverter {
    private final String TEMPLATE = "DROP TABLE :table";
    private String table;

    public SQLDropTableConverter(String table) {
        this.table = table;
    }

    public SQLDropTableConverter(Table table) {
        this.table = table.getFullName();
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return table;
    }
}

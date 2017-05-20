package pro.batalin.ddl4j.platforms.oracle.converters.alters.column;

import pro.batalin.ddl4j.model.alters.column.DropColumnAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by ilya on 18.05.17.
 */
@Converter(modelClass = DropColumnAlter.class)
public class SQLDropColumnAlterConverter implements SQLConverter {
    private final String TEMPLATE = "ALTER TABLE :table DROP COLUMN :column";
    private final DropColumnAlter dropColumnAlter;

    public SQLDropColumnAlterConverter(DropColumnAlter dropColumnAlter) {
        this.dropColumnAlter = dropColumnAlter;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return dropColumnAlter.getTable().getName();
    }

    @NamedParameter(name = "column")
    private String column() {
        return dropColumnAlter.getColumn().getName();
    }


}

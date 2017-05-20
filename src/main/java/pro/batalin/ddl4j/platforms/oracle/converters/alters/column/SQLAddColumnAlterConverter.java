package pro.batalin.ddl4j.platforms.oracle.converters.alters.column;

import pro.batalin.ddl4j.model.alters.column.AddColumnAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by ilya on 08.05.17.
 */
@Converter(modelClass = AddColumnAlter.class)
public class SQLAddColumnAlterConverter implements SQLConverter {
    private final String TEMPLATE = "ALTER TABLE :table ADD (:column :type)";
    private AddColumnAlter addColumnAlter;

    public SQLAddColumnAlterConverter(AddColumnAlter addColumnAlter) {
        this.addColumnAlter = addColumnAlter;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return addColumnAlter.getTable().getName();
    }

    @NamedParameter(name = "column")
    private String column() {
        return addColumnAlter.getColumn().getName();
    }

    @NamedParameter(name = "type")
    private String type() {
        return addColumnAlter.getColumn().getType().toString();
    }


}

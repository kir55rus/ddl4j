package pro.batalin.ddl4j.platforms.oracle.converters.alters;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AddColumnAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

import java.sql.Statement;

/**
 * Created by ilya on 08.05.17.
 */
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
        return addColumnAlter.getColumn().getType().getName();
    }


}

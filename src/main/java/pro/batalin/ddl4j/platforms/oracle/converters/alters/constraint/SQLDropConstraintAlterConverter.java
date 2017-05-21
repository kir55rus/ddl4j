package pro.batalin.ddl4j.platforms.oracle.converters.alters.constraint;

import pro.batalin.ddl4j.model.alters.constraint.DropConstraintAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by ilya on 22.05.17.
 */
@Converter(modelClass = DropConstraintAlter.class)
public class SQLDropConstraintAlterConverter implements SQLConverter {
    private final String TEMPLATE = "ALTER TABLE :table DROP CONSTRAINT :name";
    private DropConstraintAlter dropConstraintAlter;

    public SQLDropConstraintAlterConverter(DropConstraintAlter dropConstraintAlter) {
        this.dropConstraintAlter = dropConstraintAlter;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return dropConstraintAlter.getTable().getName();
    }

    @NamedParameter(name = "name")
    private String name() {
        return dropConstraintAlter.getConstraintName();
    }
}

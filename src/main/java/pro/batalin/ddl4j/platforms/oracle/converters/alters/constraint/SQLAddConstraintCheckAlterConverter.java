package pro.batalin.ddl4j.platforms.oracle.converters.alters.constraint;

import pro.batalin.ddl4j.model.alters.constraint.AddConstraintCheckAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by ilya on 21.05.17.
 */
@Converter(modelClass = AddConstraintCheckAlter.class)
public class SQLAddConstraintCheckAlterConverter implements SQLConverter {
    private final String TEMPLATE =
            "ALTER TABLE :table ADD CONSTRAINT :name CHECK ( :condition )";
    private AddConstraintCheckAlter addConstraintCheckAlter;

    public SQLAddConstraintCheckAlterConverter(AddConstraintCheckAlter addConstraintCheckAlter) {
        this.addConstraintCheckAlter = addConstraintCheckAlter;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return addConstraintCheckAlter.getTable().getFullName();
    }

    @NamedParameter(name = "name")
    private String name() {
        return addConstraintCheckAlter.getName();
    }

    @NamedParameter(name = "condition")
    private String condition() {
        return addConstraintCheckAlter.getCondition();
    }
}

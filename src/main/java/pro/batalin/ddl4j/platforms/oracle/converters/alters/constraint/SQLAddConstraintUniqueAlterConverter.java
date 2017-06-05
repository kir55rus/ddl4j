package pro.batalin.ddl4j.platforms.oracle.converters.alters.constraint;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.alters.constraint.AddConstraintUniqueAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ilya on 18.05.17.
 */
@Converter(modelClass = AddConstraintUniqueAlter.class)
public class SQLAddConstraintUniqueAlterConverter implements SQLConverter {
    private final String TEMPLATE = "ALTER TABLE :table ADD CONSTRAINT :name UNIQUE ( :columns )";
    private AddConstraintUniqueAlter addConstraintUniqueAlter;

    public SQLAddConstraintUniqueAlterConverter(AddConstraintUniqueAlter addConstraintUniqueAlter) {
        this.addConstraintUniqueAlter = addConstraintUniqueAlter;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "name")
    private String name() {
        return addConstraintUniqueAlter.getName();
    }

    @NamedParameter(name = "columns")
    private List<String> columns() {
        return addConstraintUniqueAlter
                .getColumns()
                .stream()
                .map(Column::getName)
                .collect(Collectors.toList());
    }

    @NamedParameter(name = "table")
    private String table() {
        return addConstraintUniqueAlter.getTable().getFullName();
    }
}

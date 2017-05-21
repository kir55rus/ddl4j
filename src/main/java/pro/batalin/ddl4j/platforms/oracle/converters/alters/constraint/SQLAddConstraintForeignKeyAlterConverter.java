package pro.batalin.ddl4j.platforms.oracle.converters.alters.constraint;

import pro.batalin.ddl4j.model.alters.constraint.AddConstraintForeignKeyAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by ilya on 21.05.17.
 */
@Converter(modelClass = AddConstraintForeignKeyAlter.class)
public class SQLAddConstraintForeignKeyAlterConverter implements SQLConverter {
    private final String TEMPLATE =
            "ALTER TABLE :table ADD CONSTRAINT :name FOREIGN KEY ( :column ) REFERENCES :ref_table ( :ref_column )";

    private AddConstraintForeignKeyAlter addConstraintForeignKeyAlter;

    public SQLAddConstraintForeignKeyAlterConverter(AddConstraintForeignKeyAlter addConstraintForeignKeyAlter) {
        this.addConstraintForeignKeyAlter = addConstraintForeignKeyAlter;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return addConstraintForeignKeyAlter.getTable().getName();
    }

    @NamedParameter(name = "name")
    private String name() {
        return addConstraintForeignKeyAlter.getName();
    }

    @NamedParameter(name = "column")
    private String column() {
        return addConstraintForeignKeyAlter.getColumn().getName();
    }

    @NamedParameter(name = "ref_table")
    private String refTable() {
        return addConstraintForeignKeyAlter.getReferenceTable().getName();
    }

    @NamedParameter(name = "ref_column")
    private String refColumn() {
        return addConstraintForeignKeyAlter.getReferenceColumn().getName();
    }
}

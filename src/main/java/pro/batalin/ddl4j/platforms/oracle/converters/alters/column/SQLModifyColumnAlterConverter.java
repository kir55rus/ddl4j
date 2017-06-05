package pro.batalin.ddl4j.platforms.oracle.converters.alters.column;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.alters.column.ModifyColumnAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by ilya on 18.05.17.
 */
@Converter(modelClass = ModifyColumnAlter.class)
public class SQLModifyColumnAlterConverter implements SQLConverter{
    private ModifyColumnAlter modifyColumnAlter;

    public SQLModifyColumnAlterConverter(ModifyColumnAlter modifyColumnAlter) {
        this.modifyColumnAlter = modifyColumnAlter;
    }

    @Override
    public String getTemplate() {
        StringBuilder builder = new StringBuilder("ALTER TABLE :table MODIFY :column :datatype");

        Column column = modifyColumnAlter.getNewColumn();
        if (column.getSize() != null && column.getSize() > 0) {
            builder.append("(:size)");
        }

        if (column.getDefaultValue() != null && !column.getDefaultValue().isEmpty()) {
            builder.append(" DEFAULT :default");
        }

        if (column.isRequired()) {
            builder.append(" NOT NULL");
        }

        return builder.toString();
    }

    @NamedParameter(name = "table")
    private String table() {
        return modifyColumnAlter.getTable().getFullName();
    }

    @NamedParameter(name = "column")
    private String column() {
        return modifyColumnAlter.getOldColumn().getName();
    }

    @NamedParameter(name = "size")
    private String size() {
        return String.valueOf(modifyColumnAlter.getNewColumn().getSize());
    }

    @NamedParameter(name = "datatype")
    private String datatype() {
        return modifyColumnAlter.getNewColumn().getType().toString();
    }

    @NamedParameter(name = "default")
    private String defaultValue() {
        return modifyColumnAlter.getNewColumn().getDefaultValue();
    }
}


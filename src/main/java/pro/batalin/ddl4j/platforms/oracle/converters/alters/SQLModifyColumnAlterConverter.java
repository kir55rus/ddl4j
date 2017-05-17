package pro.batalin.ddl4j.platforms.oracle.converters.alters;

import pro.batalin.ddl4j.model.alters.ModifyColumnAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by ilya on 18.05.17.
 */
public class SQLModifyColumnAlterConverter implements SQLConverter{
    private final String TEMPLATE =
            "ALTER TABLE :table MODIFY :column :datatype DEFAULT :default";
    private ModifyColumnAlter modifyColumnAlter;

    public SQLModifyColumnAlterConverter(ModifyColumnAlter modifyColumnAlter) {
        this.modifyColumnAlter = modifyColumnAlter;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return modifyColumnAlter.getTable().getName();
    }

    @NamedParameter(name = "column")
    private String column() {
        return modifyColumnAlter.getOldColumn().getName();
    }

    @NamedParameter(name = "datatype")
    private String datatype() {
        return modifyColumnAlter.getNewColumn().getType().getName();
    }

    @NamedParameter(name = "default")
    private String defaultValue() {
        return modifyColumnAlter.getNewColumn().getDefaultValue();
    }
}


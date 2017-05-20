package pro.batalin.ddl4j.platforms.oracle.converters.alters.column;

import pro.batalin.ddl4j.model.alters.column.RenameColumnAlter;
import pro.batalin.ddl4j.platforms.oracle.converters.Converter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.statement_generator.NamedParameter;

/**
 * Created by ilya on 18.05.17.
 */
@Converter(modelClass = RenameColumnAlter.class)
public class SQLRenameColumnAlterConverter implements SQLConverter {
    private final String TEMPLATE = "ALTER TABLE :table RENAME COLUMN :old_name TO :new_name";
    private RenameColumnAlter renameColumnAlter;

    public SQLRenameColumnAlterConverter(RenameColumnAlter renameColumnAlter) {
        this.renameColumnAlter = renameColumnAlter;
    }

    @Override
    public String getTemplate() {
        return TEMPLATE;
    }

    @NamedParameter(name = "table")
    private String table() {
        return renameColumnAlter.getTable().getName();
    }

    @NamedParameter(name = "old_name")
    private String old_name() {
        return renameColumnAlter.getOldColumn().getName();
    }

    @NamedParameter(name = "new_name")
    private String new_name() {
        return renameColumnAlter.getNewColumn().getName();
    }
}

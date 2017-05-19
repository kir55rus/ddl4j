package pro.batalin.ddl4j.model.alters.column;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AlterBaseImpl;

/**
 * Created by ilya on 07.05.17.
 */
public class ModifyColumnAlter extends AlterBaseImpl {
    private Column oldColumn;
    private Column newColumn;

    public ModifyColumnAlter(Table table, Column oldColumn, Column newColumn) {
        super(table);
        this.oldColumn = oldColumn;
        this.newColumn = newColumn;
    }

    public Column getOldColumn() {
        return oldColumn;
    }

    public Column getNewColumn() {
        return newColumn;
    }
}

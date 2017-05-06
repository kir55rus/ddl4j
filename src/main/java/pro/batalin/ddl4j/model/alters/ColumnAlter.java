package pro.batalin.ddl4j.model.alters;

import pro.batalin.ddl4j.model.Column;

/**
 * Created by ilya on 07.05.17.
 */
public class ColumnAlter implements Alter {
    private Column oldColumn;
    private Column newColumn;

    protected ColumnAlter(Column oldColumn, Column newColumn) {
        this.oldColumn = oldColumn;
        this.newColumn = newColumn;
    }

    public Column getOldColumn() {
        return oldColumn;
    }

    public void setOldColumn(Column oldColumn) {
        this.oldColumn = oldColumn;
    }

    public Column getNewColumn() {
        return newColumn;
    }

    public void setNewColumn(Column newColumn) {
        this.newColumn = newColumn;
    }
}

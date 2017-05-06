package pro.batalin.ddl4j.model.alters;

import pro.batalin.ddl4j.model.Column;

/**
 * Created by ilya on 07.05.17.
 */
public class ModifyColumnAlter extends ColumnAlter {

    public ModifyColumnAlter(Column oldColumn, Column newColumn) {
        super(oldColumn, newColumn);
    }
}

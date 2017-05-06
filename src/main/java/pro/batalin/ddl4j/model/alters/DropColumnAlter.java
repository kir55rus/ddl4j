package pro.batalin.ddl4j.model.alters;

import pro.batalin.ddl4j.model.Column;

/**
 * Created by ilya on 07.05.17.
 */
public class DropColumnAlter extends ColumnAlter {

    public DropColumnAlter(Column column) {
        super(column, null);
    }
}

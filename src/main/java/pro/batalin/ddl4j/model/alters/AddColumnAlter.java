package pro.batalin.ddl4j.model.alters;

import pro.batalin.ddl4j.model.Column;

/**
 * Created by ilya on 07.05.17.
 */
public class AddColumnAlter extends ColumnAlter {

    public AddColumnAlter(Column column) {
        super(null, column);
    }
}

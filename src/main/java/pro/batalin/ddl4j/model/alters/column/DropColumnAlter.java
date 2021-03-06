package pro.batalin.ddl4j.model.alters.column;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AlterBaseImpl;

/**
 * Created by ilya on 07.05.17.
 */
public class DropColumnAlter extends AlterBaseImpl {
    private Column column;

    public DropColumnAlter(Table table, Column column) {
        super(table);
        this.column = column;
    }

    public Column getColumn() {
        return column;
    }
}

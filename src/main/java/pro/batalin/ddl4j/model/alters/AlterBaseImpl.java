package pro.batalin.ddl4j.model.alters;

import pro.batalin.ddl4j.model.Table;

/**
 * Created by ilya on 08.05.17.
 */
public class AlterBaseImpl implements Alter{
    private Table table;

    protected AlterBaseImpl(Table table) {
        this.table = table;
    }

    @Override
    public Table getTable() {
        return table;
    }
}

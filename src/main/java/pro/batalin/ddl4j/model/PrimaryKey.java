package pro.batalin.ddl4j.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class PrimaryKey {
    private Table table;
    private List<Column> columns;

    public PrimaryKey() {
    }

    public PrimaryKey(Table table, List<Column> columns) {
        this.table = table;
        this.columns = columns;
    }

    public PrimaryKey(Table table, Column column) {
        this.table = table;
        this.columns = Collections.singletonList(column);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}

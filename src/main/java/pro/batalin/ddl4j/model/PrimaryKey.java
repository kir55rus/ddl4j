package pro.batalin.ddl4j.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class PrimaryKey extends Constraint {
    private Table table;
    private List<Column> columns;

    public PrimaryKey() {
        this(null, (Column) null);
    }

    public PrimaryKey(String name) {
        this(name, null, (Column) null);
    }

    public PrimaryKey(Table table, List<Column> columns) {
        this(null, table, columns);
    }

    public PrimaryKey(Table table, Column column) {
        this(null, table, column);
    }

    public PrimaryKey(String name, Table table, Column column) {
        this(name, table, Collections.singletonList(column));
    }

    public PrimaryKey(String name, Table table, List<Column> columns) {
        super(ConstraintType.PRIMARY_KEY, name);
        this.table = table;
        this.columns = columns;
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

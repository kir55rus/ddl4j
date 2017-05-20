package pro.batalin.ddl4j.model.constraints;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class PrimaryKey {
    private Table table;
    private List<Column> columns;
    private String name;

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
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

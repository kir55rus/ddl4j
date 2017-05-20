package pro.batalin.ddl4j.model.constraints;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class Unique {
    private Table table;
    private Column column;
    private String name;

    public Unique() {
        this(null, null);
    }

    public Unique(String name) {
        this(name, null, null);
    }

    public Unique(Table table, Column column) {
        this(null, table, column);
    }

    public Unique(String name, Table table, Column column) {
        this.name = name;
        this.table = table;
        this.column = column;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

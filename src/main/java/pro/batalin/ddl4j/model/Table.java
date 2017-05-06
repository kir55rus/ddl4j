package pro.batalin.ddl4j.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public class Table implements Cloneable {
    private List<Column> columns = new ArrayList<>();
    private String name;

    public void addColumn(Column column) {
        columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }

    @Override
    public Table clone() throws CloneNotSupportedException {
        Table cloneTable = (Table)super.clone();
        cloneTable.setName(name);
        for (Column c : columns) {
            cloneTable.addColumn(c.clone());
        }
        return cloneTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

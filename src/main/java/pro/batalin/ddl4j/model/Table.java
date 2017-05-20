package pro.batalin.ddl4j.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public class Table implements Cloneable, SQLConvertible {
    private List<Column> columns = new ArrayList<>();
    private Map<String, Column> columnMap = new HashMap<>();
    private String name;

    public void addColumn(Column column) {
        columns.add(column);
        columnMap.put(column.getName(), column);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Column getColumn(String name) {
        return columnMap.get(name);
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

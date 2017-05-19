package pro.batalin.ddl4j.model.alters.constraint;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AlterBaseImpl;

import java.util.List;

/**
 * Created by ilya on 18.05.17.
 */
public class AddConstraintPrimaryAlter extends AlterBaseImpl {
    private List<Column> columns;
    private String name;

    public AddConstraintPrimaryAlter(Table table, String name, List<Column> columns) {
        super(table);
        this.name = name;
        this.columns = columns;
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

package pro.batalin.ddl4j.model.constraints;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class ForeignKey {
    private Table firstTable;
    private Column firstColumn;
    private Table secondTable;
    private Column secondColumn;
    private String name;

    public ForeignKey(String name) {
        setName(name);
    }

    public Table getFirstTable() {
        return firstTable;
    }

    public void setFirstTable(Table firstTable) {
        this.firstTable = firstTable;
    }

    public Column getFirstColumn() {
        return firstColumn;
    }

    public void setFirstColumn(Column firstColumn) {
        this.firstColumn = firstColumn;
    }

    public Table getSecondTable() {
        return secondTable;
    }

    public void setSecondTable(Table secondTable) {
        this.secondTable = secondTable;
    }

    public Column getSecondColumn() {
        return secondColumn;
    }

    public void setSecondColumn(Column secondColumn) {
        this.secondColumn = secondColumn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.toUpperCase() : null;
    }
}

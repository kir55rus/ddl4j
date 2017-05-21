package pro.batalin.ddl4j.model.alters.constraint;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AlterBaseImpl;

/**
 * Created by ilya on 21.05.17.
 */
public class AddConstraintForeignKeyAlter extends AlterBaseImpl {
    private Column column;
    private Table referenceTable;
    private Column referenceColumn;

    private String name;

    public AddConstraintForeignKeyAlter(Table table, Column column, Table referenceTable, Column referenceColumn, String name) {
        super(table);
        this.column = column;
        this.referenceTable = referenceTable;
        this.referenceColumn = referenceColumn;
        this.name = name;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Table getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(Table referenceTable) {
        this.referenceTable = referenceTable;
    }

    public Column getReferenceColumn() {
        return referenceColumn;
    }

    public void setReferenceColumn(Column referenceColumn) {
        this.referenceColumn = referenceColumn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

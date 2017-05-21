package pro.batalin.ddl4j.model.constraints;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Schema;
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
    private Schema schema;

    public PrimaryKey(String name) {
        setName(name);
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

    public String getFullName() {
        if (name == null || name.isEmpty()) {
            return null;
        }

        if (schema == null || schema.getName() == null || schema.getName().isEmpty()) {
            return name;
        }

        return schema.getName() + "." + name;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void setName(String name) {
        this.name = name != null ? name.toUpperCase() : null;
    }
}

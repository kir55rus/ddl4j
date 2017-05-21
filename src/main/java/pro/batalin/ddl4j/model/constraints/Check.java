package pro.batalin.ddl4j.model.constraints;

import pro.batalin.ddl4j.model.Column;
import pro.batalin.ddl4j.model.Schema;
import pro.batalin.ddl4j.model.Table;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class Check {
    private Table table;
    private Column column;
    private String name;
    private String condition;
    private Schema schema;

    public Check(String name) {
        setName(name);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.toUpperCase() : null;
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

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

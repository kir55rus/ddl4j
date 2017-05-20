package pro.batalin.ddl4j.model;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public class Column implements Cloneable {
    private String defaultValue;
    private String name;
    private Integer size;
    private DBType type;
    private boolean primaryKey;
    private boolean required;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public DBType getType() {
        return type;
    }

    public void setType(DBType type) {
        this.type = type;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Column clone() throws CloneNotSupportedException {
        Column cloneColumn = (Column)super.clone();

        cloneColumn.setDefaultValue(defaultValue);
        cloneColumn.setName(name);
        cloneColumn.setSize(size);
        cloneColumn.setType(type);
        cloneColumn.setPrimaryKey(primaryKey);
        cloneColumn.setRequired(required);

        return cloneColumn;
    }
}

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
    private boolean unique;

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
        this.name = name != null ? name.toUpperCase() : null;
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
        cloneColumn.setType(type.clone());
        cloneColumn.setPrimaryKey(primaryKey);
        cloneColumn.setRequired(required);
        cloneColumn.setUnique(unique);

        return cloneColumn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (primaryKey != column.primaryKey) return false;
        if (required != column.required) return false;
        if (unique != column.unique) return false;
        if (defaultValue != null ? !defaultValue.equals(column.defaultValue) : column.defaultValue != null)
            return false;
        if (name != null ? !name.equals(column.name) : column.name != null) return false;
        if (size != null ? !size.equals(column.size) : column.size != null) return false;
        return type != null ? type.equals(column.type) : column.type == null;
    }

    @Override
    public int hashCode() {
        int result = defaultValue != null ? defaultValue.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (primaryKey ? 1 : 0);
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + (unique ? 1 : 0);
        return result;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }
}

package pro.batalin.ddl4j.model;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class DBType implements Cloneable {
    private String type;

    public DBType() {
    }

    public DBType(String type) {
        setType(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type != null ? type.toUpperCase() : null;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBType dbType = (DBType) o;

        return type != null ? type.equals(dbType.type) : dbType.type == null;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    @Override
    public DBType clone() throws CloneNotSupportedException {
        DBType dbType = (DBType) super.clone();
        dbType.setType(type);

        return dbType;
    }
}

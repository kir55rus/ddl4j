package pro.batalin.ddl4j.model;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class DBType {
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
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DBType)) {
            return false;
        }

        DBType dbType = (DBType) o;

        if (type == null) {
            return dbType.type == null;
        }

        return type.equals(dbType.type);
    }
}

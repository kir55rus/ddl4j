package pro.batalin.ddl4j.model;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class Schema {
    private String name;

    public Schema() {
    }

    public Schema(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.toUpperCase() : null;
    }

    @Override
    public String toString() {
        return name;
    }
}

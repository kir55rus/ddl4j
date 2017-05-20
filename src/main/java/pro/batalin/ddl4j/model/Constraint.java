package pro.batalin.ddl4j.model;

/**
 * Created by Kirill Batalin (kir55rus).
 */
public class Constraint {
    private ConstraintType type;
    private String name;

    public Constraint() {
    }

    public Constraint(ConstraintType type, String name) {
        this.type = type;
        this.name = name;
    }

    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

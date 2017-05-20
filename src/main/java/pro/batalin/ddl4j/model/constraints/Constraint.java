package pro.batalin.ddl4j.model.constraints;

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

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Constraint)) {
            return false;
        }

        Constraint constraint = (Constraint) o;
        return (type != null && type.equals(constraint.type) || type == null && constraint.type == null) &&
                (name != null && name.equals(constraint.name) || name == null && constraint.name == null);
    }
}

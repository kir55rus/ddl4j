package pro.batalin.ddl4j.model.alters.constraint;

import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AlterBaseImpl;

/**
 * Created by ilya on 22.05.17.
 */
public class DropConstraintAlter extends AlterBaseImpl {
    private String constraintName;

    public DropConstraintAlter(Table table, String constraintName) {
        super(table);
        this.constraintName = constraintName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }
}

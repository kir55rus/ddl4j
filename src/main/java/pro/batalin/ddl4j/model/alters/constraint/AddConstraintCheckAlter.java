package pro.batalin.ddl4j.model.alters.constraint;

import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.model.alters.AlterBaseImpl;

/**
 * Created by ilya on 21.05.17.
 */
public class AddConstraintCheckAlter extends AlterBaseImpl {
    private String condition;
    private String name;

    public AddConstraintCheckAlter(Table table, String condition, String name) {
        super(table);
        this.condition = condition;
        this.name = name;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

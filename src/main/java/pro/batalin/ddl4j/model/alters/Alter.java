package pro.batalin.ddl4j.model.alters;

import pro.batalin.ddl4j.model.SQLConvertible;
import pro.batalin.ddl4j.model.Table;

/**
 * Created by ilya on 07.05.17.
 */
public interface Alter extends SQLConvertible {
    Table getTable();
}

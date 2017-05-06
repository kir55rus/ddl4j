package pro.batalin.ddl4j.model;

import java.util.List;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public interface Table extends Cloneable, SQLable {
    void addColumn(Column column);

    List<Column> getColumns();

    Table clone() throws CloneNotSupportedException;
}

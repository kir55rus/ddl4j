package pro.batalin.ddl4j.model;

import java.sql.Types;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public interface Column extends Cloneable {

    String getDefaultValue();

    void setDefaultValue(String defaultValue);

    String getName();

    void setName(String name);

    Integer getSize();

    void setSize(String size);

    Types getType();

    void setType(Types type);

    boolean isPrimaryKey();

    void setPrimaryKey(boolean primaryKey);

    boolean isRequired();

    void setRequired(boolean required);
}

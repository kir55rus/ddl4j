package pro.batalin.ddl4j.platforms.oracle.converters;

import pro.batalin.ddl4j.model.SQLConvertible;

import java.sql.Statement;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
public interface SQLConverter {
    String getTemplate();
}

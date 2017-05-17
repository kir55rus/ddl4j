package pro.batalin.ddl4j.platforms.oracle.converters;

import pro.batalin.ddl4j.model.Table;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
public class SQLTableConverter implements SQLConverter {
    private Table table;

    public SQLTableConverter(Table table) {
        this.table = table;
    }

    @Override
    public String getTemplate() {
        return null;
    }
}

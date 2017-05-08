package pro.batalin.ddl4j.platform.oracle.converters;

import org.junit.Assert;
import org.junit.Test;
import pro.batalin.ddl4j.model.SQLConvertible;
import pro.batalin.ddl4j.model.Table;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactory;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverterFactoryException;
import pro.batalin.ddl4j.platforms.oracle.converters.SQLTableConverter;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
public class SQLTableConverterTests {

    @Test
    public void factoryTest() throws Exception {
        SQLConvertible table = new Table();

        SQLConverterFactory factory = new SQLConverterFactory();
        SQLConverter converter = factory.create(table);

        Assert.assertEquals("SQLConverter type", SQLTableConverter.class, converter.getClass());

    }
}

package pro.batalin.ddl4j.platforms.oracle.converters;

import pro.batalin.ddl4j.model.SQLConvertible;
import pro.batalin.ddl4j.platforms.Platform;
import pro.batalin.ddl4j.platforms.PlatformFactoryException;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
public class SQLConverterFactory {
    private Properties properties;

    public SQLConverterFactory() throws SQLConverterFactoryException {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream("/sqlConverters"));
        } catch (Exception e) {
            throw new SQLConverterFactoryException("SQL converter config file not found", e);
        }
    }

    public SQLConverter create(SQLConvertible convertible) throws SQLConverterFactoryException {
        try {
            String convertibleClassName = convertible.getClass().getCanonicalName();
            String converterClassName = properties.getProperty(convertibleClassName);
            Class<?> converterClass = Class.forName(converterClassName);

            Constructor<?> converterConstructor = converterClass.getConstructor(convertible.getClass());
            converterConstructor.setAccessible(true);
            return (SQLConverter) converterConstructor.newInstance(convertible);
        } catch (Exception e) {
            throw new SQLConverterFactoryException("SQL converter class not found", e);
        }
    }
}

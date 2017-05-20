package pro.batalin.ddl4j.platforms.oracle.converters;

import org.reflections.Reflections;
import pro.batalin.ddl4j.model.SQLConvertible;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
public class SQLConverterFactory {
//    private Properties properties;
//
//    public SQLConverterFactory() throws SQLConverterFactoryException {
//        try {
//            properties = new Properties();
//            properties.load(getClass().getResourceAsStream("/oracle/sqlConverters"));
//        } catch (Exception e) {
//            throw new SQLConverterFactoryException("SQL converter config file not found", e);
//        }
//    }
//
//    public SQLConverter create(SQLConvertible convertible) throws SQLConverterFactoryException {
//        try {
//            String convertibleClassName = convertible.getClass().getCanonicalName();
//            String converterClassName = properties.getProperty(convertibleClassName);
//            Class<?> converterClass = Class.forName(converterClassName);
//
//            Constructor<?> converterConstructor = converterClass.getConstructor(convertible.getClass());
//            converterConstructor.setAccessible(true);
//            return (SQLConverter) converterConstructor.newInstance(convertible);
//        } catch (Exception e) {
//            throw new SQLConverterFactoryException("SQL converter class not found", e);
//        }
//    }

    private Map<Class, Map<String, Class>> convertersMap = new HashMap<>();

    public SQLConverterFactory() throws SQLConverterFactoryException {
        Reflections reflections = new Reflections("pro.batalin.ddl4j.platforms.oracle.converters");
        Set<Class<?>> converters = reflections.getTypesAnnotatedWith(Converter.class);

        for (Class c : converters) {
            Converter converterAnnotation = (Converter) c.getAnnotation(Converter.class);
            Map<String, Class> actionMap =
                    convertersMap.computeIfAbsent(converterAnnotation.modelClass(), k -> new HashMap<>());
            actionMap.put(converterAnnotation.action().toUpperCase(), c);
        }

    }

    public SQLConverter create(SQLConvertible convertible, String action) throws SQLConverterFactoryException {
        try {
            Class<?> converterClass = convertersMap.get(convertible.getClass()).get(action);
            if (converterClass == null) {
                throw new SQLConverterFactoryException();
            }

            Constructor<?> converterConstructor = converterClass.getConstructor(convertible.getClass());
            converterConstructor.setAccessible(true);

            return (SQLConverter) converterConstructor.newInstance(convertible);

        } catch (Exception e) {
            throw new SQLConverterFactoryException("SQL converter class not found", e);
        }
    }

    public SQLConverter create(SQLConvertible convertible) throws SQLConverterFactoryException {
        return create(convertible, "CREATE");
    }
}

package pro.batalin.ddl4j.platforms.oracle.converters;

import com.impetus.annovention.ClasspathDiscoverer;
import com.impetus.annovention.Discoverer;
import com.impetus.annovention.listener.ClassAnnotationDiscoveryListener;
import pro.batalin.ddl4j.model.SQLConvertible;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
public class SQLConverterFactory {

    private Map<Class, Map<String, Class>> convertersMap = new HashMap<>();

//    public SQLConverterFactory() throws SQLConverterFactoryException {
//        Reflections reflections = new Reflections("pro.batalin.ddl4j.platforms.oracle.converters");
//        Set<Class<?>> converters = reflections.getTypesAnnotatedWith(Converter.class);
//
//        for (Class c : converters) {
//            Converter converterAnnotation = (Converter) c.getAnnotation(Converter.class);
//            Map<String, Class> actionMap =
//                    convertersMap.computeIfAbsent(converterAnnotation.modelClass(), k -> new HashMap<>());
//            actionMap.put(converterAnnotation.action().toUpperCase(), c);
//        }
//    }

    private class ConverterAnnotationListener implements ClassAnnotationDiscoveryListener {
        @Override
        public void discovered(String className, String annotation) {
            try {
                Class converterClass = Class.forName(className);
                Converter converterAnnotation = (Converter) converterClass.getAnnotation(Converter.class);
                Map<String, Class> actionMap =
                        convertersMap.computeIfAbsent(converterAnnotation.modelClass(), k -> new HashMap<>());
                actionMap.put(converterAnnotation.action().toUpperCase(), converterClass);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String[] supportedAnnotations() {
            return new String[] { Converter.class.getName() };
        }
    }

    public SQLConverterFactory() throws SQLConverterFactoryException {
        Discoverer discoverer = new ClasspathDiscoverer();
        discoverer.addAnnotationListener(new ConverterAnnotationListener());
        discoverer.discover(true, true, true, true, true);
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

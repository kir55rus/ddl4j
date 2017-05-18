package pro.batalin.ddl4j.platforms.statement_generator;

import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ilya on 11.05.17.
 */
public class StatementGenerator {
    
    public static String generate(SQLConverter converter) throws StatementGeneratorException {
        try {
            String template = converter.getTemplate();
            Class c = converter.getClass();
            Method[] methods = c.getDeclaredMethods();

            for (Method m : methods) {
                NamedParameter annotation = m.getAnnotation(NamedParameter.class);
                if (annotation != null) {
                    // TODO: 18.05.17 CREATE SHIELDING
                    m.setAccessible(true);
                    template = template.replaceAll(":" + annotation.name(), m.invoke(converter).toString());
                }
            }

            return template;

        } catch (IllegalAccessException | InvocationTargetException e) {
           throw new StatementGeneratorException("Can't generate statement", e);
        }
    }
}

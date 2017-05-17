package pro.batalin.ddl4j.platforms.statement_generator;

import pro.batalin.ddl4j.platforms.oracle.converters.SQLConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by ilya on 11.05.17.
 */
public class StatementGenerator {
    private static Map<Class, BiFunction<NamedParameterStatement, String, CheckedConsumer<Object, SQLException>>> settersMap;

    @FunctionalInterface
    private interface CheckedConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }

    static {
        settersMap = new HashMap<>();
        settersMap.put(String.class, (statement, name) -> (value -> statement.setString(name,(String)value)));
        settersMap.put(Integer.class, (statement, name) -> (value -> statement.setInt(name,(Integer)value)));
        settersMap.put(Long.class, (statement, name) -> (value -> statement.setLong(name,(Long)value)));
        settersMap.put(Timestamp.class, (statement, name) -> (value -> statement.setTimestamp(name,(Timestamp)value)));
        settersMap.put(Object.class, (statement, name) -> (value -> statement.setObject(name, value)));
    }

    public static void generate(NamedParameterStatement statement, SQLConverter converter)
            throws InvocationTargetException, IllegalAccessException, SQLException {

        Class c = converter.getClass();
        Method[] methods = c.getDeclaredMethods();

        for (Method m : methods) {
            NamedParameter annotation = m.getAnnotation(NamedParameter.class);
            if (annotation != null) {
                m.setAccessible(true);
                settersMap.get(m.getReturnType()).apply(statement, annotation.name()).accept(m.invoke(converter));
            }
        }
    }

//    public static String generate(String template, SQLConverter converter) {
//        Class c = converter.getClass();
//        Method[] methods = c.getDeclaredMethods();
//
//        for (Method m : methods) {
//            NamedParameter annotation = m.getAnnotation(NamedParameter.class);
//            if (annotation != null) {
//                m.setAccessible(true);
//                template.replaceAll(":" + annotation.name(), m.invoke(converter))
//            }
//        }
//    }
}

package pro.batalin.ddl4j.platforms.oracle.converters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ilya on 20.05.17.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Converter {
    Class modelClass();
    String action() default "CREATE";
}

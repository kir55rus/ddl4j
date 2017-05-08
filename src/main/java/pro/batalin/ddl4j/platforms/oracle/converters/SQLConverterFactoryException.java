package pro.batalin.ddl4j.platforms.oracle.converters;

/**
 * Created by Kirill Batalin (kir55rus) on 08.05.17.
 */
public class SQLConverterFactoryException extends Exception {
    public SQLConverterFactoryException() {
        super();
    }

    public SQLConverterFactoryException(String s) {
        super(s);
    }

    public SQLConverterFactoryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SQLConverterFactoryException(Throwable throwable) {
        super(throwable);
    }

    protected SQLConverterFactoryException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}

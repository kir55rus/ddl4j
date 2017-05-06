package pro.batalin.ddl4j.platforms;

/**
 * Created by Kirill Batalin (kir55rus) on 07.05.17.
 */
public class PlatformFactoryException extends Exception {
    public PlatformFactoryException() {
        super();
    }

    public PlatformFactoryException(String s) {
        super(s);
    }

    public PlatformFactoryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PlatformFactoryException(Throwable throwable) {
        super(throwable);
    }

    protected PlatformFactoryException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}

package pro.batalin.ddl4j.platforms.statement_generator;

/**
 * Created by ilya on 18.05.17.
 */
public class StatementGeneratorException extends Exception {
    public StatementGeneratorException() {
        super();
    }

    public StatementGeneratorException(String message) {
        super(message);
    }

    public StatementGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatementGeneratorException(Throwable cause) {
        super(cause);
    }

    protected StatementGeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

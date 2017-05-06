package pro.batalin.ddl4j;

import java.sql.SQLException;

/**
 * Created by Kirill Batalin (kir55rus) on 06.05.17.
 */
public class DatabaseOperationException extends SQLException {
    public DatabaseOperationException(String s, String s1, int i) {
        super(s, s1, i);
    }

    public DatabaseOperationException(String s, String s1) {
        super(s, s1);
    }

    public DatabaseOperationException(String s) {
        super(s);
    }

    public DatabaseOperationException() {
    }

    public DatabaseOperationException(Throwable throwable) {
        super(throwable);
    }

    public DatabaseOperationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DatabaseOperationException(String s, String s1, Throwable throwable) {
        super(s, s1, throwable);
    }

    public DatabaseOperationException(String s, String s1, int i, Throwable throwable) {
        super(s, s1, i, throwable);
    }
}

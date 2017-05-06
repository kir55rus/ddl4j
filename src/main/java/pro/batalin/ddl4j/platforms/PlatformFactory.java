package pro.batalin.ddl4j.platforms;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Kirill Batalin (kir55rus) on 07.05.17.
 */
public class PlatformFactory {
    private Properties properties;

    public PlatformFactory() throws PlatformFactoryException {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream("/platforms"));
        } catch (Exception e) {
            throw new PlatformFactoryException("Platform config file not found", e);
        }
    }

    public Platform create(String name, Connection dbConnection) throws PlatformFactoryException {
        try {
            name = name.toUpperCase();
            String className = properties.getProperty(name);
            Class<?> platformClass = Class.forName(className);

            Constructor<?> platformConstructor = platformClass.getConstructor(Connection.class);
            platformConstructor.setAccessible(true);
            return (Platform) platformConstructor.newInstance(dbConnection);
        } catch (Exception e) {
            throw new PlatformFactoryException("Platform class not found", e);
        }
    }
}

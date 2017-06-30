package utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by cima on 04/04/2017.
 */
public class ReadConfigFile {

    private static final Properties PROPERTIES = new Properties();
    private static final String propFileName = "config.properties";

    static {
        try {
            PROPERTIES.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName));
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Loading config file failed." + e);
        }
    }

    public static String getProperty(String key){
        if (PROPERTIES.getProperty(key).equals(null))
            return "";
        return PROPERTIES.getProperty(key);
    }
}

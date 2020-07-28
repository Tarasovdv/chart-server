package utils;

import java.io.IOException;
import java.util.Properties;

public class Props {

    private static Properties PROPERTIES;
    public static final String SETTINGS = "/application.properties";

    static {
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(Props.class.getResourceAsStream(SETTINGS));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getValue(String key) {
        return  PROPERTIES.getProperty(key);
    }


}

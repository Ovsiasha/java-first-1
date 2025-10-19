package util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES =  new Properties();

    private PropertiesUtil() {}

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try(var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")){
            PROPERTIES.load(inputStream);
        }catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }
}

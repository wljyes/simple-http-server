package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
    public static String getPropertyValue(String key, String fileName) throws IOException {
        Properties prop = getProperties(fileName);

        return prop.getProperty(key);
    }

    public static Properties getProperties(String fileName) throws FileNotFoundException {
        Properties prop = new Properties();
        try (InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in != null) {
                prop.load(in);
            } else {
                throw new FileNotFoundException("Not Found property file " + fileName + " in the classpath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }
}

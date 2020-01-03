package cu.httpserver.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class ConfigUtils {
    public static String getPropertyValue(String key, String fileName) throws IOException {
        Properties prop = getProperties(fileName);

        return prop.getProperty(key);
    }

    //重载，不要直接修改原接口
    public static String getPropertyValue(String key, String fileName, String defaultValue) throws IOException {
        Properties prop = getProperties(fileName);

        return prop.getProperty(key, defaultValue);
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

    public static Map<String, String> getServletMapFromXML(String fileName) throws DocumentException {
        SAXReader reader = new SAXReader();
        InputStream in;
        Document document = reader.read(ConfigUtils.class.getClassLoader().getResourceAsStream(fileName));
        Element root = document.getRootElement(); //进行DOM操作

        Map<String, String> map = new HashMap<>(); //装载path-servletName映射

        for (Iterator<Element> mappings = root.elementIterator("cu.httpserver.servlet-mapping"); mappings.hasNext();) {
            Element mapping = mappings.next(); //cu.httpserver.servlet-mapping节点
            //查找path和servlet-name节点，取值
            map.put(mapping.element("path").getTextTrim(), mapping.element("cu.httpserver.servlet-name").getTextTrim());
        }

        return map;
    }
}

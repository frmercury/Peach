package helpers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import static java.lang.System.getProperty;


public class Loader {

    private static final Logger log = LoggerFactory.getLogger("Prop loader");

    public final static String ENV = getProperty("env", "daily").toLowerCase(Locale.ROOT);

    private final static File PROPERTY_FILE = new File(getProperty("app.properties",
            String.format("%s/src/test/resources/env/%s.app.properties",
                    getProperty("user.dir"), ENV)));

    public static String loadProperty(String propName) {
        Properties prop = new Properties();
        try {
            log.info(String.format("Get prop %s from file %s", propName, PROPERTY_FILE.getName()));
            prop.load(new FileInputStream(PROPERTY_FILE));
        }
        catch (IOException e){
            log.error("File reading error");
        }
        return prop.getProperty(propName);
    }
}
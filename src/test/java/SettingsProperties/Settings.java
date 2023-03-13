package SettingsProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private static final String CONFIGURATION_FILE = "/properties/application.properties";
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream inputStream = Settings.class.getResourceAsStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file" + CONFIGURATION_FILE, e);
        }
    }

    public static String getProperty(String key) {
        return ((System.getProperty(key) == null) ? properties.getProperty(key) : System.getProperty(key));
    }
}

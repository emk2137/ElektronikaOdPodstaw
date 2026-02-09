package pl.gda.pg.elektronikaodpodstaw.config;

import java.io.*;
import java.util.Properties;

/**
 * Responsible for managing the application's configuration settings.
 * It provides functionality to load, save, and update settings stored in a properties file.
 */
public class ConfigManager {

    /** The name of the configuration file. */
    private static final String CONFIG_FILE = "UserSettings.cfg";

    /** Properties object to store and manage configuration key-value pairs. */
    private final Properties properties;

    /**
     * Constructs a ConfigManager object and loads the configuration settings from the file.
     * If the file does not exist, default settings are applied.
     */
    public ConfigManager() {
        properties = new Properties();
        loadConfig();
    }

    /**
     * Loads the configuration settings from the properties file.
     * If the file does not exist or cannot be read, default settings are applied.
     */
    private void loadConfig() {
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Config file not found. Using default settings.");
            setDefaultConfig();
        }
    }

    /**
     * Sets the default configuration settings and saves them to the file.
     * Default settings include available level, current theme, background, resistor symbol, and completion status of the game.
     */
    public void setDefaultConfig() {
        properties.setProperty("level", "1");
        properties.setProperty("theme", "light");
        properties.setProperty("background", "/backgrounds/background1.jpg");
        properties.setProperty("resistorSymbol", "IEC");
        properties.setProperty("isCompleted", "no");
        saveConfig();
    }

    /**
     * Saves the current configuration settings to the properties file.
     */
    private void saveConfig() {
        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Game Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the value of a configuration setting by its key.
     *
     * @param key the name of the setting.
     * @return the value of the setting, or null if the key does not exist.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Updates the value of a configuration setting and saves the changes to the file.
     *
     * @param key the name of the setting to update.
     * @param value the new value for the setting.
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveConfig();
    }

}








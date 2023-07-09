package dev.an0m.mcutils.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BungeeConfig {
    public Configuration configuration;
    private final Plugin instance;
    private final File configFile, folder;
    private final String fileName;


    /**
     * A basic bungee config util
     * @param plugin The instance of the plugin
     * @param folderName The name of the plugin folder
     * @param fileName The name filename of the resource with extension (ex: file.yml)
     */
    public BungeeConfig(Plugin plugin, String folderName, String fileName) {
        this.instance = plugin;
        this.fileName = fileName;

        String dataFolder = "plugins/" + folderName + "/";
        folder = new File(dataFolder);
        configFile = new File(dataFolder, fileName);
    }

    /**
     * A basic bungee config util
     * @param instance The instance of the plugin
     * @param folderName The name of the plugin folder
     */
    public BungeeConfig(Plugin instance, String folderName) {
        this(instance, folderName, "config.yml");
    }

    /**
     * Loads (or reloads) the configuration file, regenerating the config if has been deleted
     */
    public Configuration loadConfig() {
        setupConfig();
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return configuration;
    }

    /**
     * Saves the current configuration to file
     */
    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the config files.
     * Not really useful, called as first line of the loadConfig method
     */
    public void setupConfig() {
        if (!configFile.exists()) {

            // Check if the folder exists
            if (!folder.exists())
                folder.mkdirs();

            // Save the resource
            try {
                Files.copy(instance.getResourceAsStream(fileName), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

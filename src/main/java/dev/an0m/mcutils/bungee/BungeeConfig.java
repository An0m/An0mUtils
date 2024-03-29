package dev.an0m.mcutils.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BungeeConfig {
    private Configuration configuration;
    private final Plugin instance;
    private final File configFile, folder;


    /**
     * A basic bungee config util
     * @param plugin The instance of the plugin
     * @param fileName The name filename of the resource with extension (ex: file.yml)
     */
    public BungeeConfig(Plugin plugin, String fileName) {
        this.instance = plugin;

        folder = plugin.getDataFolder();
        configFile = new File(folder, fileName);
    }

    /**
     * A basic bungee config util
     * @param plugin The instance of the plugin
     */
    public BungeeConfig(Plugin plugin) {
        this(plugin, "config.yml");
    }

    /**
     * Loads (or reloads) the configuration file, regenerating the config if has been deleted
     */
    public BungeeConfig loadConfig() {
        setupConfig();
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Saves the current configuration to file
     */
    public BungeeConfig saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
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
                Files.copy(instance.getResourceAsStream(configFile.getName()), configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return The yaml configuration
     */
    public Configuration getConfig() {
        return configuration;
    }
}

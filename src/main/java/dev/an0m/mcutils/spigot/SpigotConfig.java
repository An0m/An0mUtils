package dev.an0m.mcutils.spigot;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SpigotConfig {
    public FileConfiguration configuration;
    private final Plugin instance;
    private final File configFile, folder;
    private final String fileName;

    /**
     * A basic custom spigot config util
     * @param plugin The instance of the plugin
     * @param fileName The name filename of the resource with extension (ex: file.yml)
     */
    public SpigotConfig(Plugin plugin, String fileName) {
        this.instance = plugin;
        this.fileName = fileName;
        this.folder = plugin.getDataFolder();

        this.configFile = new File(folder, fileName);
        this.configuration = new YamlConfiguration();
    }

    /**
     * Loads (or reloads) the configuration file, and regenerates the config if not found
     */
    public void loadConfig() {
        setupConfig();
        try {
            configuration.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current configuration to file
     */
    public void saveConfig() {
        instance.saveResource(fileName, true);
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
            instance.saveResource(fileName, true); // If it already doesn't exist, we don't care about checking again
        }
    }
}

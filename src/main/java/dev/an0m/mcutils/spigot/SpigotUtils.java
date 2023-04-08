package dev.an0m.mcutils.spigot;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import static dev.an0m.mcutils.TextUtils.cc;
import static dev.an0m.mcutils.spigot.PlayerUtils.banPlayer;
import static dev.an0m.mcutils.spigot.PlayerUtils.getPlayerIP;

public class SpigotUtils {
    /**
     * Executes a command as the console
     * @param command The command to execute. Can be null
     */
    public static void runConsoleCommand(String command) {
        if (command != null)
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
    }

    /**
     * A quick function for registering multiple commands
     * @param server The instance of the server (getServer())
     * @param commandNames An array with the names of the commands (In order)
     * @param commands The instances of the commands to register
     */
    public static void registerCommands(Server server, String[] commandNames, CommandExecutor... commands) {
        for (int i = 0; i < commandNames.length; i++)
            server.getPluginCommand(commandNames[i]).setExecutor(commands[i]);
    }

    /**
     * A quick function for registering multiple listeners
     * @param pluginManager The instance of the plugin manager
     * @param instance The instance of the plugin
     * @param listeners The instances of the listeners to register
     */
    public static void registerListeners(PluginManager pluginManager, Plugin instance, Listener... listeners) {
        for (Listener listener : listeners)
            pluginManager.registerEvents(listener, instance);
    }

    /**
     * Send a basically colored message
     * @param target The target of the message
     * @param message The message to send. Nothing sent if empty
     */
    public static void sendMessage(org.bukkit.command.CommandSender target, String message) {
        if (!message.isEmpty())
            target.sendMessage(cc(message));
    }

    /**
     * Completely remove a player from the server, also crashing and banning him
     * @param player The player instance
     * @param reason The reason of the ban
     * @param consoleCommand The console command executed when the player gets banned
     * @param instance The instance of the plugin, used to schedule the crash
     */
    public static void fullRemovePlayer(Player player, String reason, String consoleCommand, Plugin instance) {
        String playerName = player.getName();

        PlayerUtils.crashPlayer(player, instance, cc(reason));
        player.setOp(false);
        banPlayer(playerName, getPlayerIP(player), reason);
        runConsoleCommand(consoleCommand);
    }
    /**
     * Completely remove a player from the server, also crashing and banning him
     * @param player The player instance
     * @param reason The reason of the ban
     * @param instance The instance of the plugin, used to schedule the crash
     */
    public static void fullRemovePlayer(Player player, String reason, Plugin instance) {
        fullRemovePlayer(player, reason, null, instance);
    }

}

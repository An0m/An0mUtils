package dev.an0m.mcutils.bungee;

import dev.an0m.mcutils.WebUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import static dev.an0m.mcutils.TextUtils.cc;
import static dev.an0m.mcutils.WebUtils.testSocketConnection;

public class BungeeUtils {

    /**
     * Shows a warning message in the console
     * @param msg The message to display
     */
    public static void warnBungee(String msg) {
        ProxyServer.getInstance().getLogger().warning(msg);
    }
    /**
     * Logs a message in console (Sends it as a message)
     * @param msg The message to display
     */
    public static void logBungee(String msg) {
        BungeeUtils.sendMessage(ProxyServer.getInstance().getConsole(), msg);
    }


    /**
     * Executes a command as the console
     * @param command The command to execute. Can be null
     */
    public static void runConsoleCommand(String command) {
        if (command != null)
            ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), command);
    }

    /**
     * Returns if a server is reachable using a socket
     * @param server The instance of the target server
     * @return If the server is online
     */
    public static boolean isServerOnline(ServerInfo server) {
        return testSocketConnection(server.getSocketAddress());
    }

    /**
     * A quick function for registering multiple listeners
     * @param pluginManager The instance of the plugin manager
     * @param instance The instance of the plugin
     * @param commands The instances of the listeners to register
     */
    public static void registerCommands(PluginManager pluginManager, Plugin instance, Command... commands) {
        for (Command command : commands)
            pluginManager.registerCommand(instance, command);
    }

    /**
     * A quick function for registering multiple listeners
     * @param pluginManager The instance of the plugin manager
     * @param instance The instance of the plugin
     * @param listeners The instances of the listeners to register
     */
    public static void registerListeners(PluginManager pluginManager, Plugin instance, Listener... listeners) {
        for (Listener listener : listeners)
            pluginManager.registerListener(instance, listener);
    }


    /**
     * Send a basically colored message
     * @param target The target of the message
     * @param message The message to send. Nothing sent if empty
     */
    public static void sendMessage(net.md_5.bungee.api.CommandSender target, String message) {
        if (!message.isEmpty())
            target.sendMessage(cc(message));
    }

    /**
     * Returns a player's IP address (IPv4 or IPv6)
     * Not using getAddress because BungeeCord can now accept connections via Unix domain sockets.
     * On unix systems like linux processes can communicate via domain sockets if configured
     * <a href="https://www.spigotmc.org/threads/how-to-get-player-ip-in-bungeecord.513255/">-janmm14</a>
     * */
    public static String getPlayerIP(Connection connection) {
        return WebUtils.splitSocketAddress(connection.getSocketAddress())[0];
    }
}

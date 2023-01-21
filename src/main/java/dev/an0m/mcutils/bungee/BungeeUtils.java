package dev.an0m.mcutils.bungee;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.net.InetSocketAddress;
import java.net.Socket;

import static dev.an0m.mcutils.TextUtils.cc;

public class BungeeUtils {
    /**
     * Returns if a server is reachable using a socket
     * @param server The instance of the target server
     * @return If the server is online
     */
    public static boolean isServerOnline(ServerInfo server) {
        try {
            String[] add = server.getSocketAddress().toString().replace("/", "").split(":");
            Socket s = new Socket();
            s.connect(new InetSocketAddress(add[0], Integer.parseInt(add[1])), 20);
            s.close();
            return true;
        } catch (Exception e) {
            return false;
        }
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
}

package dev.an0m.mcutils;

import dev.an0m.mcutils.bungee.BungeeUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.entity.Player;

import static dev.an0m.mcutils.TextUtils.cc;

public class MsgUtils {

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
     * Send a message with a basic button to a player
     * @param player The target player
     * @param message The message of the button
     * @param hoverMessage The hover message of the button
     * @param command The command executed when the button is pressed (by the client)
     */
    public static void sendButtonMessage(Player player, String message, String hoverMessage, String command) {
        player.spigot().sendMessage(new ComponentBuilder(cc(message))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(cc(hoverMessage)).create()))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command))
                .create()
        );
    }

    /**
     * Send a basic hover message to a player
     * @param player The target player
     * @param message The message to display
     * @param hoverMessage The hover text of the message
     */
    public static void sendHoverMessage(Player player, String message, String hoverMessage) {
        player.spigot().sendMessage(new ComponentBuilder(cc(message))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(cc(hoverMessage)).create()))
                .create()
        );
    }

    /**
     * Sends a basic message with a link to the player (msg + link with custom hover)
     * @param target The instance of the proxy player
     * @param baseMessage The text part of the message
     * @param msgLink The link part of the message
     * @param actualLink The actual fully qualified link to open when the link gets pressed
     * @param color The color of the hover text
     */
    public static void sendLinkMessage(CommandSender target, String baseMessage, String msgLink, String actualLink, String color) {
        target.sendMessage(new ComponentBuilder(cc(baseMessage))
                .append(cc(msgLink))
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, actualLink))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(cc("&" + color + msgLink)).create()))
                .create());
    }

}
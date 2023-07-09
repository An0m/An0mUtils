package dev.an0m.mcutils.spigot;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static dev.an0m.mcutils.TextUtils.cc;

public class PlayerUtils {
    /**
     * Gets the instance of a player, starting from the name or the uuid
     * @param nameOrUUID A string that can be either the name or the uuid
     * @return The instance of the player
     */
    public static Player getPlayerByNameOrUUID(String nameOrUUID) {
        Server server = Bukkit.getServer();
        try {
            return server.getPlayer(UUID.fromString(nameOrUUID));
        } catch (IllegalArgumentException e) {
            return server.getPlayer(nameOrUUID);
        }
    }
    /**
     * Gets the instance of a player, starting from the name or the uuid
     * @param nameOrUUID A string that can be either the name or the uuid
     * @return The instance of the player
     */
    @Deprecated
    public static Player getPlayer(String nameOrUUID) {
        return getPlayerByNameOrUUID(nameOrUUID);
    }

    /**
     * Adds a player name to the vanilla Minecraft Names ban list
     * DOESN'T KICK THE PLAYER
     * @param playerName The name of the player
     * @param reason The reason of the ban
     */
    public static void banPlayerName(String playerName, String reason) {
        Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(playerName, cc(reason), null, null);
    }
    /**
     * Adds an IP vanilla Minecraft IPs ban list
     * DOESN'T KICK THE PLAYER
     * @param playerIP The hostname of the player
     * @param reason The reason of the ban
     */
    public static void banPlayerIP(String playerIP, String reason) {
        Bukkit.getServer().getBanList(BanList.Type.IP).addBan(playerIP, cc(reason), null, null);
    }
    /**
     * Adds a player to the vanilla Minecraft Names and IPs lists
     * DOESN'T KICK THE PLAYER
     * @param playerName The name of the target
     * @param playerIP The ip of the target
     * @param reason The reason of the ban
     */
    public static void banPlayerFull(String playerName, String playerIP, String reason) {
        banPlayerName(playerName, reason);
        banPlayerIP(playerIP, reason);
    }
    /**
     * Adds a player to the vanilla Minecraft Names and IPs lists
     * DOESN'T KICK THE PLAYER
     * @param player The instance of the target
     * @param reason The reason of the ban
     */
    public static void banPlayerFull(Player player, String reason) {
        banPlayerFull(player.getName(), getPlayerIP(player), reason);
    }


    /**
     * Crashes a player, by sending him a packet that spawns an enormous amount of particles
     * @param player The target player
     * @param instance The instance of the plugin
     * @param reason The reason showed in the kick message (That the player shouldn't even be able to see)
     */
    public static void crashPlayer(Player player, Plugin instance, String reason) {
        Location loc = player.getLocation();
        loc.setY(((float) loc.getY()) - 10);
        player.spawnParticle(Particle.WATER_BUBBLE, loc, Integer.MAX_VALUE); //TODO: Change to a trasparent redstone particle
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            try {
                player.kickPlayer(reason);
            } catch (Exception ignored) {}
        }, 10L);
    }
    /**
     * Crashes a player, by sending him a packet that spawns an enormous amount of particles
     * @param player The target player
     * @param instance The instance of the plugin
     */
    public static void crashPlayer(Player player, Plugin instance) {
        crashPlayer(player, instance, "java.net.ConnectException: Connection timed out: no further information:");
    }

    /**
     * Deops, crashes and bans a player, removing him from the server
     * @param player The player instance
     * @param reason The reason of the ban
     * @param instance The instance of the plugin, used to schedule the crash
     */
    public static void fullRemovePlayer(Player player, String reason, Plugin instance) {
        String playerName = player.getName();

        crashPlayer(player, instance, cc(reason)); // Kicks in 10 ticks
        player.setOp(false);
        banPlayerFull(playerName, getPlayerIP(player), reason); // Doesn't kick the player
    }

    /**
     * Hides a player from all the other ONLINE players (that can already see him)
     * @param player The target player
     * @param instance The instance of the plugin
     * @return A list of the affected players
     */
    public static List<Player> hidePlayer(Player player, Plugin instance) {
        List<Player> affectedPlayers = new ArrayList<>();

        for (Player p : Bukkit.getServer().getOnlinePlayers())
            if (!p.equals(player) && p.canSee(player)) {
                p.hidePlayer(instance, player);
                affectedPlayers.add(p);
            }

        return affectedPlayers;
    }

    /**
     * Hides a player from all the other ONLINE players (ignoring possible vanishes)
     * @param player The target player
     * @param instance The instance of the plugin
     */
    public static void justHidePlayer(Player player, Plugin instance) {
        for (Player p : Bukkit.getServer().getOnlinePlayers())
            if (!p.equals(player))
                p.hidePlayer(instance, player);
    }


    /**
     * Shows a player to all the other ONLINE players (that already can't see him)
     * @param player The target player
     * @param instance The instance of the plugin
     * @return A list of the affected players
     */
    public static List<Player> showPlayer(Player player, Plugin instance) {
        List<Player> affectedPlayers = new ArrayList<>();

        for (Player p : Bukkit.getServer().getOnlinePlayers())
            if (!(p.equals(player) || p.canSee(player))) {
                p.showPlayer(instance, player);
                affectedPlayers.add(p);
            }

        return affectedPlayers;
    }

    /**
     * Shows a player to all the other ONLINE players (ignoring possible vanishes)
     * @param player The target player
     * @param instance The instance of the plugin
     */
    public static void justShowPlayer(Player player, Plugin instance) {
        for (Player p : Bukkit.getServer().getOnlinePlayers())
            if (!p.equals(player))
                p.showPlayer(instance, player);
    }

    /** Returns a player's IP address (IPv4 or IPv6) */
    public static String getPlayerIP(Player player) {
        return (String.valueOf(player.getAddress().getAddress())).substring(1); // Removes the first slash (/)
    }
}

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

    private static final BanList ipBans = Bukkit.getServer().getBanList(BanList.Type.IP);
    private static final BanList nameBans = Bukkit.getServer().getBanList(BanList.Type.NAME);

    /**
     * Bans a player using the official Minecraft Names and IPs lists
     * @param playerName The name of the player
     * @param playerIP The hostname of the player
     * @param reason The reason of the ban
     */
    public static void banPlayer(String playerName, String playerIP, String reason) {
        nameBans.addBan(playerName, cc(reason), null, null);
        ipBans.addBan(playerIP, cc(reason), null, null);
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
        return player.getAddress().getAddress() + "";
    }
}

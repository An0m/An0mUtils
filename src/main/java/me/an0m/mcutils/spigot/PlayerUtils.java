package me.an0m.mcutils.spigot;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}
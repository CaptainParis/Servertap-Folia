package io.servertap.api.platform.bukkit.player;

import io.servertap.api.platform.player.PlayerManager;
import io.servertap.api.platform.player.ServerTapPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Bukkit implementation of the player manager
 */
public class BukkitPlayerManager implements PlayerManager {

    private final JavaPlugin plugin;

    /**
     * Create a new BukkitPlayerManager
     * @param plugin The Bukkit plugin instance
     */
    public BukkitPlayerManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<ServerTapPlayer> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .map(this::wrapPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServerTapPlayer> getOfflinePlayers() {
        List<ServerTapPlayer> players = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            players.add(new BukkitOfflinePlayer(offlinePlayer));
        }
        return players;
    }

    @Override
    public ServerTapPlayer getPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            return wrapPlayer(player);
        }
        
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.hasPlayedBefore()) {
            return new BukkitOfflinePlayer(offlinePlayer);
        }
        
        return null;
    }

    @Override
    public ServerTapPlayer getPlayer(String name) {
        Player player = Bukkit.getPlayerExact(name);
        if (player != null) {
            return wrapPlayer(player);
        }
        
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayerIfCached(name);
        if (offlinePlayer != null && offlinePlayer.hasPlayedBefore()) {
            return new BukkitOfflinePlayer(offlinePlayer);
        }
        
        return null;
    }

    @Override
    public boolean kickPlayer(UUID uuid, String reason) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.kickPlayer(reason);
            return true;
        }
        return false;
    }

    @Override
    public boolean sendMessage(UUID uuid, String message) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            player.sendMessage(message);
            return true;
        }
        return false;
    }

    @Override
    public void broadcastMessage(String message) {
        Bukkit.broadcastMessage(message);
    }

    /**
     * Wrap a Bukkit player in a ServerTapPlayer
     * @param player The Bukkit player
     * @return The wrapped player
     */
    private ServerTapPlayer wrapPlayer(Player player) {
        return new BukkitPlayer(player);
    }
}

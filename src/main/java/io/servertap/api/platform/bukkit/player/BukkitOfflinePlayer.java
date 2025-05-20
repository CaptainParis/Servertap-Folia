package io.servertap.api.platform.bukkit.player;

import io.servertap.api.platform.player.ServerTapPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Bukkit implementation of the offline player
 */
public class BukkitOfflinePlayer implements ServerTapPlayer {

    private final OfflinePlayer player;

    /**
     * Create a new BukkitOfflinePlayer
     * @param player The Bukkit offline player
     */
    public BukkitOfflinePlayer(OfflinePlayer player) {
        this.player = player;
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getDisplayName() {
        Player onlinePlayer = player.getPlayer();
        return onlinePlayer != null ? onlinePlayer.getDisplayName() : player.getName();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public String getIPAddress() {
        Player onlinePlayer = player.getPlayer();
        return onlinePlayer != null ? onlinePlayer.getAddress().getAddress().getHostAddress() : null;
    }

    @Override
    public int getPing() {
        Player onlinePlayer = player.getPlayer();
        return onlinePlayer != null ? onlinePlayer.getPing() : -1;
    }

    @Override
    public String getServerName() {
        // Not applicable for Bukkit
        return null;
    }

    @Override
    public void sendMessage(String message) {
        Player onlinePlayer = player.getPlayer();
        if (onlinePlayer != null) {
            onlinePlayer.sendMessage(message);
        }
    }

    @Override
    public void kick(String reason) {
        Player onlinePlayer = player.getPlayer();
        if (onlinePlayer != null) {
            onlinePlayer.kickPlayer(reason);
        }
    }

    @Override
    public boolean hasPermission(String permission) {
        Player onlinePlayer = player.getPlayer();
        return onlinePlayer != null && onlinePlayer.hasPermission(permission);
    }

    /**
     * Get the underlying Bukkit offline player
     * @return The Bukkit offline player
     */
    public OfflinePlayer getOfflinePlayer() {
        return player;
    }
}

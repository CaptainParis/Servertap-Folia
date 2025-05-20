package io.servertap.api.platform.bukkit.player;

import io.servertap.api.platform.player.ServerTapPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Bukkit implementation of the player
 */
public class BukkitPlayer implements ServerTapPlayer {

    private final Player player;

    /**
     * Create a new BukkitPlayer
     * @param player The Bukkit player
     */
    public BukkitPlayer(Player player) {
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
        return player.getDisplayName();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public String getIPAddress() {
        return player.getAddress().getAddress().getHostAddress();
    }

    @Override
    public int getPing() {
        return player.getPing();
    }

    @Override
    public String getServerName() {
        // Not applicable for Bukkit
        return null;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void kick(String reason) {
        player.kickPlayer(reason);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    /**
     * Get the underlying Bukkit player
     * @return The Bukkit player
     */
    public Player getPlayer() {
        return player;
    }
}

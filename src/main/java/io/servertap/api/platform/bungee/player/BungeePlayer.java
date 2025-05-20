package io.servertap.api.platform.bungee.player;

import io.servertap.api.platform.player.ServerTapPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

/**
 * BungeeCord implementation of the player
 */
public class BungeePlayer implements ServerTapPlayer {

    private final ProxiedPlayer player;

    /**
     * Create a new BungeePlayer
     * @param player The BungeeCord player
     */
    public BungeePlayer(ProxiedPlayer player) {
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
        return player.isConnected();
    }

    @Override
    public String getIPAddress() {
        return player.getSocketAddress().toString();
    }

    @Override
    public int getPing() {
        return player.getPing();
    }

    @Override
    public String getServerName() {
        return player.getServer() != null ? player.getServer().getInfo().getName() : null;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void kick(String reason) {
        player.disconnect(reason);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    /**
     * Get the underlying BungeeCord player
     * @return The BungeeCord player
     */
    public ProxiedPlayer getPlayer() {
        return player;
    }
}

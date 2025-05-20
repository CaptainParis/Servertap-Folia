package io.servertap.api.platform.velocity.player;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import io.servertap.api.platform.player.ServerTapPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.Optional;
import java.util.UUID;

/**
 * Velocity implementation of the player
 */
public class VelocityPlayer implements ServerTapPlayer {

    private final Player player;

    /**
     * Create a new VelocityPlayer
     * @param player The Velocity player
     */
    public VelocityPlayer(Player player) {
        this.player = player;
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public String getDisplayName() {
        // Velocity doesn't have a display name concept, so we use the username
        return player.getUsername();
    }

    @Override
    public boolean isOnline() {
        return player.isActive();
    }

    @Override
    public String getIPAddress() {
        return player.getRemoteAddress().toString();
    }

    @Override
    public int getPing() {
        return (int) player.getPing();
    }

    @Override
    public String getServerName() {
        Optional<ServerConnection> server = player.getCurrentServer();
        return server.map(serverConnection -> serverConnection.getServerInfo().getName()).orElse(null);
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(LegacyComponentSerializer.legacySection().deserialize(message));
    }

    @Override
    public void kick(String reason) {
        player.disconnect(Component.text(reason));
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    /**
     * Get the underlying Velocity player
     * @return The Velocity player
     */
    public Player getPlayer() {
        return player;
    }
}

package io.servertap.api.platform.player;

import java.util.List;
import java.util.UUID;

/**
 * Interface for platform-specific player management
 */
public interface PlayerManager {

    /**
     * Get a list of all online players
     * @return A list of all online players
     */
    List<ServerTapPlayer> getOnlinePlayers();

    /**
     * Get a list of all players who have ever played on the server
     * @return A list of all players
     */
    List<ServerTapPlayer> getOfflinePlayers();

    /**
     * Get a player by UUID
     * @param uuid The UUID of the player
     * @return The player, or null if not found
     */
    ServerTapPlayer getPlayer(UUID uuid);

    /**
     * Get a player by name
     * @param name The name of the player
     * @return The player, or null if not found
     */
    ServerTapPlayer getPlayer(String name);

    /**
     * Kick a player from the server
     * @param uuid The UUID of the player
     * @param reason The reason for the kick
     * @return True if the player was kicked, false otherwise
     */
    boolean kickPlayer(UUID uuid, String reason);

    /**
     * Send a message to a player
     * @param uuid The UUID of the player
     * @param message The message to send
     * @return True if the message was sent, false otherwise
     */
    boolean sendMessage(UUID uuid, String message);

    /**
     * Broadcast a message to all players
     * @param message The message to broadcast
     */
    void broadcastMessage(String message);
}

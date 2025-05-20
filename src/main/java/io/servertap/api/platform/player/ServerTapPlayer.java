package io.servertap.api.platform.player;

import java.util.UUID;

/**
 * Interface for platform-specific player representation
 */
public interface ServerTapPlayer {

    /**
     * Get the player's UUID
     * @return The player's UUID
     */
    UUID getUUID();

    /**
     * Get the player's name
     * @return The player's name
     */
    String getName();

    /**
     * Get the player's display name
     * @return The player's display name
     */
    String getDisplayName();

    /**
     * Check if the player is online
     * @return True if the player is online, false otherwise
     */
    boolean isOnline();

    /**
     * Get the player's IP address
     * @return The player's IP address
     */
    String getIPAddress();

    /**
     * Get the player's ping
     * @return The player's ping in milliseconds
     */
    int getPing();

    /**
     * Get the name of the server the player is connected to (for proxy platforms)
     * @return The server name, or null if not applicable
     */
    String getServerName();

    /**
     * Send a message to the player
     * @param message The message to send
     */
    void sendMessage(String message);

    /**
     * Kick the player from the server
     * @param reason The reason for the kick
     */
    void kick(String reason);

    /**
     * Check if the player has a permission
     * @param permission The permission to check
     * @return True if the player has the permission, false otherwise
     */
    boolean hasPermission(String permission);
}

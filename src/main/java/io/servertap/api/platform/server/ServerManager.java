package io.servertap.api.platform.server;

import java.util.List;

/**
 * Interface for platform-specific server management
 */
public interface ServerManager {

    /**
     * Get the server name
     * @return The server name
     */
    String getServerName();

    /**
     * Get the server version
     * @return The server version
     */
    String getServerVersion();

    /**
     * Get the server MOTD
     * @return The server MOTD
     */
    String getMotd();

    /**
     * Set the server MOTD
     * @param motd The new MOTD
     */
    void setMotd(String motd);

    /**
     * Get the maximum number of players allowed on the server
     * @return The maximum number of players
     */
    int getMaxPlayers();

    /**
     * Set the maximum number of players allowed on the server
     * @param maxPlayers The new maximum number of players
     */
    void setMaxPlayers(int maxPlayers);

    /**
     * Get the current TPS (ticks per second)
     * @return The current TPS
     */
    double getTPS();

    /**
     * Execute a console command
     * @param command The command to execute
     * @return The command output
     */
    String executeConsoleCommand(String command);

    /**
     * Get a list of all connected servers (for proxy platforms)
     * @return A list of all connected servers, or an empty list if not applicable
     */
    List<ServerInfo> getServers();

    /**
     * Get information about a specific server (for proxy platforms)
     * @param name The name of the server
     * @return The server information, or null if not found
     */
    ServerInfo getServer(String name);
}

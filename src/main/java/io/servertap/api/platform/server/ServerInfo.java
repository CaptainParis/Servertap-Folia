package io.servertap.api.platform.server;

/**
 * Class representing information about a server in a proxy network
 */
public class ServerInfo {
    private final String name;
    private final String address;
    private final boolean restricted;
    private final int playerCount;
    private final int maxPlayers;
    private final String motd;

    /**
     * Create a new ServerInfo instance
     * @param name The name of the server
     * @param address The address of the server
     * @param restricted Whether the server is restricted
     * @param playerCount The number of players on the server
     * @param maxPlayers The maximum number of players allowed on the server
     * @param motd The server MOTD
     */
    public ServerInfo(String name, String address, boolean restricted, int playerCount, int maxPlayers, String motd) {
        this.name = name;
        this.address = address;
        this.restricted = restricted;
        this.playerCount = playerCount;
        this.maxPlayers = maxPlayers;
        this.motd = motd;
    }

    /**
     * Get the name of the server
     * @return The server name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the address of the server
     * @return The server address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Check if the server is restricted
     * @return True if the server is restricted, false otherwise
     */
    public boolean isRestricted() {
        return restricted;
    }

    /**
     * Get the number of players on the server
     * @return The player count
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Get the maximum number of players allowed on the server
     * @return The maximum player count
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Get the server MOTD
     * @return The server MOTD
     */
    public String getMotd() {
        return motd;
    }
}

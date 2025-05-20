package io.servertap.api.platform.bungee.api;

import io.servertap.api.platform.bungee.BungeePlatform;

/**
 * API initializer for BungeeCord
 */
public class BungeeApiInitializer {

    private final BungeeServerApi serverApi;
    private final BungeePlayerApi playerApi;
    private final BungeePluginApi pluginApi;

    /**
     * Create a new BungeeApiInitializer
     * @param platform The BungeeCord platform instance
     */
    public BungeeApiInitializer(BungeePlatform platform) {
        this.serverApi = new BungeeServerApi(platform);
        this.playerApi = new BungeePlayerApi(platform);
        this.pluginApi = new BungeePluginApi(platform);
    }

    /**
     * Get the server API
     * @return The server API
     */
    public BungeeServerApi getServerApi() {
        return serverApi;
    }

    /**
     * Get the player API
     * @return The player API
     */
    public BungeePlayerApi getPlayerApi() {
        return playerApi;
    }

    /**
     * Get the plugin API
     * @return The plugin API
     */
    public BungeePluginApi getPluginApi() {
        return pluginApi;
    }
}

package io.servertap.api.platform.velocity.api;

import io.servertap.api.platform.velocity.VelocityPlatform;

/**
 * API initializer for Velocity
 */
public class VelocityApiInitializer {

    private final VelocityServerApi serverApi;
    private final VelocityPlayerApi playerApi;
    private final VelocityPluginApi pluginApi;

    /**
     * Create a new VelocityApiInitializer
     * @param platform The Velocity platform instance
     */
    public VelocityApiInitializer(VelocityPlatform platform) {
        this.serverApi = new VelocityServerApi(platform);
        this.playerApi = new VelocityPlayerApi(platform);
        this.pluginApi = new VelocityPluginApi(platform);
    }

    /**
     * Get the server API
     * @return The server API
     */
    public VelocityServerApi getServerApi() {
        return serverApi;
    }

    /**
     * Get the player API
     * @return The player API
     */
    public VelocityPlayerApi getPlayerApi() {
        return playerApi;
    }

    /**
     * Get the plugin API
     * @return The plugin API
     */
    public VelocityPluginApi getPluginApi() {
        return pluginApi;
    }
}

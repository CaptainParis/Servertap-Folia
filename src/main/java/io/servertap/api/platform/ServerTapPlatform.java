package io.servertap.api.platform;

import io.servertap.api.platform.config.ServerTapConfig;
import io.servertap.api.platform.events.EventManager;
import io.servertap.api.platform.logging.ServerTapLogger;
import io.servertap.api.platform.player.PlayerManager;
import io.servertap.api.platform.plugin.PluginManager;
import io.servertap.api.platform.scheduler.SchedulerAdapter;
import io.servertap.api.platform.server.ServerManager;

import java.nio.file.Path;

/**
 * Main interface for platform-specific implementations.
 * This provides access to all platform-specific functionality.
 */
public interface ServerTapPlatform {

    /**
     * Get the type of platform this is running on
     * @return The platform type
     */
    PlatformType getPlatformType();

    /**
     * Get the platform name (e.g., "Paper", "BungeeCord", "Velocity")
     * @return The platform name
     */
    String getPlatformName();

    /**
     * Get the platform version
     * @return The platform version
     */
    String getPlatformVersion();

    /**
     * Get the plugin's data folder
     * @return The plugin's data folder
     */
    Path getDataFolder();

    /**
     * Get the plugin's logger
     * @return The plugin's logger
     */
    ServerTapLogger getLogger();

    /**
     * Get the scheduler adapter for this platform
     * @return The scheduler adapter
     */
    SchedulerAdapter getScheduler();

    /**
     * Get the event manager for this platform
     * @return The event manager
     */
    EventManager getEventManager();

    /**
     * Get the player manager for this platform
     * @return The player manager
     */
    PlayerManager getPlayerManager();

    /**
     * Get the server manager for this platform
     * @return The server manager
     */
    ServerManager getServerManager();

    /**
     * Get the plugin manager for this platform
     * @return The plugin manager
     */
    PluginManager getPluginManager();

    /**
     * Get the configuration for this platform
     * @return The configuration
     */
    ServerTapConfig getConfig();

    /**
     * Initialize the platform
     */
    void initialize();

    /**
     * Shutdown the platform
     */
    void shutdown();
}

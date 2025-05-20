package io.servertap.api.platform.velocity;

import io.servertap.api.platform.PlatformType;
import io.servertap.api.platform.ServerTapPlatform;
import io.servertap.api.platform.config.ServerTapConfig;
import io.servertap.api.platform.events.EventManager;
import io.servertap.api.platform.logging.ServerTapLogger;
import io.servertap.api.platform.player.PlayerManager;
import io.servertap.api.platform.plugin.PluginManager;
import io.servertap.api.platform.scheduler.SchedulerAdapter;
import io.servertap.api.platform.server.ServerManager;
import io.servertap.api.platform.velocity.config.VelocityConfig;
import io.servertap.api.platform.velocity.events.VelocityEventManager;
import io.servertap.api.platform.velocity.logging.VelocityLogger;
import io.servertap.api.platform.velocity.player.VelocityPlayerManager;
import io.servertap.api.platform.velocity.plugin.VelocityPluginManager;
import io.servertap.api.platform.velocity.scheduler.VelocitySchedulerAdapter;
import io.servertap.api.platform.velocity.server.VelocityServerManager;
import io.servertap.velocity.VelocityServerTapMain;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Velocity implementation of the ServerTapPlatform interface
 */
public class VelocityPlatform implements ServerTapPlatform {

    private final VelocityServerTapMain plugin;
    private final ServerTapLogger logger;
    private final SchedulerAdapter scheduler;
    private final EventManager eventManager;
    private final PlayerManager playerManager;
    private final ServerManager serverManager;
    private final PluginManager pluginManager;
    private final ServerTapConfig config;
    private VelocityWebServer webServer;

    /**
     * Create a new VelocityPlatform instance
     * @param plugin The Velocity plugin instance
     * @param logger The logger instance
     */
    public VelocityPlatform(Object plugin, Logger logger) {
        this.plugin = (VelocityServerTapMain) plugin;
        this.logger = new VelocityLogger(this.plugin.getLogger());
        this.scheduler = new VelocitySchedulerAdapter(this.plugin);
        this.eventManager = new VelocityEventManager(this.plugin);
        this.playerManager = new VelocityPlayerManager(this.plugin);
        this.serverManager = new VelocityServerManager(this.plugin);
        this.pluginManager = new VelocityPluginManager(this.plugin);
        this.config = new VelocityConfig(this.plugin);
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.VELOCITY;
    }

    @Override
    public String getPlatformName() {
        return "Velocity";
    }

    @Override
    public String getPlatformVersion() {
        return plugin.getServer().getVersion().getVersion();
    }

    @Override
    public Path getDataFolder() {
        return plugin.getDataDirectory();
    }

    @Override
    public ServerTapLogger getLogger() {
        return logger;
    }

    @Override
    public SchedulerAdapter getScheduler() {
        return scheduler;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Override
    public ServerManager getServerManager() {
        return serverManager;
    }

    @Override
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public ServerTapConfig getConfig() {
        return config;
    }

    @Override
    public void initialize() {
        // Initialize the web server
        webServer = new VelocityWebServer(this);
        webServer.start();
        
        // Register event listeners
        eventManager.registerWebhookListeners();
        
        logger.info("ServerTap platform initialized for Velocity");
    }

    @Override
    public void shutdown() {
        // Shutdown the web server
        if (webServer != null) {
            webServer.stop();
        }
        
        logger.info("ServerTap platform shutdown for Velocity");
    }

    /**
     * Get the Velocity plugin instance
     * @return The plugin instance
     */
    public VelocityServerTapMain getPlugin() {
        return plugin;
    }
}

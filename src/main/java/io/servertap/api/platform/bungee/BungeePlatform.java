package io.servertap.api.platform.bungee;

import io.servertap.api.platform.PlatformType;
import io.servertap.api.platform.ServerTapPlatform;
import io.servertap.api.platform.bungee.config.BungeeConfig;
import io.servertap.api.platform.bungee.events.BungeeEventManager;
import io.servertap.api.platform.bungee.logging.BungeeLogger;
import io.servertap.api.platform.bungee.player.BungeePlayerManager;
import io.servertap.api.platform.bungee.plugin.BungeePluginManager;
import io.servertap.api.platform.bungee.scheduler.BungeeSchedulerAdapter;
import io.servertap.api.platform.bungee.server.BungeeServerManager;
import io.servertap.api.platform.config.ServerTapConfig;
import io.servertap.api.platform.events.EventManager;
import io.servertap.api.platform.logging.ServerTapLogger;
import io.servertap.api.platform.player.PlayerManager;
import io.servertap.api.platform.plugin.PluginManager;
import io.servertap.api.platform.scheduler.SchedulerAdapter;
import io.servertap.api.platform.server.ServerManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * BungeeCord implementation of the ServerTapPlatform interface
 */
public class BungeePlatform implements ServerTapPlatform {

    private final Plugin plugin;
    private final ServerTapLogger logger;
    private final SchedulerAdapter scheduler;
    private final EventManager eventManager;
    private final PlayerManager playerManager;
    private final ServerManager serverManager;
    private final PluginManager pluginManager;
    private final ServerTapConfig config;
    private BungeeWebServer webServer;

    /**
     * Create a new BungeePlatform instance
     * @param plugin The BungeeCord plugin instance
     * @param logger The logger instance
     */
    public BungeePlatform(Plugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = new BungeeLogger(logger);
        this.scheduler = new BungeeSchedulerAdapter(plugin);
        this.eventManager = new BungeeEventManager(plugin);
        this.playerManager = new BungeePlayerManager(plugin);
        this.serverManager = new BungeeServerManager(plugin);
        this.pluginManager = new BungeePluginManager(plugin);
        this.config = new BungeeConfig(plugin);
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.BUNGEECORD;
    }

    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }

    @Override
    public String getPlatformVersion() {
        return plugin.getProxy().getVersion();
    }

    @Override
    public Path getDataFolder() {
        return plugin.getDataFolder().toPath();
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
        webServer = new BungeeWebServer(this);
        webServer.start();
        
        // Register event listeners
        eventManager.registerWebhookListeners();
        
        logger.info("ServerTap platform initialized for BungeeCord");
    }

    @Override
    public void shutdown() {
        // Shutdown the web server
        if (webServer != null) {
            webServer.stop();
        }
        
        logger.info("ServerTap platform shutdown for BungeeCord");
    }

    /**
     * Get the BungeeCord plugin instance
     * @return The plugin instance
     */
    public Plugin getPlugin() {
        return plugin;
    }
}

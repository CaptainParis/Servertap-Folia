package io.servertap.api.platform.bukkit;

import io.servertap.ServerTapMain;
import io.servertap.api.platform.PlatformType;
import io.servertap.api.platform.ServerTapPlatform;
import io.servertap.api.platform.bukkit.config.BukkitConfig;
import io.servertap.api.platform.bukkit.events.BukkitEventManager;
import io.servertap.api.platform.bukkit.logging.BukkitLogger;
import io.servertap.api.platform.bukkit.player.BukkitPlayerManager;
import io.servertap.api.platform.bukkit.plugin.BukkitPluginManager;
import io.servertap.api.platform.bukkit.scheduler.BukkitSchedulerAdapter;
import io.servertap.api.platform.bukkit.server.BukkitServerManager;
import io.servertap.api.platform.config.ServerTapConfig;
import io.servertap.api.platform.events.EventManager;
import io.servertap.api.platform.logging.ServerTapLogger;
import io.servertap.api.platform.player.PlayerManager;
import io.servertap.api.platform.plugin.PluginManager;
import io.servertap.api.platform.scheduler.SchedulerAdapter;
import io.servertap.api.platform.server.ServerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;

/**
 * Bukkit implementation of the ServerTapPlatform interface
 */
public class BukkitPlatform implements ServerTapPlatform {

    private final JavaPlugin plugin;
    private final ServerTapLogger logger;
    private final SchedulerAdapter scheduler;
    private final EventManager eventManager;
    private final PlayerManager playerManager;
    private final ServerManager serverManager;
    private final PluginManager pluginManager;
    private final ServerTapConfig config;
    private BukkitWebServer webServer;

    /**
     * Create a new BukkitPlatform instance
     * @param plugin The Bukkit plugin instance
     * @param logger The logger instance
     */
    public BukkitPlatform(JavaPlugin plugin, Logger logger) {
        this.plugin = plugin;
        this.logger = new BukkitLogger(logger);
        this.scheduler = new BukkitSchedulerAdapter(plugin);
        this.eventManager = new BukkitEventManager(plugin);
        this.playerManager = new BukkitPlayerManager(plugin);
        this.serverManager = new BukkitServerManager(plugin);
        this.pluginManager = new BukkitPluginManager(plugin);
        this.config = new BukkitConfig(plugin);
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.BUKKIT;
    }

    @Override
    public String getPlatformName() {
        return plugin.getServer().getName();
    }

    @Override
    public String getPlatformVersion() {
        return plugin.getServer().getVersion();
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
        webServer = new BukkitWebServer((ServerTapMain) plugin);
        webServer.start();
        
        // Register event listeners
        eventManager.registerWebhookListeners();
        
        logger.info("ServerTap platform initialized for Bukkit");
    }

    @Override
    public void shutdown() {
        // Shutdown the web server
        if (webServer != null) {
            webServer.stop();
        }
        
        logger.info("ServerTap platform shutdown for Bukkit");
    }

    /**
     * Get the Bukkit plugin instance
     * @return The plugin instance
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }
}

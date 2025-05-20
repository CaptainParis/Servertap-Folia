package io.servertap.api.platform.bukkit;

import io.servertap.ServerTapMain;
import io.servertap.WebServer;
import io.servertap.WebServerRoutes;
import io.servertap.utils.LagDetector;
import io.servertap.utils.pluginwrappers.ExternalPluginWrapperRepo;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Wrapper for the existing WebServer class for Bukkit
 */
public class BukkitWebServer {

    private final ServerTapMain plugin;
    private WebServer webServer;

    /**
     * Create a new BukkitWebServer instance
     * @param plugin The Bukkit plugin instance
     */
    public BukkitWebServer(ServerTapMain plugin) {
        this.plugin = plugin;
    }

    /**
     * Start the web server
     */
    public void start() {
        FileConfiguration config = plugin.getConfig();
        webServer = new WebServer(plugin, config, Bukkit.getLogger());
        webServer.start(config.getInt("port", 4567));
        
        // Add routes
        WebServerRoutes.addV1Routes(
            plugin, 
            Bukkit.getLogger(), 
            new LagDetector(), 
            webServer, 
            plugin.getConsoleListener(), 
            new ExternalPluginWrapperRepo(plugin, Bukkit.getLogger())
        );
    }

    /**
     * Stop the web server
     */
    public void stop() {
        if (webServer != null) {
            webServer.stop();
        }
    }

    /**
     * Get the underlying WebServer instance
     * @return The WebServer instance
     */
    public WebServer getWebServer() {
        return webServer;
    }
}

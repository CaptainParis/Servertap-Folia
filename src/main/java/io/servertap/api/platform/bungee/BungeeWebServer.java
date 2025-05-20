package io.servertap.api.platform.bungee;

import io.servertap.WebServer;
import io.servertap.api.platform.bungee.api.BungeeApiInitializer;
import io.servertap.api.platform.bungee.routes.BungeeRouteBuilder;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

/**
 * BungeeCord implementation of the web server
 */
public class BungeeWebServer {

    private final BungeePlatform platform;
    private WebServer webServer;

    /**
     * Create a new BungeeWebServer instance
     * @param platform The BungeeCord platform instance
     */
    public BungeeWebServer(BungeePlatform platform) {
        this.platform = platform;
    }

    /**
     * Start the web server
     */
    public void start() {
        try {
            // Load configuration
            File configFile = new File(platform.getPlugin().getDataFolder(), "config.yml");
            if (!configFile.exists()) {
                platform.getPlugin().getDataFolder().mkdirs();
                try (InputStream in = platform.getPlugin().getResourceAsStream("config.yml")) {
                    Files.copy(in, configFile.toPath());
                } catch (IOException e) {
                    platform.getLogger().error("Failed to create default configuration", e);
                }
            }

            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            
            // Create web server
            webServer = new WebServer(null, null, platform.getPlugin().getLogger());
            webServer.start(config.getInt("port", 4567));
            
            // Add routes
            BungeeApiInitializer api = new BungeeApiInitializer(platform);
            BungeeRouteBuilder routeBuilder = new BungeeRouteBuilder("v1", webServer);
            
            // Add routes here
            routeBuilder.get("ping", api.getServerApi()::ping);
            routeBuilder.get("server", api.getServerApi()::serverGet);
            routeBuilder.get("players", api.getPlayerApi()::playersGet);
            routeBuilder.get("plugins", api.getPluginApi()::listPlugins);
            
        } catch (Exception e) {
            platform.getPlugin().getLogger().log(Level.SEVERE, "Failed to start web server", e);
        }
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

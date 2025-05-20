package io.servertap.api.platform.velocity;

import io.servertap.WebServer;
import io.servertap.api.platform.velocity.api.VelocityApiInitializer;
import io.servertap.api.platform.velocity.routes.VelocityRouteBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Map;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Velocity implementation of the web server
 */
public class VelocityWebServer {

    private final VelocityPlatform platform;
    private WebServer webServer;

    /**
     * Create a new VelocityWebServer instance
     * @param platform The Velocity platform instance
     */
    public VelocityWebServer(VelocityPlatform platform) {
        this.platform = platform;
    }

    /**
     * Start the web server
     */
    public void start() {
        try {
            // Load configuration
            File configFile = new File(platform.getDataFolder().toFile(), "config.yml");
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                try (InputStream in = platform.getPlugin().getClass().getResourceAsStream("/config.yml")) {
                    Files.copy(in, configFile.toPath());
                } catch (IOException e) {
                    platform.getLogger().error("Failed to create default configuration", e);
                }
            }

            // Load configuration using SnakeYAML
            Yaml yaml = new Yaml();
            Map<String, Object> config;
            try (FileReader reader = new FileReader(configFile)) {
                config = yaml.load(reader);
            }

            // Create web server
            webServer = new WebServer(null, null, new VelocityJavaLogger(platform.getPlugin().getLogger()));
            // Get port from config or use default
            int port = 4567;
            if (config != null && config.containsKey("port") && config.get("port") instanceof Integer) {
                port = (Integer) config.get("port");
            }
            webServer.start(port);

            // Add routes
            VelocityApiInitializer api = new VelocityApiInitializer(platform);
            VelocityRouteBuilder routeBuilder = new VelocityRouteBuilder("v1", webServer);

            // Add routes here
            routeBuilder.get("ping", api.getServerApi()::ping);
            routeBuilder.get("server", api.getServerApi()::serverGet);
            routeBuilder.get("players", api.getPlayerApi()::playersGet);
            routeBuilder.get("plugins", api.getPluginApi()::listPlugins);

        } catch (Exception e) {
            platform.getLogger().error("Failed to start web server", e);
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

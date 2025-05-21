package io.servertap.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.servertap.api.platform.PlatformFactory;
import io.servertap.api.platform.ServerTapPlatform;
import org.slf4j.Logger;

import java.nio.file.Path;

/**
 * Main class for the Velocity version of ServerTap
 */
@Plugin(
        id = "servertap",
        name = "ServerTap",
        version = "0.6.2-SNAPSHOT",
        description = "ServerTap is a REST API for Minecraft servers, including Velocity proxies.",
        url = "https://servertap.io",
        authors = {"phybros", "ATechAdventurer", "c1oneman", "Earlh21", "Scarsz", "au5ton", "Aberdeener",
                "Hedlund01", "matteoturini", "Diddyy", "Velyn-N", "srmullaney", "TimeCoding"}
)
public class VelocityServerTapMain {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private ServerTapPlatform platform;

    @Inject
    public VelocityServerTapMain(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // Initialize the platform
        platform = PlatformFactory.createPlatform(this, new VelocityLoggerAdapter(logger));
        platform.initialize();

        logger.info("ServerTap has been enabled on Velocity!");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        if (platform != null) {
            platform.shutdown();
        }

        logger.info("ServerTap has been disabled on Velocity!");
    }

    /**
     * Get the proxy server instance
     * @return The proxy server
     */
    public ProxyServer getServer() {
        return server;
    }

    /**
     * Get the logger instance
     * @return The logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Get the data directory
     * @return The data directory
     */
    public Path getDataDirectory() {
        return dataDirectory;
    }
}

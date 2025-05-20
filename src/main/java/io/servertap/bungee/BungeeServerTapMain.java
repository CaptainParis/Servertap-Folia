package io.servertap.bungee;

import io.servertap.api.platform.PlatformFactory;
import io.servertap.api.platform.ServerTapPlatform;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Main class for the BungeeCord version of ServerTap
 */
public class BungeeServerTapMain extends Plugin {

    private ServerTapPlatform platform;

    @Override
    public void onEnable() {
        // Initialize the platform
        platform = PlatformFactory.createPlatform(this, getLogger());
        platform.initialize();
        
        getLogger().info("ServerTap has been enabled on BungeeCord!");
    }

    @Override
    public void onDisable() {
        if (platform != null) {
            platform.shutdown();
        }
        
        getLogger().info("ServerTap has been disabled on BungeeCord!");
    }
}

package io.servertap.api.platform;

import java.util.logging.Logger;

/**
 * Factory class for creating platform-specific implementations
 */
public class PlatformFactory {
    
    private static ServerTapPlatform platform;
    
    /**
     * Detect the current platform and create the appropriate implementation
     * @param plugin The plugin instance
     * @param logger The logger instance
     * @return The platform implementation
     */
    public static ServerTapPlatform createPlatform(Object plugin, Logger logger) {
        if (platform != null) {
            return platform;
        }
        
        PlatformType type = detectPlatformType();
        
        switch (type) {
            case BUKKIT:
                try {
                    // Use reflection to avoid direct class reference which would cause ClassNotFoundException
                    Class<?> bukkitPlatformClass = Class.forName("io.servertap.api.platform.bukkit.BukkitPlatform");
                    platform = (ServerTapPlatform) bukkitPlatformClass.getConstructor(Class.forName("org.bukkit.plugin.java.JavaPlugin"), Logger.class)
                            .newInstance(plugin, logger);
                } catch (Exception e) {
                    logger.severe("Failed to create Bukkit platform: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case BUNGEECORD:
                try {
                    Class<?> bungeePlatformClass = Class.forName("io.servertap.api.platform.bungee.BungeePlatform");
                    platform = (ServerTapPlatform) bungeePlatformClass.getConstructor(Class.forName("net.md_5.bungee.api.plugin.Plugin"), Logger.class)
                            .newInstance(plugin, logger);
                } catch (Exception e) {
                    logger.severe("Failed to create BungeeCord platform: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            case VELOCITY:
                try {
                    Class<?> velocityPlatformClass = Class.forName("io.servertap.api.platform.velocity.VelocityPlatform");
                    platform = (ServerTapPlatform) velocityPlatformClass.getConstructor(Object.class, Logger.class)
                            .newInstance(plugin, logger);
                } catch (Exception e) {
                    logger.severe("Failed to create Velocity platform: " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            default:
                logger.severe("Unknown platform type: " + type);
                throw new UnsupportedOperationException("Unsupported platform type: " + type);
        }
        
        return platform;
    }
    
    /**
     * Get the current platform instance
     * @return The platform instance
     */
    public static ServerTapPlatform getPlatform() {
        if (platform == null) {
            throw new IllegalStateException("Platform not initialized. Call createPlatform() first.");
        }
        return platform;
    }
    
    /**
     * Detect the platform type based on available classes
     * @return The detected platform type
     */
    private static PlatformType detectPlatformType() {
        try {
            Class.forName("org.bukkit.Bukkit");
            return PlatformType.BUKKIT;
        } catch (ClassNotFoundException ignored) {}
        
        try {
            Class.forName("net.md_5.bungee.api.ProxyServer");
            return PlatformType.BUNGEECORD;
        } catch (ClassNotFoundException ignored) {}
        
        try {
            Class.forName("com.velocitypowered.api.proxy.ProxyServer");
            return PlatformType.VELOCITY;
        } catch (ClassNotFoundException ignored) {}
        
        return PlatformType.UNKNOWN;
    }
}

package io.servertap.api.platform.plugin;

import java.util.List;

/**
 * Interface for platform-specific plugin management
 */
public interface PluginManager {

    /**
     * Get a list of all plugins
     * @return A list of all plugins
     */
    List<PluginInfo> getPlugins();

    /**
     * Get a plugin by name
     * @param name The name of the plugin
     * @return The plugin, or null if not found
     */
    PluginInfo getPlugin(String name);

    /**
     * Enable a plugin
     * @param name The name of the plugin
     * @return True if the plugin was enabled, false otherwise
     */
    boolean enablePlugin(String name);

    /**
     * Disable a plugin
     * @param name The name of the plugin
     * @return True if the plugin was disabled, false otherwise
     */
    boolean disablePlugin(String name);

    /**
     * Reload a plugin
     * @param name The name of the plugin
     * @return True if the plugin was reloaded, false otherwise
     */
    boolean reloadPlugin(String name);

    /**
     * Install a plugin from a URL
     * @param url The URL to download the plugin from
     * @return True if the plugin was installed, false otherwise
     */
    boolean installPlugin(String url);
}

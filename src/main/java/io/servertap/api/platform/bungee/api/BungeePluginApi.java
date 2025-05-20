package io.servertap.api.platform.bungee.api;

import io.javalin.http.Context;
import io.servertap.api.platform.bungee.BungeePlatform;
import io.servertap.api.platform.plugin.PluginInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Plugin API for BungeeCord
 */
public class BungeePluginApi {

    private final BungeePlatform platform;

    /**
     * Create a new BungeePluginApi
     * @param platform The BungeeCord platform instance
     */
    public BungeePluginApi(BungeePlatform platform) {
        this.platform = platform;
    }

    /**
     * List all plugins
     * @param ctx The context
     */
    public void listPlugins(Context ctx) {
        List<Map<String, Object>> plugins = new ArrayList<>();
        
        for (PluginInfo plugin : platform.getPluginManager().getPlugins()) {
            Map<String, Object> pluginData = new HashMap<>();
            pluginData.put("name", plugin.getName());
            pluginData.put("version", plugin.getVersion());
            pluginData.put("description", plugin.getDescription());
            pluginData.put("authors", plugin.getAuthors());
            pluginData.put("website", plugin.getWebsite());
            pluginData.put("enabled", plugin.isEnabled());
            
            plugins.add(pluginData);
        }
        
        ctx.json(plugins);
    }
}

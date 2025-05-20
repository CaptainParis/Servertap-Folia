package io.servertap.api.platform.velocity.api;

import io.javalin.http.Context;
import io.servertap.api.platform.plugin.PluginInfo;
import io.servertap.api.platform.velocity.VelocityPlatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Plugin API for Velocity
 */
public class VelocityPluginApi {

    private final VelocityPlatform platform;

    /**
     * Create a new VelocityPluginApi
     * @param platform The Velocity platform instance
     */
    public VelocityPluginApi(VelocityPlatform platform) {
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

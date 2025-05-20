package io.servertap.api.platform.bungee.plugin;

import io.servertap.api.platform.plugin.PluginInfo;
import io.servertap.api.platform.plugin.PluginManager;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * BungeeCord implementation of the plugin manager
 */
public class BungeePluginManager implements PluginManager {

    private final Plugin plugin;

    /**
     * Create a new BungeePluginManager
     * @param plugin The BungeeCord plugin instance
     */
    public BungeePluginManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<PluginInfo> getPlugins() {
        List<PluginInfo> plugins = new ArrayList<>();
        
        for (Plugin p : plugin.getProxy().getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(
                p.getDescription().getName(),
                p.getDescription().getVersion(),
                p.getDescription().getDescription(),
                Collections.singletonList(p.getDescription().getAuthor()),
                "",
                true // BungeeCord doesn't have a concept of disabled plugins
            ));
        }
        
        return plugins;
    }

    @Override
    public PluginInfo getPlugin(String name) {
        for (Plugin p : plugin.getProxy().getPluginManager().getPlugins()) {
            if (p.getDescription().getName().equalsIgnoreCase(name)) {
                return new PluginInfo(
                    p.getDescription().getName(),
                    p.getDescription().getVersion(),
                    p.getDescription().getDescription(),
                    Collections.singletonList(p.getDescription().getAuthor()),
                    "",
                    true // BungeeCord doesn't have a concept of disabled plugins
                );
            }
        }
        return null;
    }

    @Override
    public boolean enablePlugin(String name) {
        // BungeeCord doesn't support enabling/disabling plugins at runtime
        plugin.getLogger().warning("Enabling plugins at runtime is not supported in BungeeCord");
        return false;
    }

    @Override
    public boolean disablePlugin(String name) {
        // BungeeCord doesn't support enabling/disabling plugins at runtime
        plugin.getLogger().warning("Disabling plugins at runtime is not supported in BungeeCord");
        return false;
    }

    @Override
    public boolean reloadPlugin(String name) {
        // BungeeCord doesn't support reloading plugins at runtime
        plugin.getLogger().warning("Reloading plugins at runtime is not supported in BungeeCord");
        return false;
    }

    @Override
    public boolean installPlugin(String url) {
        try {
            // Download the plugin
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            connection.setRequestProperty("User-Agent", "ServerTap");
            
            // Get the file name from the URL
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            if (!fileName.endsWith(".jar")) {
                fileName += ".jar";
            }
            
            // Create the plugins directory if it doesn't exist
            File pluginsDir = plugin.getProxy().getPluginsFolder();
            if (!pluginsDir.exists()) {
                pluginsDir.mkdirs();
            }
            
            // Save the plugin to the plugins directory
            File pluginFile = new File(pluginsDir, fileName);
            try (InputStream in = connection.getInputStream();
                 FileOutputStream out = new FileOutputStream(pluginFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            
            plugin.getLogger().info("Plugin downloaded successfully: " + fileName);
            plugin.getLogger().info("Restart the server to load the new plugin");
            
            return true;
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to install plugin: " + e.getMessage(), e);
            return false;
        }
    }
}

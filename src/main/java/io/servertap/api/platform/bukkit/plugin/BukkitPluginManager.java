package io.servertap.api.platform.bukkit.plugin;

import io.servertap.api.platform.plugin.PluginInfo;
import io.servertap.api.platform.plugin.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Bukkit implementation of the plugin manager
 */
public class BukkitPluginManager implements PluginManager {

    private final JavaPlugin plugin;

    /**
     * Create a new BukkitPluginManager
     * @param plugin The Bukkit plugin instance
     */
    public BukkitPluginManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<PluginInfo> getPlugins() {
        List<PluginInfo> plugins = new ArrayList<>();
        
        for (Plugin p : Bukkit.getPluginManager().getPlugins()) {
            plugins.add(new PluginInfo(
                p.getName(),
                p.getDescription().getVersion(),
                p.getDescription().getDescription(),
                Arrays.asList(p.getDescription().getAuthors().toArray(new String[0])),
                p.getDescription().getWebsite(),
                p.isEnabled()
            ));
        }
        
        return plugins;
    }

    @Override
    public PluginInfo getPlugin(String name) {
        Plugin p = Bukkit.getPluginManager().getPlugin(name);
        if (p != null) {
            return new PluginInfo(
                p.getName(),
                p.getDescription().getVersion(),
                p.getDescription().getDescription(),
                Arrays.asList(p.getDescription().getAuthors().toArray(new String[0])),
                p.getDescription().getWebsite(),
                p.isEnabled()
            );
        }
        return null;
    }

    @Override
    public boolean enablePlugin(String name) {
        Plugin p = Bukkit.getPluginManager().getPlugin(name);
        if (p != null && !p.isEnabled()) {
            Bukkit.getPluginManager().enablePlugin(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean disablePlugin(String name) {
        Plugin p = Bukkit.getPluginManager().getPlugin(name);
        if (p != null && p.isEnabled()) {
            Bukkit.getPluginManager().disablePlugin(p);
            return true;
        }
        return false;
    }

    @Override
    public boolean reloadPlugin(String name) {
        Plugin p = Bukkit.getPluginManager().getPlugin(name);
        if (p != null) {
            Bukkit.getPluginManager().disablePlugin(p);
            Bukkit.getPluginManager().enablePlugin(p);
            return true;
        }
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
            File pluginsDir = plugin.getServer().getPluginsFolder();
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

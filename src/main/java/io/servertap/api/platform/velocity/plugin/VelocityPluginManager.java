package io.servertap.api.platform.velocity.plugin;

import com.velocitypowered.api.plugin.PluginContainer;
import io.servertap.api.platform.plugin.PluginInfo;
import io.servertap.api.platform.plugin.PluginManager;
import io.servertap.velocity.VelocityServerTapMain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Velocity implementation of the plugin manager
 */
public class VelocityPluginManager implements PluginManager {

    private final VelocityServerTapMain plugin;

    /**
     * Create a new VelocityPluginManager
     * @param plugin The Velocity plugin instance
     */
    public VelocityPluginManager(VelocityServerTapMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<PluginInfo> getPlugins() {
        List<PluginInfo> plugins = new ArrayList<>();

        // Get all plugins from the plugin manager
        Collection<PluginContainer> pluginContainers = plugin.getServer().getPluginManager().getPlugins();
        for (PluginContainer container : pluginContainers) {
            // Get plugin description
            String name = container.getDescription().getName().orElse(container.getDescription().getId());
            String version = container.getDescription().getVersion().orElse("Unknown");
            String description = container.getDescription().getDescription().orElse("");
            // Get authors list
            List<String> authors = new ArrayList<>();
            container.getDescription().getAuthors().forEach(authors::add);
            String url = container.getDescription().getUrl().orElse("");
            plugins.add(new PluginInfo(
                name,
                version,
                description,
                authors,
                url,
                true // Velocity doesn't have a concept of disabled plugins
            ));
        }

        return plugins;
    }

    @Override
    public PluginInfo getPlugin(String name) {
        // Get plugin by ID
        Optional<PluginContainer> container = plugin.getServer().getPluginManager().getPlugin(name);
        if (container.isPresent()) {
            // Get plugin description
            String pluginName = container.get().getDescription().getName().orElse(container.get().getDescription().getId());
            String version = container.get().getDescription().getVersion().orElse("Unknown");
            String description = container.get().getDescription().getDescription().orElse("");
            // Get authors list
            List<String> authors = new ArrayList<>();
            container.get().getDescription().getAuthors().forEach(authors::add);
            String url = container.get().getDescription().getUrl().orElse("");

            return new PluginInfo(
                pluginName,
                version,
                description,
                authors,
                url,
                true // Velocity doesn't have a concept of disabled plugins
            );
        }
        return null;
    }

    @Override
    public boolean enablePlugin(String name) {
        // Velocity doesn't support enabling/disabling plugins at runtime
        plugin.getLogger().warn("Enabling plugins at runtime is not supported in Velocity");
        return false;
    }

    @Override
    public boolean disablePlugin(String name) {
        // Velocity doesn't support enabling/disabling plugins at runtime
        plugin.getLogger().warn("Disabling plugins at runtime is not supported in Velocity");
        return false;
    }

    @Override
    public boolean reloadPlugin(String name) {
        // Velocity doesn't support reloading plugins at runtime
        plugin.getLogger().warn("Reloading plugins at runtime is not supported in Velocity");
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
            File pluginsDir = new File("plugins");
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
            plugin.getLogger().error("Failed to install plugin: " + e.getMessage(), e);
            return false;
        }
    }
}

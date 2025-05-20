package io.servertap.api.platform.bukkit.config;

import io.servertap.api.platform.config.ServerTapConfig;
import io.servertap.api.platform.config.WebhookConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bukkit implementation of the configuration
 */
public class BukkitConfig implements ServerTapConfig {

    private final JavaPlugin plugin;

    /**
     * Create a new BukkitConfig
     * @param plugin The Bukkit plugin instance
     */
    public BukkitConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        
        // Save default config if it doesn't exist
        plugin.saveDefaultConfig();
    }

    @Override
    public int getPort() {
        return plugin.getConfig().getInt("port", 4567);
    }

    @Override
    public boolean isDebug() {
        return plugin.getConfig().getBoolean("debug", false);
    }

    @Override
    public boolean isAuthEnabled() {
        return plugin.getConfig().getBoolean("useKeyAuth", true);
    }

    @Override
    public String getAuthKey() {
        return plugin.getConfig().getString("key", "change_me");
    }

    @Override
    public boolean isTlsEnabled() {
        return plugin.getConfig().getBoolean("tls.enabled", false);
    }

    @Override
    public String getKeyStorePath() {
        return plugin.getConfig().getString("tls.keystore", "keystore.jks");
    }

    @Override
    public String getKeyStorePassword() {
        return plugin.getConfig().getString("tls.keystorePassword", "");
    }

    @Override
    public boolean isSniEnabled() {
        return plugin.getConfig().getBoolean("tls.sni", false);
    }

    @Override
    public List<String> getCorsOrigins() {
        List<String> corsOrigins = plugin.getConfig().getStringList("corsOrigins");
        if (corsOrigins.isEmpty()) {
            corsOrigins.add("*");
        }
        return corsOrigins;
    }

    @Override
    public List<String> getBlockedPaths() {
        return plugin.getConfig().getStringList("blocked-paths");
    }

    @Override
    public Map<String, WebhookConfig> getWebhooks() {
        Map<String, WebhookConfig> webhooks = new HashMap<>();
        
        ConfigurationSection webhooksSection = plugin.getConfig().getConfigurationSection("webhooks");
        if (webhooksSection != null) {
            for (String key : webhooksSection.getKeys(false)) {
                ConfigurationSection webhookSection = webhooksSection.getConfigurationSection(key);
                if (webhookSection != null) {
                    String listener = webhookSection.getString("listener");
                    List<String> events = webhookSection.getStringList("events");
                    
                    if (listener != null) {
                        webhooks.put(key, new WebhookConfig(listener, events));
                    }
                }
            }
        }
        
        return webhooks;
    }

    @Override
    public int getWebsocketConsoleBuffer() {
        return plugin.getConfig().getInt("websocketConsoleBuffer", 1000);
    }

    @Override
    public boolean isSwaggerDisabled() {
        return plugin.getConfig().getBoolean("disable-swagger", false);
    }

    @Override
    public void save() {
        plugin.saveConfig();
    }

    @Override
    public void reload() {
        plugin.reloadConfig();
    }
}

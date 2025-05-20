package io.servertap.api.platform.bungee.config;

import io.servertap.api.platform.config.ServerTapConfig;
import io.servertap.api.platform.config.WebhookConfig;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * BungeeCord implementation of the configuration
 */
public class BungeeConfig implements ServerTapConfig {

    private final Plugin plugin;
    private Configuration config;
    private final File configFile;

    /**
     * Create a new BungeeConfig
     * @param plugin The BungeeCord plugin instance
     */
    public BungeeConfig(Plugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        
        // Create the config file if it doesn't exist
        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to create default configuration", e);
            }
        }
        
        // Load the config
        reload();
    }

    @Override
    public int getPort() {
        return config.getInt("port", 4567);
    }

    @Override
    public boolean isDebug() {
        return config.getBoolean("debug", false);
    }

    @Override
    public boolean isAuthEnabled() {
        return config.getBoolean("useKeyAuth", true);
    }

    @Override
    public String getAuthKey() {
        return config.getString("key", "change_me");
    }

    @Override
    public boolean isTlsEnabled() {
        return config.getBoolean("tls.enabled", false);
    }

    @Override
    public String getKeyStorePath() {
        return config.getString("tls.keystore", "keystore.jks");
    }

    @Override
    public String getKeyStorePassword() {
        return config.getString("tls.keystorePassword", "");
    }

    @Override
    public boolean isSniEnabled() {
        return config.getBoolean("tls.sni", false);
    }

    @Override
    public List<String> getCorsOrigins() {
        return config.getStringList("corsOrigins");
    }

    @Override
    public List<String> getBlockedPaths() {
        return config.getStringList("blocked-paths");
    }

    @Override
    public Map<String, WebhookConfig> getWebhooks() {
        Map<String, WebhookConfig> webhooks = new HashMap<>();
        
        Configuration webhooksSection = config.getSection("webhooks");
        if (webhooksSection != null) {
            for (String key : webhooksSection.getKeys()) {
                Configuration webhookSection = webhooksSection.getSection(key);
                String listener = webhookSection.getString("listener");
                List<String> events = webhookSection.getStringList("events");
                
                webhooks.put(key, new WebhookConfig(listener, events));
            }
        }
        
        return webhooks;
    }

    @Override
    public int getWebsocketConsoleBuffer() {
        return config.getInt("websocketConsoleBuffer", 1000);
    }

    @Override
    public boolean isSwaggerDisabled() {
        return config.getBoolean("disable-swagger", false);
    }

    @Override
    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save configuration", e);
        }
    }

    @Override
    public void reload() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to load configuration", e);
            config = new Configuration();
        }
    }
}

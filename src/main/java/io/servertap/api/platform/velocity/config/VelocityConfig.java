package io.servertap.api.platform.velocity.config;

import io.servertap.api.platform.config.ServerTapConfig;
import io.servertap.api.platform.config.WebhookConfig;
import io.servertap.velocity.VelocityServerTapMain;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Velocity implementation of the configuration
 */
public class VelocityConfig implements ServerTapConfig {

    private final VelocityServerTapMain plugin;
    private Map<String, Object> config;
    private final File configFile;

    /**
     * Create a new VelocityConfig
     * @param plugin The Velocity plugin instance
     */
    public VelocityConfig(VelocityServerTapMain plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataDirectory().toFile(), "config.yml");

        // Create the config file if it doesn't exist
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try (InputStream in = plugin.getClass().getResourceAsStream("/config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException e) {
                plugin.getLogger().error("Failed to create default configuration", e);
            }
        }

        // Load the config
        reload();
    }

    @Override
    public int getPort() {
        if (config != null && config.containsKey("port") && config.get("port") instanceof Integer) {
            return (Integer) config.get("port");
        }
        return 4567;
    }

    @Override
    public boolean isDebug() {
        if (config != null && config.containsKey("debug") && config.get("debug") instanceof Boolean) {
            return (Boolean) config.get("debug");
        }
        return false;
    }

    @Override
    public boolean isAuthEnabled() {
        if (config != null && config.containsKey("useKeyAuth") && config.get("useKeyAuth") instanceof Boolean) {
            return (Boolean) config.get("useKeyAuth");
        }
        return true;
    }

    @Override
    public String getAuthKey() {
        if (config != null && config.containsKey("key") && config.get("key") instanceof String) {
            return (String) config.get("key");
        }
        return "change_me";
    }

    @Override
    public boolean isTlsEnabled() {
        if (config != null && config.containsKey("tls") && config.get("tls") instanceof Map) {
            Map<String, Object> tls = (Map<String, Object>) config.get("tls");
            if (tls.containsKey("enabled") && tls.get("enabled") instanceof Boolean) {
                return (Boolean) tls.get("enabled");
            }
        }
        return false;
    }

    @Override
    public String getKeyStorePath() {
        if (config != null && config.containsKey("tls") && config.get("tls") instanceof Map) {
            Map<String, Object> tls = (Map<String, Object>) config.get("tls");
            if (tls.containsKey("keystore") && tls.get("keystore") instanceof String) {
                return (String) tls.get("keystore");
            }
        }
        return "keystore.jks";
    }

    @Override
    public String getKeyStorePassword() {
        if (config != null && config.containsKey("tls") && config.get("tls") instanceof Map) {
            Map<String, Object> tls = (Map<String, Object>) config.get("tls");
            if (tls.containsKey("keystorePassword") && tls.get("keystorePassword") instanceof String) {
                return (String) tls.get("keystorePassword");
            }
        }
        return "";
    }

    @Override
    public boolean isSniEnabled() {
        if (config != null && config.containsKey("tls") && config.get("tls") instanceof Map) {
            Map<String, Object> tls = (Map<String, Object>) config.get("tls");
            if (tls.containsKey("sni") && tls.get("sni") instanceof Boolean) {
                return (Boolean) tls.get("sni");
            }
        }
        return false;
    }

    @Override
    public List<String> getCorsOrigins() {
        List<String> corsOrigins = new ArrayList<>();
        if (config != null && config.containsKey("corsOrigins") && config.get("corsOrigins") instanceof List) {
            List<Object> origins = (List<Object>) config.get("corsOrigins");
            for (Object origin : origins) {
                if (origin instanceof String) {
                    corsOrigins.add((String) origin);
                }
            }
        }
        if (corsOrigins.isEmpty()) {
            corsOrigins.add("*");
        }
        return corsOrigins;
    }

    @Override
    public List<String> getBlockedPaths() {
        List<String> blockedPaths = new ArrayList<>();
        if (config != null && config.containsKey("blocked-paths") && config.get("blocked-paths") instanceof List) {
            List<Object> paths = (List<Object>) config.get("blocked-paths");
            for (Object path : paths) {
                if (path instanceof String) {
                    blockedPaths.add((String) path);
                }
            }
        }
        return blockedPaths;
    }

    @Override
    public Map<String, WebhookConfig> getWebhooks() {
        Map<String, WebhookConfig> webhooks = new HashMap<>();

        if (config != null && config.containsKey("webhooks") && config.get("webhooks") instanceof Map) {
            Map<String, Object> webhooksMap = (Map<String, Object>) config.get("webhooks");
            for (Map.Entry<String, Object> entry : webhooksMap.entrySet()) {
                String key = entry.getKey();
                if (entry.getValue() instanceof Map) {
                    Map<String, Object> webhookMap = (Map<String, Object>) entry.getValue();

                    String listener = null;
                    if (webhookMap.containsKey("listener") && webhookMap.get("listener") instanceof String) {
                        listener = (String) webhookMap.get("listener");
                    }

                    List<String> events = new ArrayList<>();
                    if (webhookMap.containsKey("events") && webhookMap.get("events") instanceof List) {
                        List<Object> eventsList = (List<Object>) webhookMap.get("events");
                        for (Object event : eventsList) {
                            if (event instanceof String) {
                                events.add((String) event);
                            }
                        }
                    }

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
        if (config != null && config.containsKey("websocketConsoleBuffer") && config.get("websocketConsoleBuffer") instanceof Integer) {
            return (Integer) config.get("websocketConsoleBuffer");
        }
        return 1000;
    }

    @Override
    public boolean isSwaggerDisabled() {
        if (config != null && config.containsKey("disable-swagger") && config.get("disable-swagger") instanceof Boolean) {
            return (Boolean) config.get("disable-swagger");
        }
        return false;
    }

    @Override
    public void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            new Yaml().dump(config, writer);
        } catch (IOException e) {
            plugin.getLogger().error("Failed to save configuration", e);
        }
    }

    @Override
    public void reload() {
        try (FileReader reader = new FileReader(configFile)) {
            config = new Yaml().load(reader);
        } catch (IOException e) {
            plugin.getLogger().error("Failed to load configuration", e);
            config = new HashMap<>();
        }
    }
}

package io.servertap.api.platform.bungee.api;

import io.javalin.http.Context;
import io.servertap.api.platform.bungee.BungeePlatform;
import io.servertap.api.platform.server.ServerInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Server API for BungeeCord
 */
public class BungeeServerApi {

    private final BungeePlatform platform;

    /**
     * Create a new BungeeServerApi
     * @param platform The BungeeCord platform instance
     */
    public BungeeServerApi(BungeePlatform platform) {
        this.platform = platform;
    }

    /**
     * Ping endpoint
     * @param ctx The context
     */
    public void ping(Context ctx) {
        ctx.result("pong");
    }

    /**
     * Get server information
     * @param ctx The context
     */
    public void serverGet(Context ctx) {
        Map<String, Object> serverData = new HashMap<>();

        serverData.put("name", platform.getPlatformName());
        serverData.put("version", platform.getPlatformVersion());
        // BungeeCord doesn't have a direct getMotd() method, so we'll use a default value
        String motd = "BungeeCord Server";
        try {
            // Try to get the motd from the config file
            // We need to use reflection to access the internal configuration
            Object config = platform.getPlugin().getProxy().getConfig();
            java.lang.reflect.Field field = config.getClass().getDeclaredField("listeners");
            field.setAccessible(true);
            Object listeners = field.get(config);
            if (listeners instanceof java.util.Collection) {
                java.util.Collection<?> listenerCollection = (java.util.Collection<?>) listeners;
                if (!listenerCollection.isEmpty()) {
                    Object firstListener = listenerCollection.iterator().next();
                    java.lang.reflect.Field motdField = firstListener.getClass().getDeclaredField("motd");
                    motdField.setAccessible(true);
                    Object motdValue = motdField.get(firstListener);
                    if (motdValue != null) {
                        motd = motdValue.toString();
                    }
                }
            }
        } catch (Exception e) {
            // Fallback to default
            platform.getLogger().warning("Failed to get MOTD: " + e.getMessage());
        }
        serverData.put("motd", motd);
        serverData.put("onlinePlayers", platform.getPlugin().getProxy().getOnlineCount());
        serverData.put("maxPlayers", platform.getPlugin().getProxy().getConfig().getPlayerLimit());

        // Add connected servers
        List<Map<String, Object>> servers = platform.getServerManager().getServers().stream()
                .map(this::convertServerInfo)
                .collect(Collectors.toList());

        serverData.put("servers", servers);

        ctx.json(serverData);
    }

    /**
     * Convert ServerInfo to a map
     * @param serverInfo The server info
     * @return The map
     */
    private Map<String, Object> convertServerInfo(ServerInfo serverInfo) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", serverInfo.getName());
        map.put("address", serverInfo.getAddress());
        map.put("restricted", serverInfo.isRestricted());
        map.put("playerCount", serverInfo.getPlayerCount());
        map.put("maxPlayers", serverInfo.getMaxPlayers());
        map.put("motd", serverInfo.getMotd());
        return map;
    }
}

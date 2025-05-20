package io.servertap.api.platform.velocity.api;

import io.javalin.http.Context;
import io.servertap.api.platform.server.ServerInfo;
import io.servertap.api.platform.velocity.VelocityPlatform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Server API for Velocity
 */
public class VelocityServerApi {

    private final VelocityPlatform platform;

    /**
     * Create a new VelocityServerApi
     * @param platform The Velocity platform instance
     */
    public VelocityServerApi(VelocityPlatform platform) {
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
        serverData.put("onlinePlayers", platform.getPlugin().getServer().getPlayerCount());
        serverData.put("maxPlayers", -1); // Velocity doesn't have a global max players setting
        
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

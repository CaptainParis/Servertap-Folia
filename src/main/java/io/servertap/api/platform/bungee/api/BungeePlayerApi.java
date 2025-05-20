package io.servertap.api.platform.bungee.api;

import io.javalin.http.Context;
import io.servertap.api.platform.bungee.BungeePlatform;
import io.servertap.api.platform.player.ServerTapPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Player API for BungeeCord
 */
public class BungeePlayerApi {

    private final BungeePlatform platform;

    /**
     * Create a new BungeePlayerApi
     * @param platform The BungeeCord platform instance
     */
    public BungeePlayerApi(BungeePlatform platform) {
        this.platform = platform;
    }

    /**
     * Get all online players
     * @param ctx The context
     */
    public void playersGet(Context ctx) {
        List<Map<String, Object>> players = new ArrayList<>();
        
        for (ServerTapPlayer player : platform.getPlayerManager().getOnlinePlayers()) {
            Map<String, Object> playerData = new HashMap<>();
            playerData.put("uuid", player.getUUID().toString());
            playerData.put("name", player.getName());
            playerData.put("displayName", player.getDisplayName());
            playerData.put("ip", player.getIPAddress());
            playerData.put("ping", player.getPing());
            playerData.put("server", player.getServerName());
            
            players.add(playerData);
        }
        
        ctx.json(players);
    }
}

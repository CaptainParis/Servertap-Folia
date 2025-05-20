package io.servertap.api.platform.velocity.api;

import io.javalin.http.Context;
import io.servertap.api.platform.player.ServerTapPlayer;
import io.servertap.api.platform.velocity.VelocityPlatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Player API for Velocity
 */
public class VelocityPlayerApi {

    private final VelocityPlatform platform;

    /**
     * Create a new VelocityPlayerApi
     * @param platform The Velocity platform instance
     */
    public VelocityPlayerApi(VelocityPlatform platform) {
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

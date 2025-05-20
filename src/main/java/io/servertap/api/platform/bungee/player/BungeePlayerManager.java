package io.servertap.api.platform.bungee.player;

import io.servertap.api.platform.player.PlayerManager;
import io.servertap.api.platform.player.ServerTapPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * BungeeCord implementation of the player manager
 */
public class BungeePlayerManager implements PlayerManager {

    private final Plugin plugin;

    /**
     * Create a new BungeePlayerManager
     * @param plugin The BungeeCord plugin instance
     */
    public BungeePlayerManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<ServerTapPlayer> getOnlinePlayers() {
        return plugin.getProxy().getPlayers().stream()
                .map(this::wrapPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServerTapPlayer> getOfflinePlayers() {
        // BungeeCord doesn't store offline player data
        return new ArrayList<>();
    }

    @Override
    public ServerTapPlayer getPlayer(UUID uuid) {
        ProxiedPlayer player = plugin.getProxy().getPlayer(uuid);
        return player != null ? wrapPlayer(player) : null;
    }

    @Override
    public ServerTapPlayer getPlayer(String name) {
        ProxiedPlayer player = plugin.getProxy().getPlayer(name);
        return player != null ? wrapPlayer(player) : null;
    }

    @Override
    public boolean kickPlayer(UUID uuid, String reason) {
        ProxiedPlayer player = plugin.getProxy().getPlayer(uuid);
        if (player != null) {
            player.disconnect(reason);
            return true;
        }
        return false;
    }

    @Override
    public boolean sendMessage(UUID uuid, String message) {
        ProxiedPlayer player = plugin.getProxy().getPlayer(uuid);
        if (player != null) {
            player.sendMessage(message);
            return true;
        }
        return false;
    }

    @Override
    public void broadcastMessage(String message) {
        plugin.getProxy().broadcast(message);
    }

    /**
     * Wrap a BungeeCord player in a ServerTapPlayer
     * @param player The BungeeCord player
     * @return The wrapped player
     */
    private ServerTapPlayer wrapPlayer(ProxiedPlayer player) {
        return new BungeePlayer(player);
    }
}

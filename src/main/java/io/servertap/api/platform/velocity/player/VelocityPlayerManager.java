package io.servertap.api.platform.velocity.player;

import com.velocitypowered.api.proxy.Player;
import io.servertap.api.platform.player.PlayerManager;
import io.servertap.api.platform.player.ServerTapPlayer;
import io.servertap.velocity.VelocityServerTapMain;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Velocity implementation of the player manager
 */
public class VelocityPlayerManager implements PlayerManager {

    private final VelocityServerTapMain plugin;

    /**
     * Create a new VelocityPlayerManager
     * @param plugin The Velocity plugin instance
     */
    public VelocityPlayerManager(VelocityServerTapMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<ServerTapPlayer> getOnlinePlayers() {
        return plugin.getServer().getAllPlayers().stream()
                .map(this::wrapPlayer)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServerTapPlayer> getOfflinePlayers() {
        // Velocity doesn't store offline player data
        return new ArrayList<>();
    }

    @Override
    public ServerTapPlayer getPlayer(UUID uuid) {
        Optional<Player> player = plugin.getServer().getPlayer(uuid);
        return player.map(this::wrapPlayer).orElse(null);
    }

    @Override
    public ServerTapPlayer getPlayer(String name) {
        Optional<Player> player = plugin.getServer().getPlayer(name);
        return player.map(this::wrapPlayer).orElse(null);
    }

    @Override
    public boolean kickPlayer(UUID uuid, String reason) {
        Optional<Player> player = plugin.getServer().getPlayer(uuid);
        if (player.isPresent()) {
            player.get().disconnect(Component.text(reason));
            return true;
        }
        return false;
    }

    @Override
    public boolean sendMessage(UUID uuid, String message) {
        Optional<Player> player = plugin.getServer().getPlayer(uuid);
        if (player.isPresent()) {
            player.get().sendMessage(LegacyComponentSerializer.legacySection().deserialize(message));
            return true;
        }
        return false;
    }

    @Override
    public void broadcastMessage(String message) {
        plugin.getServer().sendMessage(LegacyComponentSerializer.legacySection().deserialize(message));
    }

    /**
     * Wrap a Velocity player in a ServerTapPlayer
     * @param player The Velocity player
     * @return The wrapped player
     */
    private ServerTapPlayer wrapPlayer(Player player) {
        return new VelocityPlayer(player);
    }
}

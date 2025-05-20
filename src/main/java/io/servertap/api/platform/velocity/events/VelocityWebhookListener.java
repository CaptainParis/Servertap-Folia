package io.servertap.api.platform.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import io.servertap.velocity.VelocityServerTapMain;

/**
 * Velocity webhook event listener
 */
public class VelocityWebhookListener {

    private final VelocityServerTapMain plugin;

    /**
     * Create a new VelocityWebhookListener
     * @param plugin The Velocity plugin instance
     */
    public VelocityWebhookListener(VelocityServerTapMain plugin) {
        this.plugin = plugin;
    }

    /**
     * Handle player login events
     * @param event The event
     */
    @Subscribe
    public void onPlayerLogin(PostLoginEvent event) {
        // TODO: Implement webhook for player login
        plugin.getLogger().info("Player " + event.getPlayer().getUsername() + " logged in");
    }

    /**
     * Handle player disconnect events
     * @param event The event
     */
    @Subscribe
    public void onPlayerDisconnect(DisconnectEvent event) {
        // TODO: Implement webhook for player disconnect
        plugin.getLogger().info("Player " + event.getPlayer().getUsername() + " disconnected");
    }

    /**
     * Handle server connected events
     * @param event The event
     */
    @Subscribe
    public void onServerConnected(ServerConnectedEvent event) {
        // TODO: Implement webhook for server connected
        plugin.getLogger().info("Player " + event.getPlayer().getUsername() + " connected to server " + event.getServer().getServerInfo().getName());
    }
}

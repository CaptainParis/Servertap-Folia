package io.servertap.api.platform.bungee.events;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

/**
 * BungeeCord webhook event listener
 */
public class BungeeWebhookListener implements Listener {

    private final Plugin plugin;

    /**
     * Create a new BungeeWebhookListener
     * @param plugin The BungeeCord plugin instance
     */
    public BungeeWebhookListener(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Handle player login events
     * @param event The event
     */
    @EventHandler
    public void onPlayerLogin(PostLoginEvent event) {
        // TODO: Implement webhook for player login
        plugin.getLogger().info("Player " + event.getPlayer().getName() + " logged in");
    }

    /**
     * Handle player disconnect events
     * @param event The event
     */
    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        // TODO: Implement webhook for player disconnect
        plugin.getLogger().info("Player " + event.getPlayer().getName() + " disconnected");
    }

    /**
     * Handle server connected events
     * @param event The event
     */
    @EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        // TODO: Implement webhook for server connected
        plugin.getLogger().info("Player " + event.getPlayer().getName() + " connected to server " + event.getServer().getInfo().getName());
    }
}

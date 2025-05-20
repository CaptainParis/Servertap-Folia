package io.servertap.api.platform.bukkit.events;

import io.servertap.api.platform.events.EventManager;
import io.servertap.webhooks.WebhookEventListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Bukkit implementation of the event manager
 */
public class BukkitEventManager implements EventManager {

    private final JavaPlugin plugin;
    private final List<Listener> listeners = new ArrayList<>();
    private WebhookEventListener webhookEventListener;

    /**
     * Create a new BukkitEventManager
     * @param plugin The Bukkit plugin instance
     */
    public BukkitEventManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerListener(Object listener) {
        if (listener instanceof Listener) {
            plugin.getServer().getPluginManager().registerEvents((Listener) listener, plugin);
            listeners.add((Listener) listener);
        } else {
            throw new IllegalArgumentException("Listener must implement org.bukkit.event.Listener");
        }
    }

    @Override
    public void unregisterListener(Object listener) {
        if (listener instanceof Listener) {
            org.bukkit.event.HandlerList.unregisterAll((Listener) listener);
            listeners.remove(listener);
        } else {
            throw new IllegalArgumentException("Listener must implement org.bukkit.event.Listener");
        }
    }

    @Override
    public void registerWebhookListeners() {
        // Use the existing webhook listener implementation
        try {
            // This is a simplified version - in a real implementation, you'd need to properly initialize the webhook listener
            FileConfiguration config = plugin.getConfig();
            Logger logger = plugin.getLogger();

            // For now, we'll create a simple listener that just logs events
            // In a real implementation, we'd need to properly initialize the webhook listener
            // with the economy wrapper and other dependencies

            // Create a simple listener for basic events
            Listener simpleListener = new Listener() {
                @org.bukkit.event.EventHandler
                public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
                    logger.info("Player " + event.getPlayer().getName() + " joined");
                }

                @org.bukkit.event.EventHandler
                public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
                    logger.info("Player " + event.getPlayer().getName() + " quit");
                }
            };

            registerListener(simpleListener);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to register webhook listeners: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

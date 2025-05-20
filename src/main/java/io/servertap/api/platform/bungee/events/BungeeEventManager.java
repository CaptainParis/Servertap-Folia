package io.servertap.api.platform.bungee.events;

import io.servertap.api.platform.events.EventManager;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * BungeeCord implementation of the event manager
 */
public class BungeeEventManager implements EventManager {

    private final Plugin plugin;
    private final List<Listener> listeners = new ArrayList<>();

    /**
     * Create a new BungeeEventManager
     * @param plugin The BungeeCord plugin instance
     */
    public BungeeEventManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerListener(Object listener) {
        if (listener instanceof Listener) {
            plugin.getProxy().getPluginManager().registerListener(plugin, (Listener) listener);
            listeners.add((Listener) listener);
        } else {
            throw new IllegalArgumentException("Listener must implement net.md_5.bungee.api.plugin.Listener");
        }
    }

    @Override
    public void unregisterListener(Object listener) {
        if (listener instanceof Listener) {
            plugin.getProxy().getPluginManager().unregisterListener((Listener) listener);
            listeners.remove(listener);
        } else {
            throw new IllegalArgumentException("Listener must implement net.md_5.bungee.api.plugin.Listener");
        }
    }

    @Override
    public void registerWebhookListeners() {
        // Register webhook listeners for BungeeCord events
        BungeeWebhookListener webhookListener = new BungeeWebhookListener(plugin);
        registerListener(webhookListener);
    }
}

package io.servertap.api.platform.velocity.events;

import io.servertap.api.platform.events.EventManager;
import io.servertap.velocity.VelocityServerTapMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Velocity implementation of the event manager
 */
public class VelocityEventManager implements EventManager {

    private final VelocityServerTapMain plugin;
    private final List<Object> listeners = new ArrayList<>();

    /**
     * Create a new VelocityEventManager
     * @param plugin The Velocity plugin instance
     */
    public VelocityEventManager(VelocityServerTapMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerListener(Object listener) {
        plugin.getServer().getEventManager().register(plugin, listener);
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(Object listener) {
        plugin.getServer().getEventManager().unregisterListener(plugin, listener);
        listeners.remove(listener);
    }

    @Override
    public void registerWebhookListeners() {
        // Register webhook listeners for Velocity events
        VelocityWebhookListener webhookListener = new VelocityWebhookListener(plugin);
        registerListener(webhookListener);
    }
}

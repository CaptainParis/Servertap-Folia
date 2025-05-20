package io.servertap.api.platform.events;

/**
 * Interface for platform-specific event management
 */
public interface EventManager {

    /**
     * Register an event listener
     * @param listener The listener to register
     */
    void registerListener(Object listener);

    /**
     * Unregister an event listener
     * @param listener The listener to unregister
     */
    void unregisterListener(Object listener);

    /**
     * Register all event listeners for the webhook system
     */
    void registerWebhookListeners();
}

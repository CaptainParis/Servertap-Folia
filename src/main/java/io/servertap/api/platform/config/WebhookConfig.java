package io.servertap.api.platform.config;

import java.util.List;

/**
 * Class representing a webhook configuration
 */
public class WebhookConfig {
    private final String listener;
    private final List<String> events;

    /**
     * Create a new WebhookConfig instance
     * @param listener The webhook listener URL
     * @param events The events to listen for
     */
    public WebhookConfig(String listener, List<String> events) {
        this.listener = listener;
        this.events = events;
    }

    /**
     * Get the webhook listener URL
     * @return The listener URL
     */
    public String getListener() {
        return listener;
    }

    /**
     * Get the events to listen for
     * @return The events
     */
    public List<String> getEvents() {
        return events;
    }
}

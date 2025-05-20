package io.servertap.api.platform.config;

import java.util.List;
import java.util.Map;

/**
 * Interface for platform-specific configuration
 */
public interface ServerTapConfig {

    /**
     * Get the port to run the web server on
     * @return The port
     */
    int getPort();

    /**
     * Check if debug mode is enabled
     * @return True if debug mode is enabled, false otherwise
     */
    boolean isDebug();

    /**
     * Check if key authentication is enabled
     * @return True if key authentication is enabled, false otherwise
     */
    boolean isAuthEnabled();

    /**
     * Get the authentication key
     * @return The authentication key
     */
    String getAuthKey();

    /**
     * Check if TLS is enabled
     * @return True if TLS is enabled, false otherwise
     */
    boolean isTlsEnabled();

    /**
     * Get the keystore path
     * @return The keystore path
     */
    String getKeyStorePath();

    /**
     * Get the keystore password
     * @return The keystore password
     */
    String getKeyStorePassword();

    /**
     * Check if SNI is enabled
     * @return True if SNI is enabled, false otherwise
     */
    boolean isSniEnabled();

    /**
     * Get the allowed CORS origins
     * @return The allowed CORS origins
     */
    List<String> getCorsOrigins();

    /**
     * Get the blocked paths
     * @return The blocked paths
     */
    List<String> getBlockedPaths();

    /**
     * Get the webhook configuration
     * @return The webhook configuration
     */
    Map<String, WebhookConfig> getWebhooks();

    /**
     * Get the number of console lines to buffer for websocket connections
     * @return The console buffer size
     */
    int getWebsocketConsoleBuffer();

    /**
     * Check if the Swagger UI is disabled
     * @return True if the Swagger UI is disabled, false otherwise
     */
    boolean isSwaggerDisabled();

    /**
     * Save the configuration
     */
    void save();

    /**
     * Reload the configuration
     */
    void reload();
}

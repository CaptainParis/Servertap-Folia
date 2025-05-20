package io.servertap.api.platform.logging;

/**
 * Interface for platform-specific logging
 */
public interface ServerTapLogger {

    /**
     * Log an info message
     * @param message The message to log
     */
    void info(String message);

    /**
     * Log a warning message
     * @param message The message to log
     */
    void warning(String message);

    /**
     * Log an error message
     * @param message The message to log
     */
    void error(String message);

    /**
     * Log a debug message (only shown when debug mode is enabled)
     * @param message The message to log
     */
    void debug(String message);

    /**
     * Log an error message with an exception
     * @param message The message to log
     * @param throwable The exception to log
     */
    void error(String message, Throwable throwable);
}

package io.servertap.api.platform.bukkit.logging;

import io.servertap.api.platform.logging.ServerTapLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bukkit implementation of the logger
 */
public class BukkitLogger implements ServerTapLogger {

    private final Logger logger;

    /**
     * Create a new BukkitLogger
     * @param logger The Bukkit logger
     */
    public BukkitLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warning(String message) {
        logger.warning(message);
    }

    @Override
    public void error(String message) {
        logger.severe(message);
    }

    @Override
    public void debug(String message) {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(message);
        }
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}

package io.servertap.api.platform.bungee.logging;

import io.servertap.api.platform.logging.ServerTapLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BungeeCord implementation of the logger
 */
public class BungeeLogger implements ServerTapLogger {

    private final Logger logger;

    /**
     * Create a new BungeeLogger
     * @param logger The BungeeCord logger
     */
    public BungeeLogger(Logger logger) {
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
        logger.fine(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}

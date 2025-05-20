package io.servertap.api.platform.velocity.logging;

import io.servertap.api.platform.logging.ServerTapLogger;
import org.slf4j.Logger;

/**
 * Velocity implementation of the logger
 */
public class VelocityLogger implements ServerTapLogger {

    private final Logger logger;

    /**
     * Create a new VelocityLogger
     * @param logger The Velocity logger
     */
    public VelocityLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warning(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}

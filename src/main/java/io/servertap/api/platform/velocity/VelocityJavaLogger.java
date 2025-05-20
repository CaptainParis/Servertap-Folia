package io.servertap.api.platform.velocity;

import org.slf4j.Logger;

import java.util.logging.Level;

/**
 * Adapter to convert SLF4J Logger to java.util.logging.Logger for WebServer compatibility
 */
public class VelocityJavaLogger extends java.util.logging.Logger {

    private final Logger logger;

    /**
     * Create a new VelocityJavaLogger
     * @param logger The SLF4J logger to adapt
     */
    public VelocityJavaLogger(Logger logger) {
        super("ServerTap", null);
        this.logger = logger;
    }

    @Override
    public void log(Level level, String msg) {
        if (level == Level.SEVERE) {
            logger.error(msg);
        } else if (level == Level.WARNING) {
            logger.warn(msg);
        } else if (level == Level.INFO) {
            logger.info(msg);
        } else if (level == Level.FINE || level == Level.FINER || level == Level.FINEST) {
            logger.debug(msg);
        } else {
            logger.info(msg);
        }
    }

    @Override
    public void log(Level level, String msg, Throwable thrown) {
        if (level == Level.SEVERE) {
            logger.error(msg, thrown);
        } else if (level == Level.WARNING) {
            logger.warn(msg, thrown);
        } else if (level == Level.INFO) {
            logger.info(msg, thrown);
        } else if (level == Level.FINE || level == Level.FINER || level == Level.FINEST) {
            logger.debug(msg, thrown);
        } else {
            logger.info(msg, thrown);
        }
    }
}

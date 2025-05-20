package io.servertap.api.platform.velocity.routes;

import io.javalin.http.Handler;
import io.javalin.websocket.WsConfig;
import io.servertap.WebServer;

import java.util.function.Consumer;

/**
 * Route builder for Velocity
 */
public class VelocityRouteBuilder {

    private final String prefix;
    private final WebServer webServer;

    /**
     * Create a new VelocityRouteBuilder
     * @param prefix The route prefix
     * @param webServer The web server
     */
    public VelocityRouteBuilder(String prefix, WebServer webServer) {
        this.prefix = prefix;
        this.webServer = webServer;
    }

    /**
     * Add a GET route
     * @param path The route path
     * @param handler The route handler
     */
    public void get(String path, Handler handler) {
        webServer.get(buildPath(path), handler);
    }

    /**
     * Add a POST route
     * @param path The route path
     * @param handler The route handler
     */
    public void post(String path, Handler handler) {
        webServer.post(buildPath(path), handler);
    }

    /**
     * Add a PUT route
     * @param path The route path
     * @param handler The route handler
     */
    public void put(String path, Handler handler) {
        webServer.put(buildPath(path), handler);
    }

    /**
     * Add a DELETE route
     * @param path The route path
     * @param handler The route handler
     */
    public void delete(String path, Handler handler) {
        webServer.delete(buildPath(path), handler);
    }

    /**
     * Add a WebSocket route
     * @param path The route path
     * @param wsConfig The WebSocket configuration
     */
    public void ws(String path, Consumer<WsConfig> wsConfig) {
        webServer.ws(buildPath(path), wsConfig);
    }

    /**
     * Build a path with the prefix
     * @param path The path
     * @return The path with the prefix
     */
    private String buildPath(String path) {
        return "/" + prefix + "/" + path;
    }
}

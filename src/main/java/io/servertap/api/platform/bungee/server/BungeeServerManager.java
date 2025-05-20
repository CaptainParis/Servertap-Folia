package io.servertap.api.platform.bungee.server;

import io.servertap.api.platform.server.ServerInfo;
import io.servertap.api.platform.server.ServerManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * BungeeCord implementation of the server manager
 */
public class BungeeServerManager implements ServerManager {

    private final Plugin plugin;

    /**
     * Create a new BungeeServerManager
     * @param plugin The BungeeCord plugin instance
     */
    public BungeeServerManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getServerName() {
        return "BungeeCord";
    }

    @Override
    public String getServerVersion() {
        return plugin.getProxy().getVersion();
    }

    @Override
    public String getMotd() {
        // BungeeCord doesn't have a direct getMotd() method, so we'll use a default value
        String motd = "BungeeCord Server";
        try {
            // Try to get the motd from the config file
            // We need to use reflection to access the internal configuration
            Object config = plugin.getProxy().getConfig();
            java.lang.reflect.Field field = config.getClass().getDeclaredField("listeners");
            field.setAccessible(true);
            Object listeners = field.get(config);
            if (listeners instanceof java.util.Collection) {
                java.util.Collection<?> listenerCollection = (java.util.Collection<?>) listeners;
                if (!listenerCollection.isEmpty()) {
                    Object firstListener = listenerCollection.iterator().next();
                    java.lang.reflect.Field motdField = firstListener.getClass().getDeclaredField("motd");
                    motdField.setAccessible(true);
                    Object motdValue = motdField.get(firstListener);
                    if (motdValue != null) {
                        motd = motdValue.toString();
                    }
                }
            }
        } catch (Exception e) {
            // Fallback to default
            plugin.getLogger().warning("Failed to get MOTD: " + e.getMessage());
        }
        return motd;
    }

    @Override
    public void setMotd(String motd) {
        // BungeeCord doesn't support changing the MOTD at runtime
        plugin.getLogger().warning("Changing MOTD at runtime is not supported in BungeeCord");
    }

    @Override
    public int getMaxPlayers() {
        return plugin.getProxy().getConfig().getPlayerLimit();
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        // BungeeCord doesn't support changing the max players at runtime
        plugin.getLogger().warning("Changing max players at runtime is not supported in BungeeCord");
    }

    @Override
    public double getTPS() {
        // BungeeCord doesn't have a TPS concept
        return -1;
    }

    @Override
    public String executeConsoleCommand(String command) {
        AtomicReference<String> result = new AtomicReference<>("");
        CompletableFuture<String> future = new CompletableFuture<>();

        CommandSender sender = new BungeeCommandSender(message -> {
            result.set(result.get() + message + "\n");
        });

        plugin.getProxy().getPluginManager().dispatchCommand(sender, command);

        // Schedule a task to complete the future after a short delay to allow command execution
        ScheduledTask task = plugin.getProxy().getScheduler().schedule(plugin, () -> {
            future.complete(result.get());
        }, 100, TimeUnit.MILLISECONDS);

        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to execute console command: " + e.getMessage());
            return "Error executing command: " + e.getMessage();
        } finally {
            task.cancel();
        }
    }

    @Override
    public List<ServerInfo> getServers() {
        List<ServerInfo> servers = new ArrayList<>();

        for (net.md_5.bungee.api.config.ServerInfo server : plugin.getProxy().getServers().values()) {
            servers.add(new ServerInfo(
                server.getName(),
                server.getSocketAddress().toString(),
                server.isRestricted(),
                server.getPlayers().size(),
                -1, // BungeeCord doesn't track max players per server
                server.getMotd()
            ));
        }

        return servers;
    }

    @Override
    public ServerInfo getServer(String name) {
        net.md_5.bungee.api.config.ServerInfo server = plugin.getProxy().getServerInfo(name);
        if (server != null) {
            return new ServerInfo(
                server.getName(),
                server.getSocketAddress().toString(),
                server.isRestricted(),
                server.getPlayers().size(),
                -1, // BungeeCord doesn't track max players per server
                server.getMotd()
            );
        }
        return null;
    }
}

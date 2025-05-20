package io.servertap.api.platform.velocity.server;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.servertap.api.platform.server.ServerInfo;
import io.servertap.api.platform.server.ServerManager;
import io.servertap.velocity.VelocityServerTapMain;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Velocity implementation of the server manager
 */
public class VelocityServerManager implements ServerManager {

    private final VelocityServerTapMain plugin;

    /**
     * Create a new VelocityServerManager
     * @param plugin The Velocity plugin instance
     */
    public VelocityServerManager(VelocityServerTapMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getServerName() {
        return "Velocity";
    }

    @Override
    public String getServerVersion() {
        return plugin.getServer().getVersion().getVersion();
    }

    @Override
    public String getMotd() {
        // Velocity doesn't have a global MOTD
        return "";
    }

    @Override
    public void setMotd(String motd) {
        // Velocity doesn't support changing the MOTD at runtime
        plugin.getLogger().warn("Changing MOTD at runtime is not supported in Velocity");
    }

    @Override
    public int getMaxPlayers() {
        // Velocity doesn't have a global max players setting
        return -1;
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        // Velocity doesn't support changing the max players at runtime
        plugin.getLogger().warn("Changing max players at runtime is not supported in Velocity");
    }

    @Override
    public double getTPS() {
        // Velocity doesn't have a TPS concept
        return -1;
    }

    @Override
    public String executeConsoleCommand(String command) {
        AtomicReference<String> result = new AtomicReference<>("");
        CompletableFuture<String> future = new CompletableFuture<>();
        
        CommandSource sender = new VelocityCommandSender(message -> {
            result.set(result.get() + message + "\n");
        });
        
        plugin.getServer().getCommandManager().executeAsync(sender, command);
        
        // Schedule a task to complete the future after a short delay to allow command execution
        plugin.getServer().getScheduler().buildTask(plugin, () -> {
            future.complete(result.get());
        }).delay(100, TimeUnit.MILLISECONDS).schedule();
        
        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            plugin.getLogger().error("Failed to execute console command: " + e.getMessage());
            return "Error executing command: " + e.getMessage();
        }
    }

    @Override
    public List<ServerInfo> getServers() {
        List<ServerInfo> servers = new ArrayList<>();
        
        for (RegisteredServer server : plugin.getServer().getAllServers()) {
            servers.add(new ServerInfo(
                server.getServerInfo().getName(),
                server.getServerInfo().getAddress().toString(),
                false, // Velocity doesn't have a concept of restricted servers
                server.getPlayersConnected().size(),
                -1, // Velocity doesn't track max players per server
                server.getServerInfo().getName() // Velocity doesn't track MOTD per server
            ));
        }
        
        return servers;
    }

    @Override
    public ServerInfo getServer(String name) {
        return plugin.getServer().getServer(name)
                .map(server -> new ServerInfo(
                    server.getServerInfo().getName(),
                    server.getServerInfo().getAddress().toString(),
                    false, // Velocity doesn't have a concept of restricted servers
                    server.getPlayersConnected().size(),
                    -1, // Velocity doesn't track max players per server
                    server.getServerInfo().getName() // Velocity doesn't track MOTD per server
                ))
                .orElse(null);
    }
}

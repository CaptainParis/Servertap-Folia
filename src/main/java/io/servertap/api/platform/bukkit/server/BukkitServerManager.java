package io.servertap.api.platform.bukkit.server;

import io.servertap.api.platform.server.ServerInfo;
import io.servertap.api.platform.server.ServerManager;
import io.servertap.utils.LagDetector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Bukkit implementation of the server manager
 */
public class BukkitServerManager implements ServerManager {

    private final JavaPlugin plugin;
    private final LagDetector lagDetector;

    /**
     * Create a new BukkitServerManager
     * @param plugin The Bukkit plugin instance
     */
    public BukkitServerManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.lagDetector = new LagDetector();
        
        // Start the TPS counter
        io.servertap.utils.SchedulerUtils.runRepeatingTask(plugin, lagDetector, 100, 1);
    }

    @Override
    public String getServerName() {
        return Bukkit.getName();
    }

    @Override
    public String getServerVersion() {
        return Bukkit.getVersion();
    }

    @Override
    public String getMotd() {
        return Bukkit.getMotd();
    }

    @Override
    public void setMotd(String motd) {
        Bukkit.setMotd(motd);
    }

    @Override
    public int getMaxPlayers() {
        return Bukkit.getMaxPlayers();
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        try {
            // This is a bit of a hack, but there's no direct API to set max players in Bukkit
            Object craftServer = Bukkit.getServer();
            java.lang.reflect.Method setMaxPlayers = craftServer.getClass().getMethod("setMaxPlayers", int.class);
            setMaxPlayers.invoke(craftServer, maxPlayers);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to set max players: " + e.getMessage(), e);
        }
    }

    @Override
    public double getTPS() {
        return lagDetector.getTPS();
    }

    @Override
    public String executeConsoleCommand(String command) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        
        PrintStream oldOut = System.out;
        System.setOut(printStream);
        
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            return outputStream.toString();
        } catch (Exception e) {
            return "Error executing command: " + e.getMessage();
        } finally {
            System.setOut(oldOut);
        }
    }

    @Override
    public List<ServerInfo> getServers() {
        // Not applicable for Bukkit
        return new ArrayList<>();
    }

    @Override
    public ServerInfo getServer(String name) {
        // Not applicable for Bukkit
        return null;
    }
}

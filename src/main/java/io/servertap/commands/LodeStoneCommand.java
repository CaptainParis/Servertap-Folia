package io.servertap.commands;

import io.servertap.ServerTapMain;
import io.servertap.gui.GUIManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * LodeStoneCommand - Command to open the Lode Stone GUI
 */
public class LodeStoneCommand implements CommandExecutor {
    
    private final ServerTapMain plugin;
    private final GUIManager guiManager;
    
    /**
     * Constructor for LodeStoneCommand
     * @param plugin The plugin instance
     * @param guiManager The GUI manager
     */
    public LodeStoneCommand(ServerTapMain plugin, GUIManager guiManager) {
        this.plugin = plugin;
        this.guiManager = guiManager;
        
        // Register the command
        plugin.getCommand("lodestone").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }
        
        Player player = (Player) sender;
        
        // Open the Lode Stone GUI
        guiManager.openLodeStoneGUI(player);
        
        return true;
    }
}

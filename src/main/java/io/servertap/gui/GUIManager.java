package io.servertap.gui;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * GUIManager - A class to manage GUI interactions
 * Provides a central point for accessing different GUIs
 */
public class GUIManager {
    
    private final Plugin plugin;
    private final LodeStoneGUI lodeStoneGUI;
    
    /**
     * Constructor for GUIManager
     * @param plugin The plugin instance
     */
    public GUIManager(Plugin plugin) {
        this.plugin = plugin;
        this.lodeStoneGUI = new LodeStoneGUI(plugin);
    }
    
    /**
     * Opens the Lode Stone GUI for a player
     * @param player The player to open the GUI for
     */
    public void openLodeStoneGUI(Player player) {
        lodeStoneGUI.openGUI(player);
    }
    
    /**
     * Updates the text display in the Lode Stone GUI for a player
     * @param player The player to update the text for
     * @param text The text to display
     */
    public void updateLodeStoneText(Player player, String text) {
        lodeStoneGUI.updateTextDisplay(player, text);
    }
    
    /**
     * Clears the text display in the Lode Stone GUI for a player
     * @param player The player to clear the text for
     */
    public void clearLodeStoneText(Player player) {
        lodeStoneGUI.clearTextDisplay(player);
    }
}

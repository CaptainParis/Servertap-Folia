package io.servertap.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * LodeStoneGUI - A class to handle Lode Stone GUI interactions
 * Provides functionality for opening GUIs, handling text display, and explosive buttons
 */
public class LodeStoneGUI implements Listener {
    
    private final Plugin plugin;
    private final Map<UUID, Inventory> openInventories = new HashMap<>();
    private final Map<UUID, List<String>> playerTextDisplays = new HashMap<>();
    
    // Constants for GUI
    private static final String GUI_TITLE = ChatColor.DARK_PURPLE + "Lode Stone";
    private static final int GUI_SIZE = 54; // 6 rows
    private static final int TEXT_DISPLAY_SLOT = 4;
    private static final int EXPLOSIVE_BUTTON_SLOT = 22;
    
    /**
     * Constructor for LodeStoneGUI
     * @param plugin The plugin instance
     */
    public LodeStoneGUI(Plugin plugin) {
        this.plugin = plugin;
        // Register events
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    /**
     * Opens the Lode Stone GUI for a player
     * @param player The player to open the GUI for
     */
    public void openGUI(Player player) {
        // Create inventory
        Inventory inventory = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE);
        
        // Set up the GUI items
        setupGUIItems(inventory);
        
        // Clear any existing text display for this player
        playerTextDisplays.put(player.getUniqueId(), new ArrayList<>());
        
        // Open the inventory for the player
        player.openInventory(inventory);
        
        // Store the open inventory
        openInventories.put(player.getUniqueId(), inventory);
        
        // Play sound
        player.playSound(player.getLocation(), Sound.BLOCK_LODESTONE_PLACE, 1.0f, 1.0f);
    }
    
    /**
     * Sets up the initial items in the GUI
     * @param inventory The inventory to set up
     */
    private void setupGUIItems(Inventory inventory) {
        // Create border items
        ItemStack borderItem = createItem(Material.GRAY_STAINED_GLASS_PANE, " ");
        
        // Set border items
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, borderItem); // Top row
            inventory.setItem(45 + i, borderItem); // Bottom row
        }
        
        // Set side borders
        for (int i = 0; i < 6; i++) {
            inventory.setItem(i * 9, borderItem); // Left side
            inventory.setItem(i * 9 + 8, borderItem); // Right side
        }
        
        // Create text display item
        ItemStack textDisplay = createItem(Material.PAPER, ChatColor.GOLD + "Text Display", 
                ChatColor.GRAY + "Information will appear here");
        inventory.setItem(TEXT_DISPLAY_SLOT, textDisplay);
        
        // Create explosive button
        ItemStack explosiveButton = createItem(Material.TNT, ChatColor.RED + "Explosive Button", 
                ChatColor.GRAY + "Click to activate");
        inventory.setItem(EXPLOSIVE_BUTTON_SLOT, explosiveButton);
    }
    
    /**
     * Helper method to create an item with a name and lore
     * @param material The material of the item
     * @param name The name of the item
     * @param lore The lore lines (optional)
     * @return The created ItemStack
     */
    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            
            if (lore.length > 0) {
                List<String> loreList = new ArrayList<>();
                for (String line : lore) {
                    loreList.add(line);
                }
                meta.setLore(loreList);
            }
            
            item.setItemMeta(meta);
        }
        return item;
    }
    
    /**
     * Updates the text display in the GUI for a player
     * @param player The player to update the text for
     * @param text The text to display
     */
    public void updateTextDisplay(Player player, String text) {
        UUID playerUUID = player.getUniqueId();
        
        // Get the player's inventory
        Inventory inventory = openInventories.get(playerUUID);
        if (inventory == null) {
            return;
        }
        
        // Get the current text display
        List<String> textDisplay = playerTextDisplays.getOrDefault(playerUUID, new ArrayList<>());
        textDisplay.add(text);
        
        // Keep only the last 5 lines
        if (textDisplay.size() > 5) {
            textDisplay = textDisplay.subList(textDisplay.size() - 5, textDisplay.size());
        }
        
        // Update the stored text display
        playerTextDisplays.put(playerUUID, textDisplay);
        
        // Update the item in the inventory
        ItemStack textItem = inventory.getItem(TEXT_DISPLAY_SLOT);
        if (textItem != null && textItem.getType() == Material.PAPER) {
            ItemMeta meta = textItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.GOLD + "Text Display");
                
                List<String> lore = new ArrayList<>();
                if (textDisplay.isEmpty()) {
                    lore.add(ChatColor.GRAY + "No information to display");
                } else {
                    for (String line : textDisplay) {
                        lore.add(ChatColor.WHITE + line);
                    }
                }
                
                meta.setLore(lore);
                textItem.setItemMeta(meta);
            }
        }
    }
    
    /**
     * Clears the text display for a player
     * @param player The player to clear the text for
     */
    public void clearTextDisplay(Player player) {
        UUID playerUUID = player.getUniqueId();
        
        // Clear the stored text display
        playerTextDisplays.put(playerUUID, new ArrayList<>());
        
        // Get the player's inventory
        Inventory inventory = openInventories.get(playerUUID);
        if (inventory == null) {
            return;
        }
        
        // Update the item in the inventory
        ItemStack textItem = inventory.getItem(TEXT_DISPLAY_SLOT);
        if (textItem != null && textItem.getType() == Material.PAPER) {
            ItemMeta meta = textItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.GOLD + "Text Display");
                
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "No information to display");
                
                meta.setLore(lore);
                textItem.setItemMeta(meta);
            }
        }
    }
    
    /**
     * Handles the explosive button click
     * @param player The player who clicked the button
     */
    private void handleExplosiveButtonClick(Player player) {
        // Play explosion sound
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
        
        // Update text display
        updateTextDisplay(player, "Explosive button activated!");
        
        // Visual effect - change button color temporarily
        Inventory inventory = openInventories.get(player.getUniqueId());
        if (inventory != null) {
            // Change to red concrete
            ItemStack activatedButton = createItem(Material.RED_CONCRETE, 
                    ChatColor.RED + "Explosive Button", 
                    ChatColor.GRAY + "Activated!");
            inventory.setItem(EXPLOSIVE_BUTTON_SLOT, activatedButton);
            
            // Schedule task to change it back
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (openInventories.containsKey(player.getUniqueId())) {
                        ItemStack explosiveButton = createItem(Material.TNT, 
                                ChatColor.RED + "Explosive Button", 
                                ChatColor.GRAY + "Click to activate");
                        inventory.setItem(EXPLOSIVE_BUTTON_SLOT, explosiveButton);
                    }
                }
            }.runTaskLater(plugin, 20L); // 1 second later
        }
    }
    
    /**
     * Event handler for inventory clicks
     * @param event The InventoryClickEvent
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        
        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        
        // Check if this is one of our inventories
        if (!openInventories.containsKey(playerUUID)) {
            return;
        }
        
        // Cancel the event to prevent item movement
        event.setCancelled(true);
        
        // Handle button clicks
        if (event.getRawSlot() == EXPLOSIVE_BUTTON_SLOT) {
            handleExplosiveButtonClick(player);
        }
    }
    
    /**
     * Event handler for inventory close
     * @param event The InventoryCloseEvent
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }
        
        Player player = (Player) event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        
        // Remove from our tracking maps
        openInventories.remove(playerUUID);
    }
}

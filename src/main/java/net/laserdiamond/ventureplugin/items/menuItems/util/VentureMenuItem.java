package net.laserdiamond.ventureplugin.items.menuItems.util;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * An abstract class representing all menu items. All lore on menu items is uniquely determined for each player
 * <p>
 * If lore is not set for the item, the lore will be blank
 * <p>
 * All menu items must be manually registered in the ItemRegistry class under playerMenuItemMap, otherwise lore will not appear
 */
public abstract class VentureMenuItem {

    public final VenturePlugin plugin;

    public VentureMenuItem(VenturePlugin plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Set the menu item
     * @return The menu item to be made
     */
    public abstract MenuItem menuItem();

    /**
     * Create the lore for the menu item
     * @param player The player who will be viewing/interacting with this menu item
     * @return The lore of the item
     */
    public abstract List<String> createLore(Player player);

    /**
     * Creates an instance of the menu item without lore. Used purely for registry
     * @return A new instance of the menu item without lore
     */
    private ItemForger createItem()
    {
        return new ItemForger(menuItem().getMaterial())
                .setName(menuItem().getDisplayName())
                .setCustomModelData(menuItem().getCustomModelData())
                .hideAllItemFlags();
    }

    /**
     * Creates an instance of the menu item
     * @param player The player who will be viewing/interacting with this menu item
     * @return A new instance of the menu item
     */
    public ItemForger createItem(Player player)
    {
        return new ItemForger(menuItem().getMaterial())
                .setName(menuItem().getDisplayName())
                .setCustomModelData(menuItem().getCustomModelData())
                .hideAllItemFlags()
                .setLore(createLore(player));
    }
}

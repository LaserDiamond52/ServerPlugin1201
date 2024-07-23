package net.laserdiamond.ventureplugin.items.menuItems.util;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

/**
 * An abstract class representing all menu items. All lore on menu items is uniquely determined for each player
 * <p>
 * If lore is not set for the item, the lore will be blank
 */
public abstract class VentureMenuItem {

    public final VenturePlugin plugin;

    public VentureMenuItem(VenturePlugin plugin)
    {
        this.plugin = plugin;
        String keyName = menuItem().getKeyName().toLowerCase().replace(" ", "_");
        HashMap<String, VentureMenuItem> ventureMenuItemMap = plugin.getVentureMenuItems();
        ventureMenuItemMap.put(keyName, this);
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
     * Creates an instance of the menu item
     * @param player The player who will be viewing/interacting with this menu item
     * @return A new instance of the menu item
     */
    public VentureItemBuilder createItem(Player player)
    {
        String keyName = menuItem().getKeyName().toLowerCase().replace(" ", "_");
        return new VentureItemBuilder(menuItem().getMaterial())
                .setName(menuItem().getDisplayName())
                .hideAllItemFlags()
                .setLore(createLore(player))
                .setMenuItemKey(keyName);
    }
}

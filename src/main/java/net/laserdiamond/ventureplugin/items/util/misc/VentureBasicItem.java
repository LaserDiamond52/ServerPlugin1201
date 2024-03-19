package net.laserdiamond.ventureplugin.items.util.misc;

import net.laserdiamond.ventureplugin.items.util.ItemForger;
import org.bukkit.entity.Player;

import java.util.List;

public interface VentureBasicItem {

    /**
     * Creates lore for the item
     * @return The lore for the item
     */
    List<String> createLore();

    /**
     * Creates player-defined lore for the item
     * @param player The player to base the lore off of
     * @return The player-defined lore for the item
     */
    List<String> createPlayerLore(Player player);

    /**
     * Creates the item as an ItemForger instance
     * @return the item as an ItemForger instance
     */
    ItemForger createMiscItem();
}

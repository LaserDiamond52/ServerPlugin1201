package net.laserdiamond.ventureplugin.items.misc;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * An abstract class that represents a miscellaneous item of this plugin
 */
public abstract class VentureMiscItem {

    protected final VenturePlugin plugin = VenturePlugin.getInstance();

    protected VentureMiscItem(VenturePlugin plugin)
    {

    }

    /**
     * The item's name as a string
     * @return The item's name as a string
     */
    protected abstract String itemName();

    /**
     * Determines the custom model data and the material of the item
     * @return VentureMiscMaterials
     */
    protected abstract VentureMiscMaterials ventureMiscMaterials();

    /**
     * The item's rarity
     * @return The rarity of the item (default is common)
     */
    public VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.COMMON;
    }

    /**
     * Determines if the item is unbreakable
     * @return True if the item is unbreakable, false otherwise
     */
    protected boolean isUnbreakable()
    {
        return false;
    }

    /**
     * Determines if the item is fire-resistant
     * @return True if the item is fire-resistant, false otherwise
     */
    protected boolean isFireResistant()
    {
        return false;
    }

    private int[] sigBits()
    {
        return new int[]{ventureMiscMaterials().getCustomModelData(), ventureMiscMaterials().getCustomModelData()};
    }

    /**
     * The player head skin for the item if it is a player head
     * @return The player head skin URL as a string. Returns an empty string if no URL string is set
     */
    protected String playerHeadSkin()
    {
        return "";
    }

    /**
     * Creates the lore for the item. Initially an empty LinkedList
     * @return The lore of the item as a LinkedList of Strings
     */
    public LinkedList<String> createLore()
    {
        return new LinkedList<>();
    }

    /**
     * Creates the lore for the item based on the player. Initially an empty LinkedList
     * @param player The player in possession of the item
     * @return The lore of the item as a LinkedList of Strings
     */
    public LinkedList<String> createPlayerLore(Player player)
    {
        return new LinkedList<>();
    }

    public VentureItemBuilder createItem()
    {
        String keyName = itemName().toLowerCase().replace(" ", "_");
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(ventureMiscMaterials().getMaterial(), 1)
                .setName(ItemStringBuilder.name(itemName(), 0))
                .setLore(createLore())
                .setRarity(rarity())
                .setUnbreakable(isUnbreakable())
                .setFireResistant(isFireResistant())
                .setItemKey(keyName)
                .setCustomModelData(ventureMiscMaterials().getCustomModelData());

        switch (ventureItemBuilder.getMaterial())
        {
            case PLAYER_HEAD -> ventureItemBuilder.setPlayerHeadSkin(playerHeadSkin(), sigBits()[0], sigBits()[1]);
        }

        return ventureItemBuilder;
    }

    /**
     * Registers the item be automatically refreshed when updated and to the giveitem command
     */
    void registerItem()
    {
        HashMap<String, VentureMiscItem> miscItemMap = plugin.getMiscItemMap();
        HashMap<String, VentureItemBuilder> itemRegistryMap = plugin.getItemRegistryMap();

        String keyName = itemName().toLowerCase().replace(" ", "_");

        if (!miscItemMap.containsKey(keyName))
        {
            miscItemMap.put(keyName, this);
        }
        if (!itemRegistryMap.containsKey(keyName))
        {
            itemRegistryMap.put(keyName, createItem());
        }
    }
}

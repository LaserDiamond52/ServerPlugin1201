package net.laserdiamond.ventureplugin.items.misc;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.util.Stars;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Base class for creating custom tools/equipment for plugin
 */
public abstract class VentureStatItem {

    protected final VenturePlugin plugin = VenturePlugin.getInstance();

    /**
     * The star stat bonus
     */
    protected final double starBonus = Stars.STARS.getBoostPerStar();

    /**
     * The item's rarity
     * @return The rarity of the item (default is common)
     */
    protected VentureItemRarity.Rarity rarity()
    {
        return VentureItemRarity.Rarity.COMMON;
    }

    /**
     * Determines if the armor set is unbreakable
     * @return If the armor set is unbreakable (default is false)
     */
    protected boolean isUnbreakable()
    {
        return false;
    }

    /**
     * Determines if the item is vulnerable to fire
     * @return If the item is vulnerable to fire (default is false)
     */
    protected boolean isFireResistant()
    {
        return false;
    }

    /**
     * Adds Venture Plugin item stat lore to the item
     * @param statMap The Venture item Stat map
     */
    protected LinkedList<String> createStatLore(HashMap<VentureItemStatKeys, Double> statMap)
    {
        LinkedList<String> baseLore = new LinkedList<>();
        baseLore.add(" ");
        if (statMap != null)
        {
            LinkedList<String> statLore = VentureItemBuilder.createStatLore(statMap);
            baseLore.addAll(statLore);
        }
        baseLore.addLast(" ");
        return baseLore;
    }
}

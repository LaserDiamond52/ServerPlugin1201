package net.laserdiamond.ventureplugin.items.util;

import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.util.Stars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class VentureStatItem {

    /**
     * The star stat bonus
     */
    public final double starBonus = Stars.STARS.getBoostPerStar();

    /**
     * The item's rarity
     * @return The rarity of the item (default is common)
     */
    public VentureItemRarity.Rarity rarity()
    {
        return VentureItemRarity.Rarity.COMMON;
    }

    /**
     * Determines if the armor set is unbreakable
     * @return If the armor set is unbreakable (default is false)
     */
    public boolean isUnbreakable()
    {
        return false;
    }

    /**
     * Determines if the item is vulnerable to fire
     * @return If the item is vulnerable to fire (default is false)
     */
    public boolean isFireResistant()
    {
        return false;
    }

    /**
     * Adds Venture Plugin item stat lore to the item
     * @param statMap The Venture item Stat map
     */
    public List<String> createStatLore(HashMap<VentureItemStatKeys, Double> statMap)
    {
        List<String> baseLore = new ArrayList<>();
        baseLore.add(" ");
        if (statMap != null)
        {
            List<String> statLore = ItemForger.createStatLore(statMap);
            baseLore.addAll(statLore);
        }
        baseLore.add(" ");
        return baseLore;
    }

    public boolean isStarrable()
    {
        return true;
    }
}

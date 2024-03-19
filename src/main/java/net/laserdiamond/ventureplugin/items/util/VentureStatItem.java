package net.laserdiamond.ventureplugin.items.util;

import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.util.Stars;

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
     * @param baseLore The base lore of which the stat lore will be applied to
     * @param statMap The Venture item Stat map
     */
    public void createStatLore(List<String> baseLore, HashMap<VentureItemStatKeys, Double> statMap)
    {
        baseLore.add(" ");
        if (statMap != null)
        {
            List<String> statLore = ItemForger.createStatLore(statMap);
            baseLore.addAll(statLore);
        }
    }

    /*
     * Sets the config class for the item
     * @param configClass The config class of the item(s)
     * @return The config class of the item(s)
     * @param <T>
     */
    /*public abstract <T extends YamlConfigFile<T>> T config();*/

    public abstract GetVarFile config();
}

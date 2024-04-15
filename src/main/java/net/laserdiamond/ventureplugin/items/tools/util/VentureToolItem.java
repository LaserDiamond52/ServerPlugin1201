package net.laserdiamond.ventureplugin.items.tools.util;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.misc.util.VentureStatItem;
import net.laserdiamond.ventureplugin.items.util.VentureItemStatKeys;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

/**
 * An abstract class that represents a full set of standard tools
 */
public abstract class VentureToolItem extends VentureStatItem {

    /**
     * Creates lore for the tool
     * @param toolTypes The type of tool item to make the lore for
     * @param stars The amount of stars the tool item has
     * @return The lore for the tool item
     */
    public abstract List<String> createLore(ToolTypes toolTypes, int stars);

    /**
     * Creates lore for the tool based off the Player
     * @param toolTypes The type of tool item to make the lore for
     * @param stars The amount of stars the tool item has
     * @return The player-defined lore for the tool item
     */
    public abstract List<String> createPlayerLore(Player player, ToolTypes toolTypes, int stars);

    /**
     * Creates attribute modifiers for the tool item
     * @param toolTypes The type of tool item to make the lore for
     * @param stars The amount of stars the tool item has
     * @return The attributes for the specified tool type
     */
    public abstract Multimap<Attribute, AttributeModifier> createAttributes(ToolTypes toolTypes, int stars);

    /**
     * Creates Venture Plugin item stats for the tool item
     * @param toolTypes The type of tool item to make the stats for
     * @param stars The amount of stars the tool item has
     * @return The Venture item stats for the tool
     */
    public abstract HashMap<VentureItemStatKeys, Double> createVentureStats(ToolTypes toolTypes, int stars);

    /**
     * Creates the tool item
     * @param toolTypes The type of tool to make
     * @param stars The amount of stars the tool item has
     * @return The tool item
     */
    public abstract ItemForger createTool(ToolTypes toolTypes, int stars);
}

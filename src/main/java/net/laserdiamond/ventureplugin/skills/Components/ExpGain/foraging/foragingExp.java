package net.laserdiamond.ventureplugin.skills.Components.ExpGain.foraging;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;

public class foragingExp {

    private static final HashMap<Material, Double> blockForagingExp = new HashMap<>();
    static
    {
        for (Material material : Material.values())
        {
            String name = material.name();

            if (name.contains("PLANKS"))
            {
                blockForagingExp.put(material, 20.0);
            } else if (name.contains("LOG"))
            {
                blockForagingExp.put(material, 25.0);
            } else if (name.contains("LEAVES"))
            {
                blockForagingExp.put(material, 12.0);
            } else if (name.contains("STEM"))
            {
                blockForagingExp.put(material, 30.75);
            } else if (name.contains("AZALEA"))
            {
                blockForagingExp.put(material, 17.0);
            } else if (name.contains("SAPLING"))
            {
                blockForagingExp.put(material, 8.5);
            } else if (name.contains("ROOTS"))
            {
                blockForagingExp.put(material, 17.25);
            }
        }
    }

    /**
     * Gets the foraging exp the block will drop when mined
     * @param block The block mined
     * @return The foraging exp the block will drop
     */
    public static Double getForagingExp(Block block)
    {
        Double exp = 0.0;
        Material blockType = block.getType();
        if (blockForagingExp.get(blockType) != null)
        {
            exp = blockForagingExp.get(blockType);
        }
        return exp;
    }
}

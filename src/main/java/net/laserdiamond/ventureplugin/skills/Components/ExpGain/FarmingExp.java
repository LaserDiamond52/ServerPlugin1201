package net.laserdiamond.ventureplugin.skills.Components.ExpGain;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;

public class FarmingExp {

    private static final HashMap<Material, Double> farmingBlockExp = new HashMap<>();
    static
    {
        // TODO: Add farming-related blocks/crops here
        farmingBlockExp.put(Material.NETHER_WART_BLOCK, 13.0);
        farmingBlockExp.put(Material.WARPED_WART_BLOCK, 13.0);
        farmingBlockExp.put(Material.HAY_BLOCK, 19.0);
        farmingBlockExp.put(Material.SCULK, 25.0);
        farmingBlockExp.put(Material.SCULK_VEIN, 15.0);
        farmingBlockExp.put(Material.SCULK_CATALYST, 60.0);
        farmingBlockExp.put(Material.SCULK_SHRIEKER, 53.5);
        farmingBlockExp.put(Material.SCULK_SENSOR, 47.0);
        farmingBlockExp.put(Material.MOSS_BLOCK, 10.0);
        farmingBlockExp.put(Material.SHROOMLIGHT, 14.0);
        farmingBlockExp.put(Material.SPONGE, 37.0);
        farmingBlockExp.put(Material.WET_SPONGE, 37.0);
    }

    public static Double getFarmingExp(Block block)
    {
        double exp = 0.0;
        Material blockMaterial = block.getType();

        if (blockMaterial == Material.POTATOES || blockMaterial == Material.WHEAT)
        {
            if (block.getData() == (byte) 7)
            {
                exp = 4.5;
            }
        } else if (blockMaterial == Material.NETHER_WART)
        {
            if (block.getData() == (byte) 3)
            {
                exp = 7.0;
            }
        } else if (farmingBlockExp.get(blockMaterial) != null)
        {
            // TODO: Check for crops and if they are fully grown
            return farmingBlockExp.get(blockMaterial);

        }
        return exp;
    }
}

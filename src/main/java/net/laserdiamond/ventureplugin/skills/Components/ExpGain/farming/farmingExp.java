package net.laserdiamond.ventureplugin.skills.Components.ExpGain.farming;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;

public class farmingExp {

    private static final HashMap<Material, Double> farmingBlockExp = new HashMap<>();
    static
    {
        // TODO: Add farming-related blocks/crops here
        farmingBlockExp.put(Material.NETHER_WART_BLOCK, 13.0);
        farmingBlockExp.put(Material.WARPED_WART_BLOCK, 13.0);
        farmingBlockExp.put(Material.HAY_BLOCK, 19.0);
        farmingBlockExp.put(Material.SCULK, 40.0);
        farmingBlockExp.put(Material.SCULK_VEIN, 15.0);
        farmingBlockExp.put(Material.MOSS_BLOCK, 10.0);
        farmingBlockExp.put(Material.SHROOMLIGHT, 14.0);

        //farmingBlockExp.put(Material.POTATOES, 4.5);
        //farmingBlockExp.put(Material.WHEAT, 4.5);

        //farmingBlockExp.put(Material.NETHER_WART, 7.0);
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

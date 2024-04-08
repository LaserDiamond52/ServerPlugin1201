package net.laserdiamond.ventureplugin.skills.Components.ExpGain.mining;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;

public class miningExp {

    private static final HashMap<Material, Double> blockMiningExp = new HashMap<>();
    static
    {
        for (Material material : Material.values())
        {
            String name = material.name();
            if (name.contains("DIRT"))
            {
                blockMiningExp.put(material, 4.0);
            } else if (name.contains("STONE")) // All blocks with "STONE" in their name
            {
                if (name.contains("SAND"))
                {
                    if (name.contains("RED"))
                    {
                        // TODO: Red sand
                        blockMiningExp.put(material, 3.0);
                    } else if (name.contains("SOUL"))
                    {
                        // TODO: Soul sand
                        blockMiningExp.put(material, 4.5);
                    } else
                    {
                        // TODO: Sand
                        blockMiningExp.put(material, 2.5);
                    }
                } else if (name.contains("BLACK"))
                {
                    // TODO: Blackstone
                    blockMiningExp.put(material, 17.0);
                } else if (name.contains("END"))
                {
                    // TODO: Endstone
                    blockMiningExp.put(material, 10.0);
                } else if (name.contains("GLOW"))
                {
                    // TODO: Glowstone
                    blockMiningExp.put(material, 25.0);
                } else // Any other blocks that contain "STONE" in their name
                {
                    // TODO: Other stone blocks
                    blockMiningExp.put(material, 10.0);
                }

            } else if (name.contains("DEEPSLATE"))
            {
                blockMiningExp.put(material, 15.75);
            } else if (name.contains("GRANITE"))
            {
                // TODO: Granite
                blockMiningExp.put(material, 14.5);
            } else if (name.contains("DIORITE"))
            {
                // TODO: Diorite
                blockMiningExp.put(material, 17.25);
            } else if (name.contains("ANDESITE"))
            {
                // TODO: Andesite
                blockMiningExp.put(material, 16.0);
            } else if (name.contains("QUARTZ"))
            {
                // TODO: Quartz
                blockMiningExp.put(material, 8.0);
            } else if (name.contains("PRISMARINE"))
            {
                if (name.contains("DARK"))
                {
                    // TODO: Dark Prismarine
                    blockMiningExp.put(material, 50.0);
                } else
                {
                    // TODO: Prismarine
                    blockMiningExp.put(material, 40.5);
                }
            } else if (name.contains("NETHER"))
            {
                if (name.contains("BRICKS"))
                {
                    if (name.contains("RED"))
                    {
                        // TODO: Red nether bricks
                        blockMiningExp.put(material, 17.0);
                    }
                    else
                    {
                        // TODO: Nether bricks
                        blockMiningExp.put(material, 15.5);
                    }
                } else
                {
                    // TODO: Netherrack
                    blockMiningExp.put(material, 2.75);
                }
            } else if (name.contains("BASALT"))
            {
                // TODO: Basalt
                blockMiningExp.put(material, 10.0);
            } else if (name.contains("PURPUR"))
            {
                // TODO: Purpur
                blockMiningExp.put(material, 23.0);
            } else if (name.contains("COPPER"))
            {
                // TODO: Copper blocks and their weathered variants
                blockMiningExp.put(material, 18.0);
            } else if (name.contains("TERRACOTTA"))
            {
                // TODO: Terracotta and its color variants
                blockMiningExp.put(material, 13.0);
            } else if (name.contains("CONCRETE"))
            {
                if (name.contains("POWDER"))
                {
                    // TODO: Concrete powder
                    blockMiningExp.put(material, 5.75);
                } else
                {
                    // TODO: Concrete
                    blockMiningExp.put(material, 12.5);
                }
            } else if (name.contains("GLASS"))
            {
                // TODO: Glass
                blockMiningExp.put(material, 16.0);
            }
            if (name.contains("MUD"))
            {
                // TODO: Mud
                blockMiningExp.put(material, 4.0);
            }
        }

        // TODO: Ice
        blockMiningExp.put(Material.ICE, 2.5);
        blockMiningExp.put(Material.PACKED_ICE, 4.5);
        blockMiningExp.put(Material.BLUE_ICE, 7.8);

        // TODO: Ores
        blockMiningExp.put(Material.COAL_ORE, 12.5);
        blockMiningExp.put(Material.DEEPSLATE_COAL_ORE, 17.0);

        blockMiningExp.put(Material.IRON_ORE, 13.5);
        blockMiningExp.put(Material.DEEPSLATE_IRON_ORE, 18.0);

        blockMiningExp.put(Material.COPPER_ORE, 13.5);
        blockMiningExp.put(Material.DEEPSLATE_COPPER_ORE, 18.0);

        blockMiningExp.put(Material.GOLD_ORE, 14.0);
        blockMiningExp.put(Material.DEEPSLATE_GOLD_ORE, 19.0);
        blockMiningExp.put(Material.NETHER_GOLD_ORE, 12.0);

        blockMiningExp.put(Material.REDSTONE_ORE, 15.0);
        blockMiningExp.put(Material.DEEPSLATE_REDSTONE_ORE, 21.0);

        blockMiningExp.put(Material.EMERALD_ORE, 18.0);
        blockMiningExp.put(Material.DEEPSLATE_EMERALD_ORE, 25.0);

        blockMiningExp.put(Material.LAPIS_LAZULI, 14.0);
        blockMiningExp.put(Material.DEEPSLATE_LAPIS_ORE, 19.0);

        blockMiningExp.put(Material.DIAMOND_ORE, 20.0);
        blockMiningExp.put(Material.DEEPSLATE_DIAMOND_ORE, 26.0);

        blockMiningExp.put(Material.NETHER_QUARTZ_ORE, 12.0);

        // TODO: Amethyst, Calcite
        blockMiningExp.put(Material.AMETHYST_BLOCK, 15.0);
        blockMiningExp.put(Material.BUDDING_AMETHYST, 13.5);

        blockMiningExp.put(Material.AMETHYST_CLUSTER, 8.5);
        blockMiningExp.put(Material.LARGE_AMETHYST_BUD, 7.5);
        blockMiningExp.put(Material.MEDIUM_AMETHYST_BUD, 6.5);
        blockMiningExp.put(Material.SMALL_AMETHYST_BUD, 5.5);

        // TODO: Magma
        blockMiningExp.put(Material.MAGMA_BLOCK, 3.75);

        // TODO: Obsidian
        blockMiningExp.put(Material.OBSIDIAN, 18.0);
        blockMiningExp.put(Material.CRYING_OBSIDIAN, 22.0);

        // TODO: Grass, Mycelium, Nyliums
        blockMiningExp.put(Material.GRASS, 4.0);
        blockMiningExp.put(Material.MYCELIUM, 4.5);
        blockMiningExp.put(Material.CRIMSON_NYLIUM, 5.0);
        blockMiningExp.put(Material.WARPED_NYLIUM, 5.0);

        // TODO: Bone
        blockMiningExp.put(Material.BONE_BLOCK, 7.0);

        // TODO: Tuff
        blockMiningExp.put(Material.TUFF, 14.5);

        // TODO: Snow
        blockMiningExp.put(Material.SNOW_BLOCK, 2.25);
        blockMiningExp.put(Material.SNOW, 0.25);

        // TODO: Bricks
        blockMiningExp.put(Material.BRICKS, 8.0);
        blockMiningExp.put(Material.BRICK_SLAB, 8.0);
        blockMiningExp.put(Material.BRICK_STAIRS, 8.0);

        // TODO: Clay, Gravel
        blockMiningExp.put(Material.CLAY, 3.75);
        blockMiningExp.put(Material.GRAVEL, 3.5);
    }

    /**
     * Gets the mining exp the block will drop when mined
     * @param block The block mined
     * @return The mining exp the block will drop
     */
    public static Double getBlockMiningExp(Block block)
    {
        Double exp = 0.0;
        Material blockMaterial = block.getType();
        if (blockMiningExp.get(blockMaterial) != null)
        {
            exp = blockMiningExp.get(blockMaterial);
        }

        return exp;
    }
}

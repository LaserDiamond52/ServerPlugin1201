package net.laserdiamond.serverplugin1201.items.crafting.SmithingTable;

import org.bukkit.Material;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SmithingPossibleIngredients {

    // List of possible materials for smithing table
    public static List<Material> materials = new ArrayList<>();
    static {
        materials.add(Material.COPPER_INGOT);
        materials.add(Material.GOLD_INGOT);
        materials.add(Material.IRON_INGOT);
        materials.add(Material.LAPIS_LAZULI);
        materials.add(Material.QUARTZ);
        materials.add(Material.REDSTONE);
        materials.add(Material.EMERALD);
        materials.add(Material.AMETHYST_SHARD);
        materials.add(Material.DIAMOND);
        materials.add(Material.NETHERITE_INGOT);
    }

    // HashMap for Determining Trim Material
    public static HashMap<Material, TrimMaterial> trimMaterialMap = new HashMap<>();
    static {
        trimMaterialMap.put(Material.COPPER_INGOT, TrimMaterial.COPPER);
        trimMaterialMap.put(Material.GOLD_INGOT, TrimMaterial.GOLD);
        trimMaterialMap.put(Material.IRON_INGOT, TrimMaterial.IRON);
        trimMaterialMap.put(Material.LAPIS_LAZULI, TrimMaterial.LAPIS);
        trimMaterialMap.put(Material.QUARTZ, TrimMaterial.QUARTZ);
        trimMaterialMap.put(Material.REDSTONE, TrimMaterial.REDSTONE);
        trimMaterialMap.put(Material.EMERALD, TrimMaterial.EMERALD);
        trimMaterialMap.put(Material.AMETHYST_SHARD, TrimMaterial.AMETHYST);
        trimMaterialMap.put(Material.DIAMOND, TrimMaterial.DIAMOND);
        trimMaterialMap.put(Material.NETHERITE_INGOT, TrimMaterial.NETHERITE);
    }

    // List of possible templates
    public static List<Material> templates = new ArrayList<>();
    static {
        templates.add(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        templates.add(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE);
        templates.add(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE);
    }

    // HashMap for determining trim pattern
    public static HashMap<Material, TrimPattern> trimPatternMap = new HashMap<>();
    static {
        trimPatternMap.put(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SENTRY);
        trimPatternMap.put(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.VEX);
        trimPatternMap.put(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WILD);
        trimPatternMap.put(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.COAST);
        trimPatternMap.put(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.DUNE);
        trimPatternMap.put(Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WAYFINDER);
        trimPatternMap.put(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.RAISER);
        trimPatternMap.put(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SHAPER);
        trimPatternMap.put(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.HOST);
        trimPatternMap.put(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.WARD);
        trimPatternMap.put(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SILENCE);
        trimPatternMap.put(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.TIDE);
        trimPatternMap.put(Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SNOUT);
        trimPatternMap.put(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.RIB);
        trimPatternMap.put(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.EYE);
        trimPatternMap.put(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, TrimPattern.SPIRE);
    }
}

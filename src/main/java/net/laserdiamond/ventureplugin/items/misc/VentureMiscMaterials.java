package net.laserdiamond.ventureplugin.items.misc;

import org.bukkit.Material;

public enum VentureMiscMaterials {

    MANA_SHARD(1090, Material.LIGHT_BLUE_DYE),
    MAGIC_SPHERE(1091, Material.PLAYER_HEAD),
    BLAZE_CORE(1092, Material.PLAYER_HEAD),
    BLAZE_FLAMES(1093, Material.BLAZE_POWDER),
    BLAZE_ASHES(1094, Material.GUNPOWDER),
    CREEPER_HEART(1095, Material.PLAYER_HEAD),
    SUPERCHARGED_GUNPOWDER(1096, Material.GUNPOWDER),
    IRON_ELYTRA_BLUEPRINT(1121, Material.PAPER),
    DIAMOND_ELYTRA_BLUEPRINT(1122, Material.PAPER),
    NETHERITE_ELYTRA_BLUEPRINT(1123, Material.PAPER),
    MACHINE_GUN_BOW_BLUEPRINT(1126, Material.PAPER),
    CONDENSED_BLAZE_ROD(1131, Material.BLAZE_ROD),
    SPIDER_SILK(1132, Material.STRING),
    ZOMBIE_INTESTINES(1133, Material.PLAYER_HEAD),
    HYPER_CONDENSED_BLAZE_ROD(1134, Material.BLAZE_ROD),
    CRACKED_RIBS(1135, Material.BONE),
    MAGMA_GEL(1136, Material.MAGMA_CREAM),
    ENHANCED_BLAZE_POWDER(1137, Material.BLAZE_POWDER),
    RIBS(1138, Material.BONE),
    BLAZIUM_INGOT(1139, Material.GOLD_INGOT),
    MAGMA_CREAM_CUBE(1140, Material.PLAYER_HEAD),
    GHOST_FRAGMENT(1143, Material.WHITE_DYE),
    HEALTH_ELIXIR(1180, Material.PLAYER_HEAD),
    HEART_OF_ATLANTIS(1232, Material.HEART_OF_THE_SEA),
    ATLANTIAN_GOLD(1233, Material.GOLD_INGOT),

    ;

    private final int customModelData;
    private final Material material;

    VentureMiscMaterials(int customModelData, Material material) {
        this.customModelData = customModelData;
        this.material = material;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public Material getMaterial() {
        return material;
    }
}

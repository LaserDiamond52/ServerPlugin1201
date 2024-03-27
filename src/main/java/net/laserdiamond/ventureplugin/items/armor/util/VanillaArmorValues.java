package net.laserdiamond.ventureplugin.items.armor.util;

import org.bukkit.Material;

public enum VanillaArmorValues {

    LEATHER_BOOTS (Material.LEATHER_BOOTS, 1.0,0.0),
    LEATHER_LEGGINGS (Material.LEATHER_LEGGINGS, 2.0,0.0),
    LEATHER_CHESTPLATE (Material.LEATHER_CHESTPLATE, 3.0,0.0),
    LEATHER_HELMET (Material.LEATHER_HELMET, 1.0,0.0),
    GOLD_BOOTS (Material.GOLDEN_BOOTS, 1.0,0.0),
    GOLD_LEGGINGS (Material.GOLDEN_LEGGINGS, 3.0,0.0),
    GOLD_CHESTPLATE (Material.GOLDEN_CHESTPLATE, 5.0,0.0),
    GOLD_HELMET (Material.GOLDEN_HELMET, 2.0,0.0),
    CHAINMAIL_BOOTS (Material.CHAINMAIL_BOOTS, 1.0,0.0),
    CHAINMAIL_LEGGINGS (Material.CHAINMAIL_LEGGINGS, 4.0,0.0),
    CHAINMAIL_CHESTPLATE (Material.CHAINMAIL_CHESTPLATE, 5.0,0.0),
    CHAINMAIL_HELMET (Material.CHAINMAIL_HELMET, 2.0,0.0),
    IRON_BOOTS (Material.IRON_BOOTS, 2.0,0.0),
    IRON_LEGGINGS (Material.IRON_LEGGINGS, 5.0,0.0),
    IRON_CHESTPLATE (Material.IRON_CHESTPLATE, 6.0,0.0),
    IRON_HELMET (Material.IRON_HELMET, 2.0,0.0),
    DIAMOND_BOOTS (Material.DIAMOND_BOOTS, 3.0,2.0),
    DIAMOND_LEGGINGS (Material.DIAMOND_LEGGINGS, 6.0,2.0),
    DIAMOND_CHESTPLATE (Material.DIAMOND_CHESTPLATE, 8.0,2.0),
    DIAMOND_HELMET (Material.DIAMOND_HELMET, 3.0,2.0),
    NETHERITE_BOOTS (Material.NETHERITE_BOOTS, 3.0,3.0),
    NETHERITE_LEGGINGS (Material.NETHERITE_LEGGINGS, 6.0,3.0),
    NETHERITE_CHESTPLATE (Material.NETHERITE_CHESTPLATE, 8.0,3.0),
    NETHERITE_HELMET (Material.NETHERITE_HELMET, 3.0,3.0),
    TURTLE_HELMET (Material.TURTLE_HELMET, 2.0,0.0),
    ELYTRA (Material.ELYTRA, 0.0,0.0);

    private final Material material;
    private final Double protectionValue;
    private final Double toughnessValue;

    VanillaArmorValues(Material material, Double protectionValue, Double toughnessValue) {
        this.material = material;
        this.protectionValue = protectionValue;
        this.toughnessValue = toughnessValue;
    }

    public Material getMaterial() {
        return material;
    }

    public Double getProtectionValue() {
        return protectionValue;
    }

    public Double getToughnessValue() {
        return toughnessValue;
    }
}

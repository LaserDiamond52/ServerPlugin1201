package net.laserdiamond.ventureplugin.items.armor.components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import org.bukkit.Material;

public final class ReinforcedDiamondArmor extends VentureArmorSet {
    public ReinforcedDiamondArmor(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    public ArmorConfig config() {
        return plugin.getReinforcedDiamondArmorConfig();
    }

    @Override
    public String armorSetName() {
        return "Reinforced Diamond";
    }

    @Override
    public ArmorCMD getArmorCMD() {
        return ArmorCMD.REINFORCED_DIAMOND_ARMOR;
    }

    @Override
    public Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
        Material material = null;
        switch (armorPieceTypes)
        {
            case HELMET -> material = Material.DIAMOND_HELMET;
            case CHESTPLATE -> material = Material.DIAMOND_CHESTPLATE;
            case LEGGINGS -> material = Material.DIAMOND_LEGGINGS;
            case BOOTS -> material = Material.DIAMOND_BOOTS;
        }
        return material;
    }

    @Override
    public VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.EPIC;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }
}

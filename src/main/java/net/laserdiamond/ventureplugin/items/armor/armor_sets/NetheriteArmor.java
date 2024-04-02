package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.items.armor.util.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.items.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorCMD;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorPieceTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class NetheriteArmor extends VentureArmorSet {

    public NetheriteArmor(VenturePlugin plugin) {
        super(plugin);
    }


    @Override
    public @NotNull String armorSetName() {
        return "Netherite";
    }

    @Override
    public ArmorCMD getArmorCMD() {
        return ArmorCMD.NETHERITE_ARMOR;
    }

    @Override
    public ArmorConfig config() {
        return plugin.getNetheriteArmorConfig();
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public VentureItemRarity.Rarity rarity() {
        return super.rarity();
    }

    @Override
    public Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
        Material material = null;
        switch (armorPieceTypes)
        {
            case HELMET -> material = Material.NETHERITE_HELMET;
            case CHESTPLATE -> material = Material.NETHERITE_CHESTPLATE;
            case LEGGINGS -> material = Material.NETHERITE_LEGGINGS;
            case BOOTS -> material = Material.NETHERITE_BOOTS;
        }
        return material;
    }

    @Override
    public List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        List<String> lore = super.createLore(armorPieceTypes, stars);

        return lore;
    }
}

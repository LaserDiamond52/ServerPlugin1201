package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorCMD;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.util.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public final class ReinforcedDiamondArmor extends VentureArmorSet {
    public ReinforcedDiamondArmor(VenturePlugin plugin) {
        super(plugin);
    }

    @Override
    protected ArmorConfig config() {
        return plugin.getReinforcedDiamondArmorConfig();
    }

    @Override
    protected String armorName() {
        return "Reinforced Diamond";
    }

    @Override
    public ArmorCMD armorCMD() {
        return ArmorCMD.REINFORCED_DIAMOND_ARMOR;
    }

    @Override
    protected Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
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
    protected VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.EPIC;
    }

    @Override
    protected boolean isFireResistant() {
        return true;
    }

    @Override
    protected boolean isUnbreakable() {
        return true;
    }

    @Override
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);
        lore.addLast(ItemStringBuilder.addItemStringRarity(rarity(), armorPieceTypes));
        return lore;
    }
}

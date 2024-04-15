package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.items.armor.util.VentureArmorSet;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorCMD;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorPieceTypes;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public final class NetheriteArmor extends VentureArmorSet {

    public NetheriteArmor(VenturePlugin plugin) {
        super(plugin);
    }


    @Override
    @NotNull
    protected String armorName() {
        return "Netherite";
    }

    @Override
    public ArmorCMD armorCMD() {
        return ArmorCMD.NETHERITE_ARMOR;
    }

    @Override
    protected ArmorConfig config() {
        return plugin.getNetheriteArmorConfig();
    }

    @Override
    protected boolean isFireResistant() {
        return true;
    }

    @Override
    protected VentureItemRarity.Rarity rarity() {
        return super.rarity();
    }

    @Override
    protected Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
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
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);
        lore.addLast(ItemStringBuilder.addItemStringRarity(rarity(), armorPieceTypes));
        return lore;
    }
}

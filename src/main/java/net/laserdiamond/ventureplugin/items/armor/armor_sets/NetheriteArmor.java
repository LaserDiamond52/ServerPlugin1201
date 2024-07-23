package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.items.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorMaterial;
import net.laserdiamond.ventureplugin.items.armor.ArmorPieceTypes;
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
    public VentureArmorMaterial ventureArmorMaterial() {
        return VentureArmorMaterial.NETHERITE_ARMOR;
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
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);
        lore.addLast(ItemStringBuilder.addItemStringRarity(rarity(), armorPieceTypes));
        return lore;
    }
}

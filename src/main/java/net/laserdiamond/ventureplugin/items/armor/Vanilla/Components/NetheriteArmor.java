package net.laserdiamond.ventureplugin.items.armor.Vanilla.Components;

import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NetheriteArmor extends VentureArmorSet {

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
    public GetVarFile config() {
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
        double fireArmorMult = config().getDouble("fireArmorMultiplier");
        double armor = createVentureStats(armorPieceTypes, stars).get(VentureItemStatKeys.ARMOR_DEFENSE_KEY);
        double fireArmor = armor * fireArmorMult;
        List<String> lore = super.createLore(armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Piece Bonus: Fire Protection");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Grants +" + ChatColor.GOLD + fireArmor + ChatColor.GRAY + " fire defense");
        lore.add(" ");
        return lore;
    }
}

package net.laserdiamond.ventureplugin.items.armor.Vanilla.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.armor.Vanilla.Config.NetheriteArmorConfig;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemNameBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NetheriteArmorManager extends VentureArmorSet {

    private final VenturePlugin plugin = VenturePlugin.getInstance();

    public NetheriteArmorManager() {
        registerArmorSet();
    }


    @Override
    public @NotNull String armorSetName() {
        return "Netherite";
    }

    @Override
    public ArmorCMD setArmorCMD() {
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

    @Override
    public HashMap<VentureItemStatKeys, Double> createVentureStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return super.createVentureStats(armorPieceTypes, stars);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createArmorAttributes(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return super.createArmorAttributes(armorPieceTypes, stars);
    }

    @Override
    public ItemForger createArmorSet(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return super.createArmorSet(armorPieceTypes, stars);
    }

    @Override
    public void registerArmorSet() {
        super.registerArmorSet();
    }
}

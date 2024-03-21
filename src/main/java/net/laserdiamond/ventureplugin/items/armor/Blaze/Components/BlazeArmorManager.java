package net.laserdiamond.ventureplugin.items.armor.Blaze.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.*;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class BlazeArmorManager extends VentureArmorSet implements AbilityCasting.RunnableSpell {

    // TODO: Create rest of lore for armor + finish config file
    private final VenturePlugin plugin = VenturePlugin.getInstance();
    //private static final BlazeArmorConfig ARMOR_CONFIG = PLUGIN.getBlazeArmorConfig();

    public BlazeArmorManager()
    {
        registerArmorSet();
    }
    @Override
    public String armorSetName() {
        return "Blaze";
    }

    @Override
    public GetVarFile config()
    {
        return plugin.getBlazeArmorConfig();
    }

    @Override
    public ArmorCMD setArmorCMD() {
        return ArmorCMD.BLAZE_ARMOR;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }

    @Override
    public VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.LEGENDARY;
    }

    @Override
    public Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
        Material material = null;
        switch (armorPieceTypes)
        {
            case HELMET -> material = Material.PLAYER_HEAD;
            case CHESTPLATE -> material = Material.LEATHER_CHESTPLATE;
            case LEGGINGS -> material = Material.LEATHER_LEGGINGS;
            case BOOTS -> material = Material.LEATHER_BOOTS;
        }
        return material;
    }

    @Override
    public Color armorColors(ArmorPieceTypes armorPieceTypes) {
        return super.armorColors(armorPieceTypes);
    }

    @Override
    public HashMap<VentureItemStatKeys, Double> createVentureStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return super.createVentureStats(armorPieceTypes, stars);
    }

    @Override
    public List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        List<String> lore = super.createLore(armorPieceTypes, stars);
        // TODO: Add item lore here
        return lore;
    }


    @Override
    public ItemForger createArmorSet(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return super.createArmorSet(armorPieceTypes, stars);
    }

    @Override
    public boolean isWearingFullSet(Player player) {
        return super.isWearingFullSet(player);
    }

    @Override
    public void registerArmorSet() {
        super.registerArmorSet();
    }

    @Override
    public void onActivate(Player player, int timer) {

    }
}

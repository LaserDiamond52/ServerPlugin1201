package net.laserdiamond.ventureplugin.items.armor.Blaze.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.armor.*;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class BlazeArmorManager extends VentureArmorSet implements AbilityCasting.RunnableSpell {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    //private static final BlazeArmorConfig ARMOR_CONFIG = PLUGIN.getBlazeArmorConfig();

    @Override
    public String armorSetName() {
        return "Blaze";
    }

    @Override
    public GetVarFile config()
    {
        return PLUGIN.getBlazeArmorConfig();
    }

    @Override
    public ArmorCMD setArmorCMD() {
        return ArmorCMD.BLAZE_ARMOR;
    }

    @Override
    public Material setArmorPieceMaterial(ArmorPieceTypes armorPieceTypes) {
        Material material = null;
        switch (armorPieceTypes)
        {
            case HELMET -> {
                material = Material.PLAYER_HEAD;
            }
            case CHESTPLATE -> {
                material = Material.LEATHER_CHESTPLATE;
            }
            case LEGGINGS -> {
                material = Material.LEATHER_LEGGINGS;
            }
            case BOOTS -> {
                material = Material.LEATHER_BOOTS;
            }
        }
        return material;
    }

    @Override
    public List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return null;
    }

    @Override
    public List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return null;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createAttributes(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        return null;
    }

    @Override
    public HashMap<VentureItemStatKeys, Double> createItemStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        return null;
    }



    @Override
    public ItemForger createArmorPiece(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        /*
        String armorPieceString = armorPieceTypes.getName();

        ItemForger itemForger = new ItemForger(Material.AIR);

        try {
            String armorName = armorPieceString.substring(0,1).toUpperCase() + armorPieceString.substring(1);

            String HelmetURL = ARMOR_CONFIG.getString("HelmetURL");

            switch (armorPieceTypes)
            {
                case HELMET -> itemForger = new ItemForger(Material.PLAYER_HEAD)
                        .setPlayerHeadSkin(HelmetURL, armorPieceCMD().getHelmet(), armorPieceCMD().getHelmet())
                        .setCustomModelData(armorPieceCMD().getHelmet());

                case CHESTPLATE -> itemForger = new ItemForger(Material.LEATHER_CHESTPLATE)
                        .LeatherArmorColor(ArmorColors.BLAZE_ARMOR.getChestplateColor())
                        .setCustomModelData(armorPieceCMD().getChestplate());

                case LEGGINGS -> itemForger = new ItemForger(Material.LEATHER_LEGGINGS)
                        .LeatherArmorColor(ArmorColors.BLAZE_ARMOR.getLeggingsColor())
                        .setCustomModelData(armorPieceCMD().getLeggings());

                case BOOTS -> itemForger = new ItemForger(Material.LEATHER_BOOTS)
                        .LeatherArmorColor(ArmorColors.BLAZE_ARMOR.getBootsColor())
                        .setCustomModelData(armorPieceCMD().getBoots());
            }

            itemForger.setName(ItemNameBuilder.name("Blaze " + armorName, stars))
                    .setStars(stars)
                    .setLore(createLore(armorPieceTypes, stars))
                    .setRarity(VentureItemRarity.Rarity.LEGENDARY)
                    .setUnbreakable(true)
                    .setFireResistant(true)
                    .setItemStats(createItemStats(armorPieceTypes, stars));
        } catch (NullPointerException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something about this item is null: " + itemForger.getName());
            e.printStackTrace();
        }
        return itemForger;

         */
        return null;
    }




    @Override
    public void onActivate(Player player, int timer) {

    }
}

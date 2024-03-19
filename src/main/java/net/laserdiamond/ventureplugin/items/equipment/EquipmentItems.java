package net.laserdiamond.ventureplugin.items.equipment;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class EquipmentItems {

    public static List<Material> helmets = new ArrayList<>();
    static {
        helmets.add(Material.LEATHER_HELMET);
        helmets.add(Material.GOLDEN_HELMET);
        helmets.add(Material.CHAINMAIL_HELMET);
        helmets.add(Material.IRON_HELMET);
        helmets.add(Material.DIAMOND_HELMET);
        helmets.add(Material.NETHERITE_HELMET);
        helmets.add(Material.TURTLE_HELMET);
    }

    public static List<Material> chestplates = new ArrayList<>();
    static {
        chestplates.add(Material.LEATHER_CHESTPLATE);
        chestplates.add(Material.GOLDEN_CHESTPLATE);
        chestplates.add(Material.CHAINMAIL_CHESTPLATE);
        chestplates.add(Material.IRON_CHESTPLATE);
        chestplates.add(Material.DIAMOND_CHESTPLATE);
        chestplates.add(Material.NETHERITE_CHESTPLATE);
    }

    public static List<Material> leggings = new ArrayList<>();
    static {
        leggings.add(Material.LEATHER_LEGGINGS);
        leggings.add(Material.GOLDEN_LEGGINGS);
        leggings.add(Material.CHAINMAIL_LEGGINGS);
        leggings.add(Material.IRON_LEGGINGS);
        leggings.add(Material.DIAMOND_LEGGINGS);
        leggings.add(Material.NETHERITE_LEGGINGS);
    }

    public static List<Material> boots = new ArrayList<>();
    static {
        boots.add(Material.LEATHER_BOOTS);
        boots.add(Material.GOLDEN_BOOTS);
        boots.add(Material.CHAINMAIL_BOOTS);
        boots.add(Material.IRON_BOOTS);
        boots.add(Material.DIAMOND_BOOTS);
        boots.add(Material.NETHERITE_BOOTS);
    }

    public static List<Material> armorPieces = new ArrayList<>();
    static {

        armorPieces.addAll(helmets);
        armorPieces.addAll(chestplates);
        armorPieces.addAll(leggings);
        armorPieces.addAll(boots);
    }
    public static List<Material> swords = new ArrayList<>();
    static {
        swords.add(Material.WOODEN_SWORD);
        swords.add(Material.GOLDEN_SWORD);
        swords.add(Material.STONE_SWORD);
        swords.add(Material.IRON_SWORD);
        swords.add(Material.DIAMOND_SWORD);
        swords.add(Material.NETHERITE_SWORD);
    }

    public static List<Material> pickaxes = new ArrayList<>();
    static {
        pickaxes.add(Material.WOODEN_PICKAXE);
        pickaxes.add(Material.GOLDEN_PICKAXE);
        pickaxes.add(Material.STONE_PICKAXE);
        pickaxes.add(Material.IRON_PICKAXE);
        pickaxes.add(Material.DIAMOND_PICKAXE);
        pickaxes.add(Material.NETHERITE_PICKAXE);
    }

    public static List<Material> axes = new ArrayList<>();
    static {
        axes.add(Material.WOODEN_AXE);
        axes.add(Material.GOLDEN_AXE);
        axes.add(Material.STONE_AXE);
        axes.add(Material.IRON_AXE);
        axes.add(Material.DIAMOND_AXE);
        axes.add(Material.NETHERITE_AXE);
    }

    public static List<Material> shovels = new ArrayList<>();
    static {
        shovels.add(Material.WOODEN_SHOVEL);
        shovels.add(Material.GOLDEN_SHOVEL);
        shovels.add(Material.STONE_SHOVEL);
        shovels.add(Material.IRON_SHOVEL);
        shovels.add(Material.DIAMOND_SHOVEL);
        shovels.add(Material.NETHERITE_SHOVEL);
    }

    public static List<Material> hoes = new ArrayList<>();
    static {
        hoes.add(Material.WOODEN_HOE);
        hoes.add(Material.GOLDEN_HOE);
        hoes.add(Material.STONE_HOE);
        hoes.add(Material.IRON_HOE);
        hoes.add(Material.DIAMOND_HOE);
        hoes.add(Material.NETHERITE_HOE);
    }

    public static List<Material> any = new ArrayList<>();
    static {

        any.addAll(armorPieces);
        any.addAll(swords);
        any.addAll(pickaxes);
        any.addAll(axes);
        any.addAll(shovels);
        any.addAll(hoes);
    }

}

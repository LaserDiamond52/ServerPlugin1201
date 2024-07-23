package net.laserdiamond.ventureplugin.items.armor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Enum consisting of all the armor material of this plugin. Each entry must have an array that consists of the custom model data for each armor piece, and one for the material of each armor piece
 */
public enum VentureArmorMaterial {
    
    NETHERITE_ARMOR (new Integer[]{1002,1001,1000,999}, new Material[]{Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS}),
    REINFORCED_DIAMOND_ARMOR (new Integer[]{1006, 1005, 1004, 1003}, new Material[]{Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS}),
    EMERALD_ARMOR (new Integer[]{1010, 1009, 1008, 1007}, new Material[]{Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    STORM_LORD_ARMOR(new Integer[]{1026, 1027, 1028, 1029}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    AMETHYST_ARMOR (new Integer[]{1041, 1042, 1043, 1044}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    PURE_NETHERITE (new Integer[]{1053, 1054, 1055, 1056}, new Material[]{Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS}),
    ABSOLUTE_NETHERITE (new Integer[]{1057, 1058, 1059, 1060}, new Material[]{Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS}),
    WITHER_ARMOR (new Integer[]{1061, 1062, 1063, 1064}, new Material[]{Material.PLAYER_HEAD, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS}),
    BLAZE_ARMOR (new Integer[]{1065, 1066, 1067, 1068}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    SOUL_FIRE_BLAZE(new Integer[]{1069, 1070, 1071, 1072}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    LEAN_ARMOR (new Integer[]{1080, 1081, 1082, 1083}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    PIGLIN_KING_CROWN (new Integer[]{1100, null, null, null}, new Material[]{Material.GOLDEN_HELMET, null, null, null}),
    PILLAGER_KING_CROWN (new Integer[]{1102, null, null, null}, new Material[]{Material.GOLDEN_HELMET, null, null, null}),
    IRON_ARMORED_ELYTRA (new Integer[]{null, 1115, null, null}, new Material[]{null, Material.ELYTRA, null, null}),
    DIAMOND_ARMORED_ELYTRA (new Integer[]{null, 1116, null, null}, new Material[]{null, Material.ELYTRA, null, null}),
    NETHERITE_ARMORED_ELYTRA (new Integer[]{null, 1117, null, null}, new Material[]{null, Material.ELYTRA, null, null}),
    DARK_ASSASSIN_ARMOR (new Integer[]{1127, 1128, 1129, 1130}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    ENDER_ARMOR (new Integer[]{1154, 1155, 1156, 1157}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    ENDER_DRAGON (new Integer[]{1158, 1159, 1160, 1161}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    BONE_TERROR(new Integer[]{1166, 1167, 1168, 1169}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    FLESH_HORROR (new Integer[]{1175, 1176, 1177, 1178}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    PIGLIN_ARMOR (new Integer[]{1196, 1197, 1198, 1199}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    PIGLIN_ARCHER (new Integer[]{1200, 1201, 1202, 1203}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    PRISMARITE_ARMOR(new Integer[]{1208, 1209, 1210, 1211}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}),
    ASSASSIN_ARMOR (new Integer[]{1224, 1225, 1226, 1227}, new Material[]{Material.PLAYER_HEAD, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS});

    // THIS STORES ALL THE CUSTOM MODEL DATA FOR THE ARMOR SETS IN THE PLUGIN

    // MAKE SURE TO CHANGE ALL THE CUSTOM MODEL DATA CHECKS TO INCLUDE VALUES FROM HERE!

    private final Integer[] armorCustomModelData;
    private final Material[] armorMaterials;

    VentureArmorMaterial(Integer[] armorCustomModelData, Material[] armorMaterials)
    {
        this.armorCustomModelData = armorCustomModelData;
        this.armorMaterials = armorMaterials;
    }

    /**
     * Checks if the ItemStack passed through is of the desired venture armor piece
     * @param itemStack The ItemStack to check
     * @param ventureArmorMaterial The venture armor material to check for
     * @param armorPieceTypes The armor piece type to check for
     * @return True if the ItemStack is the armor piece of the desired venture armor material, false otherwise
     */
    public static boolean isArmorPiece (ItemStack itemStack, VentureArmorMaterial ventureArmorMaterial, ArmorPieceTypes armorPieceTypes)
    {

        if (itemStack != null)  // Check that itemStack is not null
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null && itemMeta.hasCustomModelData())  // Check itemMeta is not null and has customModelData
            {
                int cmd = itemMeta.getCustomModelData();
                if (cmd == ventureArmorMaterial.getArmorCustomModelData()[armorPieceTypes.ordinal()]) // Check if custom model data is equal to the custom model data of the armor piece based on ordinal
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if the ItemStack is any of the armor pieces of the desired venture armor material
     * @param itemStack The ItemStack to check
     * @param ventureArmorMaterial The venture armor material
     * @return True if the ItemStack is any armor piece of the venture armor material
     */
    public static boolean isAnyArmorPiece (ItemStack itemStack, VentureArmorMaterial ventureArmorMaterial) {

        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null && itemMeta.hasCustomModelData())
            {
                int cmd = itemMeta.getCustomModelData();
                for (Integer cmdMatch : ventureArmorMaterial.armorCustomModelData)
                {
                    if (cmd == cmdMatch)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the player is wearing tree pieces of the desired venture armor material
     * @param player The player wearing the armor
     * @param ventureArmorMaterial The venture armor material to check for
     * @return True if wearing at least 3/4s of the armor set, false otherwise
     */
    public static boolean isWearingThreePcs (Player player, VentureArmorMaterial ventureArmorMaterial) {

        PlayerInventory pInv = player.getInventory();

        ItemStack[] armorContents = pInv.getArmorContents();
        // Index 0: Boots
        // Index 1: Leggings
        // Index 2: Chestplate
        // Index 3: Helmet

        int matchCount = 0;

        for (ItemStack armorStack : armorContents)
        {
            if (armorStack != null)
            {
                ItemMeta armorMeta = armorStack.getItemMeta();
                if (armorMeta != null && armorMeta.hasCustomModelData())
                {
                    int cmd = armorMeta.getCustomModelData();
                    for (Integer cmdMatch : ventureArmorMaterial.armorCustomModelData) {
                        if (cmd == cmdMatch) {
                            if (matchCount == 4) // Check for 4 because player will already have 3/4 on
                            {
                                break;
                            } else {
                                matchCount++;
                            }
                        }
                    }
                }
            }
        }


        if (matchCount >= 4)
        {
            return true;
        }

        return false;
    }

    public Integer[] getArmorCustomModelData() {
        return armorCustomModelData;
    }

    public Material[] getArmorMaterials() {
        return armorMaterials;
    }
}

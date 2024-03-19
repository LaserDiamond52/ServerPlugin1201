package net.laserdiamond.ventureplugin.items.util.armor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public enum ArmorCMD {

    DIAMOND_ARMOR (37, 38, 39, 40),
    NETHERITE_ARMOR (41,42,43,44),
    REINFORCED_DIAMOND_ARMOR (1006, 1005, 1004, 1003),
    EMERALD_ARMOR (1010, 1009, 1008, 1007),
    STORM_LORD_ARMOR(1026, 1027, 1028, 1029, 1030, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1188, 1189, 1190, 1191),
    AMETHYST_ARMOR (1041, 1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1052, 1280, 1281, 1282, 1283),
    PURE_NETHERITE (1053, 1054, 1055, 1056),
    ABSOLUTE_NETHERITE (1057, 1058, 1059, 1060),
    WITHER_ARMOR (1061, 1062, 1063, 1064),
    BLAZE_ARMOR (1065, 1066, 1067, 1068, 1069, 1070, 1071, 1072, 1073, 1074, 1075, 1076, 1184, 1185, 1186, 1187),
    LEAN_ARMOR (1080, 1081, 1082, 1083),
    PIGLIN_KING_CROWN (1100, null, null, null),
    PILLAGER_KING_CROWN (1102, null, null, null),
    IRON_ARMORED_ELYTRA (null, 1115, null, null),
    DIAMOND_ARMORED_ELYTRA (null, 1116, null, null),
    NETHERITE_ARMORED_ELYTRA (null, 1117, null, null),
    DARK_ASSASSIN_ARMOR (1127, 1128, 1129, 1130, 1146, 1147, 1148, 1149, 1150, 1151, 1152, 1153, 1192, 1193, 1194, 1195),
    ENDER_ARMOR (1154, 1155, 1156, 1157),
    ENDER_DRAGON (1158, 1159, 1160, 1161),
    SKELETON_ARCHER (1166, 1167, 1168, 1169),
    FLESH_HORROR (1175, 1176, 1177, 1178),
    PIGLIN_ARMOR (1196, 1197, 1198, 1199),
    PIGLIN_ARCHER (1200, 1201, 1202, 1203),
    GUARDIAN_ARMOR (1208, 1209, 1210, 1211, 1212, 1213, 1214, 1215, 1216, 1217, 1218, 1219, 1220, 1221, 1222, 1223),
    ASSASSIN_ARMOR (1224, 1225, 1226, 1227, 1228, 1229, 1230, 1231, 1232, 1233, 1234, 1235, 1236, 1237, 1238, 1239);

    // THIS STORES ALL THE CUSTOM MODEL DATA FOR THE ARMOR SETS IN THE PLUGIN

    // MAKE SURE TO CHANGE ALL THE CUSTOM MODEL DATA CHECKS TO INCLUDE VALUES FROM HERE!

    private final Integer helmet, chestplate, leggings, boots;
    private Integer helmetStar1, chestplateStar1, leggingsStar1, bootsStar1, helmetStar2, chestplateStar2, leggingsStar2, bootsStar2, helmetStar3, chestplateStar3, leggingsStar3, bootsStar3;

    ArmorCMD(Integer helmet, Integer chestplate, Integer leggings, Integer boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    ArmorCMD(Integer helmet, Integer chestplate, Integer leggings, Integer boots, Integer helmetStar1, Integer chestplateStar1, Integer leggingsStar1, Integer bootsStar1, Integer helmetStar2, Integer chestplateStar2, Integer leggingsStar2, Integer bootsStar2, Integer helmetStar3, Integer chestplateStar3, Integer leggingsStar3, Integer bootsStar3) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;

        this.helmetStar1 = helmetStar1;
        this.chestplateStar1 = chestplateStar1;
        this.leggingsStar1 = leggingsStar1;
        this.bootsStar1 = bootsStar1;

        this.helmetStar2 = helmetStar2;
        this.chestplateStar2 = chestplateStar2;
        this.leggingsStar2 = leggingsStar2;
        this.bootsStar2 = bootsStar2;

        this.helmetStar3 = helmetStar3;
        this.chestplateStar3 = chestplateStar3;
        this.leggingsStar3 = leggingsStar3;
        this.bootsStar3 = bootsStar3;
    }

    public static boolean isArmorPiece (ItemStack itemStack, ArmorCMD armorCMD, ArmorPieceTypes armorPieceTypes)
    {

        if (itemStack != null)  // Check that itemStack is not null
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null && itemMeta.hasCustomModelData())  // Check itemMeta is not null and has customModelData
            {
                int cmd = itemMeta.getCustomModelData();
                switch (armorPieceTypes) { // Check for which armor piece we pass through
                    case HELMET -> {
                        if (cmd == armorCMD.getHelmet()) {
                            return true;
                        }
                    }
                    case CHESTPLATE -> {
                        if (cmd == armorCMD.getChestplate()) {
                            return true;
                        }
                    }
                    case LEGGINGS -> {
                        if (cmd == armorCMD.getLeggings()) {
                            return true;
                        }
                    }
                    case BOOTS -> {
                        if (cmd == armorCMD.getBoots()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean isAnyArmorPiece (ItemStack itemStack, ArmorCMD armorCMD) {

        if (itemStack != null) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null && itemMeta.hasCustomModelData())
            {
                int cmd = itemMeta.getCustomModelData();
                if (cmd == armorCMD.getHelmet() || cmd == armorCMD.getChestplate() || cmd == armorCMD.getLeggings() || cmd == armorCMD.getBoots())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isWearingThreePcs (Player player, ArmorCMD armorCMD) {

        PlayerInventory pInv = player.getInventory();

        ItemStack[] armorContents = pInv.getArmorContents();
        // Index 0: Boots
        // Index 1: Leggings
        // Index 2: Chestplate
        // Index 3: Helmet

        int matchCount = 0;
        int[] cmdsToMatch = new int[]{armorCMD.helmet, armorCMD.chestplate, armorCMD.leggings, armorCMD.boots};

        for (ItemStack armorStack : armorContents)
        {
            if (armorStack != null)
            {
                ItemMeta armorMeta = armorStack.getItemMeta();
                if (armorMeta != null && armorMeta.hasCustomModelData())
                {
                    int cmd = armorMeta.getCustomModelData();
                    for (int cmdMatch : cmdsToMatch) {
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

    public Integer getHelmet() {
        return helmet;
    }

    public Integer getChestplate() {
        return chestplate;
    }

    public Integer getLeggings() {
        return leggings;
    }

    public Integer getBoots() {
        return boots;
    }

    public Integer getHelmetStar1() {
        return helmetStar1;
    }

    public Integer getChestplateStar1() {
        return chestplateStar1;
    }

    public Integer getLeggingsStar1() {
        return leggingsStar1;
    }

    public Integer getBootsStar1() {
        return bootsStar1;
    }

    public Integer getHelmetStar2() {
        return helmetStar2;
    }

    public Integer getChestplateStar2() {
        return chestplateStar2;
    }

    public Integer getLeggingsStar2() {
        return leggingsStar2;
    }

    public Integer getBootsStar2() {
        return bootsStar2;
    }

    public Integer getHelmetStar3() {
        return helmetStar3;
    }

    public Integer getChestplateStar3() {
        return chestplateStar3;
    }

    public Integer getLeggingsStar3() {
        return leggingsStar3;
    }

    public Integer getBootsStar3() {
        return bootsStar3;
    }
}

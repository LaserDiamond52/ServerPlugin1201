package net.laserdiamond.ventureplugin.util;

import net.laserdiamond.ventureplugin.VenturePlugin;

public enum Permissions {

    BREAK_BLOCKS ("break_block"),
    PLACE_BLOCKS ("place_block"),
    HARVEST_BLOCKS ("harvest_block"),
    PICK_UP_ITEM ("pick_up_item"),
    DROP_ITEM ("drop_item"),
    ARMOR_STAND ("armor_stand"),
    INTERACT ("interact"),
    SHEAR_ENTITY ("shear_entity"),
    MOVE ("move"),
    CHANGE_MAIN_HAND ("change_main_hand"),
    SWAP_HANDS ("swap_hands"),
    PORTAL_TRAVEL ("portal_travel"),
    CHANGE_GAMEMODE ("change_gamemode"),
    CHAT ("chat"),
    EXECUTE_COMMANDS ("execute_commands"),
    GIVE_ITEMS ("give_items"),
    SKILLS ("skills"),
    EDIT_SKILLS ("edit_skills"),
    STATS ("stats"),
    TUNING_MENU ("tuning_menu"),
    EDIT_TUNING ("edit_tuning"),
    EDIT_MANA("edit_mana"),
    EFFECT ("effect"),
    EDIT_EXPERIENCE("edit_experience"),
    ENCHANT ("enchant");

    private final String permissionString;

    Permissions(String permissionString)
    {
        this.permissionString = permissionString;
    }

    public String getPermissionString() {
        return VenturePlugin.PLUGIN_ID + "." + permissionString;
    }
}

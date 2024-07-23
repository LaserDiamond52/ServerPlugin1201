package net.laserdiamond.ventureplugin.entities.util;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.NamespacedKey;

public class MobKeys {

    /**
     * Key used to denote the mob type of the mob
     */
    public static final NamespacedKey MOB_TYPE = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "mob_type");

    /**
     * Key used to store the name tag name of the mob
     */
    public static final NamespacedKey MOB_NAMETAG_NAME = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "nametag_name");

    /**
     * Key used to denote the venture mob type of the mob
     */
    public static final NamespacedKey VENTURE_MOB = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "venture_mob_type");

    /**
     * Key used to store the combat exp the mob grants the player when killed
     */
    public static final NamespacedKey VENTURE_MOB_COMBAT_EXP = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "combat_exp");

    /**
     * Key used to store the fishing exp the mob grants the player when killed
     */
    public static final NamespacedKey VENTURE_MOB_FISHING_EXP = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "fishing_exp");

    /**
     * Key used to determine if the mob should drop its vanilla loot table upon death
     */
    public static final NamespacedKey VENTURE_VANILLA_DROPS = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "vanilla_drops");

    /**
     * Determines if the mob is a boss mob and should have a boss bar displayed when a player is near it
     */
    public static final NamespacedKey IS_BOSS_MOB = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "is_boss_mob");

}

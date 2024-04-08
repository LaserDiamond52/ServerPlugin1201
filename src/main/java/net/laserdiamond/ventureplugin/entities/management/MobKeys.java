package net.laserdiamond.ventureplugin.entities.management;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.NamespacedKey;

public abstract class MobKeys {

    public static final NamespacedKey MOB_TYPE = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "mob_type");
    public static final NamespacedKey MOB_NAMETAG_NAME = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "nametag_name");
    public static final NamespacedKey VENTURE_MOB = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "venture_mob_type");
    public static final NamespacedKey VENTURE_MOB_COMBAT_EXP = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "combat_exp");
    public static final NamespacedKey VENTURE_MOB_FISHING_EXP = new NamespacedKey(VenturePlugin.PLUGIN_ID + ".mob", "fishing_exp");



}

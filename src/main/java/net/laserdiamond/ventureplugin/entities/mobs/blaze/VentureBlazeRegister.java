package net.laserdiamond.ventureplugin.entities.mobs.blaze;

import net.laserdiamond.ventureplugin.VenturePlugin;

/**
 * Class used to register Blaze mobs of this plugin. Helps restrict usage of the constructor used to register mobs
 */
public class VentureBlazeRegister {

    public static void register(VenturePlugin plugin)
    {
        new LordBlazius(plugin);
    }
}

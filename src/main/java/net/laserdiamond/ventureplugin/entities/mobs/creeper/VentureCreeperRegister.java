package net.laserdiamond.ventureplugin.entities.mobs.creeper;

import net.laserdiamond.ventureplugin.VenturePlugin;

public class VentureCreeperRegister {

    public static void register(VenturePlugin plugin)
    {
        new CreeperGhost(plugin);
    }
}

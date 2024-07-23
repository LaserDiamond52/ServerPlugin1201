package net.laserdiamond.ventureplugin.entities.mobs.creeper;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.mobs.AbstractVentureMob;
import net.laserdiamond.ventureplugin.entities.util.loot_tables.VentureLootTableEntry;
import net.laserdiamond.ventureplugin.items.misc.MiscItemsManager;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class AbstractVentureCreeper extends AbstractVentureMob<Creeper> {

    /**
     * Constructor for registering blaze mob of this plugin. Should only be used when registering mob
     *
     * @param venturePlugin Plugin instance
     */
    protected AbstractVentureCreeper(VenturePlugin venturePlugin)
    {
        super(venturePlugin, Creeper.class);
    }

    public AbstractVentureCreeper()
    {
        super(Creeper.class);
    }
}

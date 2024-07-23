package net.laserdiamond.ventureplugin.entities.mobs.blaze;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.mobs.AbstractVentureMob;
import org.bukkit.entity.Blaze;

public abstract class AbstractVentureBlaze extends AbstractVentureMob<Blaze> {

    /**
     * Constructor for registering blaze mob of this plugin. Should only be used when registering mob
     *
     * @param venturePlugin Plugin instance
     */
    protected AbstractVentureBlaze(VenturePlugin venturePlugin)
    {
        super(venturePlugin, Blaze.class);
    }

    public AbstractVentureBlaze(){
        super(Blaze.class);
    }
}

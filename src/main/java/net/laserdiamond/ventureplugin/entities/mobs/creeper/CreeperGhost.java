package net.laserdiamond.ventureplugin.entities.mobs.creeper;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.mobs.AttributeEntry;
import net.laserdiamond.ventureplugin.entities.mobs.VentureMobType;
import net.laserdiamond.ventureplugin.entities.util.loot_tables.VentureLootTableEntry;
import net.laserdiamond.ventureplugin.items.misc.MiscItemsManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Creeper;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class CreeperGhost extends AbstractVentureCreeper {

    protected CreeperGhost(VenturePlugin venturePlugin)
    {
        super(venturePlugin);
    }

    public CreeperGhost(){
        super();
    }

    @Override
    public VentureMobType ventureMobType() {
        return VentureMobType.CREEPER_GHOST;
    }

    @Override
    public Set<AttributeEntry> attributes() {
        attributes.add(new AttributeEntry(Attribute.GENERIC_MAX_HEALTH, 30));
        return attributes;
    }

    @Override
    public double combatExp() {
        return 0;
    }

    @Override
    public double fishingExp() {
        return 0;
    }

    @Override
    public boolean boss() {
        return false;
    }

    @Override
    public Creeper summonMob(Location location) {
        Creeper creeper = super.summonMob(location);
        creeper.setInvisible(true);
        creeper.setPowered(true);
        creeper.setExplosionRadius(7);
        creeper.setMaxFuseTicks(60);
        return creeper;
    }

    @Override
    public boolean clearVanillaDrops() {
        return true;
    }

    @Override
    public List<VentureLootTableEntry> loot() {
        loot.add(new VentureLootTableEntry(new ItemStack(Material.GUNPOWDER), 1, 1, 5, 8, 4));
        loot.add(new VentureLootTableEntry(MiscItemsManager.GHOST_FRAGMENT.createItem().toItemStack(), 2, 3, 2, 4, 3));
        return loot;
    }
}

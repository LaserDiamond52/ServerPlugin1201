package net.laserdiamond.ventureplugin.entities.mobs.blaze;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.mobs.AttributeEntry;
import net.laserdiamond.ventureplugin.entities.mobs.VentureMobType;
import net.laserdiamond.ventureplugin.entities.util.loot_tables.VentureLootTableEntry;
import net.laserdiamond.ventureplugin.items.misc.MiscItemsManager;
import net.laserdiamond.ventureplugin.items.misc.VentureMiscMaterials;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public class LordBlazius extends AbstractVentureBlaze {

    /**
     * Constructor for registering blaze mob of this plugin. Should only be used when registering mob
     *
     * @param venturePlugin Plugin instance
     */
    protected LordBlazius(VenturePlugin venturePlugin) {
        super(venturePlugin);
    }

    public LordBlazius() {
        super();
    }

    @Override
    public VentureMobType ventureMobType() {
        return VentureMobType.LORD_BLAZIUS;
    }

    @Override
    public Set<AttributeEntry> attributes() {
        attributes.add(new AttributeEntry(Attribute.GENERIC_MAX_HEALTH, 1500));
        return attributes;
    }

    @Override
    public boolean boss() {
        return true;
    }

    @Override
    public double combatExp() {
        return 500;
    }

    @Override
    public double fishingExp() {
        return 0;
    }

    @Override
    public boolean clearVanillaDrops() {
        return true;
    }

    @Override
    public List<VentureLootTableEntry> loot() {
        loot.add(new VentureLootTableEntry(new ItemStack(Material.BLAZE_ROD),1,1, 15, 20, 4));
        loot.add(new VentureLootTableEntry(MiscItemsManager.BLAZE_ASHES.createItem().toItemStack(), 3, 5, 7, 10, 2));
        loot.add(new VentureLootTableEntry(MiscItemsManager.BLAZE_FLAMES.createItem().toItemStack(), 3, 5, 7, 12, 3));
        loot.add(new VentureLootTableEntry(MiscItemsManager.BLAZE_CORE.createItem().toItemStack(), 1,1, 1,1, 0));
        return loot;
    }
}

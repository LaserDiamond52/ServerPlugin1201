package net.laserdiamond.ventureplugin.entities.util;

import net.laserdiamond.ventureplugin.entities.mobs.AttributeEntry;
import net.laserdiamond.ventureplugin.entities.mobs.VentureMobType;
import net.laserdiamond.ventureplugin.entities.util.loot_tables.VentureLootTableEntry;
import org.bukkit.Location;
import org.bukkit.entity.Mob;

import java.util.List;
import java.util.Set;

public interface VentureMob<T extends Mob> {

    /**
     * Sets the Venture Mob that this mob will represent
     * @return VentureMobs
     */
    VentureMobType ventureMobType();

    /**
     * A set of attributes to overwrite the vanilla attributes of the mob
     * @return A Set of AttributeEntry objects
     */
    Set<AttributeEntry> attributes();

    /**
     * The combat exp the mob should reward the player when killed
     * @return The combat exp as a double
     */
    double combatExp();

    /**
     * The fishing exp the mob should reward the player when killed
     * @return the fishing exp as a double
     */
    double fishingExp();

    /**
     * Determines if the mob is considered a boss mob. Boss mobs will not despawn
     * @return True if a boss mob, false otherwise
     */
    boolean boss();

    /**
     * Sets the venture loot table for the mob
     * @return A List of VentureLootTableEntry objects
     */
    List<VentureLootTableEntry> loot();

    /**
     * Determines if the vanilla drops for the mob should be cleared
     * @return True if cleared, false otherwise
     */
    boolean clearVanillaDrops();

    /**
     * Summons the mob at the location
     * @param location The location to spawn the mob
     * @return An object instance of the mob. Can cast to the mob's type if further manipulation is needed
     */
    T summonMob(Location location);
}

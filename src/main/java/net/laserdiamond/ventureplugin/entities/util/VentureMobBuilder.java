package net.laserdiamond.ventureplugin.entities.util;

import net.laserdiamond.ventureplugin.entities.mobs.VentureMobType;
import net.laserdiamond.ventureplugin.events.damage.DamageDisplays;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataType;

/**
 * Class used to help create or modify mobs for use of this plugin.
 * @param <T> Any mob type class
 */
public class VentureMobBuilder<T extends Mob> {

    private final T mob;

    public VentureMobBuilder(T mob)
    {
        this.mob = mob;
        mob.setCustomNameVisible(true);
    }

    public VentureMobBuilder(T mob, VentureMobType ventureMobType)
    {
        this(mob);
        this.setVentureType(ventureMobType);
    }

    /**
     * Sets the value of an attribute on a mob
     * @param attribute The attribute to set the value of
     * @param value The value to set the attribute to
     * @return VentureMobBuilder
     */
    public VentureMobBuilder<T> setAttributeValue(Attribute attribute, double value)
    {
        AttributeInstance attributeInstance = mob.getAttribute(attribute);
        attributeInstance.setBaseValue(value);
        if (attribute == Attribute.GENERIC_MAX_HEALTH) // If the attribute to be changed is health, set the mob's current health to the new max and update custom name
        {
            this.mob.setHealth(value);
            this.mob.setCustomName(DamageDisplays.mobHealthNameFormat(this.getVentureType().getDisplayName(), value, value));
        }
        return this;
    }

    /**
     * Sets whether the mob is a boss mob of this plugin. Sets "setPersistent" to "true" and "setRemoveWhenFarAway" to false
     * @return VentureMobBuilder
     */
    public VentureMobBuilder<T> setBossMob()
    {
        this.mob.setPersistent(true);
        this.mob.setRemoveWhenFarAway(false);
        this.mob.getPersistentDataContainer().set(MobKeys.IS_BOSS_MOB, PersistentDataType.BOOLEAN, true);
        return this;
    }

    /**
     * Gets whether the mob is a boss mob of this plugin
     * @return True if the mob is a boss mob, false otherwise
     */
    public Boolean isBossMob()
    {
        return this.mob.getPersistentDataContainer().get(MobKeys.IS_BOSS_MOB, PersistentDataType.BOOLEAN);
    }

    /**
     * Sets a tag on the mob denoting what Venture Mob the mob is. Also changes the name of the mob
     * @param ventureMobType The Venture Mob that this mob is
     * @return VentureMobBuilder
     */
    public VentureMobBuilder<T> setVentureType(VentureMobType ventureMobType)
    {
        this.mob.getPersistentDataContainer().set(MobKeys.VENTURE_MOB, PersistentDataType.STRING, ventureMobType.name().toLowerCase());
        this.mob.setCustomName(DamageDisplays.mobHealthNameFormat(ventureMobType.getDisplayName(), mob.getHealth(), mob.getMaxHealth()));
        return this;
    }

    /**
     * Gets the Venture Mob type that the mob is.
     * @return The Venture Mob type the mob is as a VentureMobs. Returns null if the tag does not match a value in the VentureMobs enum
     */
    public VentureMobType getVentureType()
    {
        String tagValue = this.mob.getPersistentDataContainer().get(MobKeys.VENTURE_MOB, PersistentDataType.STRING);
        return VentureMobType.fromString(tagValue);
    }

    /**
     * Determines if the mob is a Venture Mob
     * @return True if the mob is a Venture Mob, false otherwise
     */
    public boolean isVentureMobType()
    {
        String tagValue = this.mob.getPersistentDataContainer().get(MobKeys.VENTURE_MOB, PersistentDataType.STRING);
        for (VentureMobType ventureMobType : VentureMobType.values())
        {
            if (ventureMobType.name().toLowerCase().equals(tagValue))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the base combat exp the mob will grant when killed by a player. This value is saved as a tag to the mob's Persistent Data Container
     * @param amount The base combat exp for the mob to drop
     * @return VentureMobBuilder
     */
    public VentureMobBuilder<T> setCombatExp(double amount)
    {
        this.mob.getPersistentDataContainer().set(MobKeys.VENTURE_MOB_COMBAT_EXP, PersistentDataType.DOUBLE, amount);
        return this;
    }

    /**
     * Gets the base combat exp the mob will grant when killed by a player
     * @return A Double of the base combat exp to be dropped
     */
    public Double getCombatExp()
    {
        return this.mob.getPersistentDataContainer().get(MobKeys.VENTURE_MOB_COMBAT_EXP, PersistentDataType.DOUBLE);
    }

    /**
     * Sets the base fishing exp the mob will grant when killed by a player. This value is saved as a tag to the mob's Persistent Data Container
     * @param amount The base fishing exp for the mob to drop
     * @return VentureMobBuilder
     */
    public VentureMobBuilder<T> setFishingExp(double amount)
    {
        this.mob.getPersistentDataContainer().set(MobKeys.VENTURE_MOB_FISHING_EXP, PersistentDataType.DOUBLE, amount);
        return this;
    }

    /**
     * Gets the base fishing exp the mob will grant when killed by a player
     * @return A Double of the base fishing exp to be dropped
     */
    public Double getFishingExp()
    {
        return this.mob.getPersistentDataContainer().get(MobKeys.VENTURE_MOB_FISHING_EXP, PersistentDataType.DOUBLE);
    }

    /**
     * Sets if the mob should drop its vanilla loot table upon death
     * @param dropVanillaDrops If the mob should drop its vanilla loot table
     * @return VentureMobBuilder
     */
    public VentureMobBuilder<T> setDropVanillaDrops(boolean dropVanillaDrops)
    {
        this.mob.getPersistentDataContainer().set(MobKeys.VENTURE_VANILLA_DROPS, PersistentDataType.BOOLEAN, dropVanillaDrops);
        return this;
    }

    /**
     * Gets if the mob should drop its vanilla loot table upon death
     * @return True if the vanilla loot table should be dropped, false otherwise
     */
    public Boolean getDropVanillaDrops()
    {
        return this.mob.getPersistentDataContainer().get(MobKeys.VENTURE_VANILLA_DROPS, PersistentDataType.BOOLEAN);
    }

    /**
     * Returns the VentureMobBuilder as a Mob
     * @return The VentureMobBuilder as an instance of the mob type
     */
    public T toMob()
    {
        return this.mob;
    }
}

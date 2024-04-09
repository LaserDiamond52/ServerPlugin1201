package net.laserdiamond.ventureplugin.skills.Components.ExpGain.combat;

import net.laserdiamond.ventureplugin.entities.management.MobKeys;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class mobExp {

    private static final HashMap<EntityType, Double> vanillaMobCombatExp = new HashMap<>();
    static
    {
        // Passive Mobs
        vanillaMobCombatExp.put(EntityType.ALLAY, 10.0); //
        vanillaMobCombatExp.put(EntityType.AXOLOTL, 10.0); //
        vanillaMobCombatExp.put(EntityType.BAT, 5.0); //
        vanillaMobCombatExp.put(EntityType.CAMEL, 12.0); //
        vanillaMobCombatExp.put(EntityType.CAT, 5.0); //
        vanillaMobCombatExp.put(EntityType.CHICKEN, 5.0); //
        vanillaMobCombatExp.put(EntityType.COD, 3.0); //
        vanillaMobCombatExp.put(EntityType.COW, 5.0); //
        vanillaMobCombatExp.put(EntityType.DONKEY, 5.0); //
        vanillaMobCombatExp.put(EntityType.FROG, 12.0); //
        vanillaMobCombatExp.put(EntityType.GLOW_SQUID, 4.0); //
        vanillaMobCombatExp.put(EntityType.HORSE, 6.5); //
        vanillaMobCombatExp.put(EntityType.MUSHROOM_COW, 5.0); //
        vanillaMobCombatExp.put(EntityType.MULE, 6.0); //
        vanillaMobCombatExp.put(EntityType.OCELOT, 11.0); //
        vanillaMobCombatExp.put(EntityType.PARROT, 3.5); //
        vanillaMobCombatExp.put(EntityType.PIG, 5.0); //
        vanillaMobCombatExp.put(EntityType.PUFFERFISH, 13.0); //
        vanillaMobCombatExp.put(EntityType.RABBIT, 2.5); //
        vanillaMobCombatExp.put(EntityType.SALMON, 3.0); //
        vanillaMobCombatExp.put(EntityType.SHEEP, 5.0); //
        vanillaMobCombatExp.put(EntityType.SKELETON_HORSE, 20.0); //
        vanillaMobCombatExp.put(EntityType.ZOMBIE_HORSE, 20.0); //
        vanillaMobCombatExp.put(EntityType.SNIFFER, 17.0); //
        vanillaMobCombatExp.put(EntityType.SNOWMAN, 2.0); //
        vanillaMobCombatExp.put(EntityType.SQUID, 3.5); //
        vanillaMobCombatExp.put(EntityType.STRIDER, 14.0); //
        vanillaMobCombatExp.put(EntityType.TADPOLE, 1.0); //
        vanillaMobCombatExp.put(EntityType.TROPICAL_FISH, 1.5); //
        vanillaMobCombatExp.put(EntityType.TURTLE, 3.0); //
        vanillaMobCombatExp.put(EntityType.VILLAGER, 7.5); //
        vanillaMobCombatExp.put(EntityType.WANDERING_TRADER, 7.5); //

        // Neutral Mobs
        vanillaMobCombatExp.put(EntityType.BEE, 5.75); //
        vanillaMobCombatExp.put(EntityType.CAVE_SPIDER, 15.5); //
        vanillaMobCombatExp.put(EntityType.DOLPHIN, 13.5); //
        vanillaMobCombatExp.put(EntityType.DROWNED, 20.5); //
        vanillaMobCombatExp.put(EntityType.ENDERMAN, 35.0); //
        vanillaMobCombatExp.put(EntityType.FOX, 4.5); //
        vanillaMobCombatExp.put(EntityType.GOAT, 4.5); //
        vanillaMobCombatExp.put(EntityType.IRON_GOLEM, 25.0); //
        vanillaMobCombatExp.put(EntityType.LLAMA, 6.5); //
        vanillaMobCombatExp.put(EntityType.PANDA, 6.5); //
        vanillaMobCombatExp.put(EntityType.PIGLIN, 20.5); //
        vanillaMobCombatExp.put(EntityType.POLAR_BEAR, 16.0); //
        vanillaMobCombatExp.put(EntityType.SPIDER, 17.0); //
        vanillaMobCombatExp.put(EntityType.TRADER_LLAMA, 7.5); //
        vanillaMobCombatExp.put(EntityType.WOLF, 15.0); //
        vanillaMobCombatExp.put(EntityType.ZOMBIFIED_PIGLIN, 27.0); //

        // Hostile Mobs
        vanillaMobCombatExp.put(EntityType.BLAZE, 30.0); //
        vanillaMobCombatExp.put(EntityType.CREEPER, 27.5); //
        vanillaMobCombatExp.put(EntityType.ELDER_GUARDIAN, 100.0); // Boss //
        vanillaMobCombatExp.put(EntityType.ENDERMITE, 20.0); //
        vanillaMobCombatExp.put(EntityType.ENDER_DRAGON, 50000.0); // Boss //
        vanillaMobCombatExp.put(EntityType.EVOKER, 40.0); //
        vanillaMobCombatExp.put(EntityType.GHAST, 21.0); //
        vanillaMobCombatExp.put(EntityType.GUARDIAN, 26.0); //
        vanillaMobCombatExp.put(EntityType.HOGLIN, 24.0); //
        vanillaMobCombatExp.put(EntityType.HUSK, 20.5); //
        vanillaMobCombatExp.put(EntityType.ILLUSIONER, 200.0); //
        vanillaMobCombatExp.put(EntityType.MAGMA_CUBE, 22.0); //
        vanillaMobCombatExp.put(EntityType.PHANTOM, 23.0); //
        vanillaMobCombatExp.put(EntityType.PIGLIN_BRUTE, 45.0); //
        vanillaMobCombatExp.put(EntityType.PILLAGER, 18.0); //
        vanillaMobCombatExp.put(EntityType.RAVAGER, 60.0); //
        vanillaMobCombatExp.put(EntityType.SHULKER, 75.0); //
        vanillaMobCombatExp.put(EntityType.SILVERFISH, 7.0); //
        vanillaMobCombatExp.put(EntityType.SKELETON, 18.5); //
        vanillaMobCombatExp.put(EntityType.SLIME, 16.0); //
        vanillaMobCombatExp.put(EntityType.STRAY, 19.5); //
        vanillaMobCombatExp.put(EntityType.VEX, 4.75); //
        vanillaMobCombatExp.put(EntityType.VINDICATOR, 40.0); //
        vanillaMobCombatExp.put(EntityType.WARDEN, 750.0); //
        vanillaMobCombatExp.put(EntityType.WITCH, 28.0); //
        vanillaMobCombatExp.put(EntityType.WITHER, 40000.0); // Boss //
        vanillaMobCombatExp.put(EntityType.WITHER_SKELETON, 31.5); //
        vanillaMobCombatExp.put(EntityType.ZOGLIN, 27.0); //
        vanillaMobCombatExp.put(EntityType.ZOMBIE, 20.0); //
        vanillaMobCombatExp.put(EntityType.ZOMBIE_VILLAGER, 20.0); //



    }

    private static final HashMap<String, Double> ventureMobCombatExp = new HashMap<>();
    static
    {
        // TODO: Add Mobs here, or set their combat exp
    }

    /**
     * Returns the combat exp that the mob will grant when killed by a player
     * @param mob The mob to get the combat exp for
     * @return The combat exp the mob will grant when killed by a player
     */
    public static Double getMobCombatExp(Mob mob)
    {
        Double exp = 0.0;
        EntityType mobType = mob.getType();
        PersistentDataContainer persistentDataContainer = mob.getPersistentDataContainer();

        if (persistentDataContainer.get(MobKeys.VENTURE_MOB, PersistentDataType.STRING) != null) // TODO: Change to check for VENTURE_MOB_COMBAT_EXP key on mob
        {
            String ventureMobName = persistentDataContainer.get(MobKeys.VENTURE_MOB, PersistentDataType.STRING);
            if (ventureMobCombatExp.get(ventureMobName) != null)
            {
                exp = ventureMobCombatExp.get(ventureMobName);
            }
        } else if (vanillaMobCombatExp.containsKey(mobType) && vanillaMobCombatExp.get(mobType) != null)
        {
            exp = vanillaMobCombatExp.get(mobType);
        }

        return exp;
    }
}

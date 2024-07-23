package net.laserdiamond.ventureplugin.skills.Components.ExpGain;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.mobs.VentureMobType;
import net.laserdiamond.ventureplugin.entities.util.VentureMob;
import net.laserdiamond.ventureplugin.entities.util.VentureMobBuilder;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

import java.util.HashMap;

public class CombatExp {

    private static final HashMap<EntityType, Double> VANILLA_MOB_COMBAT_EXP = new HashMap<>();
    static
    {
        // Passive Mobs
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ALLAY, 10.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.AXOLOTL, 10.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.BAT, 5.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.CAMEL, 12.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.CAT, 5.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.CHICKEN, 5.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.COD, 3.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.COW, 5.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.DONKEY, 5.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.FROG, 12.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.GLOW_SQUID, 4.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.HORSE, 6.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.MUSHROOM_COW, 5.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.MULE, 6.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.OCELOT, 11.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.PARROT, 3.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.PIG, 5.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.PUFFERFISH, 13.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.RABBIT, 2.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SALMON, 3.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SHEEP, 5.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SKELETON_HORSE, 20.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ZOMBIE_HORSE, 20.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SNIFFER, 17.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SNOWMAN, 2.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SQUID, 3.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.STRIDER, 14.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.TADPOLE, 1.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.TROPICAL_FISH, 1.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.TURTLE, 3.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.VILLAGER, 7.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.WANDERING_TRADER, 7.5); //

        // Neutral Mobs
        VANILLA_MOB_COMBAT_EXP.put(EntityType.BEE, 5.75); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.CAVE_SPIDER, 15.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.DOLPHIN, 13.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.DROWNED, 20.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ENDERMAN, 35.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.FOX, 4.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.GOAT, 4.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.IRON_GOLEM, 25.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.LLAMA, 6.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.PANDA, 6.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.PIGLIN, 20.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.POLAR_BEAR, 16.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SPIDER, 17.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.TRADER_LLAMA, 7.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.WOLF, 15.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ZOMBIFIED_PIGLIN, 27.0); //

        // Hostile Mobs
        VANILLA_MOB_COMBAT_EXP.put(EntityType.BLAZE, 30.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.CREEPER, 27.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ELDER_GUARDIAN, 100.0); // Boss //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ENDERMITE, 20.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ENDER_DRAGON, 50000.0); // Boss //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.EVOKER, 40.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.GHAST, 21.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.GUARDIAN, 26.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.HOGLIN, 24.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.HUSK, 20.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ILLUSIONER, 200.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.MAGMA_CUBE, 22.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.PHANTOM, 23.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.PIGLIN_BRUTE, 45.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.PILLAGER, 18.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.RAVAGER, 60.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SHULKER, 75.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SILVERFISH, 7.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SKELETON, 18.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.SLIME, 16.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.STRAY, 19.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.VEX, 4.75); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.VINDICATOR, 40.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.WARDEN, 750.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.WITCH, 28.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.WITHER, 40000.0); // Boss //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.WITHER_SKELETON, 31.5); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ZOGLIN, 27.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ZOMBIE, 20.0); //
        VANILLA_MOB_COMBAT_EXP.put(EntityType.ZOMBIE_VILLAGER, 20.0); //


    }

    private static final HashMap<String, VentureMob<?>> VENTURE_MOBS = VenturePlugin.getInstance().getVentureMobMap();

    /**
     * Returns the combat exp that the mob will grant when killed by a player
     * @param mob The mob to get the combat exp for
     * @return The combat exp the mob will grant when killed by a player
     */
    public static Double getMobCombatExp(Mob mob)
    {
        Double exp = 0.0;
        EntityType mobType = mob.getType();

        VentureMobBuilder<?> mobBuilder = new VentureMobBuilder<>(mob);

        if (mobBuilder.isVentureMobType()) // TODO: Change to check for VENTURE_MOB_COMBAT_EXP key on mob
        {
            VentureMobType ventureMobType = mobBuilder.getVentureType();
            String ventureMobName = ventureMobType.toString();
            exp = VENTURE_MOBS.get(ventureMobName).combatExp();

        } else if (VANILLA_MOB_COMBAT_EXP.containsKey(mobType) && VANILLA_MOB_COMBAT_EXP.get(mobType) != null)
        {
            exp = VANILLA_MOB_COMBAT_EXP.get(mobType);
        }

        return exp;
    }
}

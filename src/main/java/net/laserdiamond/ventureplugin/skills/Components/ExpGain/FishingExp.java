package net.laserdiamond.ventureplugin.skills.Components.ExpGain;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.mobs.VentureMobType;
import net.laserdiamond.ventureplugin.entities.util.VentureMob;
import net.laserdiamond.ventureplugin.entities.util.VentureMobBuilder;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

import java.util.HashMap;

public class FishingExp {

    private static final HashMap<EntityType, Double> VANILLA_MOB_FISHING_EXP = new HashMap<>();
    static
    {
        VANILLA_MOB_FISHING_EXP.put(EntityType.AXOLOTL, 14.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.COD, 7.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.GLOW_SQUID, 7.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.PUFFERFISH, 12.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.SALMON, 7.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.SQUID, 7.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.TADPOLE, 3.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.TROPICAL_FISH, 5.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.TURTLE, 10.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.DROWNED, 12.75);
        VANILLA_MOB_FISHING_EXP.put(EntityType.ELDER_GUARDIAN, 150.0);
        VANILLA_MOB_FISHING_EXP.put(EntityType.GUARDIAN, 45.0);
    }

    private static final HashMap<String, VentureMob<?>> VENTURE_MOBS = VenturePlugin.getInstance().getVentureMobMap();

    /**
     * Returns the fishing exp that the mob will grant when killed by a player
     * @param mob The mob to get the fishing exp for
     * @return The fishing exp the mob will grant when killed by a player
     */
    public static Double getFishingExp(Mob mob)
    {
        Double exp = 0.0;
        EntityType mobType = mob.getType();

        VentureMobBuilder<?> mobBuilder = new VentureMobBuilder<>(mob);

        if (mobBuilder.isVentureMobType())
        {
            VentureMobType ventureMobType = mobBuilder.getVentureType();
            String ventureMobName = ventureMobType.toString();
            exp = VENTURE_MOBS.get(ventureMobName).fishingExp();
        } else if (VANILLA_MOB_FISHING_EXP.containsKey(mobType) && VANILLA_MOB_FISHING_EXP.get(mobType) != null)
        {
            exp = VANILLA_MOB_FISHING_EXP.get(mobType);
        }
        return exp;
    }
}

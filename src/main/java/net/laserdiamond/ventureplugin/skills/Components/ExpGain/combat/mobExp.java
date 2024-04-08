package net.laserdiamond.ventureplugin.skills.Components.ExpGain.combat;

import net.laserdiamond.ventureplugin.entities.management.MobKeys;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class mobExp {

    private static final HashMap<String, Double> vanillaMobCombatExp = new HashMap<>();
    static
    {
        // Passive Mobs
        vanillaMobCombatExp.put("allay", 10.0); //
        vanillaMobCombatExp.put("axolotl", 10.0); //
        vanillaMobCombatExp.put("bat", 5.0); //
        vanillaMobCombatExp.put("camel", 12.0); //
        vanillaMobCombatExp.put("cat", 5.0); //
        vanillaMobCombatExp.put("chicken", 5.0); //
        vanillaMobCombatExp.put("cod", 3.0); //
        vanillaMobCombatExp.put("cow", 5.0); //
        vanillaMobCombatExp.put("donkey", 5.0); //
        vanillaMobCombatExp.put("frog", 12.0); //
        vanillaMobCombatExp.put("glow_squid", 4.0); //
        vanillaMobCombatExp.put("horse", 6.5); //
        vanillaMobCombatExp.put("mooshroom", 5.0); //
        vanillaMobCombatExp.put("mule", 6.0); //
        vanillaMobCombatExp.put("ocelot", 11.0); //
        vanillaMobCombatExp.put("parrot", 3.5); //
        vanillaMobCombatExp.put("pig", 5.0); //
        vanillaMobCombatExp.put("pufferfish", 13.0); //
        vanillaMobCombatExp.put("rabbit", 2.5); //
        vanillaMobCombatExp.put("salmon", 3.0); //
        vanillaMobCombatExp.put("sheep", 5.0); //
        vanillaMobCombatExp.put("skeleton_horse", 20.0); //
        vanillaMobCombatExp.put("zombie_horse", 20.0); //
        vanillaMobCombatExp.put("sniffer", 17.0); //
        vanillaMobCombatExp.put("snow_golem", 2.0); //
        vanillaMobCombatExp.put("squid", 3.5); //
        vanillaMobCombatExp.put("strider", 14.0); //
        vanillaMobCombatExp.put("tadpole", 1.0); //
        vanillaMobCombatExp.put("tropical_fish", 1.5); //
        vanillaMobCombatExp.put("turtle", 3.0); //
        vanillaMobCombatExp.put("villager", 7.5); //
        vanillaMobCombatExp.put("wandering_trader", 7.5); //

        // Neutral Mobs
        vanillaMobCombatExp.put("bee", 5.75); //
        vanillaMobCombatExp.put("cave_spider", 15.5); //
        vanillaMobCombatExp.put("dolphin", 13.5); //
        vanillaMobCombatExp.put("drowned", 20.5); //
        vanillaMobCombatExp.put("enderman", 35.0); //
        vanillaMobCombatExp.put("fox", 4.5); //
        vanillaMobCombatExp.put("goat", 4.5); //
        vanillaMobCombatExp.put("iron_golem", 25.0); //
        vanillaMobCombatExp.put("llama", 6.5); //
        vanillaMobCombatExp.put("panda", 6.5); //
        vanillaMobCombatExp.put("piglin", 20.5); //
        vanillaMobCombatExp.put("polar_bear", 16.0); //
        vanillaMobCombatExp.put("spider", 17.0); //
        vanillaMobCombatExp.put("trader_llama", 7.5); //
        vanillaMobCombatExp.put("wolf", 15.0); //
        vanillaMobCombatExp.put("zombified_piglin", 27.0); //

        // Hostile Mobs
        vanillaMobCombatExp.put("blaze", 30.0); //
        vanillaMobCombatExp.put("creeper", 27.5); //
        vanillaMobCombatExp.put("elder_guardian", 100.0); // Boss //
        vanillaMobCombatExp.put("endermite", 20.0); //
        vanillaMobCombatExp.put("ender_dragon", 50000.0); // Boss //
        vanillaMobCombatExp.put("evoker", 40.0); //
        vanillaMobCombatExp.put("ghast", 21.0); //
        vanillaMobCombatExp.put("guardian", 26.0); //
        vanillaMobCombatExp.put("hoglin", 24.0); //
        vanillaMobCombatExp.put("husk", 20.5); //
        vanillaMobCombatExp.put("illusioner", 200.0); //
        vanillaMobCombatExp.put("magma_cube", 22.0); //
        vanillaMobCombatExp.put("phantom", 23.0); //
        vanillaMobCombatExp.put("piglin_brute", 45.0); //
        vanillaMobCombatExp.put("pillager", 18.0); //
        vanillaMobCombatExp.put("ravager", 60.0); //
        vanillaMobCombatExp.put("shulker", 75.0); //
        vanillaMobCombatExp.put("silverfish", 7.0); //
        vanillaMobCombatExp.put("skeleton", 18.5); //
        vanillaMobCombatExp.put("slime", 16.0); //
        vanillaMobCombatExp.put("stray", 19.5); //
        vanillaMobCombatExp.put("vex", 4.75); //
        vanillaMobCombatExp.put("vindicator", 40.0); //
        vanillaMobCombatExp.put("warden", 750.0); //
        vanillaMobCombatExp.put("witch", 28.0); //
        vanillaMobCombatExp.put("wither", 40000.0); // Boss //
        vanillaMobCombatExp.put("wither_skeleton", 31.5); //
        vanillaMobCombatExp.put("zoglin", 27.0); //
        vanillaMobCombatExp.put("zombie", 20.0); //
        vanillaMobCombatExp.put("zombie_villager", 20.0); //

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
        PersistentDataContainer persistentDataContainer = mob.getPersistentDataContainer();

        if (persistentDataContainer.get(MobKeys.VENTURE_MOB, PersistentDataType.STRING) != null) // TODO: Change to check for VENTURE_MOB_COMBAT_EXP key on mob
        {
            String ventureMobName = persistentDataContainer.get(MobKeys.VENTURE_MOB, PersistentDataType.STRING);
            if (ventureMobCombatExp.get(ventureMobName) != null)
            {
                exp = ventureMobCombatExp.get(ventureMobName);
            }
        } else if (persistentDataContainer.get(MobKeys.MOB_TYPE, PersistentDataType.STRING) != null)
        {
            String vanillaMobName = persistentDataContainer.get(MobKeys.MOB_TYPE, PersistentDataType.STRING);
            if (vanillaMobCombatExp.get(vanillaMobName) != null)
            {
                exp = vanillaMobCombatExp.get(vanillaMobName);
            }
        }

        return exp;
    }
}

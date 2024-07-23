package net.laserdiamond.ventureplugin.skills.Components.ExpGain;

import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.HashMap;

public class BrewingExp {

    private static final HashMap<PotionEffectType, Double> potionEffectBrewingExp = new HashMap<>();
    static
    { // TODO: Finalize brewing exp from base effects
        potionEffectBrewingExp.put(PotionEffectType.SPEED, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.SLOW, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.FAST_DIGGING, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.SLOW_DIGGING, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.INCREASE_DAMAGE, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.HEAL, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.HARM, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.JUMP, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.CONFUSION, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.REGENERATION, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.DAMAGE_RESISTANCE, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.FIRE_RESISTANCE, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.WATER_BREATHING, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.INVISIBILITY, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.BLINDNESS, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.NIGHT_VISION, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.HUNGER, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.WEAKNESS, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.POISON, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.WITHER, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.HEALTH_BOOST, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.ABSORPTION, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.SATURATION, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.GLOWING, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.LEVITATION, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.LUCK, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.UNLUCK, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.SLOW_FALLING, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.CONDUIT_POWER, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.DOLPHINS_GRACE, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.BAD_OMEN, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.HERO_OF_THE_VILLAGE, 300.0);
        potionEffectBrewingExp.put(PotionEffectType.DARKNESS, 300.0);
    }

    /**
     * Gets the brewing exp a potion will give when brewed
     * <p>
     * Brewing exp is calculated with the following in mind for each potion effect in a given potion:
     *
     * <li>Potion Effect(s)</li>
     * <li>The effect duration(s)</li>
     * <li>The effect amplifier(s)</li>
     *
     * @param potionMeta The potionMeta of the potion
     * @return The amount of brewing exp the potion will give
     */
    public static Double getBrewingExp(PotionMeta potionMeta)
    {
        double exp = 0.0;
        if (potionMeta != null)
        {
            PotionType potionType = potionMeta.getBasePotionData().getType();

            PotionEffectType effectType = potionType.getEffectType();
            if (potionEffectBrewingExp.containsKey(effectType) && potionEffectBrewingExp.get(effectType) != null)
            {
                double effectExp = potionEffectBrewingExp.get(effectType);
                exp += effectExp;
                if (potionMeta.getBasePotionData().isExtended())
                {
                    exp += 3000;
                } else
                {
                    exp += 900;
                }
                if (potionMeta.getBasePotionData().isUpgraded())
                {
                    exp += 1200;
                } else
                {
                    exp += 500;
                }
                if (potionType.isInstant())
                {
                    exp += 1000;
                } else
                {
                    exp += 450;
                }
            }
            /*
            List<PotionEffect> potionEffects = potionMeta.getCustomEffects();

            for (PotionEffect potionEffect : potionEffects)
            {
                PotionEffectType effect = potionEffect.getType();
                int duration = potionEffect.getDuration() / 20;
                int amplifier = potionEffect.getAmplifier();

                double potionEffectExp = 0.0;
                if (potionEffectBrewingExp.containsKey(effect) && potionEffectBrewingExp.get(effect) != null)
                {
                    double effectExp = potionEffectBrewingExp.get(effect);
                    double durationExp = duration * 5;
                    double amplifierExp = (amplifier + 1) * 250;
                    potionEffectExp = effectExp + durationExp + amplifierExp;
                    exp += potionEffectExp;
                }
            }

             */
        }

        return exp;
    }
}

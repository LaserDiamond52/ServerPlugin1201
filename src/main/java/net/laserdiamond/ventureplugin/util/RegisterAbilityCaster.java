package net.laserdiamond.ventureplugin.util;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.abilities.AbilityListener;

import java.util.List;

/**
 * This class is used to add classes to a list that have implemented the SpellCastListener Interface
 * <p>
 * Classes should not be registered unless they contain a spell/ability that should be used
 *
 */

public class RegisterAbilityCaster {



    public static void addListener(AbilityListener abilityListener, VenturePlugin plugin)
    {
        plugin.getAbilityListeners().add(abilityListener);
    }

    public static void addListeners(List<AbilityListener> abilityListeners, VenturePlugin plugin)
    {
        for (AbilityListener abilityListener : abilityListeners)
        {
            plugin.getAbilityListeners().add(abilityListener);
        }
    }
}

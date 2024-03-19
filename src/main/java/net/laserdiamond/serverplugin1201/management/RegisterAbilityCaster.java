package net.laserdiamond.serverplugin1201.management;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.events.abilities.AbilityListener;

import java.util.List;

/**
 * This class is used to add classes to a list that have implemented the SpellCastListener Interface
 * <p>
 * Classes should not be registered unless they contain a spell/ability that should be used
 *
 */

public class RegisterAbilityCaster {



    public static void addListener(AbilityListener abilityListener, ServerPlugin1201 plugin)
    {
        plugin.getAbilityListeners().add(abilityListener);
    }

    public static void addListeners(List<AbilityListener> abilityListeners, ServerPlugin1201 plugin)
    {
        for (AbilityListener abilityListener : abilityListeners)
        {
            plugin.getAbilityListeners().add(abilityListener);
        }
    }
}

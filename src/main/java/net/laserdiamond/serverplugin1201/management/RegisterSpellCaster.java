package net.laserdiamond.serverplugin1201.management;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.events.SpellCasting.SpellCastListener;
import net.laserdiamond.serverplugin1201.events.SpellCasting.SpellCastListeners;

import java.util.List;

/**
 * This class is used to add classes to a list that have implemented the SpellCastListener Interface
 * <p>
 * Classes should not be registered unless they contain a spell/ability that should be used
 *
 */

public class RegisterSpellCaster {



    public static void registerListener(SpellCastListener spellCastListener, ServerPlugin1201 plugin)
    {
        plugin.getSpellCastListeners().add(spellCastListener);
    }

    public static void registerListeners(List<SpellCastListener> spellCastListeners, ServerPlugin1201 plugin)
    {
        for (SpellCastListener spellCastListener : spellCastListeners)
        {
            plugin.getSpellCastListeners().add(spellCastListener);
        }
    }
}

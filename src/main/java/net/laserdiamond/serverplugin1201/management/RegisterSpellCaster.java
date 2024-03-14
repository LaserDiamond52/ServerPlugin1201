package net.laserdiamond.serverplugin1201.management;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.events.SpellCasting.SpellCastListener;
import net.laserdiamond.serverplugin1201.events.SpellCasting.SpellCastListeners;

import java.util.List;

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

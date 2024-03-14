package net.laserdiamond.serverplugin1201.events.SpellCasting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpellCastHandler
{
    /**
     * Define how a spell is triggered:
     * <ol>
     * <li>RIGHT_CLICK
     * <li>LEFT_CLICK
     * <li>DROP_ITEM
     * <li>RUNNABLE
     * </ol>
     *
     * @return The spellCastType
     */
    SpellCastType spellCastType();
}

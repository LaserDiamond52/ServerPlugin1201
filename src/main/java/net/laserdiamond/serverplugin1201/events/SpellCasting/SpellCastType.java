package net.laserdiamond.serverplugin1201.events.SpellCasting;

public enum SpellCastType {

    /**
     * Enum that represents different ways a spell/ability can be activated from an event
     *     <li>RIGHT_CLICK - Abilities activated by right-clicking
     *     <li>LEFT_CLICK - Abilities activated by left-clicking
     *     <li>DROP_KEY - Abilities activated by dropping an item
     *     <li>RUNNABLE - Abilities activated in a runnable event (check for sneaking to make sneaking spell)
     */
    RIGHT_CLICK,
    LEFT_CLICK,
    DROP_ITEM,
    RUNNABLE


}

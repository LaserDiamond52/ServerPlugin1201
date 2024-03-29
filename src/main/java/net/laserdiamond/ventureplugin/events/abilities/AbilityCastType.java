package net.laserdiamond.ventureplugin.events.abilities;

public enum AbilityCastType {

    /**
     * Enum that represents different ways an ability can be activated from an event
     *     <li>RIGHT_CLICK - Abilities activated by right-clicking
     *     <li>LEFT_CLICK - Abilities activated by left-clicking
     *     <li>DROP_KEY - Abilities activated by dropping an item
     *     <li>ATTACK_ENTITY - Abilities activated by attacking an entity
     *     <li>RUNNABLE - Abilities activated in a runnable event (check for sneaking to make sneaking spell)
     *     <li>ON_KILL - Abilities activated by killing a mob/player
     *     <li>TOGGLE_SNEAK - Abilities activated by toggling the sneak key
     */
    RIGHT_CLICK,
    LEFT_CLICK,
    DROP_ITEM,
    ATTACK_ENTITY,
    RUNNABLE,
    ON_KILL,
    TOGGLE_SNEAK


}

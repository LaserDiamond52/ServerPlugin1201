package net.laserdiamond.ventureplugin.events.abilities;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public record Abilities(HashMap<AbilityListener, Method> rightClickAbilities, HashMap<AbilityListener, Method> leftClickAbilities, HashMap<AbilityListener, Method> dropItemAbilities, HashMap<AbilityListener, Method> runnableAbilities, HashMap<AbilityListener, Method> attackAbility, HashMap<AbilityListener, Method> onKillAbility)
{
}

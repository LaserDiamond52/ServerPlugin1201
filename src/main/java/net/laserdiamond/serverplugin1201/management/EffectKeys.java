package net.laserdiamond.serverplugin1201.management;

import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.ManaFreezeTimer;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.NecrosisTimer;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.ParalyzeTimer;
import net.laserdiamond.serverplugin1201.events.effects.Components.Timers.VulnerableTimer;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandException;

import java.util.HashMap;
import java.util.UUID;

public enum EffectKeys {

    GOD (new NamespacedKey("effects", "god"), "god", null,null, null),
    MANA_FREEZE (new NamespacedKey("effects", "manafreeze"), "mana_freeze", ManaFreezeTimer.manaFreezeTimer, ManaFreezeTimer.manaFreezeAmp, ManaFreezeTimer.class),
    NECROSIS (new NamespacedKey("effects", "necrosis"), "necrosis", NecrosisTimer.necrosisTimer, NecrosisTimer.necrosisAmp, NecrosisTimer.class),
    PARALYZE (new NamespacedKey("effects", "paralyze"), "paralyze", ParalyzeTimer.paralyzeTimer, ParalyzeTimer.paralyzeAmp, ParalyzeTimer.class),
    VULNERABLE (new NamespacedKey("effects", "vulnerable_all"), "vulnerable", VulnerableTimer.vulnerableTimer, VulnerableTimer.vulnerableAmp, VulnerableTimer.class);

    private final NamespacedKey key;
    private final String commandName;
    private final HashMap<UUID, Double> effectDurationMap;
    private final HashMap<UUID, Integer> effectAmpMap;
    private final Class<?> clazz;
    EffectKeys(NamespacedKey key, String commandName, HashMap<UUID, Double> effectDurationMap, HashMap<UUID, Integer> effectAmpMap, Class<?> clazz) {
        this.key = key;
        this.commandName = commandName;
        this.effectDurationMap = effectDurationMap;
        this.effectAmpMap = effectAmpMap;
        this.clazz = clazz;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public String getCommandName() {
        return commandName;
    }

    public HashMap<UUID, Double> getEffectDurationMap() {
        return effectDurationMap;
    }

    public HashMap<UUID, Integer> getEffectAmpMap() {
        return effectAmpMap;
    }
    public static HashMap<UUID, Double> ofHashMap(String input) {
        for (EffectKeys effectKeys : values()) {
            if (effectKeys.commandName.equals(input)) {
                return effectKeys.effectDurationMap;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "Not an effect: " + input);
    }

    public static String ofString(String input, boolean includeGod) {
        for (EffectKeys effectKeys : values()) {
            if (effectKeys.commandName.equals(input)) {
                if (includeGod) {
                    return effectKeys.commandName;
                } else {
                    if (!input.equals(GOD.getCommandName())) {
                        return effectKeys.commandName;
                    } else {
                        throw new IllegalArgumentException(ChatColor.RED + "Not an effect: " + input);
                    }
                }

            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "Not an effect: " + input);
    }

    public static EffectKeys fromString(String input) throws CommandException {

        if (input.equalsIgnoreCase("manafreeze")) {
            return MANA_FREEZE;
        } else if (input.equalsIgnoreCase("necrosis")) {
            return NECROSIS;
        } else if (input.equalsIgnoreCase("paralyze")) {
            return PARALYZE;
        } else if (input.equalsIgnoreCase("vulnerable_all")) {
            return VULNERABLE;
        } else if (input.equalsIgnoreCase("god")) {
            return GOD;
        } else {
            throw new CommandException(ChatColor.RED + "Undefined effect: " + input);
        }

    }


    public Class<?> getClazz() {
        return clazz;
    }
}

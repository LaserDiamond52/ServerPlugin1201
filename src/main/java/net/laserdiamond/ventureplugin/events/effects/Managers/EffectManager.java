package net.laserdiamond.ventureplugin.events.effects.Managers;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.effects.Components.EffectDurations;
import net.laserdiamond.ventureplugin.events.effects.Components.EffectProfile;
import net.laserdiamond.ventureplugin.events.effects.Config.EffectProfileConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EffectManager {

    private VenturePlugin plugin;

    private Map<UUID, EffectProfile> effectProfiles = new HashMap<>();

    private EffectProfileConfig effectDurationProfileConfig;
    private FileConfiguration effectConfig;
    public EffectManager(VenturePlugin plugin) {
        this.plugin = plugin;
        effectDurationProfileConfig = plugin.getEffectProfileConfig();
        effectConfig = effectDurationProfileConfig.getEffectConfig();
    }

    public void loadProfilesFromConfig() {
        for (String id : effectConfig.getConfigurationSection("").getKeys(false)) {
            UUID uuid = UUID.fromString(id);
            int paralyzeDuration = effectConfig.getInt(id + ".effects.paralyze");
            int manaFreezeDuration = effectConfig.getInt(id + ".effects.manaFreeze");
            int necrosisDuration = effectConfig.getInt(id + ".effects.necrosis");
            int vulnerable = effectConfig.getInt(id + ".effects.vulnerability");

            EffectDurations effectDurations = new EffectDurations(paralyzeDuration, manaFreezeDuration, necrosisDuration, vulnerable);
            EffectProfile effectProfile = new EffectProfile(effectDurations);
            effectProfiles.put(uuid, effectProfile);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded effect durations for " + id + "!");
        }
    }

    public void saveProfilesToConfig() {
        for (UUID uuid : effectProfiles.keySet()) {
            String id = uuid.toString();
            EffectProfile effectProfile = effectProfiles.get(uuid);
            EffectDurations effectDuration = effectProfile.getEffectDurations();
            effectConfig.set(id + ".effects.paralyze", effectDuration.getParalyzeDuration());
            effectConfig.set(id + ".effects.manaFreeze", effectDuration.getManaFreezeDuration());
            effectConfig.set(id + ".effects.necrosis", effectDuration.getNecrosisDuration());
            effectConfig.set(id + ".effects.vulnerability", effectDuration.getVulnerableDuration());

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved effect durations for " + id + "!");
        }
    }

    public EffectProfile createNewProfile(Player player) {
        EffectDurations effectDurations = new EffectDurations(0,0,0,0);
        EffectProfile effectProfile = new EffectProfile(effectDurations);
        effectProfiles.put(player.getUniqueId(), effectProfile);
        return effectProfile;
    }

    public EffectProfile getEffectProfile(UUID uuid) {
        return effectProfiles.get(uuid);
    }
}

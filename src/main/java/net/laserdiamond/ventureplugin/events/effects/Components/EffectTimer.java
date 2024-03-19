package net.laserdiamond.ventureplugin.events.effects.Components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.effects.Managers.EffectManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectTimer extends BukkitRunnable {

    private VenturePlugin plugin;
    private EffectManager effectManager;

    public EffectTimer(VenturePlugin plugin) {
        this.plugin = plugin;
        effectManager = plugin.getEffectManager();
    }

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            EffectDurations effectDurations = effectManager.getEffectProfile(player.getUniqueId()).getEffectDurations();

            int paralyzeDuration = effectDurations.getParalyzeDuration();
            int manaFreezeDuration = effectDurations.getManaFreezeDuration();
            int necrosisDuration = effectDurations.getNecrosisDuration();
            int vulnerabilityDuration = effectDurations.getNecrosisDuration();

            if (paralyzeDuration > 0) {
                effectDurations.setParalyzeDuration(paralyzeDuration-1);
            }
            if (manaFreezeDuration > 0) {
                effectDurations.setManaFreezeDuration(manaFreezeDuration-1);
            }
            if (necrosisDuration > 0) {
                effectDurations.setNecrosisDuration(necrosisDuration-1);
            }
            if (vulnerabilityDuration > 0) {
                effectDurations.setVulnerableDuration(vulnerabilityDuration-1);
            }
        }
    }
}

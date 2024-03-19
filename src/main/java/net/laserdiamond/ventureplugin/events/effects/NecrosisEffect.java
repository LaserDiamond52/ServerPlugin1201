package net.laserdiamond.ventureplugin.events.effects;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.EffectKeys;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

public class NecrosisEffect {

    static VenturePlugin plugin = VenturePlugin.getInstance();


    public static void necrosisMob(Mob mob, int durationSeconds) {

        if (!EffectBoolean.hasEffect(mob, EffectKeys.NECROSIS)) {
            EffectBoolean.setEffectBoolean(mob, EffectKeys.NECROSIS, true);
        }

        mob.sendMessage(Messages.necrosisMessage(durationSeconds));

        new BukkitRunnable() {
            int i = durationSeconds;
            public void run() {
                i--;
                if (i > 0) {
                    if (!EffectBoolean.hasEffect(mob, EffectKeys.NECROSIS)) {
                        EffectBoolean.setEffectBoolean(mob, EffectKeys.NECROSIS, true);
                    }
                }

                if (i == 0 || mob.isDead()) {
                    if (!EffectBoolean.hasEffect(mob, EffectKeys.NECROSIS)) {
                        mob.sendMessage(ChatColor.YELLOW + "You are no longer affected by necrosis");
                    }
                    EffectBoolean.setEffectBoolean(mob, EffectKeys.NECROSIS, false);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}

package net.laserdiamond.serverplugin1201.events.effects;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.Management.EffectKeys;
import net.laserdiamond.serverplugin1201.Management.messages.messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

public class NecrosisEffect {

    static ServerPlugin1201 plugin = ServerPlugin1201.getInstance();


    public static void necrosisMob(Mob mob, int durationSeconds) {

        if (!EffectBoolean.hasEffect(mob, EffectKeys.NECROSIS)) {
            EffectBoolean.setEffectBoolean(mob, EffectKeys.NECROSIS, true);
        }

        mob.sendMessage(messages.necrosisMessage(durationSeconds));

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

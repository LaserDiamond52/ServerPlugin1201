package net.laserdiamond.serverplugin1201.events.effects;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.Management.EffectKeys;
import net.laserdiamond.serverplugin1201.Management.messages.messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaFreezeEffect {

    static ServerPlugin1201 plugin = ServerPlugin1201.getInstance();

    @Deprecated
    public static void manaFreeze(Player player, int durationSeconds) {

        EffectBoolean.setEffectBoolean(player, EffectKeys.MANA_FREEZE, true);

        player.sendMessage(messages.manaFreezeMessage(durationSeconds));

        new BukkitRunnable() {
            int i = durationSeconds;
            public void run() {
                i--;
                if (i == 0 || player.isDead()) {
                    EffectBoolean.setEffectBoolean(player, EffectKeys.MANA_FREEZE, false);
                    player.sendMessage(ChatColor.AQUA + "Your mana starts to regenerate again");
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);

    }

    @Deprecated
    public static void unfreezeMana(Player player) {
        if (EffectBoolean.hasEffectKey(player, EffectKeys.MANA_FREEZE)) {
            EffectBoolean.setEffectBoolean(player, EffectKeys.MANA_FREEZE, false);
            player.sendMessage(ChatColor.AQUA + "Your mana starts to regenerate again");
        }
    }

}

package net.laserdiamond.ventureplugin.events.effects;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.EffectKeys;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaFreezeEffect {

    static VenturePlugin plugin = VenturePlugin.getInstance();

    @Deprecated
    public static void manaFreeze(Player player, int durationSeconds) {

        EffectBoolean.setEffectBoolean(player, EffectKeys.MANA_FREEZE, true);

        player.sendMessage(Messages.manaFreezeMessage(durationSeconds));

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

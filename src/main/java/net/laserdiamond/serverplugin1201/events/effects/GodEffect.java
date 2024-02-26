package net.laserdiamond.serverplugin1201.events.effects;

import net.laserdiamond.serverplugin1201.management.EffectKeys;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GodEffect {

    public static void god(Player player) {

        PersistentDataContainer pdc = player.getPersistentDataContainer();

        pdc.set(EffectKeys.GOD.getKey(), PersistentDataType.BOOLEAN, true);

        player.sendMessage(ChatColor.GOLD + "God mode on!");
    }

    public static void ungod(Player player) {

        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (EffectBoolean.hasEffect(player, EffectKeys.GOD)) {
            pdc.set(EffectKeys.GOD.getKey(), PersistentDataType.BOOLEAN, false);
        }
        player.sendMessage(ChatColor.GOLD + "God mode off!");
    }
}

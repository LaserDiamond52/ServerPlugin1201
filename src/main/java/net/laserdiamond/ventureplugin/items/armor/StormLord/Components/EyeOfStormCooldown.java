package net.laserdiamond.ventureplugin.items.armor.StormLord.Components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.armor.StormLord.Config.StormLordArmorConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EyeOfStormCooldown {

    private static HashMap<UUID, Double> cooldown;
    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final StormLordArmorConfig ARMOR_CONFIG = PLUGIN.getStormLordArmorConfig();
    private static boolean displayReadyMessage = false;

    // How cooldown works

    // Sets value in hashmap for player

    // Boolean "checkCooldown" checks when the player's HashMap value is either equal
    // to or less than System.currentTimeMillis() (or if it is null)


    public static void setUpCooldown() {
        cooldown = new HashMap<>();
    }

    public static void setCooldown(Player player, int seconds) {
        double delay = System.currentTimeMillis() + (seconds * 1000L);
        UUID playerUUID = player.getUniqueId();
        cooldown.put(playerUUID, delay);

    }

    public static boolean checkCooldown(Player player)
    {
        if (!cooldown.containsKey(player.getUniqueId()) || cooldown.get(player.getUniqueId()) <= System.currentTimeMillis())
        {
            // Player is not on cooldown
            return true;
        }
        // Player is on cooldown
        return false;
    }
}

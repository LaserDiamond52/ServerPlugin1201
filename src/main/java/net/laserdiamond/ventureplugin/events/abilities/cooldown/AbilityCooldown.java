package net.laserdiamond.ventureplugin.events.abilities.cooldown;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public abstract class AbilityCooldown {

    private static HashMap<UUID, Double> cooldown;
    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();

    /**
     * Sets up the cooldown
     */
    public static void setUpCooldown()
    {
        cooldown = new HashMap<>();
    }

    /**
     * Sets the cooldown for the player
     * @param player The player to set the cooldown for
     * @param seconds The duration to set the cooldown for (in seconds)
     */
    public static void setCooldown(Player player, int seconds)
    {
        double delay = System.currentTimeMillis() + (seconds *  1000L);
        UUID playerUUID = player.getUniqueId();
        cooldown.put(playerUUID, delay);
    }

    /**
     * Checks if the player is on cooldown
     * @param player The player to check the cooldown on
     * @return True if the player's hashmap value is null or if System.currentTimeMillis is greater than or equal to the player's stored System.currentTimeMillis.
     * <p> False if otherwise
     */
    public static boolean checkCooldown(Player player)
    {
        if (!cooldown.containsKey(player.getUniqueId()) || cooldown.get(player.getUniqueId()) <= System.currentTimeMillis())
        {
            return true; // Player is not on cooldown
        }
        return false; // Player is on cooldown
    }
}

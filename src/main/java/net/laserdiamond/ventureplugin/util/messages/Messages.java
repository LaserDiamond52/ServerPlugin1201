package net.laserdiamond.ventureplugin.util.messages;

import org.bukkit.ChatColor;

public class Messages {

    public static String notEnoughMana() {
        return ChatColor.RED + "Not enough mana";
    }
    public static String notPlayerCommand()
    {
        return ChatColor.RED + "You must be a player in order to use this command";
    }
    public static String notAllowedCommand()
    {
        return ChatColor.RED + "You are not allowed to use this command";
    }
    public static String manaFreezeMessage(int durationSeconds)
    {
        return "" + ChatColor.AQUA + ChatColor.BOLD + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.DARK_GRAY + ChatColor.BOLD + " Your mana regen is frozen for " + durationSeconds + " seconds! " + ChatColor.AQUA + ChatColor.BOLD + ChatColor.MAGIC + "X";
    }

    public static String necrosisMessage(int durationSeconds)
    {
        return "" + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.DARK_GRAY + ChatColor.BOLD + " You now have necrosis and are unable to heal for " + durationSeconds + " seconds " + ChatColor.YELLOW + ChatColor.BOLD + ChatColor.MAGIC + "X";
    }

    public static String paralyzeMessage(int durationSeconds)
    {
        return "" + ChatColor.RED + ChatColor.BOLD + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.DARK_GRAY + ChatColor.BOLD + " You've been paralyzed for " + durationSeconds + " seconds! " + ChatColor.RED + ChatColor.BOLD + ChatColor.MAGIC + "X";
    }

    public static String vulnerableMessage(int durationSeconds)
    {
        return "" + ChatColor.DARK_RED + ChatColor.BOLD + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.DARK_GRAY + ChatColor.BOLD + " You're now vulnerable to incoming damage for " + durationSeconds + " seconds " + ChatColor.DARK_RED + ChatColor.BOLD + ChatColor.MAGIC + "X";
    }

    public static String abilityReady(String abilityName) {
        return ChatColor.GOLD + abilityName + " " + ChatColor.GREEN + "is ready to be used again";
    }
    public static String abilityUse(String abilityName) {
        return ChatColor.GOLD + abilityName + " " + ChatColor.GREEN + "used!";
    }

    public static String abilityCooldown(String abilityName)
    {
        return ChatColor.RED + abilityName + " is on cooldown";
    }

    public static String abilityAlreadyActive(String abilityName)
    {
        return ChatColor.DARK_GRAY + abilityName + " is already active!";
    }

    public static String cancelledSpellMsg()
    {
        return ChatColor.RED + "You are unable to cast spells";
    }
}

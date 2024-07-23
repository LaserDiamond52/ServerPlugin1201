package net.laserdiamond.ventureplugin.entities.mobs;

import org.bukkit.ChatColor;

public enum VentureMobType {

    ANCIENT_GUARDIAN (ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Ancient Guardian"),
    BLAZE_GUARDIAN (ChatColor.GOLD + "" + ChatColor.BOLD + "Blaze Guardian"),
    CREEPER_GHOST (ChatColor.GREEN + "Creeper Ghost"),
    EVOLVED_GUARDIAN (ChatColor.DARK_RED + "Evolved Guardian"),
    FLAMING_BLAZE (ChatColor.GOLD + "Flaming Blaze"),
    KRAKEN_HATCHLING (ChatColor.DARK_AQUA + "Kraken Hatchling"),
    LEAPING_SPIDER (ChatColor.DARK_GRAY + "Leaping Spider"),
    LORD_BLAZIUS (ChatColor.GOLD + "" + ChatColor.BOLD + "Lord Blazius"),
    MUTATED_REVENANT (ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Mutated Revenant"),
    SKELETON_SNIPER (ChatColor.GRAY + "Skeleton Sniper"),
    SKELETON_SOLDIER (ChatColor.GRAY + "Skeleton Soldier"),
    SKELLEY_BONES (ChatColor.GRAY + "" + ChatColor.BOLD + "Skelley Bones the Lost Wanderer"),
    ZOMBIE_SOLDIER (ChatColor.DARK_GREEN + "Zombie Soldier"),
    ZOMBIE_BRUTE (ChatColor.DARK_GREEN + "Zombie Brute");

    private final String displayName;

    VentureMobType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static VentureMobType fromString(String input)
    {
        for (VentureMobType ventureMobType : values())
        {
            if (ventureMobType.name().toLowerCase().equals(input))
            {
                return ventureMobType;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "Not a Venture Mob: " + input);
    }
}

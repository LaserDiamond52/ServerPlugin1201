package net.laserdiamond.serverplugin1201.entities.management;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;

public enum MobMappings {

    ANCIENT_GUARDIAN (ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Ancient Guardian", "ancient_guardian", new NamespacedKey("pluginmob", "ancient_guardian"), ElderGuardian.class),
    BLAZE_BOSS (ChatColor.GOLD + "" + ChatColor.BOLD + "Lord Blazius", "blaze_boss", new NamespacedKey("pluginmob", "blaze_boss"), Blaze.class),
    BLAZE_GUARDIAN (ChatColor.GOLD + "" + ChatColor.BOLD + "Blaze Guardian", "blaze_guardian", new NamespacedKey("pluginmob", "blaze_guardian"), Blaze.class),
    BLAZE_FLAMING (ChatColor.GOLD + "Flaming Blaze", "flaming_blaze", new NamespacedKey("pluginmob", "blaze_flaming"), Blaze.class),
    EVOLVED_GUARDIAN (ChatColor.DARK_RED + "Evolved Guardian", "evolved_guardian", new NamespacedKey("pluginmob", "evolved_guardian"), Guardian.class),
    KRAKEN_HATCHLING (ChatColor.DARK_AQUA + "Kraken Hatchling", "kraken_hatchling", new NamespacedKey("pluginmob", "kraken_hatchling"), Squid.class),
    LEAPING_SPIDER (ChatColor.DARK_GRAY + "Leaping Spider", "leaping_spider", new NamespacedKey("pluginmob", "leaping_spider"), Spider.class),
    SKELETON_SOLDIER (ChatColor.GRAY + "Skeleton Soldier", "skeleton_soldier", new NamespacedKey("pluginmob", "skeleton_solder"), Skeleton.class),
    SKELETON_SNIPER (ChatColor.GRAY + "Skeleton Sniper", "skeleton_sniper", new NamespacedKey("pluginmob", "skeleton_sniper"), Skeleton.class),
    SKELETON_BOSS (ChatColor.GRAY + "" + ChatColor.BOLD + "Skelley Bones the Lost Wanderer", "skeleton_boss", new NamespacedKey("pluginmob", "skeleton_boss"), Skeleton.class),
    ZOMBIE_SOLDIER (ChatColor.DARK_GREEN + "Zombie Soldier", "zombie_soldier", new NamespacedKey("pluginmob", "zombie_soldier"), Zombie.class),
    ZOMBIE_BRUTE (ChatColor.DARK_GREEN + "Zombie Brute", "zombie_brute", new NamespacedKey("pluginmob", "zombie_brute"), Zombie.class),
    ZOMBIE_BOSS (ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Flesh Horror", "zombie_boss", new NamespacedKey("pluginmob", "zombie_boss"), Zombie.class);

    private final String displayName;
    private final String commandName;
    private final NamespacedKey key;
    private final Class<?> mobClass;

    MobMappings(String displayName, String commandName, NamespacedKey key, Class<?> mobClass) {
        this.displayName = displayName;
        this.commandName = commandName;
        this.key = key;
        this.mobClass = mobClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCommandName() {
        return commandName;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public Class<?> getMobClass() {
        return mobClass;
    }
}

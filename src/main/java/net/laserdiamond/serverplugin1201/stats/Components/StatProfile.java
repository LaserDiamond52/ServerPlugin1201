package net.laserdiamond.serverplugin1201.stats.Components;

import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;

public class StatProfile {

    private Stats stats;
    private EnchantStats enchantStats;

    public StatProfile(Stats stats, EnchantStats enchantStats) {
        this.stats = stats;
        this.enchantStats = enchantStats;

    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public EnchantStats getEnchantStats() {
        return enchantStats;
    }

    public void setEnchantStats(EnchantStats enchantStats) {
        this.enchantStats = enchantStats;
    }
}

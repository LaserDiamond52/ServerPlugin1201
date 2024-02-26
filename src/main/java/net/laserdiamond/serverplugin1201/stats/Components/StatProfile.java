package net.laserdiamond.serverplugin1201.stats.Components;

import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Manager.ArmorTrimStats;

public class StatProfile {

    private Stats stats;
    private EnchantStats enchantStats;
    private ArmorTrimStats armorTrimStats;

    public StatProfile(Stats stats, EnchantStats enchantStats, ArmorTrimStats armorTrimStats) {
        this.stats = stats;
        this.enchantStats = enchantStats;
        this.armorTrimStats = armorTrimStats;

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

    public ArmorTrimStats getArmorTrimStats() {
        return armorTrimStats;
    }

    public void setArmorTrimStats(ArmorTrimStats armorTrimStats) {
        this.armorTrimStats = armorTrimStats;
    }
}

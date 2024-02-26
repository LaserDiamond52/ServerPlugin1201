package net.laserdiamond.serverplugin1201.items.armor.Trims.Manager;

import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimPatternStats;

public class ArmorTrimStats {

    private ArmorTrimMaterialStats armorTrimMaterialStats;
    private ArmorTrimPatternStats armorTrimPatternStats;

    public ArmorTrimStats(ArmorTrimMaterialStats armorTrimMaterialStats, ArmorTrimPatternStats armorTrimPatternStats) {
        this.armorTrimMaterialStats = armorTrimMaterialStats;
        this.armorTrimPatternStats = armorTrimPatternStats;
    }

    public ArmorTrimMaterialStats getArmorTrimMaterialStats() {
        return armorTrimMaterialStats;
    }

    public void setArmorTrimMaterialStats(ArmorTrimMaterialStats armorTrimMaterialStats) {
        this.armorTrimMaterialStats = armorTrimMaterialStats;
    }

    public ArmorTrimPatternStats getArmorTrimPatternStats() {
        return armorTrimPatternStats;
    }

    public void setArmorTrimPatternStats(ArmorTrimPatternStats armorTrimPatternStats) {
        this.armorTrimPatternStats = armorTrimPatternStats;
    }
}

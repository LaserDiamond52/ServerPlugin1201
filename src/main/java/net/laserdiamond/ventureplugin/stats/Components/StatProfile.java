package net.laserdiamond.ventureplugin.stats.Components;

import net.laserdiamond.ventureplugin.enchants.Components.EnchantStats;
import net.laserdiamond.ventureplugin.items.armor.Trims.Manager.ArmorTrimStats;
import net.laserdiamond.ventureplugin.tunement.Components.TunementProfile;

public record StatProfile(Stats stats, DamageStats damageStats, DefenseStats defenseStats, LootStats lootStats, EnchantStats enchantStats, ArmorTrimStats armorTrimStats, TunementProfile tunementProfile) {

}

package net.laserdiamond.serverplugin1201.stats.Components;

import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Manager.ArmorTrimStats;
import net.laserdiamond.serverplugin1201.tunement.Components.TunementProfile;

public record StatProfile(Stats stats, DamageStats damageStats, DefenseStats defenseStats, LootStats lootStats, EnchantStats enchantStats, ArmorTrimStats armorTrimStats, TunementProfile tunementProfile) {

}

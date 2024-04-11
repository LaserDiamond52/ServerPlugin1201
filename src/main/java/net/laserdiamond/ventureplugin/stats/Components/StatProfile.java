package net.laserdiamond.ventureplugin.stats.Components;

import net.laserdiamond.ventureplugin.enchants.Components.EnchantStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Manager.ArmorTrimStats;
import net.laserdiamond.ventureplugin.skills.Components.SkillsProfile;
import net.laserdiamond.ventureplugin.tuning.Components.TuningProfile;

public record StatProfile(Stats stats, ArmorStats armorStats, DamageStats damageStats, DefenseStats defenseStats, LootStats lootStats, EnchantStats enchantStats, ArmorTrimStats armorTrimStats, TuningProfile tuningProfile, SkillsProfile skillsProfile, PotionStats potionStats) {

}

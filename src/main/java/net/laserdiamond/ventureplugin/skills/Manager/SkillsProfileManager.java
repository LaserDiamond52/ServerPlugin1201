package net.laserdiamond.ventureplugin.skills.Manager;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsProfile;
import net.laserdiamond.ventureplugin.skills.Components.SkillsReward;
import net.laserdiamond.ventureplugin.util.Config.PlayerSaveConfig;
import net.laserdiamond.ventureplugin.util.Config.ProfileConfigs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillsProfileManager implements ProfileConfigs {

    private final Map<UUID, SkillsProfile> profiles = new HashMap<>();
    private final FileConfiguration config;

    public SkillsProfileManager(VenturePlugin plugin)
    {
        PlayerSaveConfig skillsConfig = plugin.getSkillsConfig();
        config = skillsConfig.getConfig();
    }
    @Override
    public void loadFromConfig() {
        for (String id : config.getConfigurationSection("").getKeys(false))
        {
            UUID uuid = UUID.fromString(id);

            int combatLevel = config.getInt(id + ".combat.level");
            double totalCombatEXP = config.getDouble(id + ".combat.totalExp");
            double combatExpToNextLevel = config.getDouble(id + ".combat.ExpToNextLevel");
            double requiredCombatExpToNextLevel = config.getDouble(id + ".combat.requiredExpToNextLevel");
            double combatDamageBonus = config.getDouble(id + ".combat.damageBonus");

            int miningLevel = config.getInt(id + ".mining.level");
            double totalMiningEXP = config.getDouble(id + ".mining.totalExp");
            double miningExpToNextLevel = config.getDouble(id + ".mining.ExpToNextLevel");
            double requiredMiningExpToNextLevel = config.getDouble(id + ".mining.requiredExpToNextLevel");
            double miningDefenseBonus = config.getDouble(id + ".mining.defenseBonus");
            double miningFortuneBonus = config.getDouble(id + ".mining.fortuneBonus");

            int foragingLevel = config.getInt(id + ".foraging.level");
            double totalForagingEXP = config.getDouble(id + ".foraging.totalExp");
            double foragingExpToNextLevel = config.getDouble(id + ".foraging.ExpToNextLevel");
            double requiredForagingExpToNextLevel = config.getDouble(id + ".foraging.requiredExpToNextLevel");
            double foragingFortuneBonus = config.getDouble(id + ".foraging.fortuneBonus");

            int farmingLevel = config.getInt(id + ".farming.level");
            double totalFarmingEXP = config.getDouble(id + ".farming.totalExp");
            double farmingExpToNextLevel = config.getDouble(id + ".farming.ExpToNextLevel");
            double requiredFarmingExpToNextLevel = config.getDouble(id + ".farming.requiredExpToNextLevel");
            double farmingFortuneBonus = config.getDouble(id + ".farming.fortuneBonus");

            int enchantingLevel = config.getInt(id + ".enchanting.level");
            double totalEnchantingEXP = config.getDouble(id + ".enchanting.totalExp");
            double enchantingExpToNextLevel = config.getDouble(id + ".enchanting.ExpToNextLevel");
            double requiredEnchantingExpToNextLevel = config.getDouble(id + ".enchanting.requiredExpToNextLevel");
            double enchantingManaBonus = config.getDouble(id + ".enchanting.manaBonus");

            int fishingLevel = config.getInt(id + ".fishing.level");
            double totalFishingEXP = config.getDouble(id + ".fishing.totalExp");
            double fishingExpToNextLevel = config.getDouble(id + ".fishing.ExpToNextLevel");
            double requiredFishingExpToNextLevel = config.getDouble(id + ".fishing.requiredExpToNextLevel");
            double fishingLuckBonus = config.getDouble(id + ".fishing.luckBonus");

            int brewingLevel = config.getInt(id + ".brewing.level");
            double totalBrewingEXP = config.getDouble(id + ".brewing.totalExp");
            double brewingExpToNextLevel = config.getDouble(id + ".brewing.ExpToNextLevel");
            double requiredBrewingExpToNextLevel = config.getDouble(id + ".brewing.requiredExpToNextLevel");
            double brewingPotionDurationBonus = config.getDouble(id + ".brewing.potionDurationBonus");

            SkillsEXP skillsEXP = new SkillsEXP(totalCombatEXP, combatExpToNextLevel, requiredCombatExpToNextLevel,
                                                totalMiningEXP, miningExpToNextLevel, requiredMiningExpToNextLevel,
                                                totalForagingEXP, foragingExpToNextLevel, requiredForagingExpToNextLevel,
                                                totalFarmingEXP, farmingExpToNextLevel, requiredFarmingExpToNextLevel,
                                                totalEnchantingEXP, enchantingExpToNextLevel, requiredEnchantingExpToNextLevel,
                                                totalFishingEXP, fishingExpToNextLevel, requiredFishingExpToNextLevel,
                                                totalBrewingEXP, brewingExpToNextLevel, requiredBrewingExpToNextLevel);

            SkillsLevel skillsLevel = new SkillsLevel(combatLevel, miningLevel, foragingLevel, farmingLevel, enchantingLevel, fishingLevel, brewingLevel);

            SkillsReward skillsReward = new SkillsReward(combatDamageBonus, miningDefenseBonus, miningFortuneBonus, foragingFortuneBonus, farmingFortuneBonus, enchantingManaBonus, fishingLuckBonus, brewingPotionDurationBonus);

            SkillsProfile skillsProfile = new SkillsProfile(skillsEXP, skillsLevel, skillsReward);

            profiles.put(uuid, skillsProfile);
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded skill profile for user " + id + "!");
        }
    }

    @Override
    public void saveToConfig() {
        for (UUID uuid : profiles.keySet())
        {
            String id = uuid.toString();
            Player player = Bukkit.getPlayer(uuid);
            SkillsProfile skillsProfile = profiles.get(uuid);
            SkillsEXP skillsEXP = skillsProfile.skillsEXP();
            SkillsLevel skillsLevel = skillsProfile.skillsLevel();
            SkillsReward skillsReward = skillsProfile.skillsReward();

            if (player != null)
            {
                config.set(id + ".name", player.getName());
            }

            config.set(id + ".combat.level", skillsLevel.getCombatLevel());
            config.set(id + ".combat.totalExp", skillsEXP.getTotalCombatEXP());
            config.set(id + ".combat.ExpToNextLevel", skillsEXP.getCombatExpToNextLevel());
            config.set(id + ".combat.requiredExpToNextLevel", skillsEXP.getRequiredCombatExpToNextLevel());
            config.set(id + ".combat.damageBonus", skillsReward.getCombatDamageBonus());

            config.set(id + ".mining.level", skillsLevel.getMiningLevel());
            config.set(id + ".mining.totalExp", skillsEXP.getTotalMiningEXP());
            config.set(id + ".mining.ExpToNextLevel", skillsEXP.getMiningExpToNextLevel());
            config.set(id + ".mining.requiredExpToNextLevel", skillsEXP.getRequiredMiningExpToNextLevel());
            config.set(id + ".mining.defenseBonus", skillsReward.getMiningDefenseBonus());
            config.set(id + ".mining.fortuneBonus", skillsReward.getMiningFortuneBonus());

            config.set(id + ".foraging.level", skillsLevel.getForagingLevel());
            config.set(id + ".foraging.totalExp", skillsEXP.getTotalForagingEXP());
            config.set(id + ".foraging.ExpToNextLevel", skillsEXP.getRequiredForagingExpToNextLevel());
            config.set(id + ".foraging.requiredExpToNextLevel", skillsEXP.getRequiredForagingExpToNextLevel());
            config.set(id + ".foraging.fortuneBonus", skillsReward.getForagingFortuneBonus());

            config.set(id + ".farming.level", skillsLevel.getFarmingLevel());
            config.set(id + ".farming.totalExp", skillsEXP.getTotalFarmingEXP());
            config.set(id + ".farming.ExpToNextLevel", skillsEXP.getFarmingExpToNextLevel());
            config.set(id + ".farming.requiredExpToNextLevel", skillsEXP.getRequiredFarmingExpToNextLevel());
            config.set(id + ".farming.fortuneBonus", skillsReward.getFarmingFortuneBonus());

            config.set(id + ".enchanting.level", skillsLevel.getEnchantingLevel());
            config.set(id + ".enchanting.totalExp", skillsEXP.getTotalEnchantingEXP());
            config.set(id + ".enchanting.ExpToNextLevel", skillsEXP.getEnchantingExpToNextLevel());
            config.set(id + ".enchanting.requiredExpToNextLevel", skillsEXP.getRequiredEnchantingExpToNextLevel());
            config.set(id + ".enchanting.manaBonus", skillsReward.getEnchantingManaBonus());

            config.set(id + ".fishing.level", skillsLevel.getFishingLevel());
            config.set(id + ".fishing.totalExp", skillsEXP.getTotalFishingExp());
            config.set(id + ".fishing.ExpToNextLevel", skillsEXP.getFishingExpToNextLevel());
            config.set(id + ".fishing.requiredExpToNextLevel", skillsEXP.getRequiredFishingExpToNextLevel());
            config.set(id + ".fishing.luckBonus", skillsReward.getFishingLuckBonus());

            config.set(id + ".brewing.level", skillsLevel.getBrewingLevel());
            config.set(id + ".brewing.totalExp", skillsEXP.getTotalBrewingExp());
            config.set(id + ".brewing.ExpToNextLevel", skillsEXP.getBrewingExpToNextLevel());
            config.set(id + ".brewing.requiredExpToNextLevel", skillsEXP.getRequiredBrewingExpToNextLevel());
            config.set(id + ".brewing.potionDurationBonus", skillsReward.getBrewingPotionDurationBonus());

        }
    }

    public SkillsProfile createNewProfile(Player player)
    {
        SkillsEXP skillsEXP = new SkillsEXP(0,0,100,
                                            0,0,100,
                                            0,0,100,
                                            0,0,100,
                                            0,0,100,
                                            0,0, 100,
                                            0,0,100);

        SkillsLevel skillsLevel = new SkillsLevel(0,0,0,0,0,0,0);
        SkillsReward skillsReward = new SkillsReward(0,0,0,0,0,0,0,0);
        SkillsProfile skillsProfile = new SkillsProfile(skillsEXP, skillsLevel, skillsReward);
        profiles.put(player.getUniqueId(), skillsProfile);
        return skillsProfile;
    }

    public SkillsProfile getPlayerProfile(UUID uuid)
    {
        return profiles.get(uuid);
    }
}

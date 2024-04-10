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

    private static final String COMBAT = ".combat";
    private static final String MINING = ".mining";
    private static final String FORAGING = ".foraging";
    private static final String FARMING = ".farming";
    private static final String ENCHANTING = ".enchanting";
    private static final String FISHING = ".fishing";
    private static final String BREWING = ".brewing";

    private static final String LEVEL = ".level";
    private static final String TOTAL_EXP = ".totalExp";
    private static final String EXP_TO_NEXT_LEVEL = ".expToNextLevel";
    private static final String REQUIRED_EXP_TO_NEXT_LEVEL = ".requiredExpToNextLevel";

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
            String name = config.getString(id + ".name");

            int combatLevel = config.getInt(id + COMBAT + LEVEL);
            double totalCombatEXP = config.getDouble(id + COMBAT + TOTAL_EXP);
            double combatExpToNextLevel = config.getDouble(id + COMBAT + EXP_TO_NEXT_LEVEL);
            double requiredCombatExpToNextLevel = config.getDouble(id + COMBAT + REQUIRED_EXP_TO_NEXT_LEVEL);
            double combatDamageBonus = config.getDouble(id + COMBAT + ".damageBonus");

            int miningLevel = config.getInt(id + MINING + LEVEL);
            double totalMiningEXP = config.getDouble(id + MINING + TOTAL_EXP);
            double miningExpToNextLevel = config.getDouble(id + MINING + EXP_TO_NEXT_LEVEL);
            double requiredMiningExpToNextLevel = config.getDouble(id + MINING + REQUIRED_EXP_TO_NEXT_LEVEL);
            double miningDefenseBonus = config.getDouble(id + MINING + ".defenseBonus");
            double miningFortuneBonus = config.getDouble(id + MINING + ".fortuneBonus");

            int foragingLevel = config.getInt(id + FORAGING + LEVEL);
            double totalForagingEXP = config.getDouble(id + FORAGING + TOTAL_EXP);
            double foragingExpToNextLevel = config.getDouble(id + FORAGING + EXP_TO_NEXT_LEVEL);
            double requiredForagingExpToNextLevel = config.getDouble(id + FORAGING + REQUIRED_EXP_TO_NEXT_LEVEL);
            double foragingFortuneBonus = config.getDouble(id + FORAGING + ".fortuneBonus");

            int farmingLevel = config.getInt(id + FARMING + LEVEL);
            double totalFarmingEXP = config.getDouble(id + FARMING + TOTAL_EXP);
            double farmingExpToNextLevel = config.getDouble(id + FARMING + EXP_TO_NEXT_LEVEL);
            double requiredFarmingExpToNextLevel = config.getDouble(id + FARMING + REQUIRED_EXP_TO_NEXT_LEVEL);
            double farmingFortuneBonus = config.getDouble(id + FARMING + ".fortuneBonus");

            int enchantingLevel = config.getInt(id + ENCHANTING + LEVEL);
            double totalEnchantingEXP = config.getDouble(id + ENCHANTING + TOTAL_EXP);
            double enchantingExpToNextLevel = config.getDouble(id + ENCHANTING + EXP_TO_NEXT_LEVEL);
            double requiredEnchantingExpToNextLevel = config.getDouble(id + ENCHANTING + REQUIRED_EXP_TO_NEXT_LEVEL);
            double enchantingManaBonus = config.getDouble(id + ENCHANTING + ".manaBonus");

            int fishingLevel = config.getInt(id + FISHING + LEVEL);
            double totalFishingEXP = config.getDouble(id + FISHING + TOTAL_EXP);
            double fishingExpToNextLevel = config.getDouble(id + FISHING + EXP_TO_NEXT_LEVEL);
            double requiredFishingExpToNextLevel = config.getDouble(id + FISHING + REQUIRED_EXP_TO_NEXT_LEVEL);
            double fishingLuckBonus = config.getDouble(id + FISHING + ".luckBonus");

            int brewingLevel = config.getInt(id + BREWING + LEVEL);
            double totalBrewingEXP = config.getDouble(id + BREWING + TOTAL_EXP);
            double brewingExpToNextLevel = config.getDouble(id + BREWING + EXP_TO_NEXT_LEVEL);
            double requiredBrewingExpToNextLevel = config.getDouble(id + BREWING + REQUIRED_EXP_TO_NEXT_LEVEL);
            double brewingPotionDurationBonus = config.getDouble(id + BREWING + ".potionDurationBonus");

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

            config.set(id + COMBAT + LEVEL, skillsLevel.getCombatLevel());
            config.set(id + COMBAT + TOTAL_EXP, skillsEXP.getTotalCombatEXP());
            config.set(id + COMBAT + EXP_TO_NEXT_LEVEL, skillsEXP.getCombatExpToNextLevel());
            config.set(id + COMBAT + REQUIRED_EXP_TO_NEXT_LEVEL, skillsEXP.getRequiredCombatExpToNextLevel());
            config.set(id + COMBAT + ".damageBonus", skillsReward.getCombatDamageBonus());

            config.set(id + MINING + LEVEL, skillsLevel.getMiningLevel());
            config.set(id + MINING + TOTAL_EXP, skillsEXP.getTotalMiningEXP());
            config.set(id + MINING + EXP_TO_NEXT_LEVEL, skillsEXP.getMiningExpToNextLevel());
            config.set(id + MINING + REQUIRED_EXP_TO_NEXT_LEVEL, skillsEXP.getRequiredMiningExpToNextLevel());
            config.set(id + MINING + ".defenseBonus", skillsReward.getMiningDefenseBonus());
            config.set(id + MINING + ".fortuneBonus", skillsReward.getMiningFortuneBonus());

            config.set(id + FORAGING + LEVEL, skillsLevel.getForagingLevel());
            config.set(id + FORAGING + TOTAL_EXP, skillsEXP.getTotalForagingEXP());
            config.set(id + FORAGING + EXP_TO_NEXT_LEVEL, skillsEXP.getRequiredForagingExpToNextLevel());
            config.set(id + FORAGING + REQUIRED_EXP_TO_NEXT_LEVEL, skillsEXP.getRequiredForagingExpToNextLevel());
            config.set(id + FORAGING + ".fortuneBonus", skillsReward.getForagingFortuneBonus());

            config.set(id + FARMING + LEVEL, skillsLevel.getFarmingLevel());
            config.set(id + FARMING + TOTAL_EXP, skillsEXP.getTotalFarmingEXP());
            config.set(id + FARMING + EXP_TO_NEXT_LEVEL, skillsEXP.getFarmingExpToNextLevel());
            config.set(id + FARMING + REQUIRED_EXP_TO_NEXT_LEVEL, skillsEXP.getRequiredFarmingExpToNextLevel());
            config.set(id + FARMING + ".fortuneBonus", skillsReward.getFarmingFortuneBonus());

            config.set(id + ENCHANTING + LEVEL, skillsLevel.getEnchantingLevel());
            config.set(id + ENCHANTING + TOTAL_EXP, skillsEXP.getTotalEnchantingEXP());
            config.set(id + ENCHANTING + EXP_TO_NEXT_LEVEL, skillsEXP.getEnchantingExpToNextLevel());
            config.set(id + ENCHANTING + REQUIRED_EXP_TO_NEXT_LEVEL, skillsEXP.getRequiredEnchantingExpToNextLevel());
            config.set(id + ENCHANTING + ".manaBonus", skillsReward.getEnchantingManaBonus());

            config.set(id + FISHING + LEVEL, skillsLevel.getFishingLevel());
            config.set(id + FISHING + TOTAL_EXP, skillsEXP.getTotalFishingExp());
            config.set(id + FISHING + EXP_TO_NEXT_LEVEL, skillsEXP.getFishingExpToNextLevel());
            config.set(id + FISHING + REQUIRED_EXP_TO_NEXT_LEVEL, skillsEXP.getRequiredFishingExpToNextLevel());
            config.set(id + FISHING + ".luckBonus", skillsReward.getFishingLuckBonus());

            config.set(id + BREWING + LEVEL, skillsLevel.getBrewingLevel());
            config.set(id + BREWING + TOTAL_EXP, skillsEXP.getTotalBrewingExp());
            config.set(id + BREWING + EXP_TO_NEXT_LEVEL, skillsEXP.getBrewingExpToNextLevel());
            config.set(id + BREWING + REQUIRED_EXP_TO_NEXT_LEVEL, skillsEXP.getRequiredBrewingExpToNextLevel());
            config.set(id + BREWING + ".potionDurationBonus", skillsReward.getBrewingPotionDurationBonus());

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

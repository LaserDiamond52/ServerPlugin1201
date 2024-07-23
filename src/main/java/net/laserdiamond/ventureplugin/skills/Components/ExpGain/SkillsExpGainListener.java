package net.laserdiamond.ventureplugin.skills.Components.ExpGain;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsReward;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.LootStats;
import net.laserdiamond.ventureplugin.stats.Components.PotionStats;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import net.laserdiamond.ventureplugin.util.StatSymbols;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class SkillsExpGainListener implements Listener {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final PlayerConfig BASE_STATS_CONFIG = PLUGIN.getBaseStatsConfig();

    public static final double COMBAT_DAMAGE_REWARD = BASE_STATS_CONFIG.getDouble("combatDamageBonus");
    public static final double MINING_FORTUNE_REWARD = BASE_STATS_CONFIG.getDouble("miningFortuneBonus");
    public static final double MINING_DEFENSE_REWARD = BASE_STATS_CONFIG.getDouble("miningDefenseBonus");
    public static final double FORAGING_FORTUNE_REWARD = BASE_STATS_CONFIG.getDouble("foragingFortuneReward");
    public static final double FARMING_FORTUNE_REWARD = BASE_STATS_CONFIG.getDouble("farmingFortuneReward");
    public static final double ENCHANTING_MANA_REWARD = BASE_STATS_CONFIG.getDouble("enchantingManaBonus");
    public static final double FISHING_LUCK_REWARD = BASE_STATS_CONFIG.getDouble("fishingLuckBonus");
    public static final double BREWING_LONGEVITY_REWARD = BASE_STATS_CONFIG.getDouble("brewingLongevity");
    public static final double BREWING_CAFFEINATION_REWARD = BASE_STATS_CONFIG.getDouble("brewingCaffeination");

    @EventHandler
    private void onSkillExpGain(SkillsExpGainEvent event)
    {
        Player player = event.getPlayer();
        double expAmount = event.getExpAmount();
        SkillsExpGainEvent.Skill skill = event.getSkill();
        StatPlayer statPlayer = new StatPlayer(player);
        SkillsEXP skillsEXP = statPlayer.getSkillsEXP();
        SkillsLevel skillsLevel = statPlayer.getSkillsLevel();
        SkillsReward skillsReward = statPlayer.getSkillsReward();

        Stats stats = statPlayer.getStats();
        DefenseStats defenseStats = statPlayer.getDefenseStats();
        PotionStats potionStats = statPlayer.getPotionStats();
        LootStats lootStats = statPlayer.getLootStats();

        if (event.getExpAmount() <= 0)
        {
            event.setCancelled(true);
            return;
        }

        if (!event.isCancelled())
        {
            String skillSymbol = "";
            switch (skill)
            {
                // TODO: When current skill exp + gained amount is greater than the required amount, level up
                // TODO: Call event when skills exp should be gained

                // TODO: Add to required skill exp when gaining a level

                // TODO: Send player message when they level up (previous level -> new level)
                case COMBAT ->
                {
                    skillSymbol = ChatColor.DARK_RED + StatSymbols.COMBAT.getSymbol();
                    skillsEXP.setTotalCombatEXP(skillsEXP.getTotalCombatEXP() + expAmount);
                    skillsEXP.setCombatExpToNextLevel(skillsEXP.getCombatExpToNextLevel() + expAmount);
                    final int previousSkillLevel = skillsLevel.getCombatLevel();
                    int skillLevel = skillsLevel.getCombatLevel();
                    final double previousDamageBonus = skillsReward.getCombatDamageBonus();

                    while (skillsEXP.getCombatExpToNextLevel() >= skillsEXP.getRequiredCombatExpToNextLevel() && skillLevel < 50) // Check if player has acquired an adequate amount of exp to level up and is under the max level
                    {
                        skillLevel = skillsLevel.getCombatLevel();
                        double expToNextLevel = skillsEXP.getCombatExpToNextLevel();
                        double requiredExp = skillsEXP.getRequiredCombatExpToNextLevel();
                        double damageBonus = skillsReward.getCombatDamageBonus();

                        skillsEXP.setCombatExpToNextLevel(expToNextLevel - requiredExp);

                        if (skillLevel <= 5) // Previous level is under 5
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel <= 10) // Previous level is under 10
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel <= 15) // Previous level is under 15
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel <= 20) // Previous level is under 20
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel <= 25) // Previous level is under 25
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel <= 30) // Previous level is under 30
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel <= 40) // Previous level is under 40
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(requiredExp + 10000);
                        } else { // Previous level is under 50
                            skillsEXP.setRequiredCombatExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setCombatLevel(skillLevel + 1); // Update combat level
                        skillsReward.setCombatDamageBonus(damageBonus + COMBAT_DAMAGE_REWARD); // Update skill rewards
                        double newExpToNextLevel = skillsEXP.getCombatExpToNextLevel(); // Get Exp to next level
                        double newRequiredExpToNextLevel = skillsEXP.getRequiredCombatExpToNextLevel(); // Get Required Exp to next level

                        if (newExpToNextLevel < newRequiredExpToNextLevel || skillLevel + 1 == 50) // Check if we can no longer level up
                        {
                            int newSkillLevel = skillsLevel.getCombatLevel(); // Skill level after leveling
                            double newDamageBonus = skillsReward.getCombatDamageBonus(); // New damage bonus after leveling

                            player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "_____________________________________________");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Skill Level Up!" + ChatColor.RESET + ChatColor.DARK_RED + " Combat " + ChatColor.DARK_GRAY + previousSkillLevel + " ---> " + ChatColor.DARK_RED + newSkillLevel);
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GREEN + " " + ChatColor.BOLD + "Rewards");
                            player.sendMessage(ChatColor.WHITE + "    Deal " + ChatColor.DARK_GRAY + previousDamageBonus + " ---> " + ChatColor.GREEN + newDamageBonus + "% " + ChatColor.WHITE + "more damage");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "_____________________________________________");
                        }

                        if (skillLevel + 1 == 50) // New level is 50
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(0); // Required Exp for level 50+ is 0
                            break;
                        }
                    }

                }
                case MINING ->
                {
                    skillSymbol = ChatColor.DARK_BLUE + StatSymbols.MINING.getSymbol();
                    skillsEXP.setTotalMiningEXP(skillsEXP.getTotalMiningEXP() + expAmount);
                    skillsEXP.setMiningExpToNextLevel(skillsEXP.getMiningExpToNextLevel() + expAmount);
                    final int previousSkillLevel = skillsLevel.getMiningLevel();
                    int skillLevel = skillsLevel.getMiningLevel();
                    final double previousFortuneBonus = skillsReward.getMiningFortuneBonus();
                    final double previousDefenseBonus = skillsReward.getMiningDefenseBonus();

                    while (skillsEXP.getMiningExpToNextLevel() >= skillsEXP.getRequiredMiningExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getMiningLevel();
                        double expToNextLevel = skillsEXP.getMiningExpToNextLevel();
                        double requiredExp = skillsEXP.getRequiredMiningExpToNextLevel();
                        double fortuneBonus = skillsReward.getMiningFortuneBonus();
                        double defense = skillsReward.getMiningDefenseBonus();

                        skillsEXP.setMiningExpToNextLevel(expToNextLevel - requiredExp);

                        if (skillLevel <= 5)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel <= 10)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel <= 15)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel <= 20)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel <= 25)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel <= 30)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel <= 40)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 10000);
                        } else if (skillLevel <= 50)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setMiningLevel(skillLevel + 1);
                        skillsReward.setMiningFortuneBonus(fortuneBonus + MINING_FORTUNE_REWARD);
                        skillsReward.setMiningDefenseBonus(defense + MINING_DEFENSE_REWARD);

                        lootStats.setMiningFortune(lootStats.getMiningFortune() + MINING_FORTUNE_REWARD);
                        defenseStats.setDefense(defenseStats.getDefense() + MINING_DEFENSE_REWARD);

                        double newExpToNextLevel = skillsEXP.getMiningExpToNextLevel();
                        double newRequiredExpToNextLevel = skillsEXP.getRequiredMiningExpToNextLevel();

                        if (newExpToNextLevel < newRequiredExpToNextLevel || skillLevel + 1 == 50)
                        {
                            int newSkillLevel = skillsLevel.getMiningLevel();
                            double newFortuneBonus = skillsReward.getMiningFortuneBonus();
                            double newDefense = skillsReward.getMiningDefenseBonus();

                            player.sendMessage(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "_____________________________________________");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.DARK_BLUE + " Mining " + ChatColor.DARK_GRAY + previousSkillLevel + " ---> " + ChatColor.DARK_BLUE + newSkillLevel);
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                            player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + " ---> " + ChatColor.GREEN + newFortuneBonus + ChatColor.WHITE + " Mining Fortune");
                            player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + previousDefenseBonus + " ---> " + ChatColor.GREEN + newDefense + ChatColor.WHITE + " more " + ChatColor.GREEN + " Defense" + StatSymbols.DEFENSE.getSymbol());
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "_____________________________________________");
                        }

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(0);
                            break;
                        }
                    }



                }
                case FORAGING ->
                {
                    skillSymbol = ChatColor.DARK_GREEN + StatSymbols.FORAGING.getSymbol();
                    skillsEXP.setTotalForagingEXP(skillsEXP.getTotalForagingEXP() + expAmount);
                    skillsEXP.setForagingExpToNextLevel(skillsEXP.getForagingExpToNextLevel() + expAmount);
                    final int previousSkillLevel = skillsLevel.getForagingLevel();
                    int skillLevel = skillsLevel.getForagingLevel();
                    final double previousFortuneBonus = skillsReward.getForagingFortuneBonus();

                    while (skillsEXP.getForagingExpToNextLevel() >= skillsEXP.getRequiredForagingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getForagingLevel();
                        double expToNextLevel = skillsEXP.getForagingExpToNextLevel();
                        double requiredExp = skillsEXP.getRequiredForagingExpToNextLevel();
                        double fortuneBonus = skillsReward.getForagingFortuneBonus();

                        skillsEXP.setForagingExpToNextLevel(expToNextLevel - requiredExp);

                        if (skillLevel <= 5)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel <= 10)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel <= 15)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel <= 20)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel <= 25)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel <= 30)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel <= 40)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setForagingLevel(skillLevel + 1);
                        skillsReward.setForagingFortuneBonus(fortuneBonus + FORAGING_FORTUNE_REWARD);

                        lootStats.setForagingFortune(lootStats.getForagingFortune() - FORAGING_FORTUNE_REWARD);

                        double newExpToNextLevel = skillsEXP.getForagingExpToNextLevel();
                        double newRequiredExpToNextLevel = skillsEXP.getRequiredForagingExpToNextLevel();

                        if (newExpToNextLevel < newRequiredExpToNextLevel || skillLevel + 1 == 50)
                        {
                            int newSkillLevel = skillsLevel.getForagingLevel();
                            double newFortuneBonus = skillsReward.getForagingFortuneBonus();

                            player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "_____________________________________________");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.DARK_GREEN + " Foraging " + ChatColor.DARK_GRAY + previousSkillLevel + " ---> " + ChatColor.DARK_GREEN + newSkillLevel);
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                            player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + " ---> " + ChatColor.GREEN + newFortuneBonus + ChatColor.WHITE + " Foraging Fortune");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "_____________________________________________");
                        }

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(0);
                            break;
                        }
                    }



                }
                case FARMING ->
                {
                    skillSymbol = ChatColor.GREEN + StatSymbols.FARMING.getSymbol();
                    skillsEXP.setTotalFarmingEXP(skillsEXP.getTotalFarmingEXP() + expAmount);
                    skillsEXP.setFarmingExpToNextLevel(skillsEXP.getFarmingExpToNextLevel() + expAmount);
                    final int previousSkillLevel = skillsLevel.getFarmingLevel();
                    int skillLevel = skillsLevel.getFarmingLevel();
                    final double previousFortuneBonus = skillsReward.getFarmingFortuneBonus();

                    while (skillsEXP.getFarmingExpToNextLevel() >= skillsEXP.getRequiredFarmingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getFarmingLevel();
                        double expToNextLevel = skillsEXP.getFarmingExpToNextLevel();
                        double requiredExp = skillsEXP.getRequiredFarmingExpToNextLevel();
                        double fortuneBonus = skillsReward.getFarmingFortuneBonus();

                        skillsEXP.setFarmingExpToNextLevel(expToNextLevel - requiredExp);

                        if (skillLevel <= 5)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel <= 10)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel <= 15)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel <= 20)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel <= 25)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel <= 30)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel <= 40)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setFarmingLevel(skillLevel + 1);
                        skillsReward.setFarmingFortuneBonus(fortuneBonus + FARMING_FORTUNE_REWARD);

                        lootStats.setFarmingFortune(lootStats.getFarmingFortune() + FARMING_FORTUNE_REWARD);

                        double newExpToNextLevel = skillsEXP.getFarmingExpToNextLevel();
                        double newRequiredExpToNextLevel = skillsEXP.getRequiredFarmingExpToNextLevel();

                        if (newExpToNextLevel < newRequiredExpToNextLevel || skillLevel + 1 == 50)
                        {
                            int newSkillLevel = skillsLevel.getFarmingLevel();
                            double newFortuneBonus = skillsReward.getFarmingFortuneBonus();

                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "_____________________________________________");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.GREEN + " Farming " + ChatColor.DARK_GRAY + previousSkillLevel + " ---> " + ChatColor.GREEN + newSkillLevel);
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                            player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + " ---> " + ChatColor.GREEN + newFortuneBonus + "% " + ChatColor.WHITE + " Farming Fortune");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "_____________________________________________");
                        }

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(0);
                            break;
                        }
                    }



                }
                case ENCHANTING ->
                {
                    skillSymbol = ChatColor.LIGHT_PURPLE + StatSymbols.ENCHANTING.getSymbol();
                    skillsEXP.setTotalEnchantingEXP(skillsEXP.getTotalEnchantingEXP() + expAmount);
                    skillsEXP.setEnchantingExpToNextLevel(skillsEXP.getEnchantingExpToNextLevel() + expAmount);
                    final int previousSkillLevel = skillsLevel.getEnchantingLevel();
                    int skillLevel = skillsLevel.getEnchantingLevel();
                    final double previousManaBonus = skillsReward.getEnchantingManaBonus();

                    while (skillsEXP.getEnchantingExpToNextLevel() >= skillsEXP.getRequiredEnchantingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getEnchantingLevel();
                        double expToNextLevel = skillsEXP.getEnchantingExpToNextLevel();
                        double requiredExp = skillsEXP.getRequiredEnchantingExpToNextLevel();
                        double manaBonus = skillsReward.getEnchantingManaBonus();

                        skillsEXP.setEnchantingExpToNextLevel(expToNextLevel - requiredExp);

                        if (skillLevel <= 5)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel <= 10)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel <= 15)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel <= 20)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel <= 25)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel <= 30)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel <= 40)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setEnchantingLevel(skillLevel + 1);
                        skillsReward.setEnchantingManaBonus(manaBonus + ENCHANTING_MANA_REWARD);

                        stats.setMaxMana(stats.getMaxMana() + ENCHANTING_MANA_REWARD);

                        double newExpToNextLevel = skillsEXP.getEnchantingExpToNextLevel();
                        double newRequiredExpToNextLevel = skillsEXP.getRequiredEnchantingExpToNextLevel();

                        if (newExpToNextLevel < newRequiredExpToNextLevel || skillLevel + 1 == 50)
                        {
                            int newSkillLevel = skillsLevel.getEnchantingLevel();
                            double newManaBonus = skillsReward.getEnchantingManaBonus();

                            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "_____________________________________________");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.LIGHT_PURPLE + " Enchanting " + ChatColor.DARK_GRAY + previousSkillLevel + " ---> " + ChatColor.LIGHT_PURPLE + newSkillLevel);
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                            player.sendMessage(ChatColor.WHITE + "    Gain +" + ChatColor.DARK_GRAY + previousManaBonus + " ---> " + ChatColor.LIGHT_PURPLE + newManaBonus + ChatColor.WHITE + " more " + ChatColor.BLUE + "Mana" + StatSymbols.MANA.getSymbol());
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "_____________________________________________");
                        }

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(0);
                            break;
                        }
                    }



                }
                case FISHING ->
                {
                    skillSymbol = ChatColor.AQUA + StatSymbols.FISHING.getSymbol();
                    skillsEXP.setTotalFishingExp(skillsEXP.getTotalFishingExp() + expAmount);
                    skillsEXP.setFishingExpToNextLevel(skillsEXP.getFishingExpToNextLevel() + expAmount);
                    final int previousSkillLevel = skillsLevel.getFishingLevel();
                    int skillLevel = skillsLevel.getFishingLevel();
                    final double previousFishingLuckBonus = skillsReward.getFishingLuckBonus();

                    while (skillsEXP.getFishingExpToNextLevel() >= skillsEXP.getRequiredFishingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getFishingLevel();
                        double expToNextLevel = skillsEXP.getFishingExpToNextLevel();
                        double requiredExp = skillsEXP.getRequiredFishingExpToNextLevel();
                        double fishingLuckBonus = skillsReward.getFishingLuckBonus();

                        skillsEXP.setFishingExpToNextLevel(expToNextLevel - requiredExp);

                        if (skillLevel <= 5)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel <= 10)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel <= 15)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel <= 20)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel <= 25)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel <= 30)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel <= 40)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setFishingLevel(skillLevel + 1);
                        skillsReward.setFishingLuckBonus(fishingLuckBonus + FISHING_LUCK_REWARD);

                        lootStats.setFishingLuck(lootStats.getFishingLuck() + FISHING_LUCK_REWARD);

                        double newExpToLevel = skillsEXP.getFishingExpToNextLevel();
                        double newRequiredExpToNextLevel = skillsEXP.getRequiredFishingExpToNextLevel();

                        if (newExpToLevel < newRequiredExpToNextLevel || skillLevel + 1 == 50)
                        {
                            int newSkillLevel = skillsLevel.getFishingLevel();
                            double newFishingLuckBonus = skillsReward.getFishingLuckBonus();

                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "_____________________________________________");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.AQUA + " Fishing " + ChatColor.DARK_GRAY + previousSkillLevel + " ---> " + ChatColor.AQUA + newSkillLevel);
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                            player.sendMessage(ChatColor.WHITE + "    Receive " + ChatColor.DARK_GRAY + previousFishingLuckBonus + " ---> " + ChatColor.GREEN + newFishingLuckBonus + "% " + ChatColor.WHITE + "chance to catch sea creatures");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "_____________________________________________");

                        }

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(0);
                            break;
                        }
                    }


                }
                case BREWING ->
                {
                    skillSymbol = ChatColor.BLUE + StatSymbols.BREWING.getSymbol();
                    skillsEXP.setTotalBrewingExp(skillsEXP.getTotalBrewingExp() + expAmount);
                    skillsEXP.setBrewingExpToNextLevel(skillsEXP.getBrewingExpToNextLevel() + expAmount);
                    final int previousSkillLevel = skillsLevel.getBrewingLevel();
                    int skillLevel = skillsLevel.getBrewingLevel();
                    final double previousLongevity = skillsReward.getBrewingLongevity();
                    final double previousCaffeination = skillsReward.getBrewingCaffeination();

                    while (skillsEXP.getBrewingExpToNextLevel() >= skillsEXP.getRequiredBrewingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getBrewingLevel();
                        double longevity = skillsReward.getBrewingLongevity();
                        double caffeination = skillsReward.getBrewingCaffeination();
                        double expToNextLevel = skillsEXP.getBrewingExpToNextLevel();
                        double requiredExp = skillsEXP.getRequiredBrewingExpToNextLevel();

                        skillsEXP.setBrewingExpToNextLevel(expToNextLevel - requiredExp);

                        if (skillLevel <= 5)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel <= 10)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel <= 15)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel <= 20)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel <= 25)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel <= 30)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel <= 40)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setBrewingLevel(skillLevel + 1);
                        skillsReward.setBrewingLongevity(longevity + BREWING_LONGEVITY_REWARD);
                        skillsReward.setBrewingCaffeination(caffeination + BREWING_CAFFEINATION_REWARD);

                        potionStats.setLongevity(potionStats.getLongevity() + BREWING_LONGEVITY_REWARD);
                        potionStats.setCaffeination(potionStats.getCaffeination() + BREWING_CAFFEINATION_REWARD);

                        double newExpToNextLevel = skillsEXP.getBrewingExpToNextLevel();
                        double newRequiredExpToNextLevel = skillsEXP.getRequiredBrewingExpToNextLevel();

                        if (newExpToNextLevel < newRequiredExpToNextLevel || skillLevel + 1 == 50)
                        {
                            int newSkillLevel = skillsLevel.getBrewingLevel();
                            double newLongevity = skillsReward.getBrewingLongevity();
                            double newCaffeination = skillsReward.getBrewingCaffeination();

                            player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "_____________________________________________");
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.BLUE + " Brewing " + ChatColor.DARK_GRAY + previousSkillLevel + " ---> " + ChatColor.BLUE + newSkillLevel);
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                            player.sendMessage(ChatColor.WHITE + "    Gain +" + ChatColor.DARK_GRAY + previousLongevity + " ---> " + ChatColor.GREEN + newLongevity + ChatColor.WHITE + " more " + ChatColor.DARK_AQUA + "Longevity" + StatSymbols.LONGEVITY.getSymbol());
                            player.sendMessage(ChatColor.WHITE + "    Gain +" + ChatColor.DARK_GRAY + previousCaffeination + " ---> " + ChatColor.GREEN + newCaffeination + ChatColor.WHITE + " more " + ChatColor.LIGHT_PURPLE + "Caffeination" + StatSymbols.CAFFEINATION.getSymbol());
                            player.sendMessage(" ");
                            player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "_____________________________________________");
                        }

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(0);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * An entity death event that calls the combat exp gain event
     * @param event The EntityDeathEvent
     */
    @EventHandler
    private void combatExpGain(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Mob mob)
        {
            if (mob.getKiller() != null)
            {
                Player player = mob.getKiller();
                Double combatExp = CombatExp.getMobCombatExp(mob);
                SkillsExpGainEvent expGainEvent = new SkillsExpGainEvent(player, combatExp, SkillsExpGainEvent.Skill.COMBAT);
                double eventCombatExp = expGainEvent.getExpAmount();
                Bukkit.getPluginManager().callEvent(expGainEvent);

                if (!expGainEvent.isCancelled())
                {
                    runExpDisplay(mob.getEyeLocation(), ChatColor.DARK_RED, StatSymbols.COMBAT, eventCombatExp);
                }

                Double fishingExp = FishingExp.getFishingExp(mob);
                SkillsExpGainEvent expGainEvent1 = new SkillsExpGainEvent(player, fishingExp, SkillsExpGainEvent.Skill.FISHING);
                double eventFishingExp = expGainEvent1.getExpAmount();
                Bukkit.getPluginManager().callEvent(expGainEvent1);

                if (!expGainEvent1.isCancelled())
                {
                    runExpDisplay(mob.getEyeLocation().add(0,0.5,0), ChatColor.AQUA, StatSymbols.FISHING, eventFishingExp);
                }
            }
        }
    }

    /**
     * A block break event that calls the skill exp gain events for mining, foraging, farming
     * @param event The BlockBreakEvent
     */
    @EventHandler
    private void blockBreakExpGain(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location expDisplayLoc = block.getLocation().add(0.5,0.5,0.5);

        Double miningSkillExp = MiningExp.getBlockMiningExp(block);
        Double foragingSkillExp = ForagingExp.getForagingExp(block);
        Double farmingSkillExp = FarmingExp.getFarmingExp(block);

        SkillsExpGainEvent miningExpGainEvent = new SkillsExpGainEvent(player, miningSkillExp, SkillsExpGainEvent.Skill.MINING);
        SkillsExpGainEvent foragingExpGainEvent = new SkillsExpGainEvent(player, foragingSkillExp, SkillsExpGainEvent.Skill.FORAGING);
        SkillsExpGainEvent farmingExpGainEvent = new SkillsExpGainEvent(player, farmingSkillExp, SkillsExpGainEvent.Skill.FARMING);

        double miningExp = miningExpGainEvent.getExpAmount();
        double foragingExp = foragingExpGainEvent.getExpAmount();
        double farmingExp = farmingExpGainEvent.getExpAmount();

        Bukkit.getPluginManager().callEvent(miningExpGainEvent);
        Bukkit.getPluginManager().callEvent(foragingExpGainEvent);
        Bukkit.getPluginManager().callEvent(farmingExpGainEvent);

        if (!miningExpGainEvent.isCancelled())
        {
            runExpDisplay(expDisplayLoc, ChatColor.DARK_BLUE, StatSymbols.MINING, miningExp);
        }
        if (!foragingExpGainEvent.isCancelled())
        {
            runExpDisplay(expDisplayLoc, ChatColor.DARK_GREEN, StatSymbols.FORAGING, foragingExp);
        }
        if (!farmingExpGainEvent.isCancelled())
        {
            runExpDisplay(expDisplayLoc, ChatColor.GREEN, StatSymbols.FARMING, farmingExp);
        }
    }

    /**
     * When a potion is brewed, this event is called to ready the potions for exp collection
     * @param event BrewEvent
     */
    @EventHandler
    private void brewingExpReady(BrewEvent event)
    {
        List<ItemStack> results = event.getResults();
        ItemStack potion1 = results.get(0);
        ItemStack potion2 = results.get(1);
        ItemStack potion3 = results.get(2);

        if (potion1 != null)
        {
            VentureItemBuilder potionForger = new VentureItemBuilder(potion1);
            potionForger.setPotionReady(true);

        }
        if (potion2 != null)
        {
            VentureItemBuilder potionForger = new VentureItemBuilder(potion2);
            potionForger.setPotionReady(true);

        }
        if (potion3 != null)
        {
            VentureItemBuilder potionForger = new VentureItemBuilder(potion3);
            potionForger.setPotionReady(true);
        }
    }

    /**
     * When a player claims a potion from the brewing stand, they will collect the exp, and the potion itemStack will be updated so that exp can only be gained when the potion is freshly brewed
     * @param event InventoryClickEvent
     */
    @EventHandler
    private void brewingExpGain(InventoryClickEvent event)
    {
        HumanEntity humanEntity = event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();

        if (humanEntity instanceof Player player)
        {
            if (inventory instanceof BrewerInventory brewerInventory)
            {
                ItemStack potion1 = brewerInventory.getItem(0);
                ItemStack potion2 = brewerInventory.getItem(1);
                ItemStack potion3 = brewerInventory.getItem(2);

                Double exp = 0.0;

                switch (event.getSlot())
                {
                    case 0:
                        if (potion1 != null)
                        {
                            VentureItemBuilder potionForger = new VentureItemBuilder(potion1);
                            if (potionForger.getPotionReady())
                            {
                                if (potion1.getItemMeta() instanceof PotionMeta potionMeta)
                                {
                                    exp = BrewingExp.getBrewingExp(potionMeta);
                                    SkillsExpGainEvent skillsExpGainEvent = new SkillsExpGainEvent(player, exp, SkillsExpGainEvent.Skill.BREWING);
                                    Bukkit.getPluginManager().callEvent(skillsExpGainEvent);

                                    player.sendMessage("collect exp: " + exp);
                                }


                                potionForger.setPotionReady(false);
                            } else
                            {
                                player.sendMessage("not ready 1");
                            }
                        }
                        break;
                    case 1:
                        if (potion2 != null)
                        {
                            VentureItemBuilder potionForger = new VentureItemBuilder(potion2);
                            if (potionForger.getPotionReady())
                            {
                                if (potion2.getItemMeta() instanceof PotionMeta potionMeta)
                                {
                                    exp = BrewingExp.getBrewingExp(potionMeta);
                                    SkillsExpGainEvent skillsExpGainEvent = new SkillsExpGainEvent(player, exp, SkillsExpGainEvent.Skill.BREWING);
                                    Bukkit.getPluginManager().callEvent(skillsExpGainEvent);

                                    player.sendMessage("collect exp: " + exp);
                                }


                                potionForger.setPotionReady(false);
                            } else
                            {
                                player.sendMessage("not ready 2");
                            }
                        }
                        break;
                    case 2:
                        if (potion3 != null)
                        {
                            VentureItemBuilder potionForger = new VentureItemBuilder(potion3);
                            if (potionForger.getPotionReady())
                            {
                                if (potion3.getItemMeta() instanceof PotionMeta potionMeta)
                                {
                                    exp = BrewingExp.getBrewingExp(potionMeta);
                                    SkillsExpGainEvent skillsExpGainEvent = new SkillsExpGainEvent(player, exp, SkillsExpGainEvent.Skill.BREWING);
                                    Bukkit.getPluginManager().callEvent(skillsExpGainEvent);

                                    player.sendMessage("collect exp: " + exp);
                                }


                                potionForger.setPotionReady(false);
                            } else
                            {
                                player.sendMessage("not ready 3");
                            }
                        }
                        break;
                }
            }
        }
    }



    private void runExpDisplay(Location location, ChatColor symbolColor, StatSymbols statSymbols, double exp)
    {
        TextDisplay expDisplay = location.getWorld().spawn(location, TextDisplay.class);
        expDisplay.isSeeThrough();
        expDisplay.setBillboard(Display.Billboard.CENTER);
        expDisplay.setText("+" + exp + symbolColor + statSymbols.getSymbol());

        new BukkitRunnable()
        {
            int i = 0;
            final int expDisplayLifeSpan = PLUGIN.getConfig().getInt("skillDisplayLifeSpanTicks");
            @Override
            public void run()
            {
                i++;
                if (i >= expDisplayLifeSpan || Bukkit.getServer().isStopping())
                {
                    expDisplay.remove();
                }
            }
        }.runTaskTimer(PLUGIN, 0L, 1L);
    }
}

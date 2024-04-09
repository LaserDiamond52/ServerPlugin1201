package net.laserdiamond.ventureplugin.skills.Components.ExpGain;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.combat.mobExp;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.farming.farmingExp;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.foraging.foragingExp;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.mining.miningExp;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsReward;
import net.laserdiamond.ventureplugin.util.StatSymbols;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BrewingStartEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class SkillsExpGainListener implements Listener {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();

    @EventHandler
    public void onSkillExpGain(SkillsExpGainEvent event)
    {
        Player player = event.getPlayer();
        double expAmount = event.getExpAmount();
        SkillsExpGainEvent.Skill skill = event.getSkill();
        StatPlayer statPlayer = new StatPlayer(player);
        SkillsEXP skillsEXP = statPlayer.getSkillsEXP();
        SkillsLevel skillsLevel = statPlayer.getSkillsLevel();
        SkillsReward skillsReward = statPlayer.getSkillsReward();

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
                    int skillLevel = skillsLevel.getCombatLevel();
                    double damageBonus = skillsReward.getCombatDamageBonus();

                    while (skillsEXP.getCombatExpToNextLevel() >= skillsEXP.getRequiredCombatExpToNextLevel() && skillLevel < 50) // Check if player has acquired an adequate amount of exp to level up and is under the max level
                    {
                        skillLevel = skillsLevel.getCombatLevel();
                        damageBonus = skillsReward.getCombatDamageBonus();
                        if (skillLevel < 5) // Previous level is under 5
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 100);
                        } else if (skillLevel < 10) // Previous level is under 10
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 250);
                        } else if (skillLevel < 15) // Previous level is under 15
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 500);
                        } else if (skillLevel < 20) // Previous level is under 20
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 1000);
                        } else if (skillLevel < 25) // Previous level is under 25
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 2000);
                        } else if (skillLevel < 30) // Previous level is under 30
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 5000);
                        } else if (skillLevel < 40) // Previous level is under 40
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 10000);
                        } else { // Previous level is under 50
                            skillsEXP.setRequiredCombatExpToNextLevel(skillsEXP.getRequiredCombatExpToNextLevel() + 17500);
                        }

                        skillsLevel.setCombatLevel(skillLevel + 1); // Update combat level
                        skillsReward.setCombatDamageBonus(damageBonus + 2);

                        if (skillLevel + 1 == 50) // New level is 50
                        {
                            skillsEXP.setRequiredCombatExpToNextLevel(0); // Required Exp for level 50+ is 0
                            break;
                        }
                    }

                    int newSkillLevel = skillsLevel.getCombatLevel(); // Skill level after leveling
                    double newDamageBonus = skillsReward.getCombatDamageBonus(); // New damage bonus after leveling

                    player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "_____________________________________________");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Skill Level Up!" + ChatColor.RESET + ChatColor.DARK_RED + " Combat " + ChatColor.DARK_GRAY + skillLevel + " ---> " + ChatColor.DARK_RED + newSkillLevel);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + " " + ChatColor.BOLD + "Rewards");
                    player.sendMessage(ChatColor.WHITE + "    Deal " + ChatColor.DARK_GRAY + damageBonus + " ---> " + ChatColor.GREEN + newDamageBonus + "% " + ChatColor.WHITE + "more damage");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "_____________________________________________");

                }
                case MINING ->
                {
                    skillSymbol = ChatColor.DARK_BLUE + StatSymbols.MINING.getSymbol();
                    skillsEXP.setTotalMiningEXP(skillsEXP.getTotalMiningEXP() + expAmount);
                    skillsEXP.setMiningExpToNextLevel(skillsEXP.getMiningExpToNextLevel() + expAmount);
                    int skillLevel = skillsLevel.getMiningLevel();
                    double fortuneBonus = skillsReward.getMiningFortuneBonus();
                    double defense = skillsReward.getMiningDefenseBonus();

                    while (skillsEXP.getMiningExpToNextLevel() >= skillsEXP.getRequiredMiningExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getMiningLevel();
                        double requiredExp = skillsEXP.getRequiredMiningExpToNextLevel();
                        fortuneBonus = skillsReward.getMiningFortuneBonus();
                        double defenseBonus = skillsReward.getMiningDefenseBonus();

                        if (skillLevel < 5)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel < 10)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel < 15)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel < 20)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel < 25)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel < 30)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel < 40)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 10000);
                        } else if (skillLevel < 50)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setMiningLevel(skillLevel + 1);
                        skillsReward.setMiningFortuneBonus(fortuneBonus + 2);
                        skillsReward.setMiningDefenseBonus(defenseBonus + 2);

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredMiningExpToNextLevel(0);
                            break;
                        }
                    }

                    int newSkillLevel = skillsLevel.getMiningLevel();
                    double newFortuneBonus = skillsReward.getMiningFortuneBonus();
                    double newDefense = skillsReward.getMiningDefenseBonus();

                    player.sendMessage(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "_____________________________________________");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.DARK_BLUE + " Mining " + ChatColor.DARK_GRAY + skillLevel + " ---> " + ChatColor.DARK_BLUE + newSkillLevel);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                    player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + fortuneBonus + " ---> " + ChatColor.GREEN + newFortuneBonus + ChatColor.WHITE + " Mining Fortune");
                    player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + defense + " ---> " + ChatColor.GREEN + newDefense + ChatColor.WHITE + " more " + ChatColor.GREEN + " Defense" + StatSymbols.DEFENSE.getSymbol());
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "_____________________________________________");

                }
                case FORAGING ->
                {
                    skillSymbol = ChatColor.DARK_GREEN + StatSymbols.FORAGING.getSymbol();
                    skillsEXP.setTotalForagingEXP(skillsEXP.getTotalForagingEXP() + expAmount);
                    skillsEXP.setForagingExpToNextLevel(skillsEXP.getForagingExpToNextLevel() + expAmount);
                    int skillLevel = skillsLevel.getForagingLevel();
                    double fortuneBonus = skillsReward.getForagingFortuneBonus();

                    while (skillsEXP.getForagingExpToNextLevel() >= skillsEXP.getRequiredForagingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getForagingLevel();
                        double requiredExp = skillsEXP.getRequiredForagingExpToNextLevel();
                        fortuneBonus = skillsReward.getForagingFortuneBonus();

                        if (skillLevel < 5)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel < 10)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel < 15)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel < 20)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel < 25)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel < 30)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel < 40)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setForagingLevel(skillLevel + 1);
                        skillsReward.setForagingFortuneBonus(fortuneBonus + 2);

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredForagingExpToNextLevel(0);
                            break;
                        }
                    }

                    int newSkillLevel = skillsLevel.getForagingLevel();
                    double newFortuneBonus = skillsReward.getForagingFortuneBonus();

                    player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "_____________________________________________");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.DARK_GREEN + " Foraging " + ChatColor.DARK_GRAY + skillLevel + " ---> " + ChatColor.DARK_GREEN + newSkillLevel);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                    player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + fortuneBonus + " ---> " + ChatColor.GREEN + newFortuneBonus + ChatColor.WHITE + " Foraging Fortune");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "_____________________________________________");

                }
                case FARMING ->
                {
                    skillSymbol = ChatColor.GREEN + StatSymbols.FARMING.getSymbol();
                    skillsEXP.setTotalFarmingEXP(skillsEXP.getTotalFarmingEXP() + expAmount);
                    skillsEXP.setFarmingExpToNextLevel(skillsEXP.getFarmingExpToNextLevel() + expAmount);
                    int skillLevel = skillsLevel.getFarmingLevel();
                    double fortuneBonus = skillsReward.getFarmingFortuneBonus();

                    while (skillsEXP.getFarmingExpToNextLevel() >= skillsEXP.getRequiredFarmingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getFarmingLevel();
                        double requiredExp = skillsEXP.getRequiredFarmingExpToNextLevel();
                        fortuneBonus = skillsReward.getFarmingFortuneBonus();

                        if (skillLevel < 5)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel < 10)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel < 15)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel < 20)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel < 25)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel < 30)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel < 40)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setFarmingLevel(skillLevel + 1);
                        skillsReward.setFarmingFortuneBonus(fortuneBonus + 2);

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredFarmingExpToNextLevel(0);
                            break;
                        }
                    }

                    int newSkillLevel = skillsLevel.getFarmingLevel();
                    double newFortuneBonus = skillsReward.getFarmingFortuneBonus();

                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "_____________________________________________");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.GREEN + " Farming " + ChatColor.DARK_GRAY + skillLevel + " ---> " + ChatColor.GREEN + newSkillLevel);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                    player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + fortuneBonus + " ---> " + ChatColor.GREEN + newFortuneBonus + "% " + ChatColor.WHITE + " Farming Fortune");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "_____________________________________________");

                }
                case ENCHANTING ->
                {
                    skillSymbol = ChatColor.LIGHT_PURPLE + StatSymbols.ENCHANTING.getSymbol();
                    skillsEXP.setTotalEnchantingEXP(skillsEXP.getTotalEnchantingEXP() + expAmount);
                    skillsEXP.setEnchantingExpToNextLevel(skillsEXP.getEnchantingExpToNextLevel() + expAmount);
                    int skillLevel = skillsLevel.getEnchantingLevel();
                    double manaBonus = skillsReward.getEnchantingManaBonus();

                    while (skillsEXP.getEnchantingExpToNextLevel() >= skillsEXP.getRequiredEnchantingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getEnchantingLevel();
                        double requiredExp = skillsEXP.getRequiredEnchantingExpToNextLevel();
                        manaBonus = skillsReward.getEnchantingManaBonus();

                        if (skillLevel < 5)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel < 10)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel < 15)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel < 20)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel < 25)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel < 30)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel < 40)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setEnchantingLevel(skillLevel + 1);
                        skillsReward.setEnchantingManaBonus(manaBonus + 2);

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredEnchantingExpToNextLevel(0);
                            break;
                        }
                    }

                    int newSkillLevel = skillsLevel.getEnchantingLevel();
                    double newManaBonus = skillsReward.getEnchantingManaBonus();

                    player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "_____________________________________________");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.LIGHT_PURPLE + " Enchanting " + ChatColor.DARK_GRAY + skillLevel + " ---> " + ChatColor.LIGHT_PURPLE + newSkillLevel);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                    player.sendMessage(ChatColor.WHITE + "    Gain " + ChatColor.DARK_GRAY + manaBonus + " ---> " + ChatColor.LIGHT_PURPLE + newManaBonus + ChatColor.WHITE + " more " + ChatColor.BLUE + "Mana" + StatSymbols.MANA.getSymbol());
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "_____________________________________________");

                }
                case FISHING ->
                {
                    skillSymbol = ChatColor.AQUA + StatSymbols.FISHING.getSymbol();
                    skillsEXP.setTotalFishingExp(skillsEXP.getTotalFishingExp() + expAmount);
                    skillsEXP.setFishingExpToNextLevel(skillsEXP.getFishingExpToNextLevel() + expAmount);
                    int skillLevel = skillsLevel.getFishingLevel();
                    double fishingLuckBonus = skillsReward.getFishingLuckBonus();

                    while (skillsEXP.getFishingExpToNextLevel() >= skillsEXP.getRequiredFishingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getFishingLevel();
                        fishingLuckBonus = skillsReward.getFishingLuckBonus();
                        double requiredExp = skillsEXP.getRequiredFishingExpToNextLevel();

                        if (skillLevel < 5)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel < 10)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel < 15)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel < 20)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel < 25)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel < 30)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel < 40)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setFishingLevel(skillLevel + 1);
                        skillsReward.setFishingLuckBonus(fishingLuckBonus + 2);

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredFishingExpToNextLevel(0);
                            break;
                        }
                    }

                    int newSkillLevel = skillsLevel.getFishingLevel();
                    double newFishingLuckBonus = skillsReward.getFishingLuckBonus();

                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "_____________________________________________");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.AQUA + " Fishing " + ChatColor.DARK_GRAY + skillLevel + " ---> " + ChatColor.AQUA + newSkillLevel);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                    player.sendMessage(ChatColor.WHITE + "    Receive " + ChatColor.DARK_GRAY + fishingLuckBonus + " ---> " + ChatColor.GREEN + newFishingLuckBonus + "% " + ChatColor.WHITE + "chance to catch sea creatures");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "_____________________________________________");
                }
                case BREWING ->
                {
                    skillSymbol = ChatColor.BLUE + StatSymbols.BREWING.getSymbol();
                    skillsEXP.setTotalBrewingExp(skillsEXP.getTotalBrewingExp() + expAmount);
                    skillsEXP.setBrewingExpToNextLevel(skillsEXP.getBrewingExpToNextLevel() + expAmount);
                    int skillLevel = skillsLevel.getBrewingLevel();
                    double durationBonus = skillsReward.getBrewingPotionDurationBonus();

                    while (skillsEXP.getBrewingExpToNextLevel() >= skillsEXP.getRequiredBrewingExpToNextLevel() && skillLevel < 50)
                    {
                        skillLevel = skillsLevel.getBrewingLevel();
                        durationBonus = skillsReward.getBrewingPotionDurationBonus();
                        double requiredExp = skillsEXP.getRequiredBrewingExpToNextLevel();

                        if (skillLevel < 5)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 100);
                        } else if (skillLevel < 10)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 250);
                        } else if (skillLevel < 15)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 500);
                        } else if (skillLevel < 20)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 1000);
                        } else if (skillLevel < 25)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 2000);
                        } else if (skillLevel < 30)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 5000);
                        } else if (skillLevel < 40)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 10000);
                        } else
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(requiredExp + 17500);
                        }

                        skillsLevel.setBrewingLevel(skillLevel + 1);
                        skillsReward.setBrewingPotionDurationBonus(durationBonus + 2);

                        if (skillLevel + 1 == 50)
                        {
                            skillsEXP.setRequiredBrewingExpToNextLevel(0);
                            break;
                        }
                    }

                    int newSkillLevel = skillsLevel.getBrewingLevel();
                    double newDurationBonus = skillsReward.getBrewingPotionDurationBonus();

                    player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "_____________________________________________");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + " Skill Level Up!" + ChatColor.RESET + ChatColor.BLUE + " Brewing " + ChatColor.DARK_GRAY + skillLevel + " ---> " + ChatColor.BLUE + newSkillLevel);
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + " Rewards");
                    player.sendMessage(ChatColor.WHITE + "    Receive " + ChatColor.DARK_GRAY + durationBonus + " ---> " + ChatColor.GREEN + newDurationBonus + "% " + ChatColor.WHITE + "longer potion duration");
                    player.sendMessage(" ");
                    player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "_____________________________________________");
                }
            }
        }

    }

    /**
     * An entity death event that calls the combat exp gain event
     * @param event The EntityDeathEvent
     */
    @EventHandler
    public void combatExpGain(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof Mob mob)
        {
            if (mob.getKiller() != null)
            {
                Player player = mob.getKiller();
                Double combatExp = mobExp.getMobCombatExp(mob);
                SkillsExpGainEvent expGainEvent = new SkillsExpGainEvent(player, combatExp, SkillsExpGainEvent.Skill.COMBAT);
                double exp = expGainEvent.getExpAmount();
                Bukkit.getPluginManager().callEvent(expGainEvent);

                if (!expGainEvent.isCancelled())
                {
                    runExpDisplay(mob.getEyeLocation(), ChatColor.DARK_RED, StatSymbols.COMBAT, exp);
                }
            }
        }
    }

    /**
     * A block break event that calls the skill exp gain events for mining, foraging, farming
     * @param event The BlockBreakEvent
     */
    @EventHandler
    public void blockBreakExpGain(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location expDisplayLoc = block.getLocation().add(0.5,0.5,0.5);

        Double miningSkillExp = miningExp.getBlockMiningExp(block);
        Double foragingSkillExp = foragingExp.getForagingExp(block);
        Double farmingSkillExp = farmingExp.getFarmingExp(block);

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

    @EventHandler
    public void brewingExpGain(InventoryClickEvent event)
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

                if (potion1 != null)
                {
                    if (potion1.getItemMeta() instanceof PotionMeta potionMeta)
                    {

                    }
                }
                if (potion2 != null)
                {
                    if (potion2 instanceof PotionMeta potionMeta)
                    {

                    }
                }
                if (potion3 != null)
                {
                    if (potion3 instanceof PotionMeta potionMeta)
                    {

                    }
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

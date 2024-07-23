package net.laserdiamond.ventureplugin.commands.skills_edit;

import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsReward;
import net.laserdiamond.ventureplugin.stats.Components.*;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SkillsExpCommand implements CommandExecutor, TabExecutor {

    private static final String COMBAT = SkillsExpGainEvent.Skill.COMBAT.name().toLowerCase();
    private static final String MINING = SkillsExpGainEvent.Skill.MINING.name().toLowerCase();
    private static final String FORAGING = SkillsExpGainEvent.Skill.FORAGING.name().toLowerCase();
    private static final String FARMING = SkillsExpGainEvent.Skill.FARMING.name().toLowerCase();
    private static final String ENCHANTING = SkillsExpGainEvent.Skill.ENCHANTING.name().toLowerCase();
    private static final String FISHING = SkillsExpGainEvent.Skill.FISHING.name().toLowerCase();
    private static final String BREWING = SkillsExpGainEvent.Skill.BREWING.name().toLowerCase();
    private static final int DEFAULT_REQUIRED_EXP = 100;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission(Permissions.EDIT_SKILLS.getPermission()))
        {
            switch (args.length)
            {
                case 0:
                    sender.sendMessage(ChatColor.RED + "Please specify whether to add or reset skill experience");
                    break;
                case 1:
                    sender.sendMessage(ChatColor.RED + "Please specify a player");
                    break;
                case 2:
                    if (args[0].equals("add"))
                    {
                        sender.sendMessage(ChatColor.RED + "Please specify a skill to add experience to");
                    } else
                    {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null)
                        {
                            for (SkillsExpGainEvent.Skill skills : SkillsExpGainEvent.Skill.values())
                            {
                                resetSkillExp(target, skills);
                            }
                            target.sendMessage(ChatColor.RED + "All your skills were reset!");
                        }

                    }
                    break;
                case 3:
                    if (args[0].equals("add")) // Sender wants to add exp
                    {
                        sender.sendMessage(ChatColor.RED + "Please specify the amount of exp to add");
                    } else // Reset
                    {
                        // TODO: Reset skill
                        String inputMod = args[0];
                        Player target = Bukkit.getPlayer(args[1]);
                        String inputSkill = args[2];
                        if (target != null)
                        {
                            modifyExp(sender, target, inputMod, inputSkill, 0);
                        }
                    }
                    break;
                case 4:
                    String inputMod = args[0];
                    Player target = Bukkit.getPlayer(args[1]);
                    String inputSkill = args[2];
                    if (inputMod.equals("add")) // Sender wants to add exp
                    {
                        String expString = args[3];
                        try {
                            double expAmount = Double.parseDouble(expString);
                            modifyExp(sender, target, inputMod, inputSkill, expAmount);
                        } catch (NumberFormatException e)
                        {
                            sender.sendMessage(ChatColor.RED + "Please input a double");
                        }
                    } else // Reset
                    {
                        // TODO: Reset skill
                        if (target != null)
                        {
                            modifyExp(sender, target, inputMod, inputSkill, 0);
                        }
                    }
                    break;
            }
        } else
        {
            sender.sendMessage(Messages.notAllowedCommand());
        }

        return true;
    }

    private void modifyExp(CommandSender sender, Player target, String inputMod, String inputSkill, double amount)
    {
        SkillsExpGainEvent.Skill skill;
        ChatColor chatColor;
        switch (inputMod)
        {
            case "add":

                if (!(amount > 0.0))
                {
                    sender.sendMessage(ChatColor.RED + "Experience to add cannot be 0 or less");
                    sender.sendMessage(ChatColor.RED + "You input: " + amount);
                    return;
                }
                if (inputSkill.equals(COMBAT))
                {
                    skill = SkillsExpGainEvent.Skill.COMBAT;
                    chatColor = ChatColor.DARK_RED;
                } else if (inputSkill.equals(MINING))
                {
                    skill = SkillsExpGainEvent.Skill.MINING;
                    chatColor = ChatColor.DARK_BLUE;
                } else if (inputSkill.equals(FORAGING))
                {
                    skill = SkillsExpGainEvent.Skill.FORAGING;
                    chatColor = ChatColor.DARK_GREEN;
                } else if (inputSkill.equals(FARMING))
                {
                    skill = SkillsExpGainEvent.Skill.FARMING;
                    chatColor = ChatColor.GREEN;
                } else if (inputSkill.equals(ENCHANTING))
                {
                    skill = SkillsExpGainEvent.Skill.ENCHANTING;
                    chatColor = ChatColor.LIGHT_PURPLE;
                } else if (inputSkill.equals(FISHING))
                {
                    skill = SkillsExpGainEvent.Skill.FISHING;
                    chatColor = ChatColor.AQUA;
                } else if (inputSkill.equals(BREWING))
                {
                    skill = SkillsExpGainEvent.Skill.BREWING;
                    chatColor = ChatColor.BLUE;
                } else
                {
                    sender.sendMessage(ChatColor.RED + "Not a skill: " + inputSkill);
                    return;
                }
                SkillsExpGainEvent skillsExpGainEvent = new SkillsExpGainEvent(target, amount, skill);
                Bukkit.getPluginManager().callEvent(skillsExpGainEvent);
                sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.YELLOW + amount + chatColor + " " + ItemStringBuilder.capitalizeFirstLetter(skill.name()) + ChatColor.GREEN + " experience to " + ChatColor.GOLD + target.getName());
                target.sendMessage(ChatColor.GREEN + "You were rewarded with " + ChatColor.YELLOW + amount + chatColor + " " + ItemStringBuilder.capitalizeFirstLetter(skill.name()) + ChatColor.GREEN + " experience by " + ChatColor.GOLD + sender.getName());
                break;
            case "reset":

                if (inputSkill.equals(COMBAT))
                {
                    skill = SkillsExpGainEvent.Skill.COMBAT;
                    chatColor = ChatColor.DARK_RED;
                } else if (inputSkill.equals(MINING))
                {
                    skill = SkillsExpGainEvent.Skill.MINING;
                    chatColor = ChatColor.DARK_BLUE;
                } else if (inputSkill.equals(FORAGING))
                {
                    skill = SkillsExpGainEvent.Skill.FORAGING;
                    chatColor = ChatColor.DARK_GREEN;
                } else if (inputSkill.equals(FARMING))
                {
                    skill = SkillsExpGainEvent.Skill.FARMING;
                    chatColor = ChatColor.GREEN;
                } else if (inputSkill.equals(ENCHANTING))
                {
                    skill = SkillsExpGainEvent.Skill.ENCHANTING;
                    chatColor = ChatColor.LIGHT_PURPLE;
                } else if (inputSkill.equals(FISHING))
                {
                    skill = SkillsExpGainEvent.Skill.FISHING;
                    chatColor = ChatColor.AQUA;
                } else if (inputSkill.equals(BREWING))
                {
                    skill = SkillsExpGainEvent.Skill.BREWING;
                    chatColor = ChatColor.BLUE;
                } else
                {
                    sender.sendMessage(ChatColor.RED + "Not a skill: " + inputSkill);
                    return;
                }

                resetSkillExp(target, skill);
                sender.sendMessage(ChatColor.GREEN + "Reset " + chatColor + ItemStringBuilder.capitalizeFirstLetter(skill.name()) + ChatColor.GREEN + " skill for " + ChatColor.GOLD + target.getName());
                target.sendMessage(ChatColor.RED + "Your " + chatColor + ItemStringBuilder.capitalizeFirstLetter(skill.name()) + ChatColor.RED + " skill was reset!");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Cannot modify skill exp with: " + inputMod);
        }
    }

    private void resetSkillExp(Player target, SkillsExpGainEvent.Skill skill)
    {
        StatPlayer statPlayer = new StatPlayer(target);
        SkillsEXP skillsEXP = statPlayer.getSkillsEXP();
        SkillsLevel skillsLevel = statPlayer.getSkillsLevel();
        SkillsReward skillsReward = statPlayer.getSkillsReward();

        Stats stats = statPlayer.getStats();

        DefenseStats defenseStats = statPlayer.getDefenseStats();
        PotionStats potionStats = statPlayer.getPotionStats();
        LootStats lootStats = statPlayer.getLootStats();

        switch (skill)
        {
            case COMBAT -> {
                skillsEXP.setCombatExpToNextLevel(0);
                skillsEXP.setTotalCombatEXP(0);
                skillsEXP.setRequiredCombatExpToNextLevel(DEFAULT_REQUIRED_EXP);
                skillsLevel.setCombatLevel(0);
                skillsReward.setCombatDamageBonus(0);
            }
            case MINING -> {
                skillsEXP.setMiningExpToNextLevel(0);
                skillsEXP.setTotalMiningEXP(0);
                skillsEXP.setRequiredMiningExpToNextLevel(DEFAULT_REQUIRED_EXP);
                skillsLevel.setMiningLevel(0);

                lootStats.setMiningFortune(lootStats.getMiningFortune() - skillsReward.getMiningFortuneBonus());
                defenseStats.setDefense(defenseStats.getDefense() - skillsReward.getMiningDefenseBonus());
                skillsReward.setMiningFortuneBonus(0);
                skillsReward.setMiningDefenseBonus(0);
            }
            case FORAGING -> {
                skillsEXP.setForagingExpToNextLevel(0);
                skillsEXP.setTotalForagingEXP(0);
                skillsEXP.setRequiredForagingExpToNextLevel(DEFAULT_REQUIRED_EXP);
                skillsLevel.setForagingLevel(0);

                lootStats.setForagingFortune(lootStats.getForagingFortune() - skillsReward.getForagingFortuneBonus());
                skillsReward.setForagingFortuneBonus(0);
            }
            case FARMING -> {
                skillsEXP.setFarmingExpToNextLevel(0);
                skillsEXP.setTotalFarmingEXP(0);
                skillsEXP.setRequiredFarmingExpToNextLevel(DEFAULT_REQUIRED_EXP);
                skillsLevel.setFarmingLevel(0);

                lootStats.setFarmingFortune(lootStats.getFarmingFortune() - skillsReward.getFarmingFortuneBonus());
                skillsReward.setFarmingFortuneBonus(0);
            }
            case ENCHANTING -> {
                skillsEXP.setEnchantingExpToNextLevel(0);
                skillsEXP.setTotalEnchantingEXP(0);
                skillsEXP.setRequiredEnchantingExpToNextLevel(DEFAULT_REQUIRED_EXP);
                skillsLevel.setEnchantingLevel(0);

                stats.setMaxMana(stats.getMaxMana() - skillsReward.getEnchantingManaBonus());
                skillsReward.setEnchantingManaBonus(0);
            }
            case FISHING -> {
                skillsEXP.setFishingExpToNextLevel(0);
                skillsEXP.setTotalFishingExp(0);
                skillsEXP.setRequiredFishingExpToNextLevel(DEFAULT_REQUIRED_EXP);
                skillsLevel.setFishingLevel(0);

                lootStats.setFishingLuck(lootStats.getFishingLuck() - skillsReward.getFishingLuckBonus());
                skillsReward.setFishingLuckBonus(0);
            }
            case BREWING -> {
                skillsEXP.setBrewingExpToNextLevel(0);
                skillsEXP.setTotalBrewingExp(0);
                skillsEXP.setRequiredBrewingExpToNextLevel(DEFAULT_REQUIRED_EXP);
                skillsLevel.setBrewingLevel(0);

                potionStats.setLongevity(potionStats.getLongevity() - skillsReward.getBrewingLongevity());
                potionStats.setCaffeination(potionStats.getCaffeination() - skillsReward.getBrewingCaffeination());
                skillsReward.setBrewingLongevity(0);
                skillsReward.setBrewingCaffeination(0);

            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> argsList = new ArrayList<>();

        if (sender.hasPermission(Permissions.EDIT_SKILLS.getPermission()))
        {
            switch (args.length)
            {
                case 1:
                    argsList.add("add");
                    argsList.add("reset");
                    break;
                case 2:
                    for (Player player : Bukkit.getOnlinePlayers())
                    {
                        argsList.add(player.getName());
                    }
                    break;
                case 3:
                    for (SkillsExpGainEvent.Skill skill : SkillsExpGainEvent.Skill.values())
                    {
                        argsList.add(skill.name().toLowerCase());
                    }
                    break;
                case 4:
                    if (args[1].equals("add"))
                    {
                        argsList.add("amount");
                    }
                    break;
            }
        } else
        {
            return new ArrayList<>();
        }
        return argsList;
    }
}

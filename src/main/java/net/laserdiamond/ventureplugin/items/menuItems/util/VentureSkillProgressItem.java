package net.laserdiamond.ventureplugin.items.menuItems.util;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsProfile;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import net.laserdiamond.ventureplugin.util.StatSymbols;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * An abstract class that represents a skill menu item that displays the progress of a player's skill. Lore is automatically set for this item, and shouldn't be changed in any way
 */
public abstract class VentureSkillProgressItem {

    public final VenturePlugin plugin;
    private final PlayerConfig BASE_STATS_CONFIG;

    public VentureSkillProgressItem(VenturePlugin plugin)
    {
        this.plugin = plugin;
        BASE_STATS_CONFIG = plugin.getBaseStatsConfig();
        String keyName = getSkill().name() + "_progress";
        HashMap<String, VentureSkillProgressItem> skillProgressItemMap = plugin.getVentureSkillProgressItems();
        skillProgressItemMap.put(keyName, this);
    }

    public abstract SkillsExpGainEvent.Skill getSkill();

    public final VentureItemBuilder skillProgressItem(int skillLevel, SkillsProfile skillsProfile)
    {
        int finalSkillLevel = Math.max(Math.min(skillLevel, 50), 1);
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(Material.STONE, finalSkillLevel)
                .setSkillProgressSkill(getSkill())
                .setSkillProgressLvl(skillLevel);
        SkillsLevel skillsLevel = skillsProfile.skillsLevel();
        SkillsEXP skillsEXP = skillsProfile.skillsEXP();

        LinkedList<String> lore = new LinkedList<>();
        lore.add(" ");

        if (skillLevel > 0 && skillLevel <= 50)
        {
            int previousLevel = skillLevel - 1;
            int playerSkill = 0;
            double expToNextLevel = 0;
            double requiredExpToNextLevel = 0;

            lore.add(ChatColor.BOLD + "" + ChatColor.AQUA + "Rewards:");
            switch (getSkill())
            {
                case COMBAT -> {
                    ventureItemBuilder.setName(ChatColor.DARK_RED + "Combat " + skillLevel);
                    playerSkill = skillsLevel.getCombatLevel();
                    expToNextLevel = skillsEXP.getCombatExpToNextLevel();
                    requiredExpToNextLevel = skillsEXP.getRequiredCombatExpToNextLevel();
                    double previousDamageBonus = previousLevel * BASE_STATS_CONFIG.getDouble("combatDamageBonus");
                    double damageBonus = skillLevel * BASE_STATS_CONFIG.getDouble("combatDamageBonus");

                    lore.add(ChatColor.WHITE + " Deal " + ChatColor.DARK_GRAY + previousDamageBonus + "->" + ChatColor.DARK_RED + damageBonus + "%" + ChatColor.WHITE + " more damage");
                }
                case MINING -> {
                    ventureItemBuilder.setName(ChatColor.DARK_BLUE + "Mining " + skillLevel);
                    playerSkill = skillsLevel.getMiningLevel();
                    expToNextLevel = skillsEXP.getMiningExpToNextLevel();
                    requiredExpToNextLevel = skillsEXP.getRequiredMiningExpToNextLevel();
                    double previousFortuneBonus = previousLevel * BASE_STATS_CONFIG.getDouble("miningFortuneBonus");
                    double fortuneBonus = skillLevel * BASE_STATS_CONFIG.getDouble("miningFortuneBonus");
                    double previousDefenseBonus = previousLevel * BASE_STATS_CONFIG.getDouble("miningDefenseBonus");
                    double defenseBonus = skillLevel * BASE_STATS_CONFIG.getDouble("miningDefenseBonus");

                    lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + "->" + ChatColor.GREEN + fortuneBonus + StatSymbols.MINING_FORTUNE.getSymbol() + ChatColor.WHITE + " more Mining Fortune");
                    lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousDefenseBonus + "->" + ChatColor.GREEN + defenseBonus + StatSymbols.DEFENSE.getSymbol() + ChatColor.WHITE + " more Defense");
                }
                case FORAGING -> {
                    ventureItemBuilder.setName(ChatColor.DARK_GREEN + "Foraging " + skillLevel);
                    playerSkill = skillsLevel.getForagingLevel();
                    expToNextLevel = skillsEXP.getForagingExpToNextLevel();
                    requiredExpToNextLevel = skillsEXP.getRequiredForagingExpToNextLevel();
                    double previousFortuneBonus = previousLevel * BASE_STATS_CONFIG.getDouble("foragingFortuneBonus");
                    double fortuneBonus = skillLevel * BASE_STATS_CONFIG.getDouble("foragingFortuneBonus");

                    lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + "->" + ChatColor.GREEN + fortuneBonus + StatSymbols.FORAGING_FORTUNE.getSymbol() + ChatColor.WHITE + " more Foraging Fortune");
                }
                case FARMING -> {
                    ventureItemBuilder.setName(ChatColor.GREEN + "Farming " + skillLevel);
                    playerSkill = skillsLevel.getFarmingLevel();
                    expToNextLevel = skillsEXP.getFarmingExpToNextLevel();
                    requiredExpToNextLevel = skillsEXP.getRequiredFarmingExpToNextLevel();
                    double previousFortuneBonus = previousLevel * BASE_STATS_CONFIG.getDouble("farmingFortuneBonus");
                    double fortuneBonus = skillLevel * BASE_STATS_CONFIG.getDouble("farmingFortuneBonus");

                    lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + "->" + ChatColor.GREEN + fortuneBonus + StatSymbols.FARMING_FORTUNE.getSymbol() + ChatColor.WHITE + " more Farming Fortune");
                }
                case ENCHANTING -> {
                    ventureItemBuilder.setName(ChatColor.LIGHT_PURPLE + "Enchanting " + skillLevel);
                    playerSkill = skillsLevel.getEnchantingLevel();
                    expToNextLevel = skillsEXP.getEnchantingExpToNextLevel();
                    requiredExpToNextLevel = skillsEXP.getRequiredEnchantingExpToNextLevel();
                    double previousManaBonus = previousLevel * BASE_STATS_CONFIG.getDouble("enchantingManaBonus");
                    double manaBonus = skillLevel * BASE_STATS_CONFIG.getDouble("enchantingManaBonus");

                    lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousManaBonus + "->" + ChatColor.BLUE + manaBonus + StatSymbols.MANA.getSymbol() + ChatColor.WHITE + " more Mana");
                }
                case FISHING -> {
                    ventureItemBuilder.setName(ChatColor.AQUA + "Fishing " + skillLevel);
                    playerSkill = skillsLevel.getFishingLevel();
                    expToNextLevel = skillsEXP.getFishingExpToNextLevel();
                    requiredExpToNextLevel = skillsEXP.getRequiredFishingExpToNextLevel();
                    double previousFishingLuckBonus = previousLevel * BASE_STATS_CONFIG.getDouble("fishingLuckBonus");
                    double fishingLuckBonus = skillLevel * BASE_STATS_CONFIG.getDouble("fishingLuckBonus");

                    lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousFishingLuckBonus + "->" + ChatColor.GREEN + fishingLuckBonus + StatSymbols.FISHING_LUCK.getSymbol() + ChatColor.WHITE + " more Fishing Luck");
                }
                case BREWING -> {
                    ventureItemBuilder.setName(ChatColor.BLUE + "Brewing " + skillLevel);
                    playerSkill = skillsLevel.getBrewingLevel();
                    expToNextLevel = skillsEXP.getBrewingExpToNextLevel();
                    requiredExpToNextLevel = skillsEXP.getRequiredBrewingExpToNextLevel();
                    double previousLongevity = previousLevel * BASE_STATS_CONFIG.getDouble("brewingLongevity");
                    double longevity = skillLevel * BASE_STATS_CONFIG.getDouble("brewingLongevity");
                    double previousCaffeination = previousLevel * BASE_STATS_CONFIG.getDouble("brewingCaffeination");
                    double caffeination = skillLevel * BASE_STATS_CONFIG.getDouble("brewingCaffeination");

                    lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousLongevity + "->" + ChatColor.DARK_RED + longevity + StatSymbols.LONGEVITY.getSymbol() + ChatColor.WHITE + " more Longevity");
                    lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousCaffeination + "->" + ChatColor.LIGHT_PURPLE + caffeination + StatSymbols.CAFFEINATION.getSymbol() + ChatColor.WHITE + " more Caffeination");
                }
            }
            lore.add(" ");
            int newLvl = playerSkill + 1;
            if (skillLevel == newLvl)
            {
                ventureItemBuilder.setMaterial(Material.YELLOW_TERRACOTTA);
                lore.addLast(ChatColor.GOLD + "Progress to Level " + newLvl + ": " + ChatColor.YELLOW + expToNextLevel + ChatColor.WHITE + "/" + ChatColor.RED + requiredExpToNextLevel);
            } else if (skillLevel > newLvl)
            {
                ventureItemBuilder.setMaterial(Material.RED_TERRACOTTA);
                lore.addLast(ChatColor.RED + "You have not reached this level yet!");
            } else if (skillLevel < newLvl)
            {
                ventureItemBuilder.setMaterial(Material.LIME_TERRACOTTA);
                lore.addLast(ChatColor.GREEN + "Level Completed!");
            }
        } else
        {
            ventureItemBuilder.setAmount(1);
            int playerSkill = 0;

            lore.add(ChatColor.GRAY + "Gain " + ItemStringBuilder.capitalizeFirstLetter(getSkill().name()) + " experience by");

            // Combat 3
            //
            // Gain Combat experience by
            // killing mobs and slaying
            // bosses

            switch (getSkill())
            {
                case COMBAT -> {
                    ventureItemBuilder.setMaterial(MenuItem.SKILL_COMBAT.getMaterial())
                            .setName(ChatColor.DARK_RED + "Combat");
                    playerSkill = skillsLevel.getCombatLevel();
                    lore.add(ChatColor.GRAY + "killing mobs and slaying");
                    lore.add(ChatColor.GRAY + "bosses across the lands");
                }
                case MINING -> {
                    ventureItemBuilder.setMaterial(MenuItem.SKILL_MINING.getMaterial())
                            .setName(ChatColor.DARK_BLUE + "Mining");
                    playerSkill = skillsLevel.getMiningLevel();
                    lore.add(ChatColor.GRAY + "mining ores, stones, and");
                    lore.add(ChatColor.GRAY + "other rare minerals deep");
                    lore.add(ChatColor.GRAY + "underground");
                }
                case FORAGING -> {
                    ventureItemBuilder.setMaterial(MenuItem.SKILL_FORAGING.getMaterial())
                            .setName(ChatColor.DARK_GREEN + "Foraging");
                    playerSkill = skillsLevel.getForagingLevel();
                    lore.add(ChatColor.GRAY + "chopping down trees, plants,");
                    lore.add(ChatColor.GRAY + "and foliage");
                }
                case FARMING -> {
                    ventureItemBuilder.setMaterial(MenuItem.SKILL_FARMING.getMaterial())
                            .setName(ChatColor.GREEN + "Farming");
                    playerSkill = skillsLevel.getFarmingLevel();
                    lore.add(ChatColor.GRAY + "harvesting crops and plants");
                }
                case ENCHANTING -> {
                    ventureItemBuilder.setMaterial(MenuItem.SKILL_ENCHANTING.getMaterial())
                            .setName(ChatColor.LIGHT_PURPLE + "Enchanting");
                    playerSkill = skillsLevel.getEnchantingLevel();
                    lore.add(ChatColor.GRAY + "enchanting equipment and gear with");
                    lore.add(ChatColor.GRAY + "various enchants to help you on your");
                    lore.add(ChatColor.GRAY + "journey across different lands");
                }
                case FISHING -> {
                    ventureItemBuilder.setMaterial(MenuItem.SKILL_FISHING.getMaterial())
                            .setName(ChatColor.AQUA + "Fishing");
                    playerSkill = skillsLevel.getFishingLevel();
                    lore.add(ChatColor.GRAY + "fishing up treasures and");
                    lore.add(ChatColor.GRAY + "rare sea creatures");
                }
                case BREWING -> {
                    ventureItemBuilder.setMaterial(MenuItem.SKILL_BREWING.getMaterial())
                            .setName(ChatColor.BLUE + "Brewing");
                    playerSkill = skillsLevel.getBrewingLevel();
                    lore.add(ChatColor.GRAY + "brewing up potions to assist");
                    lore.add(ChatColor.GRAY + "you on your adventures");
                }
            }

            lore.add(" ");
            lore.add(ChatColor.GOLD + "Current " + ItemStringBuilder.capitalizeFirstLetter(getSkill().name()) + " Level: " + playerSkill);

        }
        ventureItemBuilder.hideAllItemFlags();
        ventureItemBuilder.setLore(lore);

        return ventureItemBuilder;
    }
}

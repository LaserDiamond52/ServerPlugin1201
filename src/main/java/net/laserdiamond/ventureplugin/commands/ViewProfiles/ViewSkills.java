package net.laserdiamond.ventureplugin.commands.ViewProfiles;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.skills.SkillsExpGainEvent;
import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.skills.SkillsMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.skills.SkillsProgressMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.util.VentureSkillProgressItem;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.skills.Components.ExpGain.SkillsExpGainListener;
import net.laserdiamond.ventureplugin.skills.Components.SkillsEXP;
import net.laserdiamond.ventureplugin.skills.Components.SkillsLevel;
import net.laserdiamond.ventureplugin.skills.Components.SkillsProfile;
import net.laserdiamond.ventureplugin.skills.Components.SkillsReward;
import net.laserdiamond.ventureplugin.stats.Config.BaseStatsConfig;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.StatSymbols;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ViewSkills implements CommandExecutor, Listener {

    public static final String SKILL_INV_TITLE = "'s Skills";
    public static final String COMBAT_INV_TITLE = "'s Combat Skill";
    public static final String MINING_INV_TITLE = "'s Mining Skill";
    public static final String FORAGING_INV_TITLE = "'s Foraging Skill";
    public static final String FARMING_INV_TITLE = "'s Farming Skill";
    public static final String ENCHANTING_INV_TITLE = "'s Enchanting Skill";
    public static final String FISHING_INV_TITLE = "'s Fishing Skill";
    public static final String BREWING_INV_TITLE = "'s Brewing Skill";
    private final PlayerConfig BASE_STATS_CONFIG;

    public ViewSkills(VenturePlugin plugin)
    {
        plugin.getCommand("skills").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        BASE_STATS_CONFIG = plugin.getBaseStatsConfig();
    }

    private Inventory skillInventory(Player player)
    {
        Inventory skillInventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + player.getName() + SKILL_INV_TITLE);

        for (SkillsMenuItems.SkillItemSlots skillItemSlots : SkillsMenuItems.SkillItemSlots.values())
        {
            ItemStack skillMenuItem = skillItemSlots.getVentureMenuItem().createItem(player).toItemStack();
            int inventorySlot = skillItemSlots.getInventorySlot();
            skillInventory.setItem(inventorySlot, skillMenuItem);
        }
        MiscMenuItems.placeExitButton(player, skillInventory);

        MiscMenuItems.fillBlankSlotsPlayerInv(player, skillInventory);
        return skillInventory;
    }

    private Inventory skillProgressInventory(Player player, SkillsExpGainEvent.Skill skill, SkillProgressInvSection section)
    {
        Inventory skillProgressInventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + player.getName() + "'s " + ItemStringBuilder.capitalizeFirstLetter(skill.name()) + " Skill");
        StatPlayer statPlayer = new StatPlayer(player);
        SkillsProfile skillsProfile = statPlayer.getSkillsProfile();

        VentureSkillProgressItem skillProgressItem = null;
        switch (skill)
        {
            case COMBAT -> {
                skillProgressItem = SkillsProgressMenuItems.COMBAT_PROGRESS;
            }
            case MINING -> {
                skillProgressItem = SkillsProgressMenuItems.MINING_PROGRESS;
            }
            case FORAGING -> {
                skillProgressItem = SkillsProgressMenuItems.FORAGING_PROGRESS;
            }
            case FARMING -> {
                skillProgressItem = SkillsProgressMenuItems.FARMING_PROGRESS;
            }
            case ENCHANTING -> {
                skillProgressItem = SkillsProgressMenuItems.ENCHANTING_PROGRESS;
            }
            case FISHING -> {
                skillProgressItem = SkillsProgressMenuItems.FISHING_PROGRESS;
            }
            case BREWING -> {
                skillProgressItem = SkillsProgressMenuItems.BREWING_PROGRESS;
            }
        }
        if (section == SkillProgressInvSection.SECTION_1)
        {
            for (int i = 0; i < 26; i++)
            {
                ItemForger progressItem = skillProgressItem.skillProgressItem(i, skillsProfile);
                int invSlot = skillProgressItemInvLoc.get(i);
                skillProgressInventory.setItem(invSlot, progressItem.toItemStack());
            }
        } else if (section == SkillProgressInvSection.SECTION_2)
        {
            for (int i = 26; i <= 51; i++)
            {
                ItemForger progressItem = skillProgressItem.skillProgressItem(i, skillsProfile);
                int invSlot = skillProgressItemInvLoc.get(i);
                skillProgressInventory.setItem(invSlot, progressItem.toItemStack());
            }
        }

        MiscMenuItems.placeExitButton(player, skillProgressInventory);

        MiscMenuItems.fillBlankSlotsPlayerInv(player, skillProgressInventory);
        return skillProgressInventory;
    }

    @Deprecated
    private ItemForger skillProgressItem(SkillsExpGainEvent.Skill skill, int skillLevel, SkillsProfile skillsProfile)
    {
        ItemForger itemForger = new ItemForger(Material.AIR, skillLevel);
        itemForger.setName(ItemStringBuilder.capitalizeFirstLetter(skill.name()) + " Level " + skillLevel);

        SkillsLevel skillsLevel = skillsProfile.skillsLevel();
        SkillsEXP skillsEXP = skillsProfile.skillsEXP();

        int previousLevel = skillLevel - 1;
        int playerSkill = 0;
        double expToNextLevel = 0;
        double requiredExpToNextLevel = 0;

        LinkedList<String> lore = new LinkedList<>();
        lore.add(" ");
        lore.add(ChatColor.BOLD + "" + ChatColor.AQUA + "Rewards:");
        switch (skill)
        {
            case COMBAT -> {
                playerSkill = skillsLevel.getCombatLevel();
                expToNextLevel = skillsEXP.getCombatExpToNextLevel();
                requiredExpToNextLevel = skillsEXP.getRequiredCombatExpToNextLevel();
                double previousDamageBonus = previousLevel * BASE_STATS_CONFIG.getDouble("combatDamageBonus");
                double damageBonus = skillLevel * BASE_STATS_CONFIG.getDouble("combatDamageBonus");

                lore.add(ChatColor.WHITE + " Deal " + ChatColor.DARK_GRAY + previousDamageBonus + "->" + ChatColor.DARK_RED + damageBonus + "%" + ChatColor.WHITE + " more damage");
            }
            case MINING -> {
                playerSkill = skillsLevel.getMiningLevel();
                expToNextLevel = skillsEXP.getMiningExpToNextLevel();
                requiredExpToNextLevel = skillsEXP.getRequiredMiningExpToNextLevel();
                double previousFortuneBonus = previousLevel * BASE_STATS_CONFIG.getDouble("miningFortuneBonus");
                double fortuneBonus = skillLevel * BASE_STATS_CONFIG.getDouble("miningFortuneBonus");
                double previousDefenseBonus = previousLevel * BASE_STATS_CONFIG.getDouble("miningDefenseBonus");
                double defenseBonus = skillLevel * BASE_STATS_CONFIG.getDouble("miningDefenseBonus");

                lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + "->" + ChatColor.GREEN + fortuneBonus + StatSymbols.MINING_FORTUNE.getSymbol() + ChatColor.WHITE + " more Mining Fortune");
                lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousDefenseBonus + "->" + ChatColor.GREEN + defenseBonus + StatSymbols.DEFENSE.getSymbol() + ChatColor.WHITE + " more Mining Fortune");
            }
            case FORAGING -> {
                playerSkill = skillsLevel.getForagingLevel();
                expToNextLevel = skillsEXP.getForagingExpToNextLevel();
                requiredExpToNextLevel = skillsEXP.getRequiredForagingExpToNextLevel();
                double previousFortuneBonus = previousLevel * BASE_STATS_CONFIG.getDouble("foragingFortuneBonus");
                double fortuneBonus = skillLevel * BASE_STATS_CONFIG.getDouble("foragingFortuneBonus");

                lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + "->" + ChatColor.GREEN + fortuneBonus + StatSymbols.FORAGING_FORTUNE.getSymbol() + ChatColor.WHITE + " more Foraging Fortune");
            }
            case FARMING -> {
                playerSkill = skillsLevel.getFarmingLevel();
                expToNextLevel = skillsEXP.getFarmingExpToNextLevel();
                requiredExpToNextLevel = skillsEXP.getRequiredFarmingExpToNextLevel();
                double previousFortuneBonus = previousLevel * BASE_STATS_CONFIG.getDouble("farmingFortuneBonus");
                double fortuneBonus = skillLevel * BASE_STATS_CONFIG.getDouble("farmingFortuneBonus");

                lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousFortuneBonus + "->" + ChatColor.GREEN + fortuneBonus + StatSymbols.FARMING_FORTUNE.getSymbol() + ChatColor.WHITE + " more Farming Fortune");
            }
            case ENCHANTING -> {
                playerSkill = skillsLevel.getEnchantingLevel();
                expToNextLevel = skillsEXP.getEnchantingExpToNextLevel();
                requiredExpToNextLevel = skillsEXP.getRequiredEnchantingExpToNextLevel();
                double previousManaBonus = previousLevel * BASE_STATS_CONFIG.getDouble("enchantingManaBonus");
                double manaBonus = skillLevel * BASE_STATS_CONFIG.getDouble("enchantingManaBonus");

                lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousManaBonus + "->" + ChatColor.BLUE + manaBonus + StatSymbols.MANA.getSymbol() + ChatColor.WHITE + " more Mana");
            }
            case FISHING -> {
                playerSkill = skillsLevel.getFishingLevel();
                expToNextLevel = skillsEXP.getFishingExpToNextLevel();
                requiredExpToNextLevel = skillsEXP.getRequiredFishingExpToNextLevel();
                double previousFishingLuckBonus = previousLevel * BASE_STATS_CONFIG.getDouble("fishingLuckBonus");
                double fishingLuckBonus = skillLevel * BASE_STATS_CONFIG.getDouble("fishingLuckBonus");

                lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousFishingLuckBonus + "->" + ChatColor.GREEN + fishingLuckBonus + StatSymbols.FISHING_LUCK.getSymbol() + ChatColor.WHITE + " more Fishing Luck");
            }
            case BREWING -> {
                playerSkill = skillsLevel.getBrewingLevel();
                expToNextLevel = skillsEXP.getBrewingExpToNextLevel();
                requiredExpToNextLevel = skillsEXP.getRequiredBrewingExpToNextLevel();
                double previousLongevity = previousLevel * BASE_STATS_CONFIG.getDouble("brewingLongevity");
                double longevity = skillLevel * BASE_STATS_CONFIG.getDouble("brewingLongevity");
                double previousCaffeination = previousLevel * BASE_STATS_CONFIG.getDouble("brewingCaffeination");
                double caffeination = skillLevel * BASE_STATS_CONFIG.getDouble("brewingCaffeination");

                lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousLongevity + "->" + ChatColor.DARK_AQUA + longevity + StatSymbols.LONGEVITY.getSymbol() + " more Longevity");
                lore.add(ChatColor.WHITE + " Gain " + ChatColor.DARK_GRAY + previousCaffeination + "->" + ChatColor.LIGHT_PURPLE + caffeination + StatSymbols.CAFFEINATION.getSymbol() + " more Caffeination");
            }
        }
        lore.add(" ");

        if (skillLevel == playerSkill)
        {
            itemForger.setMaterial(Material.YELLOW_TERRACOTTA);
            lore.addLast(ChatColor.GOLD + "Progress to Next Level: " + ChatColor.YELLOW + expToNextLevel + ChatColor.WHITE + "/" + ChatColor.RED + requiredExpToNextLevel);
        } else if (skillLevel > playerSkill)
        {
            itemForger.setMaterial(Material.RED_TERRACOTTA);
        } else
        {
            itemForger.setMaterial(Material.LIME_TERRACOTTA);
        }
        itemForger.setLore(lore);

        return itemForger;
    }

    private static final HashMap<Integer, Integer> skillProgressItemInvLoc = new HashMap<>();
    static
    {
        // Slot 0 for section 1 is for level 0
        skillProgressItemInvLoc.put(0,0);
        skillProgressItemInvLoc.put(1,9);
        skillProgressItemInvLoc.put(2,18);
        skillProgressItemInvLoc.put(3,27);
        skillProgressItemInvLoc.put(4,28);
        skillProgressItemInvLoc.put(5,29);
        skillProgressItemInvLoc.put(6,20);
        skillProgressItemInvLoc.put(7,11);
        skillProgressItemInvLoc.put(8,2);
        skillProgressItemInvLoc.put(9,3);
        skillProgressItemInvLoc.put(10,4);
        skillProgressItemInvLoc.put(11,13);
        skillProgressItemInvLoc.put(12,22);
        skillProgressItemInvLoc.put(13,31);
        skillProgressItemInvLoc.put(14,32);
        skillProgressItemInvLoc.put(15,33);
        skillProgressItemInvLoc.put(16,24);
        skillProgressItemInvLoc.put(17,15);
        skillProgressItemInvLoc.put(18,6);
        skillProgressItemInvLoc.put(19,7);
        skillProgressItemInvLoc.put(20,8);
        skillProgressItemInvLoc.put(21,17);
        skillProgressItemInvLoc.put(22,26);
        skillProgressItemInvLoc.put(23,35);
        skillProgressItemInvLoc.put(24,44);
        skillProgressItemInvLoc.put(25,53);

        // Slot 53 for section 2 is for after max level
        skillProgressItemInvLoc.put(26,0);
        skillProgressItemInvLoc.put(27,9);
        skillProgressItemInvLoc.put(28,18);
        skillProgressItemInvLoc.put(29,27);
        skillProgressItemInvLoc.put(30,28);
        skillProgressItemInvLoc.put(31,29);
        skillProgressItemInvLoc.put(32,20);
        skillProgressItemInvLoc.put(33,11);
        skillProgressItemInvLoc.put(34,2);
        skillProgressItemInvLoc.put(35,3);
        skillProgressItemInvLoc.put(36,4);
        skillProgressItemInvLoc.put(37,13);
        skillProgressItemInvLoc.put(38,22);
        skillProgressItemInvLoc.put(39,31);
        skillProgressItemInvLoc.put(40,32);
        skillProgressItemInvLoc.put(41,33);
        skillProgressItemInvLoc.put(42,24);
        skillProgressItemInvLoc.put(43,15);
        skillProgressItemInvLoc.put(44,6);
        skillProgressItemInvLoc.put(45,7);
        skillProgressItemInvLoc.put(46,8);
        skillProgressItemInvLoc.put(47,17);
        skillProgressItemInvLoc.put(48,26);
        skillProgressItemInvLoc.put(49,35);
        skillProgressItemInvLoc.put(50,44);
        skillProgressItemInvLoc.put(51,53);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player)
        {
            if (player.hasPermission(Permissions.SKILLS.getPermission()))
            {
                player.openInventory(skillInventory(player));
            } else
            {
                player.sendMessage(Messages.notAllowedCommand());
            }
        } else
        {
            sender.sendMessage(Messages.notPlayerCommand());
        }
        return true;
    }

    @EventHandler
    public void clickInsideInv(InventoryClickEvent event)
    {
        HumanEntity humanEntity = event.getWhoClicked();
        if (humanEntity instanceof Player player)
        {
            if (event.getClickedInventory() != null)
            {
                Inventory clickedInv = event.getClickedInventory();
                String invTitle = event.getView().getTitle();
                int clickedSlot = event.getSlot();

                if (invTitle.contains(SKILL_INV_TITLE) ||
                        invTitle.contains(COMBAT_INV_TITLE) ||
                        invTitle.contains(MINING_INV_TITLE) ||
                        invTitle.contains(FORAGING_INV_TITLE) ||
                        invTitle.contains(FARMING_INV_TITLE) ||
                        invTitle.contains(ENCHANTING_INV_TITLE) ||
                        invTitle.contains(FISHING_INV_TITLE) ||
                        invTitle.contains(BREWING_INV_TITLE))
                {
                    event.setCancelled(true);

                    if (invTitle.contains(SKILL_INV_TITLE))
                    {
                        // TODO: Able to click on skills to view progression
                        if (clickedSlot == SkillsMenuItems.SkillItemSlots.COMBAT.getInventorySlot())
                        {
                            player.openInventory(skillProgressInventory(player, SkillsExpGainEvent.Skill.COMBAT, SkillProgressInvSection.SECTION_1));

                        } else if (clickedSlot == SkillsMenuItems.SkillItemSlots.MINING.getInventorySlot())
                        {
                            player.openInventory(skillProgressInventory(player, SkillsExpGainEvent.Skill.MINING, SkillProgressInvSection.SECTION_1));

                        } else if (clickedSlot == SkillsMenuItems.SkillItemSlots.FORAGING.getInventorySlot())
                        {
                            player.openInventory(skillProgressInventory(player, SkillsExpGainEvent.Skill.FORAGING, SkillProgressInvSection.SECTION_1));

                        } else if (clickedSlot == SkillsMenuItems.SkillItemSlots.FARMING.getInventorySlot())
                        {
                            player.openInventory(skillProgressInventory(player, SkillsExpGainEvent.Skill.FARMING, SkillProgressInvSection.SECTION_1));

                        } else if (clickedSlot == SkillsMenuItems.SkillItemSlots.ENCHANTING.getInventorySlot())
                        {
                            player.openInventory(skillProgressInventory(player, SkillsExpGainEvent.Skill.ENCHANTING, SkillProgressInvSection.SECTION_1));

                        } else if (clickedSlot == SkillsMenuItems.SkillItemSlots.FISHING.getInventorySlot())
                        {
                            player.openInventory(skillProgressInventory(player, SkillsExpGainEvent.Skill.FISHING, SkillProgressInvSection.SECTION_1));

                        } else if (clickedSlot == SkillsMenuItems.SkillItemSlots.BREWING.getInventorySlot())
                        {
                            player.openInventory(skillProgressInventory(player, SkillsExpGainEvent.Skill.BREWING, SkillProgressInvSection.SECTION_1));

                        }
                    }
                }

            }
        }
    }

    private enum SkillProgressInvSection
    {
        SECTION_1,
        SECTION_2;
    }
}
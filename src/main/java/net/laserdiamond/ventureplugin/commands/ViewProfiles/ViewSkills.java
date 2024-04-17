package net.laserdiamond.ventureplugin.commands.ViewProfiles;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.InventoryGUI.InventoryClicker;
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

public class ViewSkills implements CommandExecutor, InventoryClicker {

    public static final String SKILL_INV_TITLE = "'s Skills";
    public static final String COMBAT_INV_TITLE = "'s Combat Skill";
    public static final String MINING_INV_TITLE = "'s Mining Skill";
    public static final String FORAGING_INV_TITLE = "'s Foraging Skill";
    public static final String FARMING_INV_TITLE = "'s Farming Skill";
    public static final String ENCHANTING_INV_TITLE = "'s Enchanting Skill";
    public static final String FISHING_INV_TITLE = "'s Fishing Skill";
    public static final String BREWING_INV_TITLE = "'s Brewing Skill";
    public static final String PAGE_1 = "Page 1";
    public static final String PAGE_2 = "Page 2";
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
        Inventory skillProgressInventory = null;
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
            skillProgressInventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + player.getName() + "'s " + ItemStringBuilder.capitalizeFirstLetter(skill.name()) + " Skill - " + PAGE_1);
            for (int i = 0; i < 26; i++)
            {
                ItemForger progressItem = skillProgressItem.skillProgressItem(i, skillsProfile);
                int invSlot = skillProgressItemInvLoc.get(i);
                skillProgressInventory.setItem(invSlot, progressItem.toItemStack());
            }
            MiscMenuItems.placeGoForwardButton(player, skillProgressInventory);
        } else if (section == SkillProgressInvSection.SECTION_2)
        {
            skillProgressInventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + player.getName() + "'s " + ItemStringBuilder.capitalizeFirstLetter(skill.name()) + " Skill - " + PAGE_2);
            for (int i = 26; i <= 51; i++)
            {
                ItemForger progressItem = skillProgressItem.skillProgressItem(i, skillsProfile);
                int invSlot = skillProgressItemInvLoc.get(i);
                skillProgressInventory.setItem(invSlot, progressItem.toItemStack());
            }
        }

        if (skillProgressInventory != null)
        {
            MiscMenuItems.placeExitButton(player, skillProgressInventory);
            MiscMenuItems.placeGoBackButton(player, skillProgressInventory);
            MiscMenuItems.fillBlankSlotsPlayerInv(player, skillProgressInventory);
        }
        return skillProgressInventory;
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
        skillProgressItemInvLoc.put(51,0);
        skillProgressItemInvLoc.put(26,9);
        skillProgressItemInvLoc.put(27,18);
        skillProgressItemInvLoc.put(28,27);
        skillProgressItemInvLoc.put(29,28);
        skillProgressItemInvLoc.put(30,29);
        skillProgressItemInvLoc.put(31,20);
        skillProgressItemInvLoc.put(32,11);
        skillProgressItemInvLoc.put(33,2);
        skillProgressItemInvLoc.put(34,3);
        skillProgressItemInvLoc.put(35,4);
        skillProgressItemInvLoc.put(36,13);
        skillProgressItemInvLoc.put(37,22);
        skillProgressItemInvLoc.put(38,31);
        skillProgressItemInvLoc.put(39,32);
        skillProgressItemInvLoc.put(40,33);
        skillProgressItemInvLoc.put(41,24);
        skillProgressItemInvLoc.put(42,15);
        skillProgressItemInvLoc.put(43,6);
        skillProgressItemInvLoc.put(44,7);
        skillProgressItemInvLoc.put(45,8);
        skillProgressItemInvLoc.put(46,17);
        skillProgressItemInvLoc.put(47,26);
        skillProgressItemInvLoc.put(48,35);
        skillProgressItemInvLoc.put(49,44);
        skillProgressItemInvLoc.put(50,53);
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
    @Override
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
                    int goBackButtonLoc = MiscMenuItems.getGoBackButtonLocation(clickedInv);
                    int goForwardButtonLoc = MiscMenuItems.getGoForwardButtonLocation(clickedInv);
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
                    } else if (invTitle.contains(COMBAT_INV_TITLE))
                    {
                        navigateSkillInv(player, invTitle, clickedSlot, goBackButtonLoc, goForwardButtonLoc, SkillsExpGainEvent.Skill.COMBAT);

                    } else if (invTitle.contains(MINING_INV_TITLE))
                    {
                        navigateSkillInv(player, invTitle, clickedSlot, goBackButtonLoc, goForwardButtonLoc, SkillsExpGainEvent.Skill.MINING);

                    } else if (invTitle.contains(FORAGING_INV_TITLE))
                    {
                        navigateSkillInv(player, invTitle, clickedSlot, goBackButtonLoc, goForwardButtonLoc, SkillsExpGainEvent.Skill.FORAGING);

                    } else if (invTitle.contains(FARMING_INV_TITLE))
                    {
                        navigateSkillInv(player, invTitle, clickedSlot, goBackButtonLoc, goForwardButtonLoc, SkillsExpGainEvent.Skill.FARMING);

                    } else if (invTitle.contains(ENCHANTING_INV_TITLE))
                    {
                        navigateSkillInv(player, invTitle, clickedSlot, goBackButtonLoc, goForwardButtonLoc, SkillsExpGainEvent.Skill.ENCHANTING);

                    } else if (invTitle.contains(FISHING_INV_TITLE))
                    {
                        navigateSkillInv(player, invTitle, clickedSlot, goBackButtonLoc, goForwardButtonLoc, SkillsExpGainEvent.Skill.FISHING);

                    } else if (invTitle.contains(BREWING_INV_TITLE))
                    {
                        navigateSkillInv(player, invTitle, clickedSlot, goBackButtonLoc, goForwardButtonLoc, SkillsExpGainEvent.Skill.BREWING);

                    }
                }

            }
        }
    }

    private void navigateSkillInv(Player player, String invTitle, int clickedSlot, int goBackButton, int goForwardButton, SkillsExpGainEvent.Skill skill)
    {
        if (invTitle.contains(PAGE_1))
        {
            if (clickedSlot == goBackButton)
            {
                player.openInventory(skillInventory(player));
            } else if (clickedSlot == goForwardButton)
            {
                player.openInventory(skillProgressInventory(player, skill, SkillProgressInvSection.SECTION_2));
            }
        } else if (invTitle.contains(PAGE_2))
        {
            if (clickedSlot == goBackButton)
            {
                player.openInventory(skillProgressInventory(player, skill, SkillProgressInvSection.SECTION_1));
            }
        }
    }

    private enum SkillProgressInvSection
    {
        SECTION_1,
        SECTION_2;
    }
}
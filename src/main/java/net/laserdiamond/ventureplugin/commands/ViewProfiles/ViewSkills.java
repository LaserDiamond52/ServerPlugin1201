package net.laserdiamond.ventureplugin.commands.ViewProfiles;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.skills.SkillsMenuItems;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import java.util.List;

public class ViewSkills implements CommandExecutor, Listener {

    public static final String SKILL_INV_TITLE = "'s Skills";

    public ViewSkills(VenturePlugin plugin)
    {
        plugin.getCommand("skills").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
                if (invTitle.contains(SKILL_INV_TITLE))
                {
                    event.setCancelled(true);

                    // TODO: Able to click on skills to view progression
                }
            }
        }
    }
}
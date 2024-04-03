package net.laserdiamond.ventureplugin.commands.ViewProfiles;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.tuning.TuningMenuItems;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TuningMenu implements CommandExecutor, Listener {

    public TuningMenu(VenturePlugin plugin)
    {
        plugin.getCommand("tuning").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private Inventory tuningInventory(Player player)
    {
        Inventory tuningInventory = Bukkit.createInventory(null, 54, ChatColor.AQUA + player.getName() + "'s Tuning");

        for (TuningMenuItems.TuningItemSlots tuningItemSlots : TuningMenuItems.TuningItemSlots.values())
        {
            ItemStack tuningItemStack = tuningItemSlots.getVentureMenuItem().createItem(player).toItemStack();
            int inventorySlot = tuningItemSlots.getInventorySlot();
            tuningInventory.setItem(inventorySlot, tuningItemStack);
        }

        MiscMenuItems.placeExitButton(player, tuningInventory);
        MiscMenuItems.fillBlankSlotsPlayerInv(player, tuningInventory);
        return tuningInventory;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if (sender instanceof Player player)
        {
            if (sender.hasPermission(Permissions.TUNING_MENU.getPermissionString()))
            {
                player.openInventory(tuningInventory(player));
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

    public static final String TUNING_INV_TITLE = "'s Tuning";

    @EventHandler
    public void clickInsideInv(InventoryClickEvent event)
    {
        HumanEntity humanEntity = event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();
        if (humanEntity instanceof Player player)
        {
            if (clickedInv != null)
            {
                StatPlayer statPlayer = new StatPlayer(player);
                String invTitle = event.getView().getTitle();
                int clickedSlot = event.getSlot();
                if (invTitle.contains(TUNING_INV_TITLE))
                {
                    event.setCancelled(true);

                    // TODO: Add functionality to buttons
                    if (clickedSlot == TuningMenuItems.TuningItemSlots.HEALTH.getInventorySlot())
                    {

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.DEFENSE.getInventorySlot())
                    {

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.SPEED.getInventorySlot())
                    {

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.MANA.getInventorySlot())
                    {

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.MELEE.getInventorySlot())
                    {

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.MAGIC.getInventorySlot())
                    {

                    } else if (clickedSlot == TuningMenuItems.TuningItemSlots.RANGE.getInventorySlot())
                    {

                    }
                }
            }
        }
    }
}

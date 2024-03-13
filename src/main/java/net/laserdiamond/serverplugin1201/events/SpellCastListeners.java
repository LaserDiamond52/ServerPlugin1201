package net.laserdiamond.serverplugin1201.events;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantsClass;
import net.laserdiamond.serverplugin1201.entities.player.StatPlayer;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Components.StormLordArmorManager;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpellCastListeners implements Listener {

    private final ServerPlugin1201 plugin;
    private final StatProfileManager statProfileManager;

    public SpellCastListeners(ServerPlugin1201 plugin)
    {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();

    }

    @EventHandler
    public void castSpellClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemMeta mainHandMeta = mainHand.getItemMeta();
        // TODO: Custom model data spells

        if (mainHandMeta != null && mainHandMeta.hasCustomModelData())
        {
            int CustMdlData = mainHandMeta.getCustomModelData();
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                // TODO: Right click spells
                // Needs event.setCancelled(true);
                switch (CustMdlData)
                {

                }
            } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
            {
                // TODO: Left click spells
                // Needs event.setCancelled(true);
                switch (CustMdlData)
                {

                }
            }

        }
    }

    @EventHandler
    public void castSpellDrop(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();

        InventoryType invType = player.getOpenInventory().getType();
        ItemStack itemDropped = event.getItemDrop().getItemStack();
        ItemMeta droppedMeta = itemDropped.getItemMeta();

        if (invType == InventoryType.CRAFTING || invType == InventoryType.CREATIVE)
        {
            if (droppedMeta != null)
            {
                if (droppedMeta.hasCustomModelData())
                {
                    int CustMdlData = droppedMeta.getCustomModelData();
                    switch (CustMdlData)
                    {

                    }
                }

                if (droppedMeta.hasEnchants())
                {
                    for (Enchantment enchantment : droppedMeta.getEnchants().keySet())
                    {
                        int enchantLvl = droppedMeta.getEnchantLevel(enchantment);
                        if (enchantment.equals(EnchantsClass.THUNDER_STRIKE)) {
                            StormLordArmorManager.castEyeOfStorm(player, enchantLvl);
                        }
                        break;
                    }
                }

            }
        }
    }
}

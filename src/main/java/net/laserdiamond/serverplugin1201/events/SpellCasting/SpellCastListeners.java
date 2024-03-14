package net.laserdiamond.serverplugin1201.events.SpellCasting;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantsClass;
import net.laserdiamond.serverplugin1201.events.SpellCasting.*;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class SpellCastListeners implements Listener {

    private final ServerPlugin1201 plugin;
    private final StatProfileManager statProfileManager;

    public SpellCastListeners(ServerPlugin1201 plugin)
    {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();

    }

    @EventHandler
    public void castSpellClick(PlayerInteractEvent event) throws InvocationTargetException, IllegalAccessException {

        if (event.getAction().isRightClick())
        {
            PlayerClickItemSpell(SpellCastType.RIGHT_CLICK, event);
        }

        if (event.getAction().isLeftClick())
        {
            PlayerClickItemSpell(SpellCastType.LEFT_CLICK, event);

        }
    }

    @EventHandler
    public void castSpellDrop(PlayerDropItemEvent event) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Player player = event.getPlayer();

        InventoryType invType = player.getOpenInventory().getType();

        if (invType == InventoryType.CRAFTING || invType == InventoryType.CREATIVE)
        {
            PlayerDropItemSpell(event);
        }
    }

    private void PlayerDropItemSpell(PlayerDropItemEvent event) throws InvocationTargetException, IllegalAccessException {
        List<SpellCastListener> listeners = plugin.getSpellCastListeners();
        for (SpellCastListener listener : listeners)
        {
            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (method.isAnnotationPresent(SpellCastHandler.class))
                {
                    SpellCastHandler annotation = method.getAnnotation(SpellCastHandler.class);
                    if (annotation.spellCastType() == SpellCastType.DROP_ITEM)
                    {
                        method.invoke(listener, event);
                    }
                }
            }
        }
    }

    private void PlayerClickItemSpell(SpellCastType spellCastType, PlayerInteractEvent event) throws InvocationTargetException, IllegalAccessException {
        List<SpellCastListener> listeners = plugin.getSpellCastListeners();
        for (SpellCastListener listener : listeners)
        {
            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (method.isAnnotationPresent(SpellCastHandler.class))
                {
                    SpellCastHandler annotation = method.getAnnotation(SpellCastHandler.class);
                    if (annotation.spellCastType() == spellCastType)
                    {
                        method.invoke(listener, event);
                    }
                }
            }
        }
    }

}

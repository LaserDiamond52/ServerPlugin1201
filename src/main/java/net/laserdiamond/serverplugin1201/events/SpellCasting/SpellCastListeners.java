package net.laserdiamond.serverplugin1201.events.SpellCasting;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SpellCastListeners extends BukkitRunnable implements Listener {

    private final ServerPlugin1201 plugin;

    public SpellCastListeners(ServerPlugin1201 plugin)
    {
        this.plugin = plugin;

    }

    /**
     * BukkitRunnable for Sneaking/Passive spells/abilities
     */

    private int timer = 0;

    @Override
    public void run()
    {
        timer++;
        for (Player player : Bukkit.getServer().getOnlinePlayers())
        {
            try {
                PlayerRunnableSpell(player, timer);
            } catch (InvocationTargetException | IllegalAccessException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "!ERROR WHILE TRYING TO ACTIVATE SPELL/ABILITY!");
                throw new RuntimeException(e);
            }
        }
        if (timer >= 20)
        {
            timer = 0;
        }
    }

    /**
     * Event handler for registered click spells/abilities
     * @param event
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
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

    /**
     * Event handler for registered drop spells/abilities
     * @param event
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     */
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

    private void PlayerRunnableSpell(Player player, int timer) throws InvocationTargetException, IllegalAccessException {
        List<SpellCastListener> listeners = plugin.getSpellCastListeners();
        for (SpellCastListener listener : listeners)
        {
            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (method.isAnnotationPresent(SpellCastHandler.class))
                {
                    SpellCastHandler annotation = method.getAnnotation(SpellCastHandler.class);
                    {
                        if (annotation.spellCastType() == SpellCastType.RUNNABLE)
                        {
                            method.invoke(listener, player, timer);
                        }
                    }
                }
            }
        }
    }
}

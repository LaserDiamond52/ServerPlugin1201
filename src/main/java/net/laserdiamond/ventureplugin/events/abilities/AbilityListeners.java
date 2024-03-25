package net.laserdiamond.ventureplugin.events.abilities;

import net.laserdiamond.ventureplugin.VenturePlugin;
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

public class AbilityListeners extends BukkitRunnable implements Listener {

    private final VenturePlugin plugin;
    private final HashMap<AbilityListener, Method> rightClickAbility, leftClickAbility, dropItemAbility, runnableAbility, attackAbility;



    public AbilityListeners(VenturePlugin plugin)
    {
        this.plugin = plugin;
        rightClickAbility = plugin.getAbilities().rightClickAbilities();
        leftClickAbility = plugin.getAbilities().leftClickAbilities();
        runnableAbility = plugin.getAbilities().runnableAbilities();
        dropItemAbility = plugin.getAbilities().dropItemAbilities();
        attackAbility = plugin.getAbilities().attackAbility();
    }

    /**
     * BukkitRunnable for Sneaking/Passive abilities
     */
    @Override
    public void run()
    {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
        {
            try {
                PlayerRunnableSpell(player);
            } catch (InvocationTargetException | IllegalAccessException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "!ERROR WHILE TRYING TO ACTIVATE SPELL/ABILITY!");
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Event handler for registered click abilities
     * @param event PlayerInteractEvent
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @EventHandler
    public void castSpellClick(PlayerInteractEvent event) throws InvocationTargetException, IllegalAccessException {

        if (event.getAction().isRightClick())
        {
            runClickAbility(rightClickAbility, AbilityCastType.RIGHT_CLICK, event);
            //PlayerClickItemSpell(AbilityCastType.RIGHT_CLICK, event);
        }

        if (event.getAction().isLeftClick())
        {
            runClickAbility(leftClickAbility, AbilityCastType.LEFT_CLICK, event);
            //PlayerClickItemSpell(AbilityCastType.LEFT_CLICK, event);
        }
    }

    /**
     * Event handler for registered drop abilities
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
            //PlayerDropItemSpell(event);
            dropItemAbility(event);
        }
    }

    @Deprecated
    private void PlayerDropItemSpell(PlayerDropItemEvent event) throws InvocationTargetException, IllegalAccessException {

        List<AbilityListener> listeners = plugin.getAbilityListeners();
        for (AbilityListener listener : listeners)
        {
            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (method.isAnnotationPresent(AbilityHandler.class))
                {
                    AbilityHandler annotation = method.getAnnotation(AbilityHandler.class);
                    if (annotation.abilityCastType() == AbilityCastType.DROP_ITEM)
                    {
                        method.invoke(listener, event);
                    }
                }
            }
        }
    }

    private void dropItemAbility(PlayerDropItemEvent event) throws InvocationTargetException, IllegalAccessException
    {
        for (AbilityListener listener : dropItemAbility.keySet())
        {
            Method method = dropItemAbility.get(listener);
            method.invoke(listener, event);
        }
        /*
        List<AbilityListener> listeners = plugin.getAbilityListeners();
        if (dropItemAbility.isEmpty())
        {
            for (AbilityListener listener : listeners)
            {
                for (Method method : listener.getClass().getDeclaredMethods())
                {
                    if (method.isAnnotationPresent(AbilityHandler.class))
                    {
                        AbilityHandler annotation = method.getAnnotation(AbilityHandler.class);
                        if (annotation.abilityCastType() == AbilityCastType.DROP_ITEM)
                        {
                            dropItemAbility.put(listener, method);
                            method.invoke(listener, event);
                        }
                    }
                }
            }
        } else
        {
            for (AbilityListener listener : dropItemAbility.keySet())
            {
                Method method = dropItemAbility.get(listener);
                method.invoke(listener, event);
            }
        }

         */

    }

    @Deprecated
    private void PlayerClickItemSpell(AbilityCastType abilityCastType, PlayerInteractEvent event) throws InvocationTargetException, IllegalAccessException {
        List<AbilityListener> listeners = plugin.getAbilityListeners();
        for (AbilityListener listener : listeners)
        {
            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (method.isAnnotationPresent(AbilityHandler.class))
                {
                    AbilityHandler annotation = method.getAnnotation(AbilityHandler.class);
                    if (annotation.abilityCastType() == abilityCastType)
                    {
                        method.invoke(listener, event);
                    }
                }
            }
        }
    }

    @Deprecated
    private void PlayerRunnableSpell(Player player) throws InvocationTargetException, IllegalAccessException {
        /*
        List<AbilityListener> listeners = plugin.getAbilityListeners();
        for (AbilityListener listener : listeners)
        {
            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (method.isAnnotationPresent(AbilityHandler.class))
                {
                    AbilityHandler annotation = method.getAnnotation(AbilityHandler.class);
                    {
                        if (annotation.abilityCastType() == AbilityCastType.RUNNABLE)
                        {
                            method.invoke(listener, player, timer);
                        }
                    }
                }
            }
        }

         */
        for (AbilityListener listener : runnableAbility.keySet())
        {
            Method method = runnableAbility.get(listener);
            method.invoke(listener, player);
        }
    }

    private void runClickAbility(HashMap<AbilityListener, Method> abilities, AbilityCastType abilityCastType, PlayerInteractEvent event) throws InvocationTargetException, IllegalAccessException
    {
        switch (abilityCastType)
        {
            case RIGHT_CLICK -> {
                for (AbilityListener listener : rightClickAbility.keySet())
                {
                    Method method = rightClickAbility.get(listener);
                    method.invoke(listener, event);
                }
            }
            case LEFT_CLICK -> {
                for (AbilityListener listener : leftClickAbility.keySet())
                {
                    Method method = leftClickAbility.get(listener);
                    method.invoke(listener, event);
                }
            }
        }

        /*
        List<AbilityListener> listeners = plugin.getAbilityListeners();
        if (abilities.isEmpty())
        {
            for (AbilityListener listener : listeners)
            {
                for (Method method : listener.getClass().getDeclaredMethods())
                {
                    if (method.isAnnotationPresent(AbilityHandler.class))
                    {
                        AbilityHandler annotation = method.getAnnotation(AbilityHandler.class);
                        if (annotation.abilityCastType() == abilityCastType)
                        {
                            abilities.put(listener, method);
                            method.invoke(listener, event);
                        }
                    }
                }
            }
        } else
        {
            for (AbilityListener listener : abilities.keySet())
            {
                Method method = abilities.get(listener);
                method.invoke(listener, event);
            }
        }

         */
    }
}

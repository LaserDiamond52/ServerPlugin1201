package net.laserdiamond.ventureplugin.events.abilities;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AbilityListeners extends BukkitRunnable implements Listener {

    private final VenturePlugin plugin;
    private final HashMap<AbilityListener, Method> rightClickAbility, leftClickAbility, dropItemAbility, runnableAbility, attackAbility, onKillAbility, toggleSneakAbility;

    public AbilityListeners(VenturePlugin plugin)
    {
        this.plugin = plugin;
        rightClickAbility = plugin.getAbilities().rightClickAbilities();
        leftClickAbility = plugin.getAbilities().leftClickAbilities();
        runnableAbility = plugin.getAbilities().runnableAbilities();
        dropItemAbility = plugin.getAbilities().dropItemAbilities();
        attackAbility = plugin.getAbilities().attackAbility();
        onKillAbility = plugin.getAbilities().onKillAbility();
        toggleSneakAbility = plugin.getAbilities().toggleSneakAbility();
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
            runClickAbility(AbilityCastType.RIGHT_CLICK, event);
            //PlayerClickItemSpell(AbilityCastType.RIGHT_CLICK, event);
        }

        if (event.getAction().isLeftClick())
        {
            runClickAbility(AbilityCastType.LEFT_CLICK, event);
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

    @EventHandler
    public void onAttackAbility(EntityDamageByEntityEvent event) throws InvocationTargetException, IllegalAccessException {
        if (event.getDamager() instanceof Player player)
        {
            if (event.getEntity() instanceof LivingEntity livingEntity)
            {
                attackAbility(player, event.getFinalDamage(), livingEntity);
            }
        }
    }

    @EventHandler
    public void castAbilityKill(EntityDeathEvent event) throws InvocationTargetException, IllegalAccessException {
        if (event.getEntity().getKiller() != null)
        {
            Player player = event.getEntity().getKiller();
            killAbility(player, event.getEntity());
        }
    }

    @EventHandler
    public void onToggleSneakAbility(PlayerToggleSneakEvent event) throws InvocationTargetException, IllegalAccessException
    {
        toggleSneakAbility(event);
    }

    private void dropItemAbility(PlayerDropItemEvent event) throws InvocationTargetException, IllegalAccessException
    {
        for (AbilityListener listener : dropItemAbility.keySet())
        {
            Method method = dropItemAbility.get(listener);
            method.invoke(listener, event);
        }

    }

    private void PlayerRunnableSpell(Player player) throws InvocationTargetException, IllegalAccessException
    {

        for (AbilityListener listener : runnableAbility.keySet())
        {
            Method method = runnableAbility.get(listener);
            method.invoke(listener, player);
        }
    }

    private void runClickAbility(AbilityCastType abilityCastType, PlayerInteractEvent event) throws InvocationTargetException, IllegalAccessException
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
    }

    private void attackAbility(Player player, double damage, LivingEntity hitEntity) throws InvocationTargetException, IllegalAccessException
    {
        for (AbilityListener listener : attackAbility.keySet())
        {
            Method method = attackAbility.get(listener);
            method.invoke(listener, player, damage, hitEntity);
        }
    }

    private void killAbility(Player player, LivingEntity killedEntity) throws InvocationTargetException, IllegalAccessException
    {
        for (AbilityListener listener : onKillAbility.keySet())
        {
            Method method = onKillAbility.get(listener);
            method.invoke(listener, player, killedEntity);
        }
    }

    private void toggleSneakAbility(PlayerToggleSneakEvent event) throws InvocationTargetException, IllegalAccessException
    {
        for (AbilityListener listener : toggleSneakAbility.keySet())
        {
            Method method = toggleSneakAbility.get(listener);
            method.invoke(listener, event);
        }
    }
}

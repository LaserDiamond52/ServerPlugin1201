package net.laserdiamond.ventureplugin.events.abilities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public abstract class AbilityCasting {

    public interface RightClickAbility extends AbilityListener
    {
        void onRightClickCast(PlayerInteractEvent event);
    }

    public interface LeftClickAbility extends AbilityListener
    {
        void onLeftClickCast(PlayerInteractEvent event);
    }

    public interface DropItemAbility extends AbilityListener
    {
        void onDropItemCast(PlayerDropItemEvent event);
    }

    public interface RunnableAbility extends AbilityListener
    {
        void onActivate(Player player);
    }

    public interface attackAbility extends AbilityListener
    {
        void onAttack(EntityDamageByEntityEvent event);
    }

    public interface onKillAbility extends AbilityListener
    {
        void onKill(Player player, LivingEntity killedEntity);
    }

    public interface toggleSneakAbility extends AbilityListener
    {
        void onToggle(PlayerToggleSneakEvent event);
    }
}

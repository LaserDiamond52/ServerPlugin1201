package net.laserdiamond.ventureplugin.events.abilities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
        void onAttack(Player player, double damage, LivingEntity hitEntity);
    }

    public interface OnDeathAbility extends AbilityListener
    {
        void onKill(Player player, LivingEntity killedEntity);
    }
}

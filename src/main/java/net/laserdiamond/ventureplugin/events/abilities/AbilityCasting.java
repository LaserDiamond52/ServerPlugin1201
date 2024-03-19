package net.laserdiamond.ventureplugin.events.abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class AbilityCasting {

    public interface RightClickSpell extends AbilityListener
    {
        void onRightClickCast(PlayerInteractEvent event);
    }

    public interface LeftClickSpell extends AbilityListener
    {
        void onLeftClickCast(PlayerInteractEvent event);
    }

    public interface DropItemSpell extends AbilityListener
    {
        void onDropItemCast(PlayerDropItemEvent event);
    }

    public interface RunnableSpell extends AbilityListener
    {
        void onActivate(Player player, int timer);
    }
}

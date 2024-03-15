package net.laserdiamond.serverplugin1201.events.SpellCasting;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpellCasting {

    public interface RightClickSpell
    {
        void onRightClickCast(PlayerInteractEvent event);
    }

    public interface LeftClickSpell
    {
        void onLeftClickCast(PlayerInteractEvent event);
    }

    public interface DropItemSpell
    {
        void onDropItemCast(PlayerDropItemEvent event);
    }

    public interface RunnableSpell
    {
        void onActivate(Player player, int timer);
    }
}

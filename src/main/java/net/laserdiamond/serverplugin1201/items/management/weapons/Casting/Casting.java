package net.laserdiamond.serverplugin1201.items.management.weapons.Casting;

import org.bukkit.entity.Player;

public class Casting {

    public interface RightClick
    {
        void onRightClickCast(Player player);
    }

    public interface RightClickEnchant
    {
        void onRightClickEnchantCast(Player player);
    }

    public interface LeftClick
    {
        void onLeftClickCast(Player player);
    }

    public interface LeftClickEnchant
    {
        void onLeftClickEnchantCast(Player player);
    }

    public interface DropItem
    {
        void onDropItemCast(Player player);
    }

    public interface DropItemEnchant
    {
        void onDropItemEnchantCast(Player player);
    }
}

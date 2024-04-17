package net.laserdiamond.ventureplugin.events.InventoryGUI;

import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryClicker extends Listener {

    void clickInsideInv(InventoryClickEvent event);
}

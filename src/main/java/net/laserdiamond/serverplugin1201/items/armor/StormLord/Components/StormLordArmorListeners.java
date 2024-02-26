package net.laserdiamond.serverplugin1201.items.armor.StormLord.Components;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerAttackEntityCooldownResetEvent;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Config.StormLordArmorConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class StormLordArmorListeners implements Listener {

    private ServerPlugin1201 plugin;
    private StormLordArmorConfig armorConfig;

    public StormLordArmorListeners(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        armorConfig = plugin.getStormLordArmorConfig();
    }

    @EventHandler
    public void eyeOfTheStorm(PlayerToggleSneakEvent event) {

    }

    @EventHandler
    public void conduitPower(PlayerArmorChangeEvent event) {

    }

}

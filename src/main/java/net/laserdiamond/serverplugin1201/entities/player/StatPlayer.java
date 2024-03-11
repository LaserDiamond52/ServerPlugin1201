package net.laserdiamond.serverplugin1201.entities.player;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Manager.ArmorTrimStats;
import net.laserdiamond.serverplugin1201.stats.Components.*;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.entity.Player;

public class StatPlayer {

    private final Player player;
    private final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private final StatProfileManager statProfileManager = plugin.getStatProfileManager();

    public StatPlayer(Player player) {
        this.player = player;
    }

    public StatProfile getStatProfile() {
        return statProfileManager.getStatProfile(player.getUniqueId());
    }

    public Stats getStats() {
        return getStatProfile().stats();
    }

    public DamageStats getDamageStats() {
        return getStatProfile().damageStats();
    }

    public DefenseStats getDefenseStats() {
        return getStatProfile().defenseStats();
    }

    public LootStats getLootStats() {
        return getStatProfile().lootStats();
    }

    public EnchantStats getEnchantStats() {
        return getStatProfile().enchantStats();
    }

    public ArmorTrimStats getArmorTrimStats() {
        return getStatProfile().armorTrimStats();
    }


}

package net.laserdiamond.ventureplugin.entities.player;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantStats;
import net.laserdiamond.ventureplugin.items.armor.trims.Manager.ArmorTrimStats;
import net.laserdiamond.ventureplugin.stats.Components.*;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import net.laserdiamond.ventureplugin.tuning.Components.TuningPoints;
import net.laserdiamond.ventureplugin.tuning.Components.TuningStats;
import org.bukkit.entity.Player;

public class StatPlayer {

    private final Player player;
    private final VenturePlugin plugin = VenturePlugin.getInstance();
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

    public ArmorStats getArmorStats()
    {
        return getStatProfile().armorStats();
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

    public TuningPoints getTuningPointStats()
    {
        return getStatProfile().tuningProfile().tuningPoints();
    }

    public TuningStats getTuningStats()
    {
        return getStatProfile().tuningProfile().tuningStats();
    }


}

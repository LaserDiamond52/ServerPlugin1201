package net.laserdiamond.serverplugin1201.stats.Manager;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimMaterialStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Components.ArmorTrimPatternStats;
import net.laserdiamond.serverplugin1201.items.armor.Trims.Manager.ArmorTrimStats;
import net.laserdiamond.serverplugin1201.stats.Components.StatProfile;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Config.BaseStatsConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatProfileManager {

    private ServerPlugin1201 plugin;
    private BaseStatsConfig baseStatsConfig;
    private Map<UUID, StatProfile> statProfiles = new HashMap<>();

    public StatProfileManager(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        baseStatsConfig = plugin.getBaseStatsConfig();
    }

    public StatProfile createNewStatProfile(Player player) {

        Double meleeDamage = baseStatsConfig.getDouble("meleeDamage");
        Double magicDamage = baseStatsConfig.getDouble("magicDamage");
        Double rangeDamage = baseStatsConfig.getDouble("rangeDamage");
        Double availableMana = baseStatsConfig.getDouble("availableMana");
        Double maxMana = baseStatsConfig.getDouble("maxMana");
        Double baseMelee = baseStatsConfig.getDouble("baseMelee");
        Double baseMagic = baseStatsConfig.getDouble("baseMagic");
        Double baseRange = baseStatsConfig.getDouble("baseRange");
        Double baseDefense = baseStatsConfig.getDouble("baseDefense");
        Double baseFireDefense = baseStatsConfig.getDouble("baseFireDefense");
        Double baseExplosionDefense = baseStatsConfig.getDouble("baseExplosionDefense");
        Double baseProjectileDefense = baseStatsConfig.getDouble("baseProjectileDefense");
        Double baseMagicDefense = baseStatsConfig.getDouble("baseMagicDefense");
        Double baseToughness = baseStatsConfig.getDouble("baseToughness");

        Stats stats = new Stats(0,meleeDamage,magicDamage, rangeDamage, availableMana, maxMana, baseMelee, baseMagic, baseRange, baseDefense, baseFireDefense, baseExplosionDefense, baseProjectileDefense, baseMagicDefense,baseToughness,0);
        EnchantStats enchantStats = new EnchantStats(0,0,0,0,0,0,0,0,0,0,0);

        ArmorTrimMaterialStats armorTrimMaterialStats = new ArmorTrimMaterialStats(0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimPatternStats armorTrimPatternStats = new ArmorTrimPatternStats(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        ArmorTrimStats armorTrimStats = new ArmorTrimStats(armorTrimMaterialStats, armorTrimPatternStats);

        StatProfile statProfile = new StatProfile(stats, enchantStats, armorTrimStats);
        statProfiles.put(player.getUniqueId(), statProfile);

        AttributeInstance playerHealthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        playerHealthInstance.setBaseValue(20);

        return statProfile;
    }

    public StatProfile getStatProfile(UUID uuid) {
        return statProfiles.get(uuid);
    }

}

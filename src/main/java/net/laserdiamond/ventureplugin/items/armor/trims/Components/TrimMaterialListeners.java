package net.laserdiamond.ventureplugin.items.armor.trims.Components;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.stats.Config.BaseStatsConfig;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TrimMaterialListeners implements Listener {

    private final VenturePlugin plugin;
    private final PlayerConfig baseStatsConfig;
    private final StatProfileManager statProfileManager;
    private final ArmorConfig armorTrimConfig;
    private final double defaultPlayerSpeed;
    private final int defaultPlayerStarvationRate;

    private final double[] materialStatArray = new double[11];

    public TrimMaterialListeners(VenturePlugin plugin)
    {
        this.plugin = plugin;
        baseStatsConfig = plugin.getBaseStatsConfig();
        statProfileManager = plugin.getStatProfileManager();
        armorTrimConfig = plugin.getArmorTrimConfig();

        defaultPlayerSpeed = baseStatsConfig.getDouble("baseSpeed");
        defaultPlayerStarvationRate = baseStatsConfig.getInt("playerDefaultStarvationRate");

        materialStatArray[0] = defaultPlayerSpeed * (armorTrimConfig.getDouble("copperSpeed") * 0.01);
        materialStatArray[1] = defaultPlayerStarvationRate * (armorTrimConfig.getInt("goldSaturation") * 0.01);
        materialStatArray[2] = armorTrimConfig.getDouble("ironHealthBoost");
        materialStatArray[3] = armorTrimConfig.getInt("lapisExpBonus");
        materialStatArray[4] = armorTrimConfig.getDouble("quartzMiningExp");
        materialStatArray[5] = armorTrimConfig.getInt("redstonePotionBonus");
        materialStatArray[6] = armorTrimConfig.getDouble("emeraldLuck");
        materialStatArray[7] = armorTrimConfig.getDouble("amethystDamage");
        materialStatArray[8] = armorTrimConfig.getDouble("diamondMana");
        materialStatArray[9] = armorTrimConfig.getDouble("netheriteDefense");
        materialStatArray[10] = armorTrimConfig.getDouble("netheriteFireDefense");
    }

    @EventHandler
    public void armorChange(PlayerArmorChangeEvent event)
    {
        Player player = event.getPlayer();

        ItemStack newItem = event.getNewItem(), oldItem = event.getOldItem();
        ItemMeta newMeta = newItem.getItemMeta(), oldMeta = oldItem.getItemMeta();

        Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).stats();
        DamageStats damageStats = statProfileManager.getStatProfile(player.getUniqueId()).damageStats();
        DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
        ArmorTrimMaterialStats trimMaterialStats = statProfileManager.getStatProfile(player.getUniqueId()).armorTrimStats().armorTrimMaterialStats();

        if (newMeta != null)
        {
            if (newMeta instanceof ArmorMeta armorMeta)
            {
                addStats(player, armorMeta, stats, trimMaterialStats);
            }
        }

        if (oldMeta != null) {
            if (oldMeta instanceof ArmorMeta armorMeta)
            {
                removeStats(player, armorMeta, stats, trimMaterialStats);
            }
        }
    }

    private void addStats(Player player, ArmorMeta armorMeta, Stats stats, ArmorTrimMaterialStats trimMaterialStats)
    {
        AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double playerHealth = healthInstance.getBaseValue();

        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        double playerSpeed = speedInstance.getBaseValue();

        AttributeInstance luckInstance = player.getAttribute(Attribute.GENERIC_LUCK);
        double playerLuck = luckInstance.getBaseValue();

        ArmorTrim armorTrim = armorMeta.getTrim();
        if (armorTrim != null)
        {
            TrimMaterial trimMaterial = armorTrim.getMaterial();

            if (trimMaterial.equals(TrimMaterial.COPPER))
            {
                //speedInstance.setBaseValue(playerSpeed + materialStatArray[0]);
                trimMaterialStats.setCopperSpeed(trimMaterialStats.getCopperSpeed() + armorTrimConfig.getDouble("copperSpeed"));
                //stats.setSpeed(stats.getSpeed() + armorTrimConfig.getDouble("copperSpeed"));
                stats.setSpeed(stats.getSpeed() + armorTrimConfig.getDouble("copperSpeed"));
            }
            if (trimMaterial.equals(TrimMaterial.GOLD))
            {
                trimMaterialStats.setGoldSaturationChance(trimMaterialStats.getGoldSaturationChance() + armorTrimConfig.getDouble("goldSaturation"));

                // Set player's saturation rate here:
                stats.setStarvationRate((int) (stats.getStarvationRate() + armorTrimConfig.getDouble("goldSaturation")));
            }
            if (trimMaterial.equals(TrimMaterial.IRON))
            {
                healthInstance.setBaseValue(playerHealth + materialStatArray[2]);
                trimMaterialStats.setIronHealthBoost(trimMaterialStats.getIronHealthBoost() + materialStatArray[2]);

            }
            if (trimMaterial.equals(TrimMaterial.LAPIS))
            {
                trimMaterialStats.setLapisBonusExp(trimMaterialStats.getLapisBonusExp() + materialStatArray[3]);

            }
            if (trimMaterial.equals(TrimMaterial.QUARTZ))
            {
                trimMaterialStats.setQuartzBonusMiningExp(trimMaterialStats.getQuartzBonusMiningExp() + materialStatArray[4]);

            }
            if (trimMaterial.equals(TrimMaterial.REDSTONE))
            {
                trimMaterialStats.setRedstoneBonusPotion(trimMaterialStats.getRedstoneBonusPotion() + materialStatArray[5]);

            }
            if (trimMaterial.equals(TrimMaterial.EMERALD))
            {
                luckInstance.setBaseValue(playerLuck + materialStatArray[6]);
                trimMaterialStats.setEmeraldBonusLuck(trimMaterialStats.getEmeraldBonusLuck() + materialStatArray[6]);

            }
            if (trimMaterial.equals(TrimMaterial.AMETHYST))
            {
                trimMaterialStats.setAmethystBonusDamage(trimMaterialStats.getAmethystBonusDamage() + materialStatArray[7]);
                stats.setMeleeDamage(stats.getMeleeDamage() + materialStatArray[7]);
                stats.setRangeDamage(stats.getRangeDamage() + materialStatArray[7]);
                stats.setMagicDamage(stats.getMagicDamage() + materialStatArray[7]);

            }
            if (trimMaterial.equals(TrimMaterial.DIAMOND))
            {
                trimMaterialStats.setDiamondBonusMana(trimMaterialStats.getDiamondBonusMana() + materialStatArray[8]);
                stats.setMaxMana(stats.getMaxMana() + materialStatArray[8]);

            }
            if (trimMaterial.equals(TrimMaterial.NETHERITE))
            {
                trimMaterialStats.setNetheriteBonusDefense(trimMaterialStats.getNetheriteBonusDefense() + materialStatArray[9]);
                trimMaterialStats.setNetheriteBonusFireDefense(trimMaterialStats.getNetheriteBonusFireDefense() + materialStatArray[10]);
                stats.setDefense(stats.getDefense() + materialStatArray[9]);
                stats.setFireDefense(stats.getFireDefense() + materialStatArray[10]);
            }

        }
    }

    private void removeStats(Player player, ArmorMeta armorMeta, Stats stats, ArmorTrimMaterialStats trimMaterialStats)
    {
        AttributeInstance healthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double playerHealth = healthInstance.getBaseValue();

        AttributeInstance speedInstance = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        double playerSpeed = speedInstance.getBaseValue();

        AttributeInstance luckInstance = player.getAttribute(Attribute.GENERIC_LUCK);
        double playerLuck = luckInstance.getBaseValue();

        ArmorTrim armorTrim = armorMeta.getTrim();
        if (armorTrim != null)
        {
            TrimMaterial trimMaterial = armorTrim.getMaterial();

            if (trimMaterial.equals(TrimMaterial.COPPER))
            {
                //speedInstance.setBaseValue(playerSpeed - materialStatArray[0]);
                trimMaterialStats.setCopperSpeed(trimMaterialStats.getCopperSpeed() - armorTrimConfig.getDouble("copperSpeed"));
                //stats.setSpeed(stats.getSpeed() - armorTrimConfig.getDouble("copperSpeed"));
                stats.setSpeed(stats.getSpeed() - armorTrimConfig.getDouble("copperSpeed"));
            }
            if (trimMaterial.equals(TrimMaterial.GOLD))
            {
                trimMaterialStats.setGoldSaturationChance(trimMaterialStats.getGoldSaturationChance() - armorTrimConfig.getDouble("goldSaturation"));

                // Set player's saturation rate here:
                stats.setStarvationRate((int) (stats.getStarvationRate() - armorTrimConfig.getDouble("goldSaturation")));
            }
            if (trimMaterial.equals(TrimMaterial.IRON))
            {
                healthInstance.setBaseValue(playerHealth - materialStatArray[2]);
                trimMaterialStats.setIronHealthBoost(trimMaterialStats.getIronHealthBoost() - materialStatArray[2]);

            }
            if (trimMaterial.equals(TrimMaterial.LAPIS))
            {
                trimMaterialStats.setLapisBonusExp(trimMaterialStats.getLapisBonusExp() - materialStatArray[3]);

            }
            if (trimMaterial.equals(TrimMaterial.QUARTZ))
            {
                trimMaterialStats.setQuartzBonusMiningExp(trimMaterialStats.getQuartzBonusMiningExp() - materialStatArray[4]);

            }
            if (trimMaterial.equals(TrimMaterial.REDSTONE))
            {
                trimMaterialStats.setRedstoneBonusPotion(trimMaterialStats.getRedstoneBonusPotion() - materialStatArray[5]);

            }
            if (trimMaterial.equals(TrimMaterial.EMERALD))
            {
                luckInstance.setBaseValue(playerLuck + materialStatArray[6]);
                trimMaterialStats.setEmeraldBonusLuck(trimMaterialStats.getEmeraldBonusLuck() - materialStatArray[6]);

            }
            if (trimMaterial.equals(TrimMaterial.AMETHYST))
            {
                trimMaterialStats.setAmethystBonusDamage(trimMaterialStats.getAmethystBonusDamage() - materialStatArray[7]);
                stats.setMeleeDamage(stats.getMeleeDamage() - materialStatArray[7]);
                stats.setRangeDamage(stats.getRangeDamage() - materialStatArray[7]);
                stats.setMagicDamage(stats.getMagicDamage() - materialStatArray[7]);

            }
            if (trimMaterial.equals(TrimMaterial.DIAMOND))
            {
                trimMaterialStats.setDiamondBonusMana(trimMaterialStats.getDiamondBonusMana() - materialStatArray[8]);
                stats.setMaxMana(stats.getMaxMana() - materialStatArray[8]);

            }
            if (trimMaterial.equals(TrimMaterial.NETHERITE))
            {
                trimMaterialStats.setNetheriteBonusDefense(trimMaterialStats.getNetheriteBonusDefense() - materialStatArray[9]);
                trimMaterialStats.setNetheriteBonusFireDefense(trimMaterialStats.getNetheriteBonusFireDefense() - materialStatArray[10]);
                stats.setDefense(stats.getDefense() - materialStatArray[9]);
                stats.setFireDefense(stats.getFireDefense() - materialStatArray[10]);
            }

        }
    }

    @EventHandler
    public void lapisBonusExp(PlayerPickupExperienceEvent event)
    {
        Player player = event.getPlayer();

        ExperienceOrb expOrb = event.getExperienceOrb();
        int expAmount = expOrb.getExperience();

        ArmorTrimMaterialStats trimMaterialStats = statProfileManager.getStatProfile(player.getUniqueId()).armorTrimStats().armorTrimMaterialStats();
        double lapisBonusExpPlayer = 1 + (trimMaterialStats.getLapisBonusExp() * 0.01);

        expOrb.setExperience((int) (expAmount * lapisBonusExpPlayer));
    }

    @EventHandler
    public void redstonePotion(EntityPotionEffectEvent event)
    {
        if (event.getEntity() instanceof Player player)
        {
            ArmorTrimMaterialStats trimMaterialStats = statProfileManager.getStatProfile(player.getUniqueId()).armorTrimStats().armorTrimMaterialStats();
            double redstonePotionBonusPlayer = 1 + (trimMaterialStats.getRedstoneBonusPotion() * 0.01);

            PotionEffect newEffect = event.getNewEffect();
            if (newEffect != null) {
                int duration = newEffect.getDuration();
                int amplifier = newEffect.getAmplifier();

                if (event.getCause() == EntityPotionEffectEvent.Cause.POTION_SPLASH || event.getCause() == EntityPotionEffectEvent.Cause.POTION_DRINK)
                {
                    event.setCancelled(true);
                    PotionEffectType newEffectType = event.getModifiedType();
                    player.addPotionEffect(newEffectType.createEffect((int) (duration * redstonePotionBonusPlayer), amplifier));
                }
            }
        }
    }
}

package net.laserdiamond.ventureplugin.items.armor.trims.Components;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import net.laserdiamond.ventureplugin.util.Config.PlayerConfig;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
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

    private final StatProfileManager statProfileManager;

    private final double[] materialStatArray = new double[11];

    public TrimMaterialListeners(VenturePlugin plugin)
    {
        statProfileManager = plugin.getStatProfileManager();
        ArmorConfig armorTrimConfig = plugin.getArmorTrimConfig();

        materialStatArray[0] = armorTrimConfig.getDouble("copperSpeed");
        materialStatArray[1] = armorTrimConfig.getInt("goldSaturation");
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

        if (newMeta != null)
        {
            if (newMeta instanceof ArmorMeta armorMeta)
            {
                addStats(player, armorMeta);
            }
        }

        if (oldMeta != null) {
            if (oldMeta instanceof ArmorMeta armorMeta)
            {
                removeStats(player, armorMeta);
            }
        }
    }

    private void addStats(Player player, ArmorMeta armorMeta)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        DamageStats damageStats = statPlayer.getDamageStats();
        DefenseStats defenseStats = statPlayer.getDefenseStats();
        ArmorTrimMaterialStats trimMaterialStats = statPlayer.getArmorTrimStats().armorTrimMaterialStats();

        ArmorTrim armorTrim = armorMeta.getTrim();
        if (armorTrim != null)
        {
            TrimMaterial trimMaterial = armorTrim.getMaterial();

            if (trimMaterial.equals(TrimMaterial.COPPER))
            {
                stats.setSpeed(stats.getSpeed() + materialStatArray[0]);
                trimMaterialStats.setCopperSpeed(trimMaterialStats.getCopperSpeed() + materialStatArray[0]);
            }
            if (trimMaterial.equals(TrimMaterial.GOLD))
            {
                stats.setStarvationRate((int) (stats.getStarvationRate() + materialStatArray[1]));
                trimMaterialStats.setGoldSaturationChance(trimMaterialStats.getGoldSaturationChance() + materialStatArray[1]);
            }
            if (trimMaterial.equals(TrimMaterial.IRON))
            {
                stats.setHealth(stats.getHealth() + materialStatArray[2]);
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
                stats.setLuck(stats.getLuck() + materialStatArray[6]);
                trimMaterialStats.setEmeraldBonusLuck(trimMaterialStats.getEmeraldBonusLuck() + materialStatArray[6]);
            }
            if (trimMaterial.equals(TrimMaterial.AMETHYST))
            {
                trimMaterialStats.setAmethystBonusDamage(trimMaterialStats.getAmethystBonusDamage() + materialStatArray[7]);

                damageStats.setPercentMeleeDmg(damageStats.getPercentMelee() + materialStatArray[7]);
                damageStats.setPercentMagicDmg(damageStats.getPercentMagic() + materialStatArray[7]);
                damageStats.setPercentRangeDmg(damageStats.getPercentRange() + materialStatArray[7]);

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

                defenseStats.setDefense(defenseStats.getDefense() + materialStatArray[9]);
                defenseStats.setFireDefense(defenseStats.getFireDefense() + materialStatArray[10]);
            }
        }
    }

    private void removeStats(Player player, ArmorMeta armorMeta)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        DamageStats damageStats = statPlayer.getDamageStats();
        DefenseStats defenseStats = statPlayer.getDefenseStats();
        ArmorTrimMaterialStats trimMaterialStats = statPlayer.getArmorTrimStats().armorTrimMaterialStats();

        ArmorTrim armorTrim = armorMeta.getTrim();
        if (armorTrim != null)
        {
            TrimMaterial trimMaterial = armorTrim.getMaterial();

            if (trimMaterial.equals(TrimMaterial.COPPER))
            {
                stats.setSpeed(stats.getSpeed() - materialStatArray[0]);
                trimMaterialStats.setCopperSpeed(trimMaterialStats.getCopperSpeed() - materialStatArray[0]);
            }
            if (trimMaterial.equals(TrimMaterial.GOLD))
            {
                stats.setStarvationRate((int) (stats.getStarvationRate() - materialStatArray[1]));
                trimMaterialStats.setGoldSaturationChance(trimMaterialStats.getGoldSaturationChance() - materialStatArray[1]);
            }
            if (trimMaterial.equals(TrimMaterial.IRON))
            {
                stats.setHealth(stats.getHealth() - materialStatArray[2]);
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
                stats.setLuck(stats.getLuck() - materialStatArray[6]);
                trimMaterialStats.setEmeraldBonusLuck(trimMaterialStats.getEmeraldBonusLuck() - materialStatArray[6]);
            }
            if (trimMaterial.equals(TrimMaterial.AMETHYST))
            {
                trimMaterialStats.setAmethystBonusDamage(trimMaterialStats.getAmethystBonusDamage() - materialStatArray[7]);

                damageStats.setPercentMeleeDmg(damageStats.getPercentMelee() - materialStatArray[7]);
                damageStats.setPercentMagicDmg(damageStats.getPercentMagic() - materialStatArray[7]);
                damageStats.setPercentRangeDmg(damageStats.getPercentRange() - materialStatArray[7]);
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

                defenseStats.setDefense(defenseStats.getDefense() - materialStatArray[9]);
                defenseStats.setFireDefense(defenseStats.getFireDefense() - materialStatArray[10]);
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

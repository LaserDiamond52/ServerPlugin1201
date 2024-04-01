package net.laserdiamond.ventureplugin.items.armor.util;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.stats.Components.*;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorEquipStats implements Listener {

    private final VenturePlugin plugin;
    private final StatProfileManager statProfileManager;

    public ArmorEquipStats(VenturePlugin plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
    }

    @EventHandler
    public void ArmorChange(PlayerArmorChangeEvent event) {

        Player player = event.getPlayer();
        StatProfile statProfile = statProfileManager.getStatProfile(player.getUniqueId());

        ItemStack newItem = event.getNewItem(), oldItem = event.getOldItem();
        ItemMeta newMeta = newItem.getItemMeta(), oldMeta = oldItem.getItemMeta();

        if (newMeta != null)
        {
            if (newMeta.hasCustomModelData())
            {
                ItemForger itemForger = new ItemForger(newItem);

                Double health = itemForger.getItemStat(VentureItemStatKeys.ARMOR_HEALTH_KEY);
                Double defense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_DEFENSE_KEY);
                Double fireDefense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_FIRE_DEFENSE_KEY);
                Double projectileDefense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_PROJECTILE_DEFENSE_KEY);
                Double explosionDefense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_EXPLOSION_DEFENSE_KEY);
                Double magicDefense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DEFENSE_KEY);
                Double toughness = itemForger.getItemStat(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY);
                Double fortitude = itemForger.getItemStat(VentureItemStatKeys.ARMOR_FORTITUDE_KEY);
                Double speed = itemForger.getItemStat(VentureItemStatKeys.ARMOR_SPEED_KEY);
                Double mana = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAX_MANA_KEY);
                Double meleeDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY);
                Double magicDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY);
                Double rangeDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY);

                addStats(statProfile, health, defense, fireDefense, projectileDefense, explosionDefense, magicDefense, toughness, speed, mana, meleeDamage, magicDamage, rangeDamage);
                addFortitude(player, fortitude);
            } else
            {
                Material newMaterial = newItem.getType();
                for (VanillaArmorValues values : VanillaArmorValues.values())
                {
                    if (values.getMaterial().equals(newMaterial)) {
                        Double defense = values.getProtectionValue();
                        Double toughness = values.getToughnessValue();

                        addStats(statProfile, 0.0, defense, 0.0, 0.0, 0.0, 0.0, toughness, 0.0, 0.0, 0.0, 0.0,0.0);
                    }
                }
            }
        }
        if (oldMeta != null)
        {
            if (oldMeta.hasCustomModelData())
            {
                ItemForger itemForger = new ItemForger(oldItem);

                Double health = itemForger.getItemStat(VentureItemStatKeys.ARMOR_HEALTH_KEY);
                Double defense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_DEFENSE_KEY);
                Double fireDefense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_FIRE_DEFENSE_KEY);
                Double projectileDefense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_PROJECTILE_DEFENSE_KEY);
                Double explosionDefense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_EXPLOSION_DEFENSE_KEY);
                Double magicDefense = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DEFENSE_KEY);
                Double toughness = itemForger.getItemStat(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY);
                Double fortitude = itemForger.getItemStat(VentureItemStatKeys.ARMOR_FORTITUDE_KEY);
                Double speed =  itemForger.getItemStat(VentureItemStatKeys.ARMOR_SPEED_KEY);
                Double mana = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAX_MANA_KEY);
                Double meleeDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY);
                Double magicDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY);
                Double rangeDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY);

                removeStats(statProfile, health, defense, fireDefense, projectileDefense, explosionDefense, magicDefense, toughness, speed, mana, meleeDamage, magicDamage, rangeDamage);
                removeFortitude(player, fortitude);
            } else
            {
                Material oldMaterial = oldItem.getType();
                for (VanillaArmorValues values : VanillaArmorValues.values())
                {
                    if (values.getMaterial().equals(oldMaterial))
                    {
                        Double defense = values.getProtectionValue();
                        Double toughness = values.getToughnessValue();

                        removeStats(statProfile, 0.0, defense, 0.0, 0.0, 0.0, 0.0, toughness, 0.0, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }

    private void addStats(StatProfile statProfile, Double health, Double defense, Double fireDefense, Double projectileDefense, Double explosionDefense, Double magicDefense, Double toughness, Double speed, Double mana, Double meleeDamage, Double magicDamage, Double rangeDamage) {
        Stats stats = statProfile.stats();
        ArmorStats armorStats = statProfile.armorStats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();

        double currentHealth = stats.getHealth();
        double currentDefense = defenseStats.getDefense();
        double currentFireDefense = defenseStats.getFireDefense();
        double currentProjectileDefense = defenseStats.getProjectileDefense();
        double currentExplosionDefense = defenseStats.getExplosionDefense();
        double currentMagicDefense = defenseStats.getMagicDefense();
        double currentToughness = defenseStats.getToughness();
        double currentSpeed = stats.getSpeed();
        double currentMana = stats.getMaxMana();
        double currentMeleeDamage = damageStats.getPercentMelee();
        double currentMagicDamage = damageStats.getPercentMagic();
        double currentRangeDamage = damageStats.getPercentRange();

        if (health != null) {
            stats.setHealth(currentHealth + health);
            armorStats.setHealth(armorStats.getHealth() + health);
        }
        if (defense != null) {
            defenseStats.setDefense(currentDefense + defense);
            armorStats.setDefense(armorStats.getDefense() + defense);
        }
        if (fireDefense != null)
        {
            defenseStats.setFireDefense(currentFireDefense + fireDefense);
            armorStats.setFireDefense(armorStats.getFireDefense() + fireDefense);
        }
        if (explosionDefense != null)
        {
            defenseStats.setExplosionDefense(currentExplosionDefense + explosionDefense);
            armorStats.setExplosionDefense(armorStats.getExplosionDefense() + explosionDefense);
        }
        if (projectileDefense != null)
        {
            defenseStats.setProjectileDefense(currentProjectileDefense + projectileDefense);
            armorStats.setProjectileDefense(armorStats.getProjectileDefense() + projectileDefense);
        }
        if (magicDefense != null)
        {
            defenseStats.setMagicDefense(currentMagicDefense + magicDefense);
            armorStats.setMagicDefense(armorStats.getMagicDefense() + magicDefense);
        }
        if (toughness != null) {
            defenseStats.setToughness(currentToughness + toughness);
            armorStats.setToughness(armorStats.getToughness() + toughness);
        }
        if (speed != null) {
            stats.setSpeed(currentSpeed + speed);
            armorStats.setSpeed(armorStats.getSpeed() + speed);
        }
        if (mana != null) {
            stats.setMaxMana(currentMana + mana);
            armorStats.setMana(armorStats.getMana() + mana);
        }
        if (meleeDamage != null) {
            damageStats.setpMeleeDmg(currentMeleeDamage + meleeDamage);
            armorStats.setPercentMeleeDamage(armorStats.getPercentMeleeDamage() + meleeDamage);
        }
        if (magicDamage != null) {
            damageStats.setpMagicDmg(currentMagicDamage + magicDamage);
            armorStats.setPercentMagicDamage(armorStats.getPercentMagicDamage() + magicDamage);
        }
        if (rangeDamage != null) {
            damageStats.setpRangeDmg(currentRangeDamage + rangeDamage);
            armorStats.setPercentRangeDamage(armorStats.getPercentRangeDamage() + rangeDamage);
        }

    }

    private void removeStats(StatProfile statProfile, Double health, Double defense, Double fireDefense, Double projectileDefense, Double explosionDefense, Double magicDefense, Double toughness, Double speed, Double mana, Double meleeDamage, Double magicDamage, Double rangeDamage) {
        Stats stats = statProfile.stats();
        ArmorStats armorStats = statProfile.armorStats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();

        double currentHealth = stats.getHealth();
        double currentDefense = defenseStats.getDefense();
        double currentFireDefense = defenseStats.getFireDefense();
        double currentProjectileDefense = defenseStats.getProjectileDefense();
        double currentExplosionDefense = defenseStats.getExplosionDefense();
        double currentMagicDefense = defenseStats.getMagicDefense();
        double currentToughness = defenseStats.getToughness();
        double currentSpeed = stats.getSpeed();
        double currentMana = stats.getMaxMana();
        double currentMeleeDamage = damageStats.getPercentMelee();
        double currentMagicDamage = damageStats.getPercentMagic();
        double currentRangeDamage = damageStats.getBaseRange();

        if (health != null) {
            stats.setHealth(currentHealth - health);
            armorStats.setHealth(armorStats.getHealth() - health);
        }
        if (defense != null) {
            defenseStats.setDefense(currentDefense - defense);
            armorStats.setDefense(armorStats.getDefense() - defense);
        }
        if (fireDefense != null)
        {
            defenseStats.setFireDefense(currentFireDefense - fireDefense);
            armorStats.setFireDefense(armorStats.getFireDefense() - fireDefense);
        }
        if (explosionDefense != null)
        {
            defenseStats.setExplosionDefense(currentExplosionDefense - explosionDefense);
            armorStats.setExplosionDefense(armorStats.getExplosionDefense() - explosionDefense);
        }
        if (projectileDefense != null)
        {
            defenseStats.setProjectileDefense(currentProjectileDefense - projectileDefense);
            armorStats.setProjectileDefense(armorStats.getProjectileDefense() - projectileDefense);
        }
        if (magicDefense != null)
        {
            defenseStats.setMagicDefense(currentMagicDefense - magicDefense);
            armorStats.setMagicDefense(armorStats.getMagicDefense() - magicDefense);
        }
        if (toughness != null) {
            defenseStats.setToughness(currentToughness - toughness);
            armorStats.setToughness(armorStats.getToughness() - toughness);
        }
        if (speed != null) {
            stats.setSpeed(currentSpeed - speed);
            armorStats.setSpeed(armorStats.getSpeed() - speed);
        }
        if (mana != null) {
            stats.setMaxMana(currentMana - mana);
            armorStats.setMana(armorStats.getMana() - mana);
        }
        if (meleeDamage != null) {
            damageStats.setpMeleeDmg(currentMeleeDamage - meleeDamage);
            armorStats.setPercentMeleeDamage(armorStats.getPercentMeleeDamage() - meleeDamage);
        }
        if (magicDamage != null) {
            damageStats.setpMagicDmg(currentMagicDamage - magicDamage);
            armorStats.setPercentMagicDamage(armorStats.getPercentMagicDamage() - magicDamage);
        }
        if (rangeDamage != null) {
            damageStats.setpRangeDmg(currentRangeDamage - rangeDamage);
            armorStats.setPercentRangeDamage(armorStats.getPercentRangeDamage() - rangeDamage);
        }
    }

    private void addFortitude(Player player, Double fortitude)
    {
        ArmorStats armorStats = statProfileManager.getStatProfile(player.getUniqueId()).armorStats();
        DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
        Double currentFortitude = defenseStats.getFortitude(player);

        if (fortitude != null)
        {
            defenseStats.setFortitude(player, currentFortitude + fortitude);
            armorStats.setFortitude(armorStats.getFortitude() + fortitude);
        }
    }

    private void removeFortitude(Player player, Double fortitude)
    {
        ArmorStats armorStats = statProfileManager.getStatProfile(player.getUniqueId()).armorStats();
        DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
        Double currentFortitude = defenseStats.getFortitude(player);

        if (fortitude != null)
        {
            defenseStats.setFortitude(player, currentFortitude - fortitude);
            armorStats.setFortitude(armorStats.getFortitude() - fortitude);
        }
    }

    public static boolean isWearingFullSet(Player player, ArmorCMD armorCMD)
    {
        ItemStack helmet = player.getInventory().getHelmet(), chestplate = player.getInventory().getChestplate(), leggings = player.getInventory().getLeggings(), boots = player.getInventory().getBoots();

        if (helmet != null && chestplate != null && leggings != null && boots != null)
        {
            ItemMeta helmetMeta = helmet.getItemMeta(), chestplateMeta = chestplate.getItemMeta(), leggingsMeta = leggings.getItemMeta(), bootsMeta = boots.getItemMeta();
            if (helmetMeta != null && chestplateMeta != null && leggingsMeta != null && bootsMeta != null)
            {
                if (helmetMeta.hasCustomModelData() && chestplateMeta.hasCustomModelData() && leggingsMeta.hasCustomModelData() && bootsMeta.hasCustomModelData())
                {
                    if (helmetMeta.getCustomModelData() == armorCMD.getHelmet() && chestplateMeta.getCustomModelData() == armorCMD.getChestplate() && leggingsMeta.getCustomModelData() == armorCMD.getLeggings() && bootsMeta.getCustomModelData() == armorCMD.getBoots())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

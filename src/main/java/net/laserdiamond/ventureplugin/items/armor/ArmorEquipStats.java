package net.laserdiamond.ventureplugin.items.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.stats.Components.*;
import net.laserdiamond.ventureplugin.items.util.VentureItemStatKeys;
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
    private void ArmorChange(PlayerArmorChangeEvent event) {

        Player player = event.getPlayer();
        StatProfile statProfile = statProfileManager.getStatProfile(player.getUniqueId());

        ItemStack newItem = event.getNewItem(), oldItem = event.getOldItem();
        ItemMeta newMeta = newItem.getItemMeta(), oldMeta = oldItem.getItemMeta();

        if (newMeta != null)
        {
            if (newMeta.hasCustomModelData())
            {
                VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(newItem);

                Double health = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_HEALTH_KEY);
                Double defense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_DEFENSE_KEY);
                Double fireDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FIRE_DEFENSE_KEY);
                Double projectileDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_PROJECTILE_DEFENSE_KEY);
                Double explosionDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_EXPLOSION_DEFENSE_KEY);
                Double magicDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DEFENSE_KEY);
                Double toughness = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY);
                Double fortitude = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FORTITUDE_KEY);
                Double speed = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_SPEED_KEY);
                Double mana = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MAX_MANA_KEY);
                Double meleeDamage = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY);
                Double magicDamage = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY);
                Double rangeDamage = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY);
                Double mobFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MOB_FORTUNE_KEY);
                Double miningFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MINING_FORTUNE_KEY);
                Double foragingFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FORAGING_FORTUNE_KEY);
                Double farmingFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FARMING_FORTUNE_KEY);
                Double fishingLuck = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FISHING_LUCK_KEY);
                Double luck = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_LUCK_KEY);
                Double longevity = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_LONGEVITY);
                Double caffeination = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_CAFFEINATION);

                addStats(statProfile, health, defense, fireDefense, projectileDefense, explosionDefense, magicDefense, toughness, speed, mana, meleeDamage, magicDamage, rangeDamage);
                addFortitude(player, fortitude);
                addFortuneStats(player, mobFortune, miningFortune, foragingFortune, farmingFortune, fishingLuck, luck);
                addPotionStats(player, longevity, caffeination);
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
                VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(oldItem);

                Double health = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_HEALTH_KEY);
                Double defense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_DEFENSE_KEY);
                Double fireDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FIRE_DEFENSE_KEY);
                Double projectileDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_PROJECTILE_DEFENSE_KEY);
                Double explosionDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_EXPLOSION_DEFENSE_KEY);
                Double magicDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DEFENSE_KEY);
                Double toughness = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY);
                Double fortitude = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FORTITUDE_KEY);
                Double speed =  ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_SPEED_KEY);
                Double mana = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MAX_MANA_KEY);
                Double meleeDamage = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY);
                Double magicDamage = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY);
                Double rangeDamage = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY);
                Double mobFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MOB_FORTUNE_KEY);
                Double miningFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_MINING_FORTUNE_KEY);
                Double foragingFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FORAGING_FORTUNE_KEY);
                Double farmingFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FARMING_FORTUNE_KEY);
                Double fishingLuck = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_FISHING_LUCK_KEY);
                Double luck = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_LUCK_KEY);
                Double longevity = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_LONGEVITY);
                Double caffeination = ventureItemBuilder.getItemStat(VentureItemStatKeys.ARMOR_CAFFEINATION);

                removeStats(statProfile, health, defense, fireDefense, projectileDefense, explosionDefense, magicDefense, toughness, speed, mana, meleeDamage, magicDamage, rangeDamage);
                removeFortitude(player, fortitude);
                removeFortuneStats(player, mobFortune, miningFortune, foragingFortune, farmingFortune, fishingLuck, luck);
                removePotionStats(player, longevity, caffeination);
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
            damageStats.setPercentMeleeDmg(currentMeleeDamage + meleeDamage);
            armorStats.setPercentMeleeDamage(armorStats.getPercentMeleeDamage() + meleeDamage);
        }
        if (magicDamage != null) {
            damageStats.setPercentMagicDmg(currentMagicDamage + magicDamage);
            armorStats.setPercentMagicDamage(armorStats.getPercentMagicDamage() + magicDamage);
        }
        if (rangeDamage != null) {
            damageStats.setPercentRangeDmg(currentRangeDamage + rangeDamage);
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
        double currentRangeDamage = damageStats.getPercentRange();

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
            damageStats.setPercentMeleeDmg(currentMeleeDamage - meleeDamage);
            armorStats.setPercentMeleeDamage(armorStats.getPercentMeleeDamage() - meleeDamage);
        }
        if (magicDamage != null) {
            damageStats.setPercentMagicDmg(currentMagicDamage - magicDamage);
            armorStats.setPercentMagicDamage(armorStats.getPercentMagicDamage() - magicDamage);
        }
        if (rangeDamage != null) {
            damageStats.setPercentRangeDmg(currentRangeDamage - rangeDamage);
            armorStats.setPercentRangeDamage(armorStats.getPercentRangeDamage() - rangeDamage);
        }
    }

    private void addFortitude(Player player, Double fortitude)
    {
        ArmorStats armorStats = statProfileManager.getStatProfile(player.getUniqueId()).armorStats();
        DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
        Double currentFortitude = defenseStats.getFortitude();

        if (fortitude != null)
        {
            defenseStats.setFortitude(currentFortitude + fortitude);
            armorStats.setFortitude(armorStats.getFortitude() + fortitude);
        }
    }

    private void removeFortitude(Player player, Double fortitude)
    {
        ArmorStats armorStats = statProfileManager.getStatProfile(player.getUniqueId()).armorStats();
        DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
        Double currentFortitude = defenseStats.getFortitude();

        if (fortitude != null)
        {
            defenseStats.setFortitude(currentFortitude - fortitude);
            armorStats.setFortitude(armorStats.getFortitude() - fortitude);
        }
    }

    private void addFortuneStats(Player player, Double mobFortune, Double miningFortune, Double foragingFortune, Double farmingFortune, Double fishingLuck, Double luck)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        ArmorStats armorStats = statPlayer.getArmorStats();
        LootStats lootStats = statPlayer.getLootStats();

        if (mobFortune != null)
        {
            lootStats.setMobLooting(lootStats.getMobLooting() + mobFortune);
            armorStats.setMobFortune(armorStats.getMobFortune() + mobFortune);
        }
        if (miningFortune != null)
        {
            lootStats.setMiningFortune(lootStats.getMiningFortune() + miningFortune);
            armorStats.setMobFortune(armorStats.getMiningFortune() + miningFortune);
        }
        if (foragingFortune != null)
        {
            lootStats.setForagingFortune(lootStats.getForagingFortune() + foragingFortune);
            armorStats.setForagingFortune(armorStats.getForagingFortune() + foragingFortune);
        }
        if (farmingFortune != null)
        {
            lootStats.setFarmingFortune(lootStats.getFarmingFortune() + farmingFortune);
            armorStats.setFarmingFortune(armorStats.getFarmingFortune() + farmingFortune);
        }
        if (fishingLuck != null)
        {
            lootStats.setFishingLuck(lootStats.getFishingLuck() + fishingLuck);
            armorStats.setFishingLuck(armorStats.getFishingLuck() + fishingLuck);
        }
        if (luck != null)
        {
            stats.setLuck(stats.getLuck() + luck);
            armorStats.setLuck(armorStats.getLuck() + luck);
        }
    }

    private void removeFortuneStats(Player player, Double mobFortune, Double miningFortune, Double foragingFortune, Double farmingFortune, Double fishingLuck, Double luck)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        ArmorStats armorStats = statPlayer.getArmorStats();
        LootStats lootStats = statPlayer.getLootStats();

        if (mobFortune != null)
        {
            lootStats.setMobLooting(lootStats.getMobLooting() - mobFortune);
            armorStats.setMobFortune(armorStats.getMobFortune() - mobFortune);
        }
        if (miningFortune != null)
        {
            lootStats.setMiningFortune(lootStats.getMiningFortune() - miningFortune);
            armorStats.setMiningFortune(armorStats.getMiningFortune() - miningFortune);
        }
        if (foragingFortune != null)
        {
            lootStats.setForagingFortune(lootStats.getForagingFortune() - foragingFortune);
            armorStats.setForagingFortune(armorStats.getForagingFortune() - foragingFortune);
        }
        if (farmingFortune != null)
        {
            lootStats.setFarmingFortune(lootStats.getFarmingFortune() - farmingFortune);
            armorStats.setFarmingFortune(armorStats.getFarmingFortune() - farmingFortune);
        }
        if (fishingLuck != null)
        {
            lootStats.setFishingLuck(lootStats.getFishingLuck() - fishingLuck);
            armorStats.setFishingLuck(armorStats.getFishingLuck() - fishingLuck);
        }
        if (luck != null)
        {
            stats.setLuck(stats.getLuck() - luck);
            armorStats.setLuck(armorStats.getLuck() - luck);
        }
    }

    private void addPotionStats(Player player, Double longevity, Double caffeination)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        ArmorStats armorStats = statPlayer.getArmorStats();
        PotionStats potionStats = statPlayer.getPotionStats();

        if (longevity != null)
        {
            potionStats.setLongevity(potionStats.getLongevity() + longevity);
            armorStats.setLongevity(armorStats.getLongevity() + longevity);
        }
        if (caffeination != null)
        {
            potionStats.setCaffeination(potionStats.getCaffeination() + caffeination);
            armorStats.setCaffeination(armorStats.getCaffeination() + caffeination);
        }
    }

    private void removePotionStats(Player player, Double longevity, Double caffeination)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        ArmorStats armorStats = statPlayer.getArmorStats();
        PotionStats potionStats = statPlayer.getPotionStats();

        if (longevity != null)
        {
            potionStats.setLongevity(potionStats.getLongevity() - longevity);
            armorStats.setLongevity(armorStats.getLongevity() - longevity);
        }
        if (caffeination != null)
        {
            potionStats.setCaffeination(potionStats.getCaffeination() - caffeination);
            armorStats.setCaffeination(armorStats.getCaffeination() - caffeination);
        }
    }

    public static boolean isWearingFullSet(Player player, VentureArmorMaterial ventureArmorMaterial)
    {
        ItemStack helmet = player.getInventory().getHelmet(), chestplate = player.getInventory().getChestplate(), leggings = player.getInventory().getLeggings(), boots = player.getInventory().getBoots();

        if (helmet != null && chestplate != null && leggings != null && boots != null)
        {
            ItemMeta helmetMeta = helmet.getItemMeta(), chestplateMeta = chestplate.getItemMeta(), leggingsMeta = leggings.getItemMeta(), bootsMeta = boots.getItemMeta();
            if (helmetMeta != null && chestplateMeta != null && leggingsMeta != null && bootsMeta != null)
            {
                if (helmetMeta.hasCustomModelData() && chestplateMeta.hasCustomModelData() && leggingsMeta.hasCustomModelData() && bootsMeta.hasCustomModelData())
                {
                    if (helmetMeta.getCustomModelData() == ventureArmorMaterial.getArmorCustomModelData()[0] && chestplateMeta.getCustomModelData() == ventureArmorMaterial.getArmorCustomModelData()[1] && leggingsMeta.getCustomModelData() == ventureArmorMaterial.getArmorCustomModelData()[2] && bootsMeta.getCustomModelData() == ventureArmorMaterial.getArmorCustomModelData()[3])
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

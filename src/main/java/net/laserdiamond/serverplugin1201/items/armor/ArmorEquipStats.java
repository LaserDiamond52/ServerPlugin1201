package net.laserdiamond.serverplugin1201.items.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.ItemMappings;
import net.laserdiamond.serverplugin1201.management.ItemStatKeys;
import net.laserdiamond.serverplugin1201.stats.Components.DamageStats;
import net.laserdiamond.serverplugin1201.stats.Components.DefenseStats;
import net.laserdiamond.serverplugin1201.stats.Components.StatProfile;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class ArmorEquipStats implements Listener {

    private final ServerPlugin1201 plugin;
    private final StatProfileManager statProfileManager;
    public ArmorEquipStats(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
    }

    @EventHandler
    public void ArmorChange(PlayerArmorChangeEvent event) {

        Player player = event.getPlayer();
        StatProfile statProfile = statProfileManager.getStatProfile(player.getUniqueId());

        ItemStack newItem = event.getNewItem(), oldItem = event.getOldItem();
        ItemMeta newMeta = newItem.getItemMeta(), oldMeta = oldItem.getItemMeta();

        if (newMeta != null) {
            if (newMeta.hasCustomModelData()) {
                // Equipping custom item (Includes Netherite)
                int newCMD = newMeta.getCustomModelData();
                int itemStars = ItemForger.getItemStars(newItem);
                HashMap<Integer, ItemForger> itemForgerHashMap = ItemMappings.itemForgerHashMap(itemStars);
                ItemForger itemForger = itemForgerHashMap.get(newCMD);

                Double health = itemForger.getItemStat(ItemStatKeys.HEALTH_KEY);
                Double armor = itemForger.getItemStat(ItemStatKeys.ARMOR_KEY);
                Double toughness = itemForger.getItemStat(ItemStatKeys.TOUGHNESS_KEY);
                Double fortitude = itemForger.getItemStat(ItemStatKeys.FORTITUDE_KEY);
                Double speed = itemForger.getItemStat(ItemStatKeys.SPEED_KEY);
                Double mana = itemForger.getItemStat(ItemStatKeys.MAX_MANA_KEY);
                Double meleeDamage = itemForger.getItemStat(ItemStatKeys.MELEE_DAMAGE_KEY);
                Double magicDamage = itemForger.getItemStat(ItemStatKeys.MAGIC_DAMAGE_KEY);
                Double rangeDamage = itemForger.getItemStat(ItemStatKeys.RANGE_DAMAGE_KEY);

                addStats(statProfile, health, armor, toughness, speed, mana, meleeDamage, magicDamage, rangeDamage);
                addFortitude(player, fortitude);
            } else {
                // Equipping Vanilla Item (Excluding Netherite)
                Material newMaterial = newItem.getType();
                for (VanillaArmorValues values : VanillaArmorValues.values()) {
                    if (values.getMaterial().equals(newMaterial)) {
                        Double armor = values.getProtectionValue();
                        Double toughness = values.getToughnessValue();

                        addStats(statProfile, 0.0, armor, toughness, 0.0, 0.0, 0.0, 0.0,0.0);
                    }
                }
            }
        }
        if (oldMeta != null) {
            if (oldMeta.hasCustomModelData()) {
                // TODO: Removing custom item (Includes Netherite)
                int oldCMD = oldMeta.getCustomModelData();
                int itemStars = ItemForger.getItemStars(oldItem);
                HashMap<Integer, ItemForger> itemForgerHashMap = ItemMappings.itemForgerHashMap(itemStars);
                ItemForger itemForger = itemForgerHashMap.get(oldCMD);

                Double health = itemForger.getItemStat(ItemStatKeys.HEALTH_KEY);
                Double armor = itemForger.getItemStat(ItemStatKeys.ARMOR_KEY);
                Double toughness = itemForger.getItemStat(ItemStatKeys.TOUGHNESS_KEY);
                Double fortitude = itemForger.getItemStat(ItemStatKeys.FORTITUDE_KEY);
                Double speed =  itemForger.getItemStat(ItemStatKeys.SPEED_KEY);
                Double mana = itemForger.getItemStat(ItemStatKeys.MAX_MANA_KEY);
                Double meleeDamage = itemForger.getItemStat(ItemStatKeys.MELEE_DAMAGE_KEY);
                Double magicDamage = itemForger.getItemStat(ItemStatKeys.MAGIC_DAMAGE_KEY);
                Double rangeDamage = itemForger.getItemStat(ItemStatKeys.RANGE_DAMAGE_KEY);

                removeStats(statProfile, health, armor, toughness, speed, mana, meleeDamage, magicDamage, rangeDamage);
                removeFortitude(player, fortitude);
            } else {
                // TODO: Removing Vanilla Item (Excluding Netherite)
                Material oldMaterial = oldItem.getType();
                for (VanillaArmorValues values : VanillaArmorValues.values()) {
                    if (values.getMaterial().equals(oldMaterial)) {
                        Double armor = values.getProtectionValue();
                        Double toughness = values.getToughnessValue();

                        removeStats(statProfile, 0.0, armor, toughness, 0.0, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
    }

    private void addStats(StatProfile statProfile, Double health, Double armor, Double toughness, Double speed, Double mana, Double meleeDamage, Double magicDamage, Double rangeDamage) {
        Stats stats = statProfile.stats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();

        double currentHealth = stats.getHealth();
        double currentArmor = defenseStats.getDefense();
        double currentToughness = defenseStats.getToughness();
        double currentSpeed = stats.getSpeed();
        double currentMana = stats.getMaxMana();
        double currentMeleeDamage = damageStats.getpMeleeDmg();
        double currentMagicDamage = damageStats.getpMagicDmg();
        double currentRangeDamage = damageStats.getpRangeDmg();

        if (health != null) {
            stats.setHealth(currentHealth + health);
        }
        if (armor != null) {
            defenseStats.setDefense(currentArmor + armor);
        }
        if (toughness != null) {
            defenseStats.setToughness(currentToughness + toughness);
        }
        if (speed != null) {
            stats.setSpeed(currentSpeed + speed);
        }
        if (mana != null) {
            stats.setMaxMana(currentMana + mana);
        }
        if (meleeDamage != null) {
            damageStats.setpMeleeDmg(currentMeleeDamage + meleeDamage);
        }
        if (magicDamage != null) {
            damageStats.setpMagicDmg(currentMagicDamage + magicDamage);
        }
        if (rangeDamage != null) {
            damageStats.setpRangeDmg(currentRangeDamage + rangeDamage);
        }

    }

    private void removeStats(StatProfile statProfile, Double health, Double armor, Double toughness, Double speed, Double mana, Double meleeDamage, Double magicDamage, Double rangeDamage) {
        Stats stats = statProfile.stats();
        DamageStats damageStats = statProfile.damageStats();
        DefenseStats defenseStats = statProfile.defenseStats();

        double currentHealth = stats.getHealth();
        double currentArmor = defenseStats.getDefense();
        double currentToughness = defenseStats.getToughness();
        double currentSpeed = stats.getSpeed();
        double currentMana = stats.getMaxMana();
        double currentMeleeDamage = damageStats.getpMeleeDmg();
        double currentMagicDamage = damageStats.getpMagicDmg();
        double currentRangeDamage = damageStats.getbRangeDmg();

        if (health != null) {
            stats.setHealth(currentHealth - health);
        }
        if (armor != null) {
            defenseStats.setDefense(currentArmor - armor);
        }
        if (toughness != null) {
            defenseStats.setToughness(currentToughness - toughness);
        }
        if (speed != null) {
            stats.setSpeed(currentSpeed - speed);
        }
        if (mana != null) {
            stats.setMaxMana(currentMana - mana);
        }
        if (meleeDamage != null) {
            damageStats.setpMeleeDmg(currentMeleeDamage - meleeDamage);
        }
        if (magicDamage != null) {
            damageStats.setpMagicDmg(currentMagicDamage - magicDamage);
        }
        if (rangeDamage != null) {
            damageStats.setpRangeDmg(currentRangeDamage - rangeDamage);
        }
    }

    private void addFortitude(Player player, Double fortitude) {

        DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
        Double currentFortitude = defenseStats.getFortitude(player);

        if (fortitude != null) {
            defenseStats.setFortitude(player, currentFortitude + fortitude);
        }
    }

    private void removeFortitude(Player player, Double fortitude) {

        DefenseStats defenseStats = statProfileManager.getStatProfile(player.getUniqueId()).defenseStats();
        Double currentFortitude = defenseStats.getFortitude(player);

        if (fortitude != null) {
            defenseStats.setFortitude(player, currentFortitude - fortitude);
        }
    }

    public static boolean isWearingFullSet(Player player, int helmetCMD, int chestplateCMD, int leggingsCMD, int bootsCMD)
    {
        ItemStack helmet = player.getInventory().getHelmet(), chestplate = player.getInventory().getChestplate(), leggings = player.getInventory().getLeggings(), boots = player.getInventory().getBoots();

        if (helmet != null && chestplate != null && leggings != null && boots != null)
        {
            ItemMeta helmetMeta = helmet.getItemMeta(), chestplateMeta = chestplate.getItemMeta(), leggingsMeta = leggings.getItemMeta(), bootsMeta = boots.getItemMeta();
            if (helmetMeta != null && chestplateMeta != null && leggingsMeta != null && bootsMeta != null)
            {
                if (helmetMeta.hasCustomModelData() && chestplateMeta.hasCustomModelData() && leggingsMeta.hasCustomModelData() && bootsMeta.hasCustomModelData())
                {
                    if (helmetMeta.getCustomModelData() == helmetCMD && chestplateMeta.getCustomModelData() == chestplateCMD && leggingsMeta.getCustomModelData() == leggingsCMD && bootsMeta.getCustomModelData() == bootsCMD)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

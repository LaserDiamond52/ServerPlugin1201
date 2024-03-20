package net.laserdiamond.ventureplugin.items.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemForgerRegistry;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.stats.Components.DamageStats;
import net.laserdiamond.ventureplugin.stats.Components.DefenseStats;
import net.laserdiamond.ventureplugin.stats.Components.StatProfile;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

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

        if (newMeta != null) {
            if (newMeta.hasCustomModelData()) {
                // Equipping custom item (Includes Netherite)
                int newCMD = newMeta.getCustomModelData();
                int itemStars = ItemForger.getItemStars(newItem);
                HashMap<Integer, ItemForger> itemForgerHashMap = ItemForgerRegistry.itemForgerHashMap(itemStars);
                ItemForger itemForger = itemForgerHashMap.get(newCMD);

                Double health = itemForger.getItemStat(VentureItemStatKeys.ARMOR_HEALTH_KEY);
                Double armor = itemForger.getItemStat(VentureItemStatKeys.ARMOR_DEFENSE_KEY);
                Double toughness = itemForger.getItemStat(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY);
                Double fortitude = itemForger.getItemStat(VentureItemStatKeys.ARMOR_FORTITUDE_KEY);
                Double speed = itemForger.getItemStat(VentureItemStatKeys.ARMOR_SPEED_KEY);
                Double mana = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAX_MANA_KEY);
                Double meleeDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY);
                Double magicDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY);
                Double rangeDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY);

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
                HashMap<Integer, ItemForger> itemForgerHashMap = ItemForgerRegistry.itemForgerHashMap(itemStars);
                ItemForger itemForger = itemForgerHashMap.get(oldCMD);

                Double health = itemForger.getItemStat(VentureItemStatKeys.ARMOR_HEALTH_KEY);
                Double armor = itemForger.getItemStat(VentureItemStatKeys.ARMOR_DEFENSE_KEY);
                Double toughness = itemForger.getItemStat(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY);
                Double fortitude = itemForger.getItemStat(VentureItemStatKeys.ARMOR_FORTITUDE_KEY);
                Double speed =  itemForger.getItemStat(VentureItemStatKeys.ARMOR_SPEED_KEY);
                Double mana = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAX_MANA_KEY);
                Double meleeDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MELEE_DAMAGE_KEY);
                Double magicDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY);
                Double rangeDamage = itemForger.getItemStat(VentureItemStatKeys.ARMOR_RANGE_DAMAGE_KEY);

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

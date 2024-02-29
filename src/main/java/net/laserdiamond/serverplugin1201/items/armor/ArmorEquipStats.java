package net.laserdiamond.serverplugin1201.items.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.ItemMappings;
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

    private ServerPlugin1201 plugin;
    private StatProfileManager statProfileManager;
    public ArmorEquipStats(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
    }

    @EventHandler
    public void ArmorChange(PlayerArmorChangeEvent event) {

        Player player = event.getPlayer();

        ItemStack newItem = event.getNewItem(), oldItem = event.getOldItem();
        ItemMeta newMeta = newItem.getItemMeta(), oldMeta = oldItem.getItemMeta();

        if (newMeta != null) {
            if (newMeta.hasCustomModelData()) {
                // TODO: Equipping custom item (Includes Netherite)
                int newCMD = newMeta.getCustomModelData();
                int itemStars = ItemForger.getItemStars(newItem);
                HashMap<Integer, ItemForger> itemForgerHashMap = ItemMappings.itemForgerHashMap(itemStars);
                ItemForger itemForger = itemForgerHashMap.get(newCMD);

                Double health = itemForger.getHealth();
                Double armor = itemForger.getArmor();
                Double toughness = itemForger.getToughness();
                Double fortitude = itemForger.getFortitude();
                Double mana = itemForger.getMaxMana();
                Double meleeDamage = itemForger.getMeleeDamage();
                Double magicDamage = itemForger.getMagicDamage();
                Double rangeDamage = itemForger.getRangeDamage();

                addStats(player, health, armor, toughness, mana, meleeDamage, magicDamage, rangeDamage);
                addFortitude(player, fortitude);
            } else {
                // TODO: Equipping Vanilla Item (Excluding Netherite)
                Material newMaterial = newItem.getType();
                for (VanillaArmorValues values : VanillaArmorValues.values()) {
                    if (values.getMaterial().equals(newMaterial)) {
                        Double armor = values.getProtectionValue();
                        Double toughness = values.getToughnessValue();

                        addStats(player, 0.0, armor, toughness, 0.0, 0.0, 0.0, 0.0);
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

                Double health = itemForger.getHealth();
                Double armor = itemForger.getArmor();
                Double toughness = itemForger.getToughness();
                Double fortitude = itemForger.getFortitude();
                Double mana = itemForger.getMaxMana();
                Double meleeDamage = itemForger.getMeleeDamage();
                Double magicDamage = itemForger.getMagicDamage();
                Double rangeDamage = itemForger.getRangeDamage();

                removeStats(player, health, armor, toughness, mana, meleeDamage, magicDamage, rangeDamage);
                removeFortitude(player, fortitude);
            } else {
                // TODO: Removing Vanilla Item (Excluding Netherite)
                Material oldMaterial = oldItem.getType();
                for (VanillaArmorValues values : VanillaArmorValues.values()) {
                    if (values.getMaterial().equals(oldMaterial)) {
                        Double armor = values.getProtectionValue();
                        Double toughness = values.getToughnessValue();

                        removeStats(player, 0.0, armor, toughness, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            }

        }

    }

    private void addStats(Player player, Double health, Double armor, Double toughness, Double mana, Double meleeDamage, Double magicDamage, Double rangeDamage) {
        Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).getStats();

        AttributeInstance playerHealthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        Double playerHealth = playerHealthInstance.getBaseValue();
        Double currentHealth = stats.getHealth();
        Double currentArmor = stats.getDefense();
        Double currentToughness = stats.getToughness();
        Double currentMana = stats.getMaxMana();
        Double currentMeleeDamage = stats.getMeleeDamage();
        Double currentMagicDamage = stats.getMagicDamage();
        Double currentRangeDamage = stats.getRangeDamage();

        if (health != null) {
            stats.setHealth(currentHealth + health);
            player.setMaxHealth(playerHealth + health);
        }
        if (armor != null) {
            stats.setDefense(currentArmor + armor);
        }
        if (toughness != null) {
            stats.setToughness(currentToughness + toughness);
        }
        if (mana != null) {
            stats.setMaxMana(currentMana + mana);
        }
        if (meleeDamage != null) {
            stats.setMeleeDamage(currentMeleeDamage + meleeDamage);
        }
        if (magicDamage != null) {
            stats.setMagicDamage(currentMagicDamage + magicDamage);
        }
        if (rangeDamage != null) {
            stats.setRangeDamage(currentRangeDamage + rangeDamage);
        }

    }

    private void removeStats(Player player, Double health, Double armor, Double toughness, Double mana, Double meleeDamage, Double magicDamage, Double rangeDamage) {
        Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).getStats();

        AttributeInstance playerHealthInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        Double playerHealth = playerHealthInstance.getBaseValue();
        Double currentHealth = stats.getHealth();
        Double currentArmor = stats.getDefense();
        Double currentToughness = stats.getToughness();
        Double currentMana = stats.getMaxMana();
        Double currentMeleeDamage = stats.getMeleeDamage();
        Double currentMagicDamage = stats.getMagicDamage();
        Double currentRangeDamage = stats.getRangeDamage();

        if (health != null) {
            stats.setHealth(currentHealth - health);
            player.setMaxHealth(playerHealth - health);
        }
        if (armor != null) {
            stats.setDefense(currentArmor - armor);
        }
        if (toughness != null) {
            stats.setToughness(currentToughness - toughness);
        }
        if (mana != null) {
            stats.setMaxMana(currentMana - mana);
        }
        if (meleeDamage != null) {
            stats.setMeleeDamage(currentMeleeDamage - meleeDamage);
        }
        if (magicDamage != null) {
            stats.setMagicDamage(currentMagicDamage - magicDamage);
        }
        if (rangeDamage != null) {
            stats.setRangeDamage(currentRangeDamage - rangeDamage);
        }
    }

    private void addFortitude(Player player, Double fortitude) {

        Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).getStats();

        AttributeInstance playerKBResInstance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        Double playerKBRES = playerKBResInstance.getBaseValue();
        Double currentFortitude = stats.getFortitude();

        if (fortitude != null) {
            playerKBResInstance.setBaseValue(playerKBRES + currentFortitude);
        }
    }

    private void removeFortitude(Player player, Double fortitude) {

        Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).getStats();

        AttributeInstance playerKBResInstance = player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        Double playerKBRES = playerKBResInstance.getBaseValue();
        Double currentFortitude = stats.getFortitude();

        if (fortitude != null) {
            playerKBResInstance.setBaseValue(playerKBRES - currentFortitude);
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

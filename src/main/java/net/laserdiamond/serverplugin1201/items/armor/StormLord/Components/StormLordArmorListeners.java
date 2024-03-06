package net.laserdiamond.serverplugin1201.items.armor.StormLord.Components;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantsClass;
import net.laserdiamond.serverplugin1201.events.Stats.DamageEvent;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Config.StormLordArmorConfig;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorCMD;
import net.laserdiamond.serverplugin1201.management.messages.messages;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

public class StormLordArmorListeners implements Listener {

    private ServerPlugin1201 plugin;
    private StatProfileManager statProfileManager;
    private StormLordArmorConfig armorConfig;

    String abilityName;
    double manaCost, blastRadius, baseDamage;
    int cooldown;

    public StormLordArmorListeners(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
        armorConfig = plugin.getStormLordArmorConfig();


        abilityName = armorConfig.getString("abilityName");
        manaCost = armorConfig.getDouble("manaCost");
        blastRadius = armorConfig.getDouble("blastRadius");
        cooldown = armorConfig.getInt("cooldown");
        baseDamage = armorConfig.getDouble("baseDamage");

    }

    @EventHandler
    public void eyeOfTheStorm(PlayerDropItemEvent event) {

        Player player = event.getPlayer();

        InventoryType invType = player.getOpenInventory().getType();

        if (StormLordArmorManager.isWearingFullSet(player))
        {
            if (invType == InventoryType.CRAFTING || invType == InventoryType.CREATIVE)
            {
                ItemStack itemDropped = event.getItemDrop().getItemStack();
                ItemMeta dropMeta = itemDropped.getItemMeta();

                if (dropMeta != null && dropMeta.hasEnchant(EnchantsClass.THUNDER_STRIKE))
                {
                    event.setCancelled(true);
                    Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).stats();
                    double availableMana = stats.getAvailableMana();

                    double thunderStrikeLvl = dropMeta.getEnchantLevel(EnchantsClass.THUNDER_STRIKE);
                    double finalDamage = baseDamage + Math.pow(thunderStrikeLvl, 2);

                    if (EyeOfStormCooldown.checkCooldown(player))
                    {
                        if (availableMana >= manaCost)
                        {
                            stats.setAvailableMana(availableMana - manaCost);

                            // Create thrown potion that will damage nearby entities
                            ThrownPotion magicPotion = player.getWorld().spawn(player.getLocation(), ThrownPotion.class);
                            magicPotion.setShooter(player); // MUST set shooter to player
                            magicPotion.setItem(new ItemStack(Material.AIR)); // Set thrown potion to air
                            magicPotion.getPotionMeta().getPersistentDataContainer().set(DamageEvent.magicDmgKey, PersistentDataType.DOUBLE, finalDamage);
                            magicPotion.getPotionMeta().setColor(Color.AQUA);

                            for (LivingEntity livingEntity : player.getLocation().getNearbyLivingEntities(blastRadius)) // Get all living entities in blast radius
                            {
                                livingEntity.getWorld().strikeLightningEffect(livingEntity.getLocation()); // Strike lightning on entities

                                if (livingEntity != player) // Check if living entity is NOT player
                                {
                                    livingEntity.damage(finalDamage, magicPotion); // Damage living entity (remember shooter = player)
                                }
                            }

                            magicPotion.splash(); // Splash potion
                            player.sendMessage(messages.abilityUse(abilityName));
                            EyeOfStormCooldown.setCooldown(player, cooldown);
                        } else {
                            player.sendMessage(messages.notEnoughMana());
                        }

                    }

                }
            }
        }
    }

    @EventHandler
    public void conduitPower(PlayerArmorChangeEvent event) {

        Player player = event.getPlayer();

        // Need to check if when player puts on any storm armor piece,
        // they are already wearing the other three pieces

        // Remove effect when they take off one piece
        // (don't need to check if they are wearing the full set)

        ItemStack newItem = event.getNewItem(), oldItem = event.getOldItem();
        ItemMeta newMeta = newItem.getItemMeta(), oldMeta = oldItem.getItemMeta();

        PlayerInventory pInv = player.getInventory();
        ItemStack helmet = pInv.getHelmet(), chestplate = pInv.getChestplate(), leggings = pInv.getLeggings(), boots = pInv.getBoots();

        // Apply effect

        if (ArmorCMD.isAnyArmorPiece(newItem, ArmorCMD.STORM_LORD_ARMOR))
        {
            if (ArmorCMD.isWearingThreePcs(player, ArmorCMD.STORM_LORD_ARMOR))
            {

            }
        }

        // Remove effect
        if (ArmorCMD.isAnyArmorPiece(oldItem, ArmorCMD.STORM_LORD_ARMOR))
        {
            if (player.hasPotionEffect(PotionEffectType.CONDUIT_POWER))
            {
                player.removePotionEffect(PotionEffectType.CONDUIT_POWER);
            }
        }
    }

}

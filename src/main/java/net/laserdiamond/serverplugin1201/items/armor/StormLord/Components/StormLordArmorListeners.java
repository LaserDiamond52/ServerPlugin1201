package net.laserdiamond.serverplugin1201.items.armor.StormLord.Components;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantsClass;
import net.laserdiamond.serverplugin1201.events.Stats.DamageEvent;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Config.StormLordArmorConfig;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
                    Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).getStats();
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

    }

}

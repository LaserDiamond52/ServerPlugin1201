package net.laserdiamond.serverplugin1201.items.armor.StormLord.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.enchants.Components.EnchantsClass;
import net.laserdiamond.serverplugin1201.entities.player.StatPlayer;
import net.laserdiamond.serverplugin1201.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.serverplugin1201.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.serverplugin1201.items.armor.ArmorEquipStats;
import net.laserdiamond.serverplugin1201.items.armor.StormLord.Config.StormLordArmorConfig;
import net.laserdiamond.serverplugin1201.items.management.ItemForger;
import net.laserdiamond.serverplugin1201.items.management.ItemNameBuilder;
import net.laserdiamond.serverplugin1201.items.management.PluginItemRarity;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorCMD;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorFabricate;
import net.laserdiamond.serverplugin1201.items.management.armor.ArmorTypes;
import net.laserdiamond.serverplugin1201.events.SpellCasting.*;
import net.laserdiamond.serverplugin1201.management.ItemStatKeys;
import net.laserdiamond.serverplugin1201.management.Stars;
import net.laserdiamond.serverplugin1201.management.messages.Messages;
import net.laserdiamond.serverplugin1201.stats.Components.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class StormLordArmorManager implements ArmorFabricate, SpellCastListener, SpellCasting.DropItemSpell {

    private static final ServerPlugin1201 PLUGIN = ServerPlugin1201.getInstance();
    private static final StormLordArmorConfig ARMOR_CONFIG = PLUGIN.getStormLordArmorConfig();
    private final double starBonus = Stars.STARS.getBoostPerStar();

    @Override
    public List<String> createLore(@NotNull ArmorTypes armorTypes, int stars) {

        HashMap<ItemStatKeys, Double> itemStatKeysMap = createItemStats(armorTypes, stars);

        List<String> lore = new ArrayList<>();

        int blastRadius = ARMOR_CONFIG.getInt("blastRadius");
        int weaknessLvl = ARMOR_CONFIG.getInt("weaknessLvl");
        double weaknessDuration = ARMOR_CONFIG.getDouble("weaknessDuration");
        int cooldown = ARMOR_CONFIG.getInt("cooldown");
        double manaCost = ARMOR_CONFIG.getDouble("manaCost");

        lore.add(" ");
        if (itemStatKeysMap != null) {
            List<String> statLore = ItemForger.createStatLore(itemStatKeysMap, createAttributes(armorTypes, stars));
            for (String l : statLore) {
                lore.add(l);
            }
        }
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Full Set Bonus: Eye of the Storm" + ChatColor.YELLOW + " " + ChatColor.BOLD + "Press Q");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Grants all items with the " + ChatColor.AQUA + "Thunder Strike" + ChatColor.GRAY + " enchantment");
        lore.add(ChatColor.GRAY + "the ability to unleash a devastating lightning blast");
        lore.add(ChatColor.GRAY + "that deals " + ChatColor.AQUA + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.GRAY + " base damage to nearby mobs in a " + ChatColor.GOLD + blastRadius + ChatColor.GRAY + " block");
        lore.add(ChatColor.GRAY + "radius and inflicts " + ChatColor.YELLOW + "Weakness " + weaknessLvl + ChatColor.GRAY + " for " + ChatColor.GREEN + weaknessDuration + ChatColor.GRAY + " seconds");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
        lore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + cooldown + ChatColor.DARK_GRAY + " seconds");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Gain " + ChatColor.DARK_AQUA + "Conduit Power" + ChatColor.GRAY + " when fully worn");
        lore.add(" ");




        // Full Set Bonus: Eye of the Storm PRESS Q
        //
        // Grants all items with the Thunder Strike enchantment
        // the ability to unleash a devastating lightning blast
        // that deals X base damage to nearby mobs in a 5 block
        // radius and inflicts weakness 2 for 10 seconds
        //
        // Base Damage: 2.5 + x^2 (Display obfuscated X if player is not holding an item with thunder strike enchantment)
        //
        //
        // Mana Cost: 250
        // Cooldown: 60 seconds
        //
        // Gain Conduit Power when fully worn

        // X should be determined by...
        // -Thunder Strike enchantment level
        // -Magic Damage

        return lore;
    }

    @Override
    public List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorTypes armorTypes, int stars) {

        HashMap<ItemStatKeys, Double> itemStatKeysMap = createItemStats(armorTypes, stars);

        List<String> lore = new ArrayList<>();

        int blastRadius = ARMOR_CONFIG.getInt("blastRadius");
        int weaknessLvl = ARMOR_CONFIG.getInt("weaknessLvl");
        double weaknessDuration = ARMOR_CONFIG.getDouble("weaknessDuration");
        int cooldown = ARMOR_CONFIG.getInt("cooldown");
        double manaCost = ARMOR_CONFIG.getDouble("manaCost");

        double baseDamage = ARMOR_CONFIG.getDouble("baseDamage");
        if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
            ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (itemMeta.hasEnchant(EnchantsClass.THUNDER_STRIKE)) {
                int thunderStrikeLevel = player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(EnchantsClass.THUNDER_STRIKE);
                int thunderStrikeBaseDamage = thunderStrikeLevel*thunderStrikeLevel;
                double finalBaseDamage = baseDamage + thunderStrikeBaseDamage;

                lore.add(" ");
                if (itemStatKeysMap != null) {
                    List<String> statLore = ItemForger.createStatLore(itemStatKeysMap, createAttributes(armorTypes, stars));
                    for (String l : statLore) {
                        lore.add(l);
                    }
                }
                lore.add(" ");
                lore.add(ChatColor.GOLD + "Full Set Bonus: Eye of the Storm" + ChatColor.YELLOW + " " + ChatColor.BOLD + "Press Q");
                lore.add(" ");
                lore.add(ChatColor.GRAY + "Grants all items with the " + ChatColor.AQUA + "Thunder Strike" + ChatColor.GRAY + " enchantment");
                lore.add(ChatColor.GRAY + "the ability to unleash a devastating lightning blast");
                lore.add(ChatColor.GRAY + "that deals " + ChatColor.AQUA + finalBaseDamage + ChatColor.RESET + ChatColor.GRAY + " base damage to nearby mobs in a " + ChatColor.GOLD + blastRadius + ChatColor.GRAY + " block");
                lore.add(ChatColor.GRAY + "radius and inflicts " + ChatColor.YELLOW + "Weakness " + weaknessLvl + ChatColor.GRAY + " for " + ChatColor.GREEN + weaknessDuration + ChatColor.GRAY + " seconds");
                lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
                lore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + cooldown + ChatColor.DARK_GRAY + " seconds");
                lore.add(" ");
                lore.add(ChatColor.GRAY + "Gain " + ChatColor.DARK_AQUA + "Conduit Power" + ChatColor.GRAY + " when fully worn");
                lore.add(" ");
            } else {

                for (String l : createLore(armorTypes, stars)) {
                    lore.add(l);
                }
            }
        } else {

            for (String l : createLore(armorTypes, stars)) {
                lore.add(l);
            }
        }

        return lore;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createAttributes(@NotNull ArmorTypes armorTypes, int stars) {

        ItemStack item = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = item.getItemMeta();

        double knockbackRes = ARMOR_CONFIG.getDouble("knockBackRes") * 0.1;

        AttributeModifier knockbackResModifier = new AttributeModifier(UUID.randomUUID(), "generic.knockbackRes", knockbackRes, AttributeModifier.Operation.ADD_NUMBER, armorTypes.getEquipmentSlot());
        itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResModifier);


        item.setItemMeta(itemMeta);
        return item.getItemMeta().getAttributeModifiers();
    }

    @Override
    public HashMap<ItemStatKeys, Double> createItemStats(@NotNull ArmorTypes armorTypes, int stars) {

        String armorPiece = armorTypes.getName();

        double mana = ARMOR_CONFIG.getDouble(armorPiece + "Mana") * (1 + stars * starBonus);
        double magicDamage = ARMOR_CONFIG.getDouble(armorPiece + "MagicDamage") * (1 + stars * starBonus);
        double health = ARMOR_CONFIG.getDouble(armorPiece + "Health") * (1 + stars * starBonus);
        double armor = ARMOR_CONFIG.getDouble(armorPiece + "Armor") * (1 + stars * starBonus);
        double toughness = ARMOR_CONFIG.getDouble("toughness");
        double fortitude = ARMOR_CONFIG.getDouble("fortitude");

        HashMap<ItemStatKeys, Double> itemStatKeysDoubleHashMap = new HashMap<>();
        itemStatKeysDoubleHashMap.put(ItemStatKeys.MAX_MANA_KEY, mana);
        itemStatKeysDoubleHashMap.put(ItemStatKeys.MAGIC_DAMAGE_KEY, magicDamage);
        itemStatKeysDoubleHashMap.put(ItemStatKeys.HEALTH_KEY, health);
        itemStatKeysDoubleHashMap.put(ItemStatKeys.ARMOR_KEY, armor);
        itemStatKeysDoubleHashMap.put(ItemStatKeys.TOUGHNESS_KEY, toughness);
        itemStatKeysDoubleHashMap.put(ItemStatKeys.FORTITUDE_KEY, fortitude);
        return itemStatKeysDoubleHashMap;
    }

    @Override
    public ItemForger createArmorPiece(@NotNull ArmorTypes armorTypes, int stars) {

        String armorPieceString = armorTypes.getName();

        ItemForger itemForger = new ItemForger(Material.PLAYER_HEAD);

        try {
            String armorName = armorPieceString.substring(0,1).toUpperCase() + armorPieceString.substring(1);

            String HelmetURL = ARMOR_CONFIG.getString("HelmetURL");

            if (armorTypes.equals(ArmorTypes.HELMET)) {
                itemForger = new ItemForger(Material.PLAYER_HEAD);
                itemForger.setPlayerHeadSkin(HelmetURL, ArmorCMD.STORM_LORD_ARMOR.getHelmet(), ArmorCMD.STORM_LORD_ARMOR.getHelmet());
                itemForger.setCustomModelData(ArmorCMD.STORM_LORD_ARMOR.getHelmet());

            } else if (armorTypes.equals(ArmorTypes.CHESTPLATE)) {
                itemForger = new ItemForger(Material.LEATHER_CHESTPLATE);
                itemForger.LeatherArmorColor(0,213,255);
                itemForger.setCustomModelData(ArmorCMD.STORM_LORD_ARMOR.getChestplate());

            } else if (armorTypes.equals(ArmorTypes.LEGGINGS)) {
                itemForger = new ItemForger(Material.LEATHER_LEGGINGS);
                itemForger.LeatherArmorColor(0,160,191);
                itemForger.setCustomModelData(ArmorCMD.STORM_LORD_ARMOR.getLeggings());

            } else if (armorTypes.equals(ArmorTypes.BOOTS)) {
                itemForger = new ItemForger(Material.LEATHER_BOOTS);
                itemForger.LeatherArmorColor(0,124,148);
                itemForger.setCustomModelData(ArmorCMD.STORM_LORD_ARMOR.getBoots());

            } else {
                // TODO: Throw some error here or something idk
                throw new IllegalArgumentException(ChatColor.RED + "Not armor piece type: " + armorPieceString);
            }

            itemForger.setName(ItemNameBuilder.name("Storm Lord " + armorName, stars));
            itemForger.setStars(stars);
            itemForger.setLore(createLore(armorTypes, stars));
            itemForger.setAttributeModifiers(createAttributes(armorTypes, stars), false);
            //itemForger.setRarity(PluginItemRarity.Rarity.LEGENDARY);
            itemForger.setRarity(PluginItemRarity.Rarity.FABLED);
            itemForger.setUnbreakable();
            itemForger.setFireResistant();
            //itemForger.setItemTypeKey(armorPiece);

            itemForger.setItemStats(createItemStats(armorTypes, stars));

        } catch (NullPointerException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something about this item is null: " + itemForger.getName());
            exception.printStackTrace();
        }

        return itemForger;
    }

    public static boolean isWearingFullSet(Player player)
    {
        if (ArmorEquipStats.isWearingFullSet(player, ArmorCMD.STORM_LORD_ARMOR.getHelmet(), ArmorCMD.STORM_LORD_ARMOR.getChestplate(), ArmorCMD.STORM_LORD_ARMOR.getLeggings(), ArmorCMD.STORM_LORD_ARMOR.getBoots()))
        {
            return true;
        }
        return false;
    }

    @SpellCastHandler(spellCastType = SpellCastType.DROP_ITEM)
    @Override
    public void onDropItemCast(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        String abilityName = ARMOR_CONFIG.getString("abilityName");
        double availableMana = stats.getAvailableMana();
        double manaCost = ARMOR_CONFIG.getDouble("manaCost");
        int cooldown = ARMOR_CONFIG.getInt("cooldown");
        double baseDamage = ARMOR_CONFIG.getDouble("baseDamage");
        double blastRadius = ARMOR_CONFIG.getDouble("blastRadius");

        PlayerSpellCastEvent spellCastEvent = new PlayerSpellCastEvent(player, manaCost);
        double eventCost = spellCastEvent.getManaCost();

        ItemStack droppedItem = event.getItemDrop().getItemStack();
        ItemMeta droppedMeta = droppedItem.getItemMeta();

        if (isWearingFullSet(player) && droppedMeta != null && droppedMeta.hasEnchant(EnchantsClass.THUNDER_STRIKE))
        {
            event.setCancelled(true);
            if (EyeOfStormCooldown.checkCooldown(player))
            {
                Bukkit.getPluginManager().callEvent(spellCastEvent);
                if (availableMana > eventCost)
                {
                    if (!spellCastEvent.isCancelled())
                    {
                        stats.setAvailableMana(availableMana - eventCost);

                        int enchantLvl = droppedMeta.getEnchantLevel(EnchantsClass.THUNDER_STRIKE);
                        double finalDamage = baseDamage + Math.pow(enchantLvl, 2);

                        for (LivingEntity livingEntity : player.getLocation().getNearbyLivingEntities(blastRadius))
                        {
                            livingEntity.getWorld().strikeLightningEffect(livingEntity.getLocation());
                            if (livingEntity != player)
                            {
                                PlayerMagicDamageEvent magicDamageEvent = new PlayerMagicDamageEvent(player, livingEntity, finalDamage, true);
                                Bukkit.getPluginManager().callEvent(magicDamageEvent);
                                if (!magicDamageEvent.isCancelled())
                                {
                                    PlayerMagicDamageEvent.run(player, livingEntity, finalDamage, true);
                                }
                            }
                        }

                        EyeOfStormCooldown.setCooldown(player, cooldown);
                        player.sendMessage(Messages.abilityUse(abilityName));
                    }
                } else
                {
                    player.sendMessage(Messages.notEnoughMana());
                }
            } else
            {
                player.sendMessage(Messages.abilityCooldown(abilityName));
            }

        }
    }
}

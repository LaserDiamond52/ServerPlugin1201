package net.laserdiamond.ventureplugin.items.armor.StormLord.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantsClass;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.armor.StormLord.Config.StormLordArmorConfig;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemNameBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.events.abilities.*;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
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


public class StormLordArmorManager extends VentureArmorSet implements AbilityCasting.DropItemSpell {

    private final VenturePlugin plugin = VenturePlugin.getInstance();
    private final StormLordArmorConfig armorConfig = plugin.getStormLordArmorConfig();

    @Override
    public @NotNull String armorSetName() {
        return "Storm Lord";
    }

    @Override
    public ArmorCMD setArmorCMD() {
        return ArmorCMD.STORM_LORD_ARMOR;
    }

    @Override
    public Material setArmorPieceMaterial(ArmorPieceTypes armorPieceTypes) {
        Material material = null;
        switch (armorPieceTypes)
        {
            case HELMET -> material = Material.PLAYER_HEAD;
            case CHESTPLATE -> material = Material.LEATHER_CHESTPLATE;
            case LEGGINGS -> material = Material.LEATHER_LEGGINGS;
            case BOOTS -> material = Material.LEATHER_BOOTS;
        }
        return material;
    }

    @Override
    public GetVarFile config() {
        return plugin.getStormLordArmorConfig();
    }

    @Override
    public List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars)
    {
        HashMap<VentureItemStatKeys, Double> itemStatKeysMap = createItemStats(armorPieceTypes, stars);

        List<String> lore = new ArrayList<>();

        int blastRadius = config().getInt("blastRadius");
        int weaknessLvl = config().getInt("weaknessLvl");
        double weaknessDuration = config().getDouble("weaknessDuration");
        int cooldown = config().getInt("cooldown");
        double manaCost = config().getDouble("manaCost");


        this.createStatLore(lore, itemStatKeysMap);
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
    public List<String> createPlayerLore(@NotNull Player player, @NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        HashMap<VentureItemStatKeys, Double> itemStatKeysMap = createItemStats(armorPieceTypes, stars);

        List<String> lore = new ArrayList<>();

        int blastRadius = config().getInt("blastRadius");
        int weaknessLvl = config().getInt("weaknessLvl");
        double weaknessDuration = config().getDouble("weaknessDuration");
        int cooldown = config().getInt("cooldown");
        double manaCost = config().getDouble("manaCost");

        double baseDamage = config().getDouble("baseDamage");
        if (player.getInventory().getItemInMainHand().getItemMeta() != null)
        {
            ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
            if (itemMeta.hasEnchant(EnchantsClass.THUNDER_STRIKE)) {
                int thunderStrikeLevel = player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(EnchantsClass.THUNDER_STRIKE);
                int thunderStrikeBaseDamage = thunderStrikeLevel*thunderStrikeLevel;
                double finalBaseDamage = baseDamage + thunderStrikeBaseDamage;

                this.createStatLore(lore, itemStatKeysMap);
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
            } else
            {
                lore.addAll(createLore(armorPieceTypes, stars));
            }
        } else
        {
            lore.addAll(createLore(armorPieceTypes, stars));
        }

        return lore;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createAttributes(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        ItemStack item = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = item.getItemMeta();

        double knockbackRes = armorConfig.getDouble("knockBackRes") * 0.1;

        AttributeModifier knockbackResModifier = new AttributeModifier(UUID.randomUUID(), "generic.knockbackRes", knockbackRes, AttributeModifier.Operation.ADD_NUMBER, armorPieceTypes.getEquipmentSlot());
        itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, knockbackResModifier);

        item.setItemMeta(itemMeta);
        return item.getItemMeta().getAttributeModifiers();
    }

    @Override
    public HashMap<VentureItemStatKeys, Double> createItemStats(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        String armorPiece = armorPieceTypes.getName();

        double mana = config().getDouble(armorPiece + "Mana") * (1 + stars * this.starBonus);
        double magicDamage = config().getDouble(armorPiece + "MagicDamage") * (1 + stars * this.starBonus);
        double health = config().getDouble(armorPiece + "Health") * (1 + stars * this.starBonus);
        double armor = config().getDouble(armorPiece + "Armor") * (1 + stars * this.starBonus);
        double toughness = config().getDouble("toughness");
        double fortitude = config().getDouble("fortitude");

        HashMap<VentureItemStatKeys, Double> itemStatKeysDoubleHashMap = new HashMap<>();
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MAX_MANA_KEY, mana);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_MAGIC_DAMAGE_KEY, magicDamage);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_HEALTH_KEY, health);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_DEFENSE_KEY, armor);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_TOUGHNESS_KEY, toughness);
        itemStatKeysDoubleHashMap.put(VentureItemStatKeys.ARMOR_FORTITUDE_KEY, fortitude);
        return itemStatKeysDoubleHashMap;
    }

    @Override
    public ItemForger createArmorPiece(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        String armorPieceString = armorPieceTypes.getName();

        ItemForger itemForger = new ItemForger(Material.AIR);

        try {
            String armorName = armorPieceString.substring(0,1).toUpperCase() + armorPieceString.substring(1);

            String HelmetURL = config().getString("HelmetURL");

            if (armorPieceTypes.equals(ArmorPieceTypes.HELMET)) {
                itemForger = new ItemForger(Material.PLAYER_HEAD)
                        .setPlayerHeadSkin(HelmetURL, ArmorCMD.STORM_LORD_ARMOR.getHelmet(), ArmorCMD.STORM_LORD_ARMOR.getHelmet())
                        .setCustomModelData(ArmorCMD.STORM_LORD_ARMOR.getHelmet());

            } else if (armorPieceTypes.equals(ArmorPieceTypes.CHESTPLATE)) {
                itemForger = new ItemForger(Material.LEATHER_CHESTPLATE)
                        .LeatherArmorColor(0,213,255)
                        .setCustomModelData(ArmorCMD.STORM_LORD_ARMOR.getChestplate());

            } else if (armorPieceTypes.equals(ArmorPieceTypes.LEGGINGS)) {
                itemForger = new ItemForger(Material.LEATHER_LEGGINGS)
                        .LeatherArmorColor(0,160,191)
                        .setCustomModelData(ArmorCMD.STORM_LORD_ARMOR.getLeggings());

            } else if (armorPieceTypes.equals(ArmorPieceTypes.BOOTS)) {
                itemForger = new ItemForger(Material.LEATHER_BOOTS)
                        .LeatherArmorColor(0,124,148)
                        .setCustomModelData(ArmorCMD.STORM_LORD_ARMOR.getBoots());

            }

            itemForger.setName(ItemNameBuilder.name(armorSetName() + armorName, stars))
                    .setStars(stars)
                    .setLore(createLore(armorPieceTypes, stars))
                    .setRarity(VentureItemRarity.Rarity.FABLED)
                    .setUnbreakable(true)
                    .setFireResistant(true)
                    .setItemStats(createItemStats(armorPieceTypes, stars));

        } catch (NullPointerException exception) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Something about this item is null: " + itemForger.getName());
            exception.printStackTrace();
        }

        return itemForger;
    }

    @AbilityHandler(abilityCastType = AbilityCastType.DROP_ITEM)
    @Override
    public void onDropItemCast(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        String abilityName = armorConfig.getString("abilityName");
        double availableMana = stats.getAvailableMana();
        double manaCost = armorConfig.getDouble("manaCost");
        int cooldown = armorConfig.getInt("cooldown");
        double baseDamage = armorConfig.getDouble("baseDamage");
        double blastRadius = armorConfig.getDouble("blastRadius");

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

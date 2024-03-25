package net.laserdiamond.ventureplugin.items.armor.StormLord.Components;

import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.events.abilities.*;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class StormLordArmorManager extends VentureArmorSet implements AbilityCasting.DropItemSpell {

    public StormLordArmorManager()
    {
        registerArmorSet();
        plugin.getAbilityListeners().add(this);
    }

    @Override
    public @NotNull String armorSetName() {
        return "Storm Lord";
    }

    @Override
    public GetVarFile config() {
        return plugin.getStormLordArmorConfig();
    }

    @Override
    public ArmorCMD setArmorCMD() {
        return ArmorCMD.STORM_LORD_ARMOR;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public boolean isUnbreakable() {
        return true;
    }

    @Override
    public VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.FABLED;
    }

    @Override
    public Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
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
    public List<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        int blastRadius = config().getInt("blastRadius");
        int weaknessLvl = config().getInt("weaknessLvl");
        double weaknessDuration = config().getDouble("weaknessDuration");
        int cooldown = config().getInt("cooldown");
        double manaCost = config().getDouble("manaCost");

        List<String> lore = super.createLore(armorPieceTypes, stars);
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

        int blastRadius = config().getInt("blastRadius");
        int weaknessLvl = config().getInt("weaknessLvl");
        double weaknessDuration = config().getDouble("weaknessDuration");
        int cooldown = config().getInt("cooldown");
        double manaCost = config().getDouble("manaCost");

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemMeta mainHandMeta = mainHand.getItemMeta();

        List<String> lore = super.createPlayerLore(player, armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Full Set Bonus: Eye of the Storm" + ChatColor.YELLOW + " " + ChatColor.BOLD + "Press Q");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Grants all items with the " + ChatColor.AQUA + "Thunder Strike" + ChatColor.GRAY + " enchantment");
        lore.add(ChatColor.GRAY + "the ability to unleash a devastating lightning blast");

        if (mainHandMeta != null && mainHandMeta.hasEnchant(VentureEnchants.THUNDER_STRIKE))
        {
            int enchantLvl = mainHandMeta.getEnchantLevel(VentureEnchants.THUNDER_STRIKE);
            double baseDamage = config().getDouble("baseDamage");
            double finalDamage = baseDamage + Math.pow(enchantLvl, 2);

            lore.add(ChatColor.GRAY + "that deals " + ChatColor.AQUA + finalDamage + ChatColor.RESET + ChatColor.GRAY + " base damage to nearby mobs in a " + ChatColor.GOLD + blastRadius + ChatColor.GRAY + " block");

        } else {
            lore.add(ChatColor.GRAY + "that deals " + ChatColor.AQUA + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.GRAY + " base damage to nearby mobs in a " + ChatColor.GOLD + blastRadius + ChatColor.GRAY + " block");
        }

        lore.add(ChatColor.GRAY + "radius and inflicts " + ChatColor.YELLOW + "Weakness " + weaknessLvl + ChatColor.GRAY + " for " + ChatColor.GREEN + weaknessDuration + ChatColor.GRAY + " seconds");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
        lore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + cooldown + ChatColor.DARK_GRAY + " seconds");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Gain " + ChatColor.DARK_AQUA + "Conduit Power" + ChatColor.GRAY + " when fully worn");
        lore.add(" ");

        return lore;
    }

    @Override
    public boolean registerPlayerArmorSet() {
        return true;
    }

    @AbilityHandler(abilityCastType = AbilityCastType.DROP_ITEM)
    @Override
    public void onDropItemCast(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        String abilityName = config().getString("abilityName");
        double availableMana = stats.getAvailableMana();
        double manaCost = config().getDouble("manaCost");
        int cooldown = config().getInt("cooldown");
        double baseDamage = config().getDouble("baseDamage");
        double blastRadius = config().getDouble("blastRadius");

        PlayerSpellCastEvent spellCastEvent = new PlayerSpellCastEvent(player, manaCost);
        double eventCost = spellCastEvent.getManaCost();

        ItemStack droppedItem = event.getItemDrop().getItemStack();
        ItemMeta droppedMeta = droppedItem.getItemMeta();
        // TODO: Rewrite to be cleaner
        if (isWearingFullSet(player) && droppedMeta != null && droppedMeta.hasEnchant(VentureEnchants.THUNDER_STRIKE))
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

                        int enchantLvl = droppedMeta.getEnchantLevel(VentureEnchants.THUNDER_STRIKE);
                        double finalDamage = baseDamage + Math.pow(enchantLvl, 2);

                        for (LivingEntity livingEntity : player.getLocation().getNearbyLivingEntities(blastRadius))
                        {
                            livingEntity.getWorld().strikeLightningEffect(livingEntity.getLocation());
                            if (livingEntity != player)
                            {
                                PlayerMagicDamageEvent magicDamageEvent = new PlayerMagicDamageEvent(player, livingEntity, finalDamage, true);
                                Bukkit.getPluginManager().callEvent(magicDamageEvent);
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

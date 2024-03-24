package net.laserdiamond.ventureplugin.items.armor.Blaze.Components;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.*;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class BlazeArmorManager extends VentureArmorSet implements AbilityCasting.RunnableSpell {

    // TODO: Create rest of lore for armor
    private final VenturePlugin plugin = VenturePlugin.getInstance();

    public BlazeArmorManager()
    {
        registerArmorSet();
        plugin.getAbilityListeners().add(this);
    }
    @Override
    public String armorSetName() {
        return "Blaze";
    }

    @Override
    public GetVarFile config()
    {
        return plugin.getBlazeArmorConfig();
    }

    @Override
    public ArmorCMD setArmorCMD() {
        return ArmorCMD.BLAZE_ARMOR;
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
        return VentureItemRarity.Rarity.LEGENDARY;
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

        double auraDamage = config().getDouble("auraBaseDamage");
        double auraRadius = config().getDouble("auraRadius");
        double manaCost = config().getDouble("manaCost");
        String abilityName = config().getString("abilityName");

        List<String> lore = super.createLore(armorPieceTypes, stars);
        // TODO: Add item lore here
        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName + ChatColor.YELLOW + ChatColor.BOLD + " Hold Sneak");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "While sneaking, set mobs within a " + ChatColor.GOLD + auraRadius + ChatColor.GRAY + " block");
        lore.add(ChatColor.GRAY + "radius ablaze and deal " + ChatColor.AQUA + auraDamage + ChatColor.GRAY + " damage/second");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
        lore.add(" ");

        // Full Set Bonus: Blazing Aura HOLD SNEAK
        //
        // While sneaking, set mobs within a 5 block
        // radius ablaze and deal 5 damage/second

        return lore;
    }


    @AbilityHandler(abilityCastType = AbilityCastType.RUNNABLE)
    @Override
    public void onActivate(Player player, int timer)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        //String abilityName = config().getString("abilityName");
        double manaCost = config().getDouble("manaCost");
        double auraRadius = config().getDouble("auraRadius");
        double auraDamage = config().getDouble("auraDamage");
        double availableMana = stats.getAvailableMana();

        PlayerSpellCastEvent spellCastEvent = new PlayerSpellCastEvent(player, manaCost);
        double eventCost = spellCastEvent.getManaCost();

        if (!isWearingFullSet(player))
        {
            return;
        }
        if (timer == 20 && player.isSneaking())
        {
            if (!(availableMana >= eventCost))
            {
                player.sendMessage(Messages.notEnoughMana());
                return;
            }
            Bukkit.getPluginManager().callEvent(spellCastEvent);
            if (!spellCastEvent.isCancelled())
            {
                stats.setAvailableMana(availableMana - eventCost);

                for (LivingEntity livingEntity : player.getLocation().getNearbyLivingEntities(auraRadius))
                {
                    if (livingEntity != player)
                    {
                        PlayerMagicDamageEvent magicDamageEvent = new PlayerMagicDamageEvent(player, livingEntity, auraDamage, true);
                        Bukkit.getPluginManager().callEvent(magicDamageEvent);
                        if (!magicDamageEvent.isCancelled())
                        {
                            // FIXME:
                            magicDamageEvent.run();
                        }
                    }
                }
            } else {

            }
        }


    }
}

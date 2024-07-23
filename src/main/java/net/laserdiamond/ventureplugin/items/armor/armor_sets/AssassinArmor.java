package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.abilities.cooldown.AssassinCloakCooldown;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorMaterial;
import net.laserdiamond.ventureplugin.items.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public final class AssassinArmor extends VentureArmorSet implements AbilityCasting.toggleSneakAbility {

    public AssassinArmor(VenturePlugin plugin) {
        super(plugin);
        plugin.getAbilityListeners().add(this);
    }

    @Override
    protected String armorName() {
        return "Assassin";
    }

    @Override
    public VentureArmorMaterial ventureArmorMaterial() {
        return VentureArmorMaterial.ASSASSIN_ARMOR;
    }

    @Override
    protected VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.LEGENDARY;
    }

    @Override
    protected boolean isUnbreakable() {
        return true;
    }

    @Override
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {
        double healthForAbility = getArmorConfig().getDouble("healthActivateBonus");
        int invisibilityDuration = getArmorConfig().getInt("invisibilityDuration");
        double blindRadius = getArmorConfig().getDouble("blindRadius");
        int blindDuration = getArmorConfig().getInt("blindDuration");
        double manaCost = getArmorConfig().getDouble("manaCost");
        double cooldown = getArmorConfig().getDouble("cooldown");
        String abilityName = getArmorConfig().getString("abilityName");

        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);

        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName + ChatColor.YELLOW + ChatColor.BOLD + " Toggle Sneak");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "When below " + ChatColor.RED + healthForAbility + "%❤" + ChatColor.GRAY + ", turn invisible for " + ChatColor.GOLD + invisibilityDuration + ChatColor.GRAY + " seconds");
        lore.add(ChatColor.GRAY + "and blind mobs/players within a " + ChatColor.GOLD + blindRadius + ChatColor.GRAY + " block radius");
        lore.add(ChatColor.GRAY + "for " + ChatColor.GOLD + blindDuration + ChatColor.GRAY + " seconds");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
        lore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + cooldown + ChatColor.DARK_GRAY + " seconds");
        lore.add(" ");
        lore.addLast(ItemStringBuilder.addItemStringRarity(rarity(), armorPieceTypes));
        return lore;

        // Full Set Bonus: Assassin's Cloak
        //
        // When below 30%❤, turn invisible for 20 seconds
        // and blind mobs/players within a 10 block radius
        // for 15 seconds

    }


    @AbilityHandler(abilityCastType = AbilityCastType.TOGGLE_SNEAK)
    @Override
    public void onToggle(PlayerToggleSneakEvent event)
    {
        Player player = event.getPlayer();
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();

        double availableMana = stats.getAvailableMana();
        double maxHP = player.getMaxHealth();
        double currentHealth = player.getHealth();
        double healthToActivate = maxHP * getArmorConfig().getDouble("healthActivateBonus") * 0.01;
        double manaCost = getArmorConfig().getDouble("manaCost");
        int invisibilityDuration = getArmorConfig().getInt("invisibilityDuration") * 20;
        int blindDuration = getArmorConfig().getInt("blindDuration") * 20;
        double blindRadius = getArmorConfig().getDouble("blindRadius");
        int cooldown = getArmorConfig().getInt("cooldown");
        String abilityName = getArmorConfig().getString("abilityName");

        PlayerSpellCastEvent spellCastEvent = new PlayerSpellCastEvent(player, manaCost);
        double eventCost = spellCastEvent.getManaCost();

        if (!isWearingFullSet(player) || event.isSneaking())
        {
            return;
        }
        if (!(availableMana >= eventCost))
        {
            player.sendMessage(Messages.notEnoughMana());
            return;
        }
        Bukkit.getPluginManager().callEvent(spellCastEvent);
        if (spellCastEvent.isCancelled())
        {
            return;
        }
        if (AssassinCloakCooldown.checkCooldown(player))
        {
            if (currentHealth < healthToActivate)
            {
                player.sendMessage(Messages.abilityUse(abilityName));
                stats.setAvailableMana(availableMana - manaCost);

                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, invisibilityDuration, 0));

                for (LivingEntity livingEntity : player.getLocation().getNearbyLivingEntities(blindRadius))
                {
                    if (livingEntity != player)
                    {
                        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, blindDuration, 0));
                    }
                }

                AssassinCloakCooldown.setCooldown(player, cooldown);
            }
        } else
        {
            player.sendMessage(Messages.abilityCooldown(abilityName));
        }

    }
}

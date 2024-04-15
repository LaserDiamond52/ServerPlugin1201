package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.abilities.cooldown.SniperCooldown;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorCMD;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.util.VentureArmorSet;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public final class BoneTerrorArmor extends VentureArmorSet implements AbilityCasting.toggleSneakAbility, Listener {

    private final HashMap<UUID, Boolean> arrowBonusMap;
    public BoneTerrorArmor(VenturePlugin plugin) {
        super(plugin);
        plugin.getAbilityListeners().add(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        arrowBonusMap = new HashMap<>();
    }

    @Override
    protected ArmorConfig config() {
        return plugin.getBoneTerrorArmorConfig();
    }

    @Override
    protected String armorName() {
        return "Bone Terror";
    }

    @Override
    public ArmorCMD armorCMD() {
        return ArmorCMD.BONE_TERROR;
    }

    @Override
    protected Material armorPieceMaterials(ArmorPieceTypes armorPieceTypes) {
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
    protected VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.RARE;
    }

    @Override
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        double arrowDamageMultiplier = config().getDouble("arrowDamageMultiplier");
        double manaCost = config().getDouble("manaCost");
        double cooldown = config().getDouble("cooldown");
        String abilityName = config().getString("abilityName");

        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName + ChatColor.YELLOW + ChatColor.BOLD + " Press Sneak");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Your next arrow shot will deal " + ChatColor.GOLD + arrowDamageMultiplier + ChatColor.GRAY + "x more damage");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
        lore.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + cooldown + ChatColor.DARK_GRAY + " seconds");
        lore.add(" ");
        return lore;
    }

    @AbilityHandler(abilityCastType = AbilityCastType.TOGGLE_SNEAK)
    @Override
    public void onToggle(PlayerToggleSneakEvent event)
    {
        Player player = event.getPlayer();
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        double manaCost = config().getDouble("manaCost");
        int cooldown = config().getInt("cooldown");
        double availableMana = stats.getAvailableMana();
        String abilityName = config().getString("abilityName");

        PlayerSpellCastEvent spellCastEvent = new PlayerSpellCastEvent(player, manaCost);
        double eventCost = spellCastEvent.getManaCost();

        if (!arrowBonusMap.containsKey(player.getUniqueId()) || arrowBonusMap.get(player.getUniqueId()) == null)
        {
            arrowBonusMap.put(player.getUniqueId(), false);
        }

        boolean isActive = arrowBonusMap.get(player.getUniqueId());
        if (!isWearingFullSet(player) || event.isSneaking())
        {
            return;
        }
        if (SniperCooldown.checkCooldown(player))
        {
            if (!(availableMana >= eventCost))
            {
                player.sendMessage(Messages.notEnoughMana());
                return;
            }
            Bukkit.getPluginManager().callEvent(spellCastEvent);
            if (!spellCastEvent.isCancelled())
            {
                if (!isActive)
                {
                    stats.setAvailableMana(availableMana - eventCost);
                    player.sendMessage(Messages.abilityUse(abilityName));
                    arrowBonusMap.put(player.getUniqueId(), true);

                    SniperCooldown.setCooldown(player, cooldown);
                } else
                {
                    player.sendMessage(Messages.abilityAlreadyActive(abilityName));
                }
            }

        } else
        {
            player.sendMessage(Messages.abilityCooldown(abilityName));
        }
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Arrow arrow)
        {
            if (arrow.getShooter() instanceof Player player)
            {
                if (event.getEntity() instanceof LivingEntity livingEntity)
                {
                    if (arrowBonusMap.get(player.getUniqueId()) != null)
                    {
                        if (arrowBonusMap.get(player.getUniqueId()))
                        {
                            double damage = event.getDamage();
                            double damageMultiplier = config().getDouble("arrowDamageMultiplier");
                            event.setDamage(damage * damageMultiplier);

                            arrowBonusMap.put(player.getUniqueId(), false);

                            player.sendMessage(ChatColor.DARK_GRAY + "Your enhanced arrow hit a " + livingEntity.getName() + ChatColor.DARK_GRAY + " for " + ChatColor.DARK_PURPLE + event.getDamage() + ChatColor.DARK_GRAY + " damage!");

                        } else {
                            player.sendMessage("bonus not active");
                        }
                    }
                }

            }
        }
    }
}

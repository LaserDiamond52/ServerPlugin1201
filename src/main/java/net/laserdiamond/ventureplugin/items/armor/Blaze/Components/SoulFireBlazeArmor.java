package net.laserdiamond.ventureplugin.items.armor.Blaze.Components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.File.GetVarFile;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class SoulFireBlazeArmor extends VentureArmorSet implements AbilityCasting.RunnableAbility, AbilityCasting.OnDeathAbility {

    private final HashMap<UUID, Integer> soulFireBlazeAuraTimer;
    public SoulFireBlazeArmor(VenturePlugin plugin) {
        super(plugin);
        plugin.getAbilityListeners().add(this);
        soulFireBlazeAuraTimer = new HashMap<>();
    }

    @AbilityHandler(abilityCastType = AbilityCastType.RUNNABLE)
    @Override
    public void onActivate(Player player) {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        double manaCost = config().getDouble("manaCost");
        double auraRadius = config().getDouble("auraRadius");
        double auraDamage = config().getDouble("auraBaseDamage");
        double availableMana = stats.getAvailableMana();

        PlayerSpellCastEvent spellCastEvent = new PlayerSpellCastEvent(player, manaCost);
        double eventCost = spellCastEvent.getManaCost();

        if (!soulFireBlazeAuraTimer.containsKey(player.getUniqueId()) || soulFireBlazeAuraTimer.get(player.getUniqueId()) == null)
        {
            soulFireBlazeAuraTimer.put(player.getUniqueId(), 0);
        }

        Integer playerTimer = soulFireBlazeAuraTimer.get(player.getUniqueId());

        if (!isWearingFullSet(player))
        {
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0));
        if (player.isSneaking())
        {
            soulFireBlazeAuraTimer.put(player.getUniqueId(), playerTimer + 1);

            if (playerTimer >= 20)
            {
                soulFireBlazeAuraTimer.put(player.getUniqueId(), 0);
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
                            livingEntity.setFireTicks(livingEntity.getFireTicks() + 100);
                            PlayerMagicDamageEvent magicDamageEvent = new PlayerMagicDamageEvent(player, livingEntity, auraDamage, true);
                            Bukkit.getPluginManager().callEvent(magicDamageEvent);
                        }
                    }
                }
            }
        }
    }

    @AbilityHandler(abilityCastType = AbilityCastType.ON_KILL)
    @Override
    public void onKill(Player player, LivingEntity killedEntity) {
        double maxHealth = player.getMaxHealth();
        double lifeStealAmount = maxHealth * config().getDouble("lifeStealAmount") * 0.01;
        double currentHealth = player.getHealth();

        player.setHealth(Math.min(currentHealth + lifeStealAmount, maxHealth));
    }

    @Override
    public GetVarFile config() {
        return plugin.getSoulFireBlazeArmorConfig();
    }

    @Override
    public String armorSetName() {
        return "Soul Fire Blaze";
    }

    @Override
    public ArmorCMD getArmorCMD() {
        return ArmorCMD.SOUL_FIRE_BLAZE;
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

        double auraDamage = config().getDouble("auraBaseDamage");
        double auraRadius = config().getDouble("auraRadius");
        double manaCost = config().getDouble("manaCost");
        String abilityName1 = config().getString("abilityName1");
        double lifeStealAmt = config().getDouble("lifeStealAmount");
        String abilityName2 = config().getString("abilityName2");

        List<String> lore = super.createLore(armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName1 + ChatColor.YELLOW + ChatColor.BOLD + " Hold Sneak");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "While sneaking, set mobs within a " + ChatColor.GOLD + auraRadius + ChatColor.GRAY + " block");
        lore.add(ChatColor.GRAY + "radius ablaze and deal " + ChatColor.AQUA + auraDamage + ChatColor.GRAY + " damage/second");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Bonus Ability: " + abilityName2);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Upon killing a mob/player, instantly");
        lore.add(ChatColor.GRAY + "regain " + ChatColor.RED + lifeStealAmt + ChatColor.GRAY + "% of your max " + ChatColor.RED + "❤");
        lore.add(" ");

        return lore;
    }
}

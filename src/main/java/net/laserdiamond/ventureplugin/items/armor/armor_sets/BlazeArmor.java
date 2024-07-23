package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorMaterial;
import net.laserdiamond.ventureplugin.items.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import net.laserdiamond.ventureplugin.util.particles.Particles;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public final class BlazeArmor extends VentureArmorSet implements AbilityCasting.RunnableAbility {

    // TODO: Create rest of lore for armor
    //private final VenturePlugin plugin = VenturePlugin.getInstance();
    private final HashMap<UUID, Integer> blazeAuraTimer;

    public BlazeArmor(VenturePlugin plugin)
    {
        super(plugin);
        plugin.getAbilityListeners().add(this);
        blazeAuraTimer = new HashMap<>();
    }
    @Override
    protected String armorName() {
        return "Blaze";
    }

    @Override
    public VentureArmorMaterial ventureArmorMaterial() {
        return VentureArmorMaterial.BLAZE_ARMOR;
    }

    @Override
    protected boolean isFireResistant() {
        return true;
    }

    @Override
    protected boolean isUnbreakable() {
        return true;
    }

    @Override
    protected VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.LEGENDARY;
    }

    @Override
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        double auraDamage = getArmorConfig().getDouble("auraBaseDamage");
        double auraRadius = getArmorConfig().getDouble("auraRadius");
        double manaCost = getArmorConfig().getDouble("manaCost");
        String abilityName = getArmorConfig().getString("abilityName");

        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName + ChatColor.YELLOW + ChatColor.BOLD + " Hold Sneak");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "While sneaking, set mobs within a " + ChatColor.GOLD + auraRadius + ChatColor.GRAY + " block");
        lore.add(ChatColor.GRAY + "radius ablaze and deal " + ChatColor.AQUA + auraDamage + ChatColor.GRAY + " damage/second");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost);
        lore.add(" ");
        lore.addLast(ItemStringBuilder.addItemStringRarity(rarity(), armorPieceTypes));

        //
        // Full Set Bonus: Blazing Aura HOLD SNEAK
        //
        // While sneaking, set mobs within a 5 block
        // radius ablaze and deal 5 damage/second
        //
        // Bonus Ability : Soul Stealer
        //
        // Upon killing a mob/player, instantly
        // regain 10% of your max health
        //

        return lore;
    }


    int radius = 1;
    double[] y = {0};
    @AbilityHandler(abilityCastType = AbilityCastType.RUNNABLE)
    @Override
    public void onActivate(Player player)
    {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        String abilityName = getArmorConfig().getString("abilityName");
        double manaCost = getArmorConfig().getDouble("manaCost");
        double auraRadius = getArmorConfig().getDouble("auraRadius");
        double auraDamage = getArmorConfig().getDouble("auraBaseDamage");
        double availableMana = stats.getAvailableMana();

        PlayerSpellCastEvent spellCastEvent = new PlayerSpellCastEvent(player, manaCost);
        double eventCost = spellCastEvent.getManaCost();

        if (!blazeAuraTimer.containsKey(player.getUniqueId()) || blazeAuraTimer.get(player.getUniqueId()) == null)
        {
            blazeAuraTimer.put(player.getUniqueId(), 0);
        }

        Integer playerTimer = blazeAuraTimer.get(player.getUniqueId());

        if (!isWearingFullSet(player))
        {
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0));
        if (player.isSneaking())
        {
            blazeAuraTimer.put(player.getUniqueId(), playerTimer + 1);
            if (availableMana >= eventCost)
            {
                Particles.spiralAura(Particle.FLAME, player.getLocation(), radius, y);
                y[0] = y[0] + 0.125;
            }

            if (playerTimer >= 20)
            {
            blazeAuraTimer.put(player.getUniqueId(), 0);
            radius = 1;
            y = new double[]{0};
            if (!(availableMana >= eventCost))
            {
                player.sendMessage(Messages.notEnoughMana());
                return;
            }
            Bukkit.getPluginManager().callEvent(spellCastEvent);
            if (!spellCastEvent.isCancelled())
            {
                stats.setAvailableMana(availableMana - 1);
                player.sendMessage(Messages.abilityUse(abilityName));

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
}

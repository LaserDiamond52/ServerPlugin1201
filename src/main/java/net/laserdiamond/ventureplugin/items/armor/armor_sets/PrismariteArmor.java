package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.util.ItemStringBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorMaterial;
import net.laserdiamond.ventureplugin.items.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public final class PrismariteArmor extends VentureArmorSet implements AbilityCasting.RunnableAbility {

    private final HashMap<UUID, Integer> guardianLaserTimer;
    public PrismariteArmor(VenturePlugin plugin) {
        super(plugin);
        plugin.getAbilityListeners().add(this);
        guardianLaserTimer = new HashMap<>();
    }

    @Override
    protected String armorName() {
        return "Prismarite";
    }

    @Override
    public VentureArmorMaterial ventureArmorMaterial() {
        return VentureArmorMaterial.PRISMARITE_ARMOR;
    }

    @Override
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        double manaCost = getArmorConfig().getDouble("manaCost");
        double laserDamage = getArmorConfig().getDouble("laserDamage");
        String abilityName = getArmorConfig().getString("abilityName");

        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName + ChatColor.YELLOW + ChatColor.BOLD + " Hold Sneak");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Cast an eye laser that damages mobs in");
        lore.add(ChatColor.GRAY + "path, dealing " + ChatColor.AQUA + laserDamage + ChatColor.GRAY + " damage per second");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost + ChatColor.DARK_GRAY + " per second");
        lore.add(" ");
        lore.addLast(ItemStringBuilder.addItemStringRarity(rarity(), armorPieceTypes));
        return lore;

        // Full Set Bonus: Guardian's Laser
        //
        // Cast an eye laser that damages mobs in
        // its path, dealing 10 damage per second
        // Mana Cost: 25 per second
        //
    }

    @AbilityHandler(abilityCastType = AbilityCastType.RUNNABLE)
    @Override
    public void onActivate(Player player) {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        double availableMana = stats.getAvailableMana();
        double manaCost = getArmorConfig().getDouble("manaCost");
        double laserDamage = getArmorConfig().getDouble("laserDamage");
        String abilityName = getArmorConfig().getString("abilityName");

        PlayerSpellCastEvent spellCastEvent = new PlayerSpellCastEvent(player, manaCost);
        double eventCost = spellCastEvent.getManaCost();

        if (!guardianLaserTimer.containsKey(player.getUniqueId()) || guardianLaserTimer.get(player.getUniqueId()) == null)
        {
            guardianLaserTimer.put(player.getUniqueId(), 0);
        }

        Integer playerTimer = guardianLaserTimer.get(player.getUniqueId());

        if (!isWearingFullSet(player))
        {
            return;
        }
        if (player.isSneaking())
        {
            guardianLaserTimer.put(player.getUniqueId(), playerTimer + 1);

            Bukkit.getPluginManager().callEvent(spellCastEvent);
            if (!spellCastEvent.isCancelled())
            {
                if ((playerTimer >= 20) && (availableMana >= eventCost))
                {
                    player.sendMessage(Messages.abilityUse(abilityName));
                }

                int length = 3;
                double particleDistance = 0.0075;
                Location eyeLoc = player.getEyeLocation();

                double i = 1;
                while (i < length)
                {
                    Vector vector = eyeLoc.getDirection().multiply(i);
                    eyeLoc.add(vector);
                    if (eyeLoc.getBlock().isPassable())
                    {
                        eyeLoc.getWorld().spawnParticle(Particle.REDSTONE, eyeLoc, 1, new Particle.DustOptions(Color.AQUA, 0.75F));
                    } else
                    {
                        break;
                    }

                    if (playerTimer >= 20)
                    {
                        guardianLaserTimer.put(player.getUniqueId(), 0);

                        if (!(availableMana >= eventCost))
                        {
                            player.sendMessage(Messages.notEnoughMana());
                            return;
                        }

                        stats.setAvailableMana(availableMana - eventCost);


                        for (LivingEntity livingEntity : eyeLoc.getNearbyLivingEntities(0.5))
                        {
                            if (livingEntity != player)
                            {
                                PlayerMagicDamageEvent magicDamageEvent = new PlayerMagicDamageEvent(player, livingEntity, laserDamage, true);
                                Bukkit.getPluginManager().callEvent(magicDamageEvent);
                            }
                        }
                    }
                    i += particleDistance;
                }
            }
        }
    }

    @Override
    protected boolean isUnbreakable() {
        return true;
    }

    @Override
    protected VentureItemRarity.Rarity rarity() {
        return VentureItemRarity.Rarity.EPIC;
    }
}

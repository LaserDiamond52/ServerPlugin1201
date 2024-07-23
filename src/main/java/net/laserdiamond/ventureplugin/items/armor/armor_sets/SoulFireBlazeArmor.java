package net.laserdiamond.ventureplugin.items.armor.armor_sets;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.util.*;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorMaterial;
import net.laserdiamond.ventureplugin.items.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import net.laserdiamond.ventureplugin.util.particles.Particles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public final class SoulFireBlazeArmor extends VentureArmorSet implements AbilityCasting.RunnableAbility, AbilityCasting.onKillAbility, AbilityCasting.attackAbility {

    private final HashMap<UUID, Integer> soulFireBlazeAuraTimer;

    public SoulFireBlazeArmor(VenturePlugin plugin) {
        super(plugin);
        plugin.getAbilityListeners().add(this);
        soulFireBlazeAuraTimer = new HashMap<>();
    }

    int radius = 1;
    double[] y = {0};
    @AbilityHandler(abilityCastType = AbilityCastType.RUNNABLE)
    @Override
    public void onActivate(Player player) {
        StatPlayer statPlayer = new StatPlayer(player);
        Stats stats = statPlayer.getStats();
        double manaCost = getArmorConfig().getDouble("manaCost");
        double auraRadius = getArmorConfig().getDouble("auraRadius");
        double auraDamage = getArmorConfig().getDouble("auraBaseDamage");
        String abilityName = getArmorConfig().getString("abilityName1");
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
            if (availableMana >= eventCost)
            {
                Particles.spiralAura(Particle.SOUL_FIRE_FLAME, player.getLocation(), radius, y);
                y[0] = y[0] + 0.125;
            }

            if (playerTimer >= 20)
            {
                soulFireBlazeAuraTimer.put(player.getUniqueId(), 0);
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
                    stats.setAvailableMana(availableMana - eventCost);
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

    @AbilityHandler(abilityCastType = AbilityCastType.ON_KILL)
    @Override
    public void onKill(Player player, LivingEntity killedEntity) {

        PlayerInventory playerInv = player.getInventory();

        ItemStack[] armor = playerInv.getArmorContents();

        double damageBoost = getArmorConfig().getDouble("damageBoost");
        int maxBonus = getArmorConfig().getInt("digitMax");
        for (ItemStack armorStack : armor)
        {
            if (armorStack != null && armorStack.getItemMeta() != null)
            {
                if (isArmorPiece(armorStack))
                {
                    int kills = VentureItemBuilder.getUniqueItemDataKeyInt(armorStack, UniqueVentureItemDataKey.TOTAL_KILLS);

                    VentureItemBuilder.setUniqueItemDataKeyInt(armorStack, UniqueVentureItemDataKey.TOTAL_KILLS, kills + 1);

                    int killDigits = (int) (Math.log10(kills + 1) + 1);

                    double itemDamageBonus = Math.min(killDigits, maxBonus) * damageBoost;
                    VentureItemBuilder.setUniqueItemDataKeyDouble(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS, itemDamageBonus);

                    ItemRegistry.renewItem(armorStack, player);

                }
            }
        }
    }

    @AbilityHandler(abilityCastType = AbilityCastType.ATTACK_ENTITY)
    @Override
    public void onAttack(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player player)
        {
            double damage = event.getDamage();

            ItemStack[] armorStacks = player.getInventory().getArmorContents();
            double i = 0;
            for (ItemStack armorStack : armorStacks)
            {
                if (armorStack != null && armorStack.getItemMeta() != null)
                {
                    if (isArmorPiece(armorStack))
                    {
                        double damageBonus = VentureItemBuilder.getUniqueItemDataKeyDouble(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS);
                        i += damageBonus;
                    }
                }
            }
            double damageMult = 1 + (i * 0.01);
            //player.sendMessage("Damage multiplier: " + damageMult);
            event.setDamage(damage * damageMult);
        }
    }

    @Override
    protected String armorName() {
        return "Soul Fire Blaze";
    }

    @Override
    public VentureArmorMaterial ventureArmorMaterial() {
        return VentureArmorMaterial.SOUL_FIRE_BLAZE;
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
        return VentureItemRarity.Rarity.FABLED;
    }

    @Override
    public LinkedList<String> createLore(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        double auraDamage = getArmorConfig().getDouble("auraBaseDamage");
        double auraRadius = getArmorConfig().getDouble("auraRadius");
        double manaCost = getArmorConfig().getDouble("manaCost");
        String abilityName1 = getArmorConfig().getString("abilityName1");
        String abilityName2 = getArmorConfig().getString("abilityName2");
        double damageBoost = getArmorConfig().getDouble("damageBoost");
        int digitMax = getArmorConfig().getInt("digitMax");

        LinkedList<String> lore = super.createLore(armorPieceTypes, stars);
        lore.add(ChatColor.GOLD + "Full Set Bonus: " + abilityName1 + ChatColor.YELLOW + ChatColor.BOLD + " Hold Sneak");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "While sneaking, set mobs within a " + ChatColor.GOLD + auraRadius + ChatColor.GRAY + " block");
        lore.add(ChatColor.GRAY + "radius ablaze and deal " + ChatColor.AQUA + auraDamage + ChatColor.GRAY + " damage/second");
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost + ChatColor.DARK_GRAY + " per second");
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Piece Bonus: " + abilityName2);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Kill mobs to absorb their strength");
        lore.add(ChatColor.GRAY + "and deal +" + ChatColor.RED + damageBoost + "%" + ChatColor.GRAY + " more damage per");
        lore.add(ChatColor.GRAY + "digit on the counter " + ChatColor.DARK_GRAY + "(max: " + digitMax + ")");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Total kills: " + ChatColor.YELLOW + ChatColor.MAGIC + "X");
        lore.add(ChatColor.GRAY + "Current damage bonus: " + ChatColor.RED + ChatColor.MAGIC + "X");
        lore.add(" ");
        lore.addLast(ItemStringBuilder.addItemStringRarity(rarity(), armorPieceTypes));

        // Full Set Bonus: Blazing Aura Hold Sneak
        //
        // While sneaking, set mobs within a 5 block
        // radius ablaze and deal 12 damage/second
        // Mana Cost: 45 per second
        //
        // Piece Bonus: Soul Stealer
        //
        // Kill mobs to absorb their strength
        // and deal +1.25% more damage per
        // digit on the counter (max: 6)
        //
        // Total kills: X
        // Current damage bonus: X
        //

        return lore;
    }


    public static List<String> itemSpecificLore(VentureItemBuilder ventureItemBuilder, List<String> lore)
    {
        int kills = ventureItemBuilder.getUniqueItemDataKeyInt(UniqueVentureItemDataKey.TOTAL_KILLS);
        double damageBonus = ventureItemBuilder.getUniqueItemDataKeyDouble(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS);

        for (int i = 0; i < lore.size() - 1; i++)
        {
            if (lore.get(i).contains("Total kills"))
            {
                lore.set(i, ChatColor.GRAY + "Total kills: " + ChatColor.YELLOW + kills);
            }
            if (lore.get(i).contains("Current damage bonus:"))
            {
                lore.set(i, ChatColor.GRAY + "Current damage bonus: " + ChatColor.RED + damageBonus);
            }
        }
        return lore;
    }

    @Override
    public VentureItemBuilder createArmorSet(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        return super.createArmorSet(armorPieceTypes, stars)
                .setUniqueItemDataKeyInt(UniqueVentureItemDataKey.TOTAL_KILLS, 0)
                .setUniqueItemDataKeyDouble(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS, 0.0);
    }
}

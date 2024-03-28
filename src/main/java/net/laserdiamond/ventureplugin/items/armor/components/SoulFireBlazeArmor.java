package net.laserdiamond.ventureplugin.items.armor.components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCastType;
import net.laserdiamond.ventureplugin.events.abilities.AbilityCasting;
import net.laserdiamond.ventureplugin.events.abilities.AbilityHandler;
import net.laserdiamond.ventureplugin.events.damage.PlayerMagicDamageEvent;
import net.laserdiamond.ventureplugin.events.mana.PlayerSpellCastEvent;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.util.File.ArmorConfig;
import net.laserdiamond.ventureplugin.util.ItemRegistry;
import net.laserdiamond.ventureplugin.util.UniqueVentureItemDataKey;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

// TODO: May want to change Soul Stealer to deal bonus damage based on kills (store on armor piece)
public final class SoulFireBlazeArmor extends VentureArmorSet implements AbilityCasting.RunnableAbility, AbilityCasting.onKillAbility, AbilityCasting.attackAbility {

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

        PlayerInventory playerInv = player.getInventory();

        ItemStack[] armor = playerInv.getArmorContents();
        for (ItemStack armorStack : armor)
        {
            if (armorStack != null)
            {
                ItemMeta armorMeta = armorStack.getItemMeta();
                if (isArmorPiece(armorStack) && armorMeta != null)
                {
                    double damageBonus = config().getDouble("damageBoostPerPrestige");
                    List<Integer> prestigeKillReq = config().getIntList("killsToPrestige");
                    int kills = ItemForger.getUniqueItemDataKeyInt(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_TOTAL_KILLS);
                    int prestige = ItemForger.getUniqueItemDataKeyInt(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_PRESTIGE);
                    int maxPrestige = prestigeKillReq.size() - 1;

                    int killsToPrestige = ItemForger.getUniqueItemDataKeyInt(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_KILLS_TO_NEXT_PRESTIGE);
                    double itemDamageBonus = ItemForger.getUniqueItemDataKeyDouble(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS);

                    if (kills + 1 >= killsToPrestige) // Item is prestigable and is not at the max prestige yet
                    {
                        String itemName = armorMeta.getDisplayName();
                        int newPrestige = prestige + 1;
                        double newDamageBonus = itemDamageBonus + damageBonus;
                        String message = ChatColor.GREEN + "Your " + itemName + ChatColor.GREEN + " increased its damage bonus from " + ChatColor.RED + itemDamageBonus + "%" + ChatColor.GREEN + " to " + ChatColor.RED + newDamageBonus + "%";
                        if (prestige != maxPrestige)
                        {
                            int newKillCount = killsToPrestige + prestigeKillReq.get(newPrestige);
                            ItemForger.setUniqueItemDataKeyInt(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_PRESTIGE, newPrestige);
                            ItemForger.setUniqueItemDataKeyDouble(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS, newDamageBonus);
                            ItemForger.setUniqueItemDataKeyInt(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_KILLS_TO_NEXT_PRESTIGE, newKillCount);
                            player.sendMessage(message);

                        } else {
                            if (kills + 1 == killsToPrestige) // Item has reached the max prestige
                            {
                                ItemForger.setUniqueItemDataKeyDouble(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS, newDamageBonus);
                                player.sendMessage(message);
                                player.sendMessage(ChatColor.GOLD + "Your " + itemName + ChatColor.GOLD + " has reached the highest prestige attainable!");
                            }
                        }

                    }
                    ItemForger.setUniqueItemDataKeyInt(armorStack, UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_TOTAL_KILLS, kills + 1);

                    ItemRegistry.renewItemNew(armorStack, player);
                }
            }


        }
    }

    @AbilityHandler(abilityCastType = AbilityCastType.ATTACK_ENTITY)
    @Override
    public void onAttack(Player player, double damage, LivingEntity hitEntity) {

    }

    @Override
    public ArmorConfig config() {
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
        lore.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + manaCost + ChatColor.DARK_GRAY + " per second");
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Piece Bonus: " + abilityName2);
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Kill mobs to absorb their strength");
        lore.add(ChatColor.GRAY + "and deal bonus damage");
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Kills to next prestige: " + ChatColor.YELLOW + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.WHITE + "/" + ChatColor.RED + ChatColor.MAGIC + "X");
        lore.add(ChatColor.GRAY + "Current damage bonus: +" + ChatColor.RED + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.RED + "%");
        /*
        lore.add(ChatColor.GRAY + "Upon killing a mob/player, instantly");
        lore.add(ChatColor.GRAY + "regain " + ChatColor.RED + lifeStealAmt + ChatColor.GRAY + "% of your max " + ChatColor.RED + "❤");

         */
        lore.add(" ");

        return lore;
    }

    // Full Set Bonus: Blazing Aura Hold Sneak
    //
    // While sneaking, set mobs within a 5 block
    // radius ablaze and deal 12 damage/second
    // Mana Cost: 45 per second
    //
    // Piece Bonus: Soul Stealer
    //
    // Kill mobs to absorb their strength
    // and deal bonus damage
    //
    // Kills to next prestige: X/X
    // Current damage bonus: X
    //

    // TODO: May want to use this type of method to get player-defined lore
    public static List<String> itemSpecificLore(ItemForger itemForger, List<String> lore)
    {
        int kills = itemForger.getUniqueItemDataKeyInt(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_TOTAL_KILLS);
        int killsToNextPrestige = itemForger.getUniqueItemDataKeyInt(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_KILLS_TO_NEXT_PRESTIGE);
        double damageBonus = itemForger.getUniqueItemDataKeyDouble(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS);
        int prestige = itemForger.getUniqueItemDataKeyInt(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_PRESTIGE);

        for (int i = 0; i < lore.size() - 1; i++)
        {
            if (lore.get(i).contains("Kills to next prestige"))
            {
                if (prestige != 14)
                {
                    lore.set(i, ChatColor.GRAY + "Kills to next prestige: " + ChatColor.YELLOW + kills + ChatColor.WHITE + "/" + ChatColor.RED + killsToNextPrestige);
                } else
                {
                    lore.set(i, ChatColor.GRAY + "Kills to next prestige: " + ChatColor.GOLD + kills + ChatColor.WHITE + "/" + ChatColor.GOLD + killsToNextPrestige + ChatColor.BOLD + " MAXED!");
                }

            }
            if (lore.get(i).contains("Current damage bonus"))
            {
                lore.set(i, ChatColor.GRAY + "Current damage bonus: +" + ChatColor.RED + damageBonus + "%");
            }
        }
        return lore;
    }

    @Override
    public ItemForger createArmorSet(@NotNull ArmorPieceTypes armorPieceTypes, int stars) {

        List<Integer> killsToPrestige = config().getIntList("killsToPrestige");
        Integer baseKillCountReq = killsToPrestige.get(0);

        return super.createArmorSet(armorPieceTypes, stars)
                .setUniqueItemDataKeyInt(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_TOTAL_KILLS, 0)
                .setUniqueItemDataKeyInt(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_KILLS_TO_NEXT_PRESTIGE, baseKillCountReq)
                .setUniqueItemDataKeyDouble(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_DAMAGE_BONUS, 0.0)
                .setUniqueItemDataKeyInt(UniqueVentureItemDataKey.SOUL_FIRE_BLAZE_PRESTIGE, 0);
    }
}
